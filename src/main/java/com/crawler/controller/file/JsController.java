package com.crawler.controller.file;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.crawler.collection.DbFile;
import com.crawler.controller.site.SiteController;
import com.crawler.service.file.FileService;

/**
 * JS控制器
 * 
 * @author K'
 * 
 */
@Controller
@RequestMapping("/admin")
public class JsController {

	private static Logger logger = Logger.getLogger(JsController.class);
	
	private final String fileType = "js";
	
	@Autowired
	private FileService fileService;

	/**
	 * liukai-2016-10.10.12
	 * @param jsName
	 * @param jsContent
	 * @param session
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/saveJs", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveJs(String jsName, String jsContent) throws UnsupportedEncodingException {
		Map<String, Object> map = new HashMap<String, Object>();
		//String siteName = session.getAttribute(BaseConfig.SITENAME).toString();
		String collectionName = SiteController.siteId + "_" + fileType;
		fileService.upsertFile(jsName, jsContent, SiteController.siteId, collectionName);
		map.put("notice", true);
		map.put("message", jsName + "脚本文件保存成功!");
		return map;
	}

	/**
	 * liukai-2016-10.10.12
	 * @param jsName
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/deleteJs", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteJs(String jsName) {
		Map<String, Object> map = new HashMap<String, Object>();
		//String siteName = session.getAttribute(BaseConfig.SITENAME).toString();
		String collectionName = SiteController.siteId + "_" + fileType;
		fileService.removeFileByName(jsName, collectionName);
		map.put("notice", true);
		map.put("message", jsName + "脚本文件删除成功!");
		return map;
	}
	
	/**
	 * liukai-2016-10.10.12
	 * @param fileType
	 * @param session
	 * @return
	 */
	@RequestMapping("/loadJsList")
	@ResponseBody
	public Map<String, Object> loadJsList() {
		Map<String, Object> map = new HashMap<String, Object>();
		//String siteName = session.getAttribute(BaseConfig.SITENAME).toString();
		if(SiteController.siteId != null){
			String collectionName = SiteController.siteId + "_" + fileType;
			List<DbFile> list= fileService.findAll(DbFile.class, collectionName);
			map.put("list", list);
		}else{
			map.put("list", null);
		}
		return map;
	}
	
	/**
	 * liukai-2016-10.10.12
	 * @param fileType
	 * @param fileName
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/loadJs", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> loadJs(String fileName) {
		Map<String, Object> map = new HashMap<String, Object>();
		//String siteName = session.getAttribute(BaseConfig.SITENAME).toString();
		String collectionName = SiteController.siteId + "_" + fileType;
		DbFile df = fileService.findFileByName(fileName, collectionName);
		if (df != null) {
			byte[] buf = df.getFileByte();
			try {
				String content = new String(buf, "utf-8");
				map.put("notice", true);
				map.put("message", content);
			} catch (UnsupportedEncodingException e) {
				logger.error(e);
			}
		}
		return map;
	}
}
