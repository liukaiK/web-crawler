package com.esd.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;

import com.esd.config.BaseConfig;
import com.esd.config.PageConfig;
import com.esd.download.EsdDownLoadHtml;
import com.esd.parser.Parser;
import com.esd.stuff.TemplateStuff;
import com.esd.util.Md5;
import com.esd.util.Util;

public class CatDao {

	private static Logger log = Logger.getLogger(CatDao.class);
	private Map<String, PageConfig> pageConfigMap = new HashMap<String, PageConfig>();
	private Map<String, PageConfig> wildcardMap = new HashMap<String, PageConfig>();

	/**
	 * 单页采集与组合网页
	 * 
	 * @param pageConfig
	 * @param linkHref
	 * @throws IOException
	 */
	public String singlCat(PageConfig pageConfig, Document document) {
		if (document == null) {
			log.error("下载失败");
			return null;// 下载失败
		}
		Parser hp = new Parser();// 创建解析器
		pageConfig = hp.ParserNode(document, pageConfig);// 解析分解页面
		TemplateStuff ts = new TemplateStuff();// 创建模版填充器
		Document doc = null;
		try {
			doc = ts.templateStuff(pageConfig);
		} catch (IOException e) {
			e.printStackTrace();
			log.debug("组装失败");
			return null;// 组装时失败
		}// 填充后返回代码
		String mName = interceptDir(pageConfig.getUrl());
		String path = BaseConfig.HTML_ROOT + File.separator + mName;
		try {
			Util.createNewFile(doc.html(), path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		File f = new File(path);
		if (!f.isFile()) {
			log.error(pageConfig.getUrl() + "-------生成失败");
			return null;// 未生成
		}
		return mName;
	}

	/**
	 * 采集单页并组合网页
	 * 
	 * @param pageConfig
	 * @param linkHref
	 * @throws IOException
	 */
	public boolean singlCat(PageConfig pageConfig, String url) {
		long l = System.currentTimeMillis();
		pageConfig.setUrl(url);
		EsdDownLoadHtml down = new EsdDownLoadHtml();// 下载
		Document htmlSource = down.downloadHtml(pageConfig);// 下载源代码
		if (htmlSource == null) {
			return false;
		}
		Parser hp = new Parser();// 创建解析器
		pageConfig = hp.ParserNode(htmlSource, pageConfig);// 解析分解页面
		TemplateStuff ts = new TemplateStuff();// 创建模版填充器
		Document doc = null;
		try {
			doc = ts.templateStuff(pageConfig);
		} catch (IOException e) {
			e.printStackTrace();
		}// 填充后返回代码
		String mName = interceptDir(url);
		String path = BaseConfig.HTML_ROOT + File.separator + mName;
		try {
			Util.createNewFile(doc.html(), path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.debug(url + "===[" + (System.currentTimeMillis() - l) + "]" + "===template[" + pageConfig.getTemplate() + ":" + mName + "]===rule[" + pageConfig.getDb() + ":" + pageConfig.getRule() + "]");
		return true;
	}

	
	/**
	 * 处理截取生成文件的路径
	 * 
	 * @param url
	 * @return
	 */
	public static String interceptDir(String url) {
		for (int i = 0; i < BaseConfig.filterSuffix.length; i++) {
			if (url.endsWith(BaseConfig.filterSuffix[i])) {
				return url;
			}
		}
		Md5 md5 = new Md5();
		String m = md5.getMd5(new StringBuffer(url));
		return m + ".html";
	}

	/**
	 * 收集所有pageconfig
	 */
	public void collectPageConfig() {
		// 收集所有采集规则PageConfig
		File file = new File(BaseConfig.PG_ROOT);
		List<PageConfig> list = new ArrayList<PageConfig>();
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File f : files) {
				ObjectInputStream oin = null;
				try {
					oin = new ObjectInputStream(new FileInputStream(f));
					PageConfig config = (PageConfig) oin.readObject();
					config.setDb(f.getName());
					list.add(config);
					oin.close();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						if (oin != null) {
							oin.close();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		// 处理成采集链接映射
		for (Iterator<PageConfig> iterator = list.iterator(); iterator.hasNext();) {
			PageConfig pageConfig = (PageConfig) iterator.next();
			if (pageConfig == null) {
				continue;
			}
			List<String> urls = pageConfig.getUrls();
			for (String url : urls) {
				if (url.indexOf("*") != -1) {
					wildcardMap.put(url, pageConfig);
				} else {
					pageConfigMap.put(url, pageConfig);
				}
			}
		}
	}

	/**
	 * 处理链接如果是站内就返回链接，并处理掉#号,非站内链接返回null。
	 * 
	 * @param url
	 * @return
	 */
	private String procUlr(String url) {
		for (String str : BaseConfig.INDEX_URL) {
			if (url.startsWith(str)) {
				if (url.endsWith("#")) {
					url = url.substring(0, url.length() - 1);
				}
				url = url.replace("./", "");
			}
		}
		return url;
	}

	/**
	 * 过滤后缀忽略掉相关链接
	 * 
	 * @param url
	 * @return
	 */
	public String filterSuffix(String realUrl) {
		String url = procUlr(realUrl);
		if (url == null) {
			return null;
		} else if (url.isEmpty()) {
			return null;
		} else if (url.equals("")) {
			return null;
		} else if (url.equals("#")) {
			return null;
		}
		for (String s : BaseConfig.filterSuffix) {
			if (url.endsWith(s)) {
				return null;
			}
		}
		return url;
	}

	/**
	 * 查找url相对应的pageConfig
	 * 
	 * @param url
	 * @return
	 */
	public PageConfig findPageConfig(String url) {
		if (url != null) {
			int last = url.lastIndexOf("?");
			if (last != -1) {
				url = url.substring(0, last);
			}
		}
		PageConfig pageConfig = pageConfigMap.get(url);
		if (pageConfig != null) {
			pageConfig.setRule(url);
			return pageConfig;
		} else {
			// 通配符查找
			for (Iterator<Map.Entry<String, PageConfig>> iterator = wildcardMap.entrySet().iterator(); iterator.hasNext();) {
				Map.Entry<String, PageConfig> it = (Map.Entry<String, PageConfig>) iterator.next();
				String key = it.getKey();
				String regular = key.replace("*", "\\w*");
				Pattern p = Pattern.compile(regular);
				Matcher m = p.matcher(url);
				if (m.matches()) {
					it.getValue().setRule(key);
					return it.getValue();
				}
			}
		}
		return null;
	}

}
