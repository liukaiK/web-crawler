package com.esd.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esd.common.MongoDBUtil;
import com.esd.core.CollectionPage;

@Controller
@RequestMapping("/admin/coreAll")
public class PageAllConfigController {

	private static Logger log = Logger.getLogger(PageAllConfigController.class);

	@Resource
	private MongoDBUtil mongoDBUtil;
	@Resource
	private CollectionPage collectionPage;

	@RequestMapping("/catingAll")
	@ResponseBody
	public Map<String, Object> catingAll(String url, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		collectionPage.init(url);
		collectionPage.start();
		map.put("notice", true);
		return map;
	}

	@RequestMapping("/cancelCating")
	@ResponseBody
	public Map<String, Object> cancelCating(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("notice", true);
		collectionPage.setCollectStatic(false);
		log.info("Collection cancel");
		return map;
	}

	@RequestMapping("/getdbCount")
	@ResponseBody
	public Map<String, Object> getDownload(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		Long downCount = mongoDBUtil.getDownloadsCount();
		Long urlsCount = mongoDBUtil.getUrlsCount();
//		Long sum = downCount + urlsCount;
		if (urlsCount == 0) {
			map.put("notice", true);
			map.put("message", 100);
		} else {
			map.put("notice", true);
			Double a1 = Double.valueOf(urlsCount) / Double.valueOf((downCount + urlsCount)) * 100;
			map.put("message", a1.intValue());
		}
		return map;
	}
}
