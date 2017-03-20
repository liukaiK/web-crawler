package com.esd.controller.collect;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esd.core.CollectionParmary;

@Controller
@RequestMapping("/admin")
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
	public Map<String, Object> cating(@RequestParam(required = true) String url) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (url == null || url.trim().isEmpty()) {
			map.put("notice", false);
			map.put("message", "链接不能为空!");
		} else {
			collectionParmary.init(url);
			collectionParmary.start();
			map.put("notice", true);
			map.put("message", "一级采集开始!");
		}
		return map;
	}

}
