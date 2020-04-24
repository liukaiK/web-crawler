package com.webcrawler.common;

import com.esd.config.BaseConfig;
import com.esd.config.Configure;
import com.esd.config.PageConfig;
import com.webcrawler.parser.Parser;
import com.webcrawler.stuff.TemplateStuff;
import com.webcrawler.util.Util;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CatDao {
	
	private Map<String, PageConfig> pageConfigMap = new HashMap<String, PageConfig>();
	private Map<String, PageConfig> wildcardMap = new HashMap<String, PageConfig>();

	@Resource
	private TemplateStuff templateStuff;
	
	@Resource
	private Parser parser;
	
	/**
	 * 单页采集与组合网页
	 * 
	 * @param pageConfig
	 * @param linkHref
	 * @throws IOException
	 */
	public String singlCat(PageConfig pageConfig, Document htmlSource) throws IOException {
		pageConfig = parser.ParserNode(htmlSource, pageConfig);// 解析分解页面
		Document doc = templateStuff.templateStuff(pageConfig);
		String mName = Util.interceptDir(pageConfig.getUrl());
		String path = BaseConfig.htmlPath + File.separator + mName;
		Util.createNewFile(doc.html(), path);
		return mName;
	}

	/**
	 * 收集所有pageconfig
	 */
	public void collectPageConfig() {
		pageConfigMap.clear();
		wildcardMap.clear();
		// 收集所有采集规则PageConfig
		File file = new File(Configure.PG_ROOT);
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
	 * 查找url相对应的pageConfig
	 * 
	 * @param url
	 * @return
	 */
	public PageConfig findPageConfig(String url) {
		int last = url.lastIndexOf("?");
		if (last != -1) {
			url = url.substring(0, last);
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
		return pageConfig;
	}

}
