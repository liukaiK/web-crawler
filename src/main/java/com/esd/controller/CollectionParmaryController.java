package com.esd.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esd.core.CollectionParmary;

@Controller
@RequestMapping("/admin/core")
public class CollectionParmaryController {

//	private static Logger logger = Logger.getLogger(CollectionParmaryController.class);
	
	@Resource
	private CollectionParmary collectionParmary;

	/**
	 * 单层采集
	 * 
	 * @param request
	 * @return
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	@RequestMapping("/cating")
	@ResponseBody
	public Map<String, Object> cating(String url, HttpServletRequest request) throws IOException, InterruptedException {
		Map<String, Object> map = new HashMap<String, Object>();
		collectionParmary.init(url);
		collectionParmary.start();
		map.put("notice", true);
		map.put("message", "一级采集开始!");
		return map;
	}

}
