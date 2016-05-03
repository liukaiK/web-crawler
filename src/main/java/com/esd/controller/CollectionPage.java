package com.esd.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.esd.common.CatDao;
import com.esd.common.MongoDBUtil;
import com.esd.config.BaseConfig;
import com.esd.config.PageConfig;
import com.esd.download.EsdDownLoadHtml;
import com.esd.entity.Downloads;
import com.esd.entity.Urls;
import com.esd.util.Md5;
import com.esd.util.Util;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;


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
			collectBszx();// 采集办事咨询
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
		Elements links = htmlSource.select("a[href],area[href]");
		String title = htmlSource.select(".title21").text().trim();
		if (title == null || title.isEmpty()) {
			title = "::::北京市工商局:::::";
		}
		for (Element e : links) {
			String href = e.attr("abs:href").trim();
			if (href == null) {
				continue;
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
			String nName = null;
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
					+ ":" + nName + "]===rule[" + pageConfig.getDb() + ":" + pageConfig.getRule() + "]");
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
//		mongoDBUtil = null;
//		dao = null;
		System.gc();
	}



	public void collectBszx() {
		String chrId;
		String clickChrid;
		String flag = "0";
		String bszxUrl = "http://www.baic.gov.cn:7021/zmhdLogin/questionQueryAction!gsqw.dhtml?search=%25E5%258A%259E&clear=true&clickChrid=de9bd39c545b43c5b38d4a55086d63ed&timeStamp=1456894244632";
		WebClient webClient = new WebClient();
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setCssEnabled(false);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		HtmlPage page1 = (HtmlPage) webClient.getCurrentWindow().getEnclosedPage();
		try {
			page1 = webClient.getPage(bszxUrl);
			HtmlForm form1 = (HtmlForm) page1.getFormByName("form1");
			HtmlElement table = form1.getElementsByTagName("table").get(0);
			DomNodeList<HtmlElement> DomNodeList = table.getElementsByTagName("tr");
			for (int i = 0; i < DomNodeList.size() - 1; i++) {
				HtmlElement td = DomNodeList.get(i).getElementsByTagName("td").get(3);
				DomNodeList<HtmlElement> links = td.getElementsByTagName("a");
				for (HtmlElement link : links) {
					String function = link.getAttribute("onclick");
					String[] str = function.split(",");
					chrId = getChrId(str[0]);
					clickChrid = getClickChrid(str[1]);
					String url = "http://www.baic.gov.cn:7021" + "/zmhdLogin/questionQueryAction!openView.dhtml?chrId="
							+ chrId + "&flag=" + flag + "&clickChrid=" + clickChrid;
					logger.debug(url);
					mongoDBUtil.downloadsInsert(url);
				}
			}
			HtmlPage hp = null;
			Integer pagesCount = Integer.valueOf(page1.getElementById("pagescount").getAttribute("value"));
			HtmlForm form = form1;
			int size = form.getElementsByTagName("a").size();
			HtmlAnchor nextPage = (HtmlAnchor) form.getElementsByTagName("a").get(size - 2);
			for (int i = 2; i <= pagesCount; i++) {
				hp = (HtmlPage) nextPage.click();
				form = (HtmlForm) hp.getFormByName("form1");
				size = form.getElementsByTagName("a").size();
				nextPage = (HtmlAnchor) hp.getFormByName("form1").getElementsByTagName("a").get(size - 2);
				HtmlElement table1 = form.getElementsByTagName("table").get(0);
				DomNodeList<HtmlElement> DomNodeList1 = table1.getElementsByTagName("tr");
				for (int j = 0; j < DomNodeList1.size() - 1; j++) {
					HtmlElement td = DomNodeList1.get(j).getElementsByTagName("td").get(3);
					DomNodeList<HtmlElement> links = td.getElementsByTagName("a");
					for (HtmlElement link : links) {
						String function = link.getAttribute("onclick");
						String[] str = function.split(",");
						chrId = getChrId(str[0]);
						clickChrid = getClickChrid(str[1]);
						String url = "http://www.baic.gov.cn:7021"
								+ "/zmhdLogin/questionQueryAction!openView.dhtml?chrId=" + chrId + "&flag=" + flag
								+ "&clickChrid=" + clickChrid;
						logger.debug(url);
						mongoDBUtil.downloadsInsert(url);
					}
				}

			}
		} catch (FailingHttpStatusCodeException e1) {
			e1.printStackTrace();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public static String getChrId(String str) {
		String chriId = str.substring(str.indexOf("'") + 1, str.lastIndexOf("'"));
		return chriId;

	}

	public static String getClickChrid(String str) {
		String clickChrid = str.substring(str.indexOf("'") + 1, str.lastIndexOf("'"));
		return clickChrid;

	}

	public void setCollectStatic(boolean collectStatic) {
		this.collectStatic = collectStatic;
	}


}
