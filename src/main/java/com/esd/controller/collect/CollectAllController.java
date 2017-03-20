package com.esd.controller.collect;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esd.core.CollectionAll;
import com.esd.core.CollectionParmary;

@Controller
@RequestMapping("/admin")
public class CollectAllController {

	private static Logger logger = Logger.getLogger(CollectAllController.class);

	@Resource
	private CollectionAll CollectionAll;
	
	@Resource
	private CollectionParmary collectionParmary;

	@RequestMapping("/catingAll")
	@ResponseBody
	public Map<String, Object> catingAll(@RequestParam(required = true) String url) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (url == null || url.trim().isEmpty()) {
			map.put("notice", false);
			map.put("message", "链接不能为空!");
		} else {
			CollectionAll.init(url);
			CollectionAll.start();
			map.put("notice", true);
			map.put("message", "整站采集开始!");
		}
		return map;
	}

	@RequestMapping("/cancelCating")
	@ResponseBody
	public Map<String, Object> cancelCating(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		CollectionAll.setCollectionFlag(false);
		collectionParmary.setCollectStatic(false);
		map.put("notice", true);
		map.put("message", "取消采集成功!");
		logger.debug("Collection cancel");
		return map;
	}

	@RequestMapping("/getdbCount")
	@ResponseBody
	public Map<String, Object> getDownload(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		int total = CollectionAll.getTotal();
		map.put("notice", true);
		map.put("message", total);
		return map;
	}
}
