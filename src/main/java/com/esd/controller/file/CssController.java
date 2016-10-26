package com.esd.controller.file;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esd.collection.DbFile;
import com.esd.config.BaseConfig;
import com.esd.controller.site.SiteController;
import com.esd.service.file.FileService;

/**
 * 样式控制器
 * 
 * @author K'
 * 
 */
@Controller
@RequestMapping("/admin")
public class CssController {

	private static Logger logger = Logger.getLogger(CssController.class);
	
	private final String fileType = "css";
	
	@Autowired
	private FileService fileService;

	@RequestMapping(value = "/saveCss", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveCss(String cssName, String cssContent) throws UnsupportedEncodingException {
		Map<String, Object> map = new HashMap<String, Object>();
		//String siteName = session.getAttribute(BaseConfig.SITENAME).toString();
		String collectionName = SiteController.siteId + "_" + fileType;
		fileService.upsertFile(cssName, cssContent, SiteController.siteId, collectionName);
		map.put("notice", true);
		map.put("message", cssName + "样式文件保存成功!");
		return map;
	}

	@RequestMapping(value = "/deleteCss", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCss(String cssName) {
		Map<String, Object> map = new HashMap<String, Object>();
		//String siteName = session.getAttribute(BaseConfig.SITENAME).toString();
		String collectionName = SiteController.siteId + "_" + fileType;
		fileService.removeFileByName(cssName, collectionName);
		map.put("notice", true);
		map.put("message", cssName + "样式文件删除成功!");
		return map;
	}
	
	@RequestMapping("/loadCssList")
	@ResponseBody
	public Map<String, Object> loadCssList() {
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
	
	@RequestMapping(value = "/loadCss", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> loadCss(String fileName) {
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
