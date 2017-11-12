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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.crawler.common.MongoDBUtil;
import com.crawler.config.BaseConfig;
import com.crawler.config.NodeConfig;
import com.crawler.config.PageConfig;
import com.crawler.download.EsdDownLoadHtml;
import com.crawler.parser.Parser;
import com.crawler.stuff.TemplateStuff;
import com.crawler.util.Util;

@Controller
@RequestMapping("/admin/test")
public class TestConfigController {

	private static Logger log = Logger.getLogger(TestConfigController.class);
	@Resource
	private MongoDBUtil mongoDBUtil;

	/**
	 * 抓取预览方法
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/view")
	@ResponseBody
	public Map<String, Object> view(HttpServletRequest request,HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		String siteId = session.getAttribute("siteId").toString();
		String url = request.getParameter("url");
		String templateName = request.getParameter("template");
		if (Util.isOutUrl(url,siteId,mongoDBUtil)) {// 网址为外部链接
			Util.doWithOutUrl(url,siteId,mongoDBUtil);
		} else {
			String javaScriptEnabled = request.getParameter("javaScriptEnabled");
			String sleep = request.getParameter("sleep");
			String[] rules = request.getParameterValues("rules[]");
			PageConfig pageConfig = new PageConfig();
			pageConfig.setJavaScriptEnabled(Boolean.valueOf(javaScriptEnabled));
			pageConfig.setSleep(Long.valueOf(sleep));
			pageConfig.setUrl(url);
			for (String s : rules) {
				String[] rule = s.split("&");
				NodeConfig nc = new NodeConfig();
				nc.setDes(rule[0]);
				nc.setParent(rule[1]);
				nc.setTag(rule[2]);
				nc.setType(rule[3]);
				nc.setName(rule[4]);
				nc.setIndex(rule[5]);
				nc.setAnchorId(rule[6]);
				pageConfig.getList().add(nc);
			}
			EsdDownLoadHtml down = new EsdDownLoadHtml();
			Document htmlSource = down.downloadHtml(pageConfig);
			Parser hp = new Parser();
			pageConfig = hp.ParserNode(htmlSource, pageConfig);
			TemplateStuff ts = new TemplateStuff();
			pageConfig.setTemplate(templateName);
			try {
				Document templateDoc = ts.templateStuff(pageConfig,siteId,mongoDBUtil);
				Util.createNewFile(templateDoc.html(), BaseConfig.TEST_ROOT + File.separator + "view.html");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		log.debug("view finish");
		map.put("notice", true);
		return map;
	}
}
