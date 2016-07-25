package com.esd.bs.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esd.common.MongoDBUtil;

/**
 * 网站浏览人数
 * 
 * @author liukai
 * 
 */
@Controller
public class PageViewController {
	
	@Resource
	private MongoDBUtil mongoDBUtil;

	@RequestMapping("/addPageView")
	@ResponseBody
	public void getProgressCount(HttpServletRequest request) {
		mongoDBUtil.addPageView();
	}
	
	@RequestMapping("/getPageView")
	@ResponseBody
	public Map<String, Object> getPageView(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String num = mongoDBUtil.getPageView();
		map.put("message", num);
		return map;
	}

}
