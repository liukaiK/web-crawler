package com.esd.common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.esd.collection.DbFile;
import com.esd.collection.DbPgFile;
import com.esd.collection.Site;
import com.esd.config.BaseConfig;
import com.esd.config.NodeConfig;
import com.esd.config.PageConfig;
import com.esd.controller.site.SiteController;
import com.esd.download.EsdDownLoadHtml;
import com.esd.parser.Parser;
import com.esd.stuff.TemplateStuff;
import com.esd.util.SerializeUtil;
import com.esd.util.SpringContextUtil;
import com.esd.util.Util;

@Repository
public class CatDao {
	
	@Resource
	private MongoDBUtil mongoDBUtil;
	
	private static Logger log = Logger.getLogger(CatDao.class);
	private Map<String, PageConfig> pageConfigMap = new HashMap<String, PageConfig>();
	private Map<String, PageConfig> wildcardMap = new HashMap<String, PageConfig>();
	private static List<PageConfig> treeDirList = new ArrayList<PageConfig>();
	
	/**
	 * 单页采集与组合网页
	 * 
	 * @param pageConfig
	 * @param linkHref
	 * @throws IOException
	 */
	public String singlCat(String siteId,PageConfig pageConfig, Document document,boolean ctrl) {
		MongoDBUtil mongoDBUtil = (MongoDBUtil)SpringContextUtil.getBean("mongoDBUtil");
		if(!ctrl){
			mongoDBUtil.insertFile(null,null,null,null,ctrl);
			return "end";
		}
		if (document == null) {
			log.error("下载失败");
			return null;// 下载失败
		}
		Parser hp = new Parser();// 创建解析器
		pageConfig = hp.ParserNode(document, pageConfig);// 解析分解页面
		TemplateStuff ts = new TemplateStuff();// 创建模版填充器
		Document doc = null;
		try {
			//2016-11-7 cx
			doc = ts.templateStuff(pageConfig,siteId);
		} catch (IOException e) {
			e.printStackTrace();
			log.debug("组装失败");
			return null;// 组装时失败
		}// 填充后返回代码
		String mName = Util.interceptUrl(pageConfig.getUrl());
		//String path = BaseConfig.HTML_ROOT + File.separator + mName;
		//从session获得站点名，siteName/html/mName
		String filedir = SiteController.siteId + "/html/"+mName;
		String content = doc.html();
		//20161102-cx 首页源码插入站点表
		
		Criteria criatira = new Criteria();
		criatira.andOperator(Criteria.where("id").is(siteId));
		Site site = mongoDBUtil.findOneByCollectionName("sites", criatira, Site.class);
		String domain = site.getDomainName();
		if(pageConfig.getUrl().equals(domain)){
			mongoDBUtil.upsert(siteId,content.getBytes(), Site.class, "sites");
			return mName;
		}
		mongoDBUtil.insertFile(mName,content,filedir,"html",true);
//		try {
//			Util.createNewFile(doc.html(), path);//不要了，doc.html()文件内容
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		File f = new File(path);
//		if (!f.isFile()) {
//			log.error(pageConfig.getUrl() + "-------生成失败");
//			return null;// 未生成
//		}
		return mName;
	}

	/**
	 * 采集单页并组合网页
	 * 
	 * @param pageConfig
	 * @param linkHref
	 * @throws IOException
	 */
	public boolean singlCat(PageConfig pageConfig, String url,String siteId) {
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
			//2016-11-7 cx
			Criteria criatira = new Criteria();
			criatira.andOperator(Criteria.where("fileName").is(pageConfig.getTemplate()));
			DbFile df = mongoDBUtil.findOneByCollectionName(siteId+"_template", criatira, DbFile.class);
			String str = new String(df.getFileByte(),"utf-8");
			doc = ts.templateStuff(pageConfig,str);
		} catch (IOException e) {
			e.printStackTrace();
		}// 填充后返回代码
		String mName = Util.interceptUrl(url);
		String path = BaseConfig.HTML_ROOT + File.separator + mName;
		// cx-20160926 存入mongodb
		
		mongoDBUtil.insertFile(mName, htmlSource.html().getBytes(), path, "html");
//		try {
//			Util.createNewFile(doc.html(), path);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		log.debug(url + "===[" + (System.currentTimeMillis() - l) + "]" + "===template[" + pageConfig.getTemplate() + ":" + mName + "]===rule[" + pageConfig.getDb() + ":" + pageConfig.getRule() + "]");
		return true;
	}

	/**
	 * 收集所有pageconfig
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public void collectPageConfig(String siteId)  throws NullPointerException{
		//cx-20160926 从mongodb中find数据
		MongoDBUtil mongoDBUtil = (MongoDBUtil)SpringContextUtil.getBean("mongoDBUtil");
		List<DbPgFile> listPg = mongoDBUtil.findAll(DbPgFile.class,siteId+"_pg");
		List<PageConfig> list = new ArrayList<PageConfig>();
		if(listPg != null){
			
			for (Iterator<DbPgFile> iterator = listPg.iterator(); iterator.hasNext();) {
				DbPgFile df = (DbPgFile) iterator.next();
				PageConfig config = df.getPageConfig();
				config.setDb(df.getFileName());
				list.add(config);	
			}
			
		}
		/****************************************************************/
		// 收集所有采集规则PageConfig
//		File file = new File(BaseConfig.PG_ROOT);
//		List<PageConfig> list = new ArrayList<PageConfig>();
//		if (file.isDirectory()) {
//			File[] files = file.listFiles();
//			for (File f : files) {
//				ObjectInputStream oin = null;
//				try {
//					oin = new ObjectInputStream(new FileInputStream(f));
//					PageConfig config = (PageConfig) oin.readObject();
//					config.setDb(f.getName());
//					list.add(config);
//					oin.close();
//				} catch (Exception e) {
//					e.printStackTrace();
//				} finally {
//					try {
//						if (oin != null) {
//							oin.close();
//						}
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		}
		// 处理成采集链接映射
		for (Iterator<PageConfig> iterator = list.iterator(); iterator.hasNext();) {
			PageConfig pageConfig = (PageConfig) iterator.next();
			if (pageConfig == null) {
				continue;
			}
			List<NodeConfig> l = pageConfig.getList();
			if (l != null) {
				if (l.size() == 1) {
					if (l.get(0).getDes() != null) {
						String s = l.get(0).getDes();
						if (s.equals("@tree")) {
							treeDirList.add(pageConfig);
							continue;
						}
					}

				}
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
