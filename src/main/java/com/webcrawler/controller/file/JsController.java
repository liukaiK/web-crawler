package com.webcrawler.controller.file;

import com.esd.config.Configure;
import com.webcrawler.util.Util;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class JsController {

	@RequestMapping("/loadJsList")
	@ResponseBody
	public Map<String, Object> loadjslist(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		File js_file = new File(Configure.JS_ROOT);
		if (js_file.isDirectory()) {
			String[] files = js_file.list();
			map.put("list", files);
		}
		return map;
	}
	
	@RequestMapping(value = "/saveJs", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveJs(String jsContent, String jsName, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Util.createNewFile(jsContent,Configure.JS_ROOT + File.separator + jsName);
			map.put("notice", true);
			map.put("message", jsName + "脚本文件保存成功!");
		} catch (IOException e) {
			map.put("notice", false);
			map.put("message", jsName + "脚本文件保存失败!");
		}
		return map;
	}	
	
	
	@RequestMapping(value = "/deleteJs", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteJs(String jsName, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		File file = new File(Configure.JS_ROOT + File.separator + jsName);
		if (file.isFile()) {
			if (file.getAbsoluteFile().delete()) {
				map.put("notice", true);
				map.put("message", jsName + "脚本文件删除成功!");
			} else {
				map.put("notice", false);
				map.put("message", jsName + "脚本文件删除失败!");
			}
		} else {
			map.put("notice", false);
			map.put("message", jsName + "脚本文件删除失败!");
		}
		return map;
	}
	
	
}
