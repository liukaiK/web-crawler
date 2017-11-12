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
 * 模板控制器
 * 
 * @author K'
 * 
 */
@Controller
@RequestMapping("/admin")
public class TemplateController {

	private static Logger logger = Logger.getLogger(TemplateController.class);
	
	private final String fileType = "template";
	
	@Autowired
	private FileService fileService;

	/**
	 * liukai-2016.10.11
	 * @param templateName
	 * @param templateContent
	 * @param session
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/saveTemplate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveTemplate(String templateName, String templateContent) throws UnsupportedEncodingException {
		Map<String, Object> map = new HashMap<String, Object>();
		//String siteName = session.getAttribute(BaseConfig.SITENAME).toString();
		String collectionName = SiteController.siteId + "_" + fileType;
		fileService.upsertFile(templateName, templateContent, SiteController.siteId, collectionName);
		map.put("notice", true);
		map.put("message", templateName + "模板文件保存成功!");
		return map;
	}

	/**
	 * liukai-2016.10.11
	 * @param templateName
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/deleteTemplate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteTemplate(String templateName) {
		Map<String, Object> map = new HashMap<String, Object>();
		//String siteName = session.getAttribute(BaseConfig.SITENAME).toString();
		String collectionName = SiteController.siteId + "_" + fileType;
		fileService.removeFileByName(templateName, collectionName);
		map.put("notice", true);
		map.put("message", templateName + "模板文件删除成功!");
		return map;
	}
	
	/**
	 * liukai-2016.10.11
	 * @param fileType
	 * @param session
	 * @return
	 */
	@RequestMapping("/loadTemplateList")
	@ResponseBody
	public Map<String, Object> loadTemplateList() {
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
	 * liukai-2016.10.11
	 * @param fileType
	 * @param fileName
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/loadTemplate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> loadTemplate(String fileName) {
		Map<String, Object> map = new HashMap<String, Object>();
		//String siteName = session.getAttribute(BaseConfig.SITENAME).toString();
		String collectionName = SiteController.siteId + "_" + fileType;
		DbFile df = fileService.findFileByName(fileName, collectionName);
		if (df != null) {
			byte[] buf = df.getFileByte();
			try {
				String content = new String(buf,"utf-8");
				map.put("notice", true);
				map.put("message", content);
			} catch (UnsupportedEncodingException e) {
				logger.error(e);
			}
		} else {
			map.put("notice", false);
			map.put("message", fileName + "模板文件不存在");
		}
		return map;
	}

}
