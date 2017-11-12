package com.crawler.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.crawler.common.CatDao;
import com.crawler.common.MongoDBUtil;
import com.crawler.config.BaseConfig;
import com.crawler.config.PageConfig;
import com.crawler.download.EsdDownLoadHtml;
import com.crawler.util.Util;

@Controller
@RequestMapping("/admin/core")
public class PageConfigController {

	private static Logger logger = Logger.getLogger(PageConfigController.class);
	
	@Autowired
	private MongoDBUtil mongoDBUtil;
	
	private Map<String, Integer> urlMap = new HashMap<String, Integer>(125);
	private int progressCount = 1;
	private int remainCount;// 进度条剩余多少
	private boolean quitFlag = false;
	@Resource
	private CatDao catDao ; 

	/**
	 * 单层采集
	 * 
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/cating")
	@ResponseBody
	public Map<String, Object> cating(HttpServletRequest request,HttpSession session) throws IOException {
		String url = request.getParameter("url");
		Map<String, Object> map = new HashMap<String, Object>();
		urlMap.clear();
		String siteId = session.getAttribute(BaseConfig.SITEID).toString();
		if (Util.isOutUrl(url,siteId,mongoDBUtil)) {// 如果为外链接
			//Document templateSource = Util.loadTemplate(BaseConfig.TEMPLATE_ROOT + File.separator + "error.html");
			Document templateSource = Util.loadTemplate("error.html",siteId,2,mongoDBUtil);
			templateSource.select("#error").attr("href", url);
			String mName = Util.interceptUrl(url);
			//String path = BaseConfig.HTML_ROOT + File.separator + mName;
			String path = File.separator + "html" + File.separator + mName;
			 // cx-20160926 存入mongodb
			siteId = session.getAttribute("siteId").toString();  
			mongoDBUtil.insertFile(mName, templateSource.html().getBytes(), path, "html");
//			try {
//				Util.createNewFile(templateSource.html(), path);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			logger.debug("view finish");
			map.put("message", true);
			return map;
		}
		//System.out.println("url:"+url);
		catPage(url,siteId);
		logger.debug("单层采集完成");
		map.put("message", true);
		return map;
	}

	private void catPage(String url,String siteId) {
		quitFlag = false; // 开启采集状态
		catDao.collectPageConfig(siteId,mongoDBUtil);
		PageConfig pageConfig = catDao.findPageConfig(url);
		pageConfig.setUrl(url);
		EsdDownLoadHtml down = new EsdDownLoadHtml();// 下载
		Document htmlSource = down.downloadHtml(pageConfig);// 下载源代码
		if (htmlSource == null) {
			return;
		}
		Elements links = htmlSource.select("a[href]");
		progressCount = remainCount = links.size();
		catDao.singlCat(pageConfig, url ,siteId,mongoDBUtil);
		for (Element link : links) {
			remainCount = remainCount - 1;
			if (quitFlag == true) {// 开关 退出采集
				return;
			}
			String href = link.attr("abs:href").trim();
			href = catDao.filterSuffix(href);
			if (href == null) {
				continue;
			}
			if (Util.isOutUrl(href,siteId,mongoDBUtil)) {
				try {
					//htmlSource = Util.loadTemplate(BaseConfig.TEMPLATE_ROOT + File.separator + "error.html");
					htmlSource = Util.loadTemplate("error.html",siteId,2,mongoDBUtil);
					htmlSource.select("#error").attr("href", href);
					String mName = Util.interceptUrl(href);
					//String path = BaseConfig.HTML_ROOT + File.separator + mName;
					String path = File.separator + "html" + File.separator + mName;
					// cx-20160926 存入mongodb
					
					mongoDBUtil.insertFile(mName, htmlSource.html().getBytes(), path,"html");
//				try {
//					Util.createNewFile(htmlSource.html(), path);
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
				} catch (IOException e) {
					e.printStackTrace();
				}
				continue;
			}
			Integer i = urlMap.get(href);
			if (i != null) {
				continue;
			}
			urlMap.put(href, 1);
			pageConfig = catDao.findPageConfig(href);
			if (pageConfig != null) {
				catDao.singlCat(pageConfig, href ,siteId,mongoDBUtil);
			} else {
				logger.debug(href);
			}

		}
	}

	@RequestMapping("/getProgressCount")
	@ResponseBody
	public Map<String, Object> getProgressCount(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		Double a1 = (Double.valueOf(remainCount) / Double.valueOf(progressCount)) * 100;
		map.put("message", true);
		map.put("g", a1.intValue());
		return map;
	}
	


}
