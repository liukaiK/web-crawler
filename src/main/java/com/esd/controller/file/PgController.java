package com.esd.controller.file;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esd.collection.DbPgFile;
import com.esd.config.NodeConfig;
import com.esd.config.PageConfig;
import com.esd.service.file.PgService;

@Controller
@RequestMapping("/admin")
public class PgController {
	
	private static Logger logger = Logger.getLogger(PgController.class);
	
	private final String fileType = "pg";
	
	@Autowired
	private PgService pgService;
	
	@RequestMapping("/savePgFile")
	@ResponseBody
	public Map<String, Object> savePgFile(HttpServletRequest request,HttpSession session) throws UnsupportedEncodingException {
		Map<String, Object> map = new HashMap<String, Object>();
		String pgName = request.getParameter("pgName");
		String javaScriptEnabled = request.getParameter("javaScriptEnabled");
		String template = request.getParameter("template");
		String sleep = request.getParameter("sleep");
		String url = request.getParameter("url");
		String[] rules = request.getParameterValues("rules[]");
		String[] urls = request.getParameterValues("urls[]");
		String siteName = session.getAttribute("siteName").toString();
		
		PageConfig pageConfig = new PageConfig();
		pageConfig.setJavaScriptEnabled(Boolean.valueOf(javaScriptEnabled));
		pageConfig.setSleep(Long.valueOf(sleep));
		pageConfig.setUrl(url);
		
		if (rules != null) {
			for (String s : rules) {
				String[] rule = s.split("&");
				NodeConfig nc = new NodeConfig();
				nc.setDes(rule[0]);
				nc.setParent(rule[1]);
				nc.setTag(rule[2]);
				nc.setType(rule[3]);
				nc.setName(rule[4]);
				nc.setIndex(rule[5]);
				nc.setAnchorId(rule[6]);
				pageConfig.getList().add(nc);
			}
		}
		pageConfig.setTemplate(template);
		
		
		List<String> urlList = new ArrayList<String>();
		for (int i = 0; i < urls.length; i++) {
			urlList.add(urls[i]);
		}
		pageConfig.setUrls(urlList);
		/**
		 * cx-201609222
		 * pg存入数据库
		 */
		//String filedir = BaseConfig.PG_ROOT + File.separator + pgName + ".pg";
		String filedir =  File.separator + "db" + File.separator + pgName + ".pg";
		pgService.upsertFile(pgName, filedir, siteName, pageConfig, siteName + "_pg");
		
		map.put("notice", true);
		map.put("message", pgName + "规则保存成功");
		return map;
	}
	
	@RequestMapping("/deletePgFile")
	@ResponseBody
	public Map<String, Object> deletePgFile(String pgFileName, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		String siteName = session.getAttribute("siteName").toString();
		String collectionName = siteName + "_" + fileType;
		pgService.removeFileByName(pgFileName, collectionName);
		map.put("notice", true);
		map.put("message", pgFileName + "规则文件删除成功!");
		return map;
	}
	
	@RequestMapping("/loadPgFile")
	@ResponseBody
	public Map<String, Object> loadPgFile(String pgFileName, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		String siteName = session.getAttribute("siteName").toString();
		String collectionName = siteName + "_" + fileType;
		DbPgFile df = pgService.findFileByName(pgFileName, collectionName);
		PageConfig pgFile = df.getPageConfig();
		map.put("pgFile", pgFile);
		map.put("notice", true);
		return map;
	}
	
	@RequestMapping("/loadPgFileList")
	@ResponseBody
	public Map<String, Object> loadPgFileList(HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		String siteName = session.getAttribute("siteName").toString();
		String collectionName = siteName + "_" + fileType;
		List<DbPgFile> list = pgService.findAll(DbPgFile.class, collectionName);
		map.put("list", list);
		return map;
	}
}
