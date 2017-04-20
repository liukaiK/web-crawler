package com.esd.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esd.config.BaseConfig;
import com.esd.config.NodeConfig;
import com.esd.config.PageConfig;
import com.esd.download.EsdDownLoadHtml;
import com.esd.parser.Parser;
import com.esd.stuff.TemplateStuff;
import com.esd.util.Util;

@Controller
@RequestMapping("/admin/test")
public class TestConfigController {

	private static Logger log = Logger.getLogger(TestConfigController.class);
	

	/**
	 * 抓取预览方法
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/view")
	@ResponseBody
	public Map<String, Object> view(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String url = request.getParameter("url");
		String templateName = request.getParameter("template");
		if (Util.isOutUrl(url)) {// 网址为外部链接
			Document templeSource = Util.downLoadTemple(BaseConfig.TEMPLATE_ROOT + File.separator + "error.html");
			templeSource.select("#error").attr("href", url);
			try {
				Util.createNewFile(templeSource.html(), BaseConfig.TEST_ROOT + File.separator + "view.html");
			} catch (IOException e) {
				e.printStackTrace();
			}
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
				Document templateDoc = ts.templateStuff(pageConfig);
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
