package com.esd.controller.file;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esd.config.BaseConfig;
import com.esd.util.Util;

@Controller
@RequestMapping("/admin")
public class CssController {

//	private static Logger logger = Logger.getLogger(CssController.class);
	
	@RequestMapping("/loadCssList")
	@ResponseBody
	public Map<String, Object> loadCssList(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		File js_file = new File(BaseConfig.CSS_ROOT);
		if (js_file.isDirectory()) {
			String[] files = js_file.list();
			map.put("list", files);
		}
		return map;
	}
	
	
	@RequestMapping(value = "/saveCss", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveCss(String cssContent, String cssName, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Util.createNewFile(cssContent,BaseConfig.CSS_ROOT + File.separator + cssName);
			map.put("notice", true);
			map.put("message", cssName + "样式文件保存成功!");
		} catch (IOException e) {
			map.put("notice", false);
			map.put("message", cssName + "样式文件保存失败!");
		}
		return map;
	}	
	
	
	@RequestMapping(value = "/deleteCss", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCss(String cssName, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		File file = new File(BaseConfig.CSS_ROOT + File.separator + cssName);
		if (file.isFile()) {
			if (file.getAbsoluteFile().delete()) {
				map.put("notice", true);
				map.put("message", cssName + "样式文件删除成功!");
			} else {
				map.put("notice", false);
				map.put("message", cssName + "样式文件删除失败!");
			}
		} else {
			map.put("notice", false);
			map.put("message", cssName + "样式文件删除失败!");
		}
		return map;
	}
	
	
	
}
