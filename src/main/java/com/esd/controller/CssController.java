package com.esd.controller;

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
import com.esd.common.MongoDBUtil;

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
	
	@Autowired
	private MongoDBUtil mdu;

	@RequestMapping(value = "/saveCss", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveCss(String cssName, String cssContent, HttpSession session) throws UnsupportedEncodingException {
		Map<String, Object> map = new HashMap<String, Object>();
		String siteName = session.getAttribute("siteName").toString();
		String collectionName = siteName + "_" + "css";
		mdu.upsert(cssName, cssContent, siteName, collectionName);
		map.put("notice", true);
		map.put("message", cssName + "样式文件保存成功!");
		return map;
	}

	@RequestMapping(value = "/deleteCss", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCss(String cssName, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		String siteName = session.getAttribute("siteName").toString();
		String collectionName = siteName + "_" + "css";
		mdu.remove(cssName, collectionName);
		map.put("notice", true);
		map.put("message", cssName + "样式文件删除成功!");
		return map;
	}
	
	@RequestMapping("/loadCssList")
	@ResponseBody
	public Map<String, Object> loadCssList(String fileType, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		String siteName = session.getAttribute("siteName").toString();
		if(siteName != null){
			String collectionName = siteName + "_" + fileType;
			List<DbFile> list= mdu.findAll(DbFile.class, collectionName);
			map.put("list", list);
		}else{
			map.put("list", null);
		}
		return map;
	}
	
	@RequestMapping(value = "/loadCss", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> loadCss(String fileType, String fileName, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		String siteName = session.getAttribute("siteName").toString();
		String collectionName = siteName + "_" + fileType;
//		DbFile df = mdu.findOneByCollectionName(siteName + "_" + fileType, fileName, DbFile.class);
		DbFile df = mdu.findOne(fileName, collectionName);
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
