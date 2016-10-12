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
import com.esd.dao.file.FileDao;

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
	private FileDao fileDao;

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
	public Map<String, Object> saveTemplate(String templateName, String templateContent, HttpSession session) throws UnsupportedEncodingException {
		Map<String, Object> map = new HashMap<String, Object>();
		String siteName = session.getAttribute("siteName").toString();
		String collectionName = siteName + "_" + fileType;
		fileDao.upsertFile(templateName, templateContent, siteName, collectionName);
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
	public Map<String, Object> deleteTemplate(String templateName, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		String siteName = session.getAttribute("siteName").toString();
		String collectionName = siteName + "_" + fileType;
		fileDao.removeFileByName(templateName, collectionName);
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
	public Map<String, Object> loadTemplateList(HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		String siteName = session.getAttribute("siteName").toString();
		if(siteName != null){
			String collectionName = siteName + "_" + fileType;
			List<DbFile> list= fileDao.findAll(DbFile.class, collectionName);
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
	public Map<String, Object> loadTemplate(String fileName, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		String siteName = session.getAttribute("siteName").toString();
		String collectionName = siteName + "_" + fileType;
		DbFile df = fileDao.findFileByName(fileName, collectionName);
		byte[] buf = df.getFileByte();
		try {
			String content = new String(buf,"utf-8");
			map.put("notice", true);
			map.put("message", content);
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
		}
		return map;
	}

}
