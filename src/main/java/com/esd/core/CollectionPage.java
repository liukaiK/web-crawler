package com.esd.core;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.esd.collection.Downloads;
import com.esd.collection.Urls;
import com.esd.common.CatDao;
import com.esd.common.MongoDBUtil;
import com.esd.config.BaseConfig;
import com.esd.config.PageConfig;
import com.esd.download.EsdDownLoadHtml;
import com.esd.util.Md5;
import com.esd.util.Util;


@Component
public class CollectionPage {

	private static Logger logger = Logger.getLogger(CollectionPage.class);
	@Resource
	private MongoDBUtil mongoDBUtil;
	private CatDao dao = new CatDao();
	private boolean collectStatic = true;
	private boolean ctrl = true;
	
	private Thread thread = new A();
	
	public void start() {
		thread = new A();
		thread.start();
	}

	private class A extends Thread {

		@Override
		public void run() {
			dao.createTree();
			while (collectStatic && ctrl) {
				ctrl = collect();
			}
			destroySource();// 释放资源
			logger.info("采集线程结束！！！！！！！！！！！！！！！！！");
		}

	}

	public CollectionPage() {

	}

	public void init(String domain) {
		collectStatic = true;
		ctrl = true;
		mongoDBUtil.dropTable();
		mongoDBUtil.downloadsInsert(domain);// 插入主页
		for (int i = 0; i < BaseConfig.str.length; i++) {
			mongoDBUtil.downloadsInsert(BaseConfig.str[i]);
		}
		dao.collectPageConfig();
	}

	/**
	 * 前置下载
	 * @return
	 */
	public boolean collect() {
		Long l = System.currentTimeMillis();
		Downloads bson = mongoDBUtil.downloadsFindAndDeleteOne();
		if (bson == null) {
			return false;
		}
		String url = bson.getUrl();
		// 通过数局库获取url
		if (url == null) {
			return false;
		}
		if (Util.isOutUrl(url)) {
			Document doc = Util.downLoadTemple(BaseConfig.TEMPLATE_ROOT + File.separator + "error.html");
			doc.select("#error").attr("href", url);
			String mName = interceptDir(url);
			String path = BaseConfig.HTML_ROOT + File.separator + mName;
			try {
				Util.createNewFile(doc.html(), path);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return true;
		}
		PageConfig pageConfig = dao.findPageConfig(url);
		Document htmlSource = null;
		if (pageConfig != null) {
			EsdDownLoadHtml down = new EsdDownLoadHtml();// 下载
			pageConfig.setUrl(url);
			htmlSource = down.downloadHtml(pageConfig);// 下载源代码
		} else {
			try {
				Connection jsoup = Jsoup.connect(url);
				htmlSource = jsoup.get();
			} catch (IOException e) {
				logger.error("download error: " + url);
			}
		}
		if (htmlSource == null) {
			return true;
		}
		Elements links = htmlSource.select("a[href],area[href],iframe[src]");
		String title = htmlSource.select("title").text().trim();
		for (Element e : links) {
			String href = e.attr("abs:href").trim();
			if (href.equals("")) {
				href = e.attr("abs:src").trim();
				if (href == null) {
					continue;
				}
			}
			// 过滤
			String s = dao.filterSuffix(href);
			// 保存url到数数库
			mongoDBUtil.downloadsInsert(s);

		}
		// 插入数据库
		Urls urlsCollection = new Urls();
		urlsCollection.setUrl(bson.getUrl());
		if (pageConfig != null && htmlSource != null) {
			try {
				dao.singlCat(pageConfig, htmlSource);
				urlsCollection.setState("1");// 已处理
			} catch (Exception e) {
				urlsCollection.setState("-1");// 已处理，发生错误
				logger.error("singlCat" + url);
				logger.error(e.getStackTrace());
			}
			// log.info(url +
			// "==============Processing time==================>[" +
			// (System.currentTimeMillis() - l) + "]");
			logger.debug(url + "===[" + (System.currentTimeMillis() - l) + "]" + "===template[" + pageConfig.getTemplate()
					+ "]===rule[" + pageConfig.getDb() + ":" + pageConfig.getRule() + "]");
		} else {
			logger.info(url);
			urlsCollection.setState("0");// 未处理
		}
		mongoDBUtil.urlsInsert(urlsCollection, title);// 插入
		return true;

	}

	/**
	 * 处理截取生成文件的路径
	 * @param url
	 * @return
	 */
	public static String interceptDir(String url) {
//		if (!url.startsWith(BaseConfig.index_url)) {
//			url = BaseConfig.RETURN_URL;
//		}
		if (url.endsWith("doc")) {
			return url;
		}
		Md5 md5 = new Md5();
		String m = md5.getMd5(new StringBuffer(url));
		return m + ".html";
	}

	/**
	 * 释放以知所有资源
	 */
	private void destroySource() {
		mongoDBUtil.dropTable();
		System.gc();
	}

	public void setCollectStatic(boolean collectStatic) {
		this.collectStatic = collectStatic;
	}


}
