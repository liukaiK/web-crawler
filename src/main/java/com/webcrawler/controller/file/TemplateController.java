package com.webcrawler.controller.file;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webcrawler.config.Configure;
import com.webcrawler.util.Util;

@Controller
@RequestMapping("/admin")
public class TemplateController {

	@RequestMapping("/loadTemplateList")
	@ResponseBody
	public Map<String, Object> loadtemplatelist(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		File file = new File(Configure.TEMPLATE_ROOT);
		if (file.isDirectory()) {
			String[] files = file.list();
			map.put("list", files);
		}

		return map;

	}
	
	@RequestMapping(value = "/saveTemplate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveTemplate(String templateContent, String templateName, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Util.createNewFile(templateContent, Configure.TEMPLATE_ROOT + File.separator + templateName);
			map.put("notice", true);
			map.put("message", templateName + "模板文件保存成功!");
		} catch (IOException e) {
			map.put("notice", false);
			map.put("message", templateName + "模板文件保存失败!");
		}
		return map;
	}
	
	@RequestMapping(value = "/deleteTemplate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteTemplate(String templateName, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		File file = new File(Configure.TEMPLATE_ROOT + File.separator + templateName);
		if (file.isFile()) {
			if (file.getAbsoluteFile().delete()) {
				map.put("notice", true);
				map.put("message", templateName + "模板文件删除成功!");
			} else {
				map.put("notice", false);
				map.put("message", templateName + "模板文件删除失败!");
			}
		} else {
			map.put("notice", false);
			map.put("message", templateName + "模板文件删除失败!");
		}
		return map;
	}
	
	
}
