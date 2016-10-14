package com.esd.controller.site;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.esd.collection.Site;
import com.esd.service.site.SiteService;

@Controller
@RequestMapping("/admin")
public class SiteController {

	@Autowired
	private SiteService siteService;
	
	

	@RequestMapping(value = "/site", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView site() {
		return new ModelAndView("site/siteList");
	}
	
	@RequestMapping(value = "/getSiteList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getSiteList() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Site> siteList = siteService.findAllSite(Site.class);
		map.put("rows", siteList);
		return map;
	}
	
	@RequestMapping(value = "/addSite", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addSite(String siteName, String domainName, String port) {
		Map<String, Object> map = new HashMap<String, Object>();
		siteService.addSite(siteName, domainName, port);
		map.put("notice", "站点添加成功!!!");
		return map;
	}
	
	@RequestMapping(value = "/removeSite", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> removeSite(String id){
		Map<String, Object> map = new HashMap<String, Object>();
		siteService.removeSite(id);
		map.put("notice", true);
		map.put("message", "删除成功!");
		return map;
	}
	
	
	@RequestMapping(value = "/updateSite", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateSite(String id, String siteName, String domainName, String port) {
		Map<String, Object> map = new HashMap<String, Object>();
		siteService.update(id, siteName, domainName, port);
		map.put("notice", true);
		map.put("message", "站点编辑成功");
		return map;
	}

	
	
	
//	@RequestMapping(value = "/getSiteById", method = RequestMethod.POST)
//	@ResponseBody
//	public Map<String, Object> getSiteById(String id) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		Site site = siteService.findOne(id);
//		System.out.println(id);
//		map.put("site", site);
//		return map;
//	}
}
