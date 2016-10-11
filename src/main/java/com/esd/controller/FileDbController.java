package com.esd.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esd.collection.DbPgFile;
import com.esd.common.MongoDBUtil;
import com.esd.config.BaseConfig;
import com.esd.config.PageConfig;
import com.esd.util.Util;
/**
 * 数据库对文件读取操作
 * cx-20160908
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/dbFiles")
public class FileDbController {
	@Autowired
	private MongoDBUtil mdu ;
	
	//private static Logger logger = Logger.getLogger(CollectionPage.class);
	/**
	 * 扫描文件批量插入数据库
	 * cx-20160908
	 * @param fileType 1:templant 2:html 3:db 4:etc/js 5:etc/styles 6:全部
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/insertFiles", method = RequestMethod.POST)
	@ResponseBody
	public String insertFiles(int fileType,HttpServletRequest request) throws Exception {
		System.out.println("come in!");
		String url = mdu.url(request);
		String collectionName = null;
		String dir = null;
		String siteName = "szft";
		int m = 2;
		if(fileType == 6){
			m = 6;
			fileType = 1;
		}
		if(fileType == 5){
			System.out.println("fileType:"+fileType);
			String filedir = siteName + "/db/";
			url = BaseConfig.ROOT1 + filedir;
			File f = new File(url);
			File[] fs = f.listFiles();
			int mm = 1;
			for (int i = 0; i < fs.length; i++) {
			  if(!fs[i].isDirectory()){
				  String fileName = fs[i].getName();
				  String uf = url + fileName;

				  ObjectInputStream oin = new ObjectInputStream(new FileInputStream(new File(uf)));
	
				  PageConfig pgFile = (PageConfig) oin.readObject();
				  
				  oin.close();
				  
				  DbPgFile dpf = new DbPgFile();
					dpf.setCreateDate(new Date());
					dpf.setFiledir(filedir);
					dpf.setFileName(fileName);
					dpf.setMd5File(pgFile.toString());
					dpf.setPageConfig(pgFile);
					dpf.setSiteName(siteName);
					dpf.setUpdateDate(new Date());
					dpf.setUserId("0001");
					dpf.setId(mm);
					mdu.insertPg(dpf, siteName + "_pg");
				  mm++;	  
			  }
			  
			}
			
			return "呵呵呵！！！";
		}
		for (int i = 1; i < m; i++) {
			if(fileType == 1){
				dir = "template";
				collectionName = siteName +"_template";
			}else if(fileType == 2){
				dir = "html";
				collectionName = siteName +"_html";
			}else if(fileType == 3){
				dir = "etc/styles";
				collectionName = siteName +"_css";
			}else if(fileType == 4){
				dir = "etc/js";
				collectionName = siteName +"_js";
			}else if(fileType == 5){
				dir = "db";
				collectionName = siteName +"_pg";
			}
			//collectionName = siteName + dir;
			url = BaseConfig.ROOT1+siteName + "/" + dir + "/";
			mdu.insertFiles(collectionName,url,siteName);
			fileType++;
		}
		
		return "哈哈哈";
	}

	@RequestMapping(value = "/findFile", method = RequestMethod.POST)
	@ResponseBody
	public void findFile(HttpServletRequest request) {
		
	}
	
	@RequestMapping(value = "/deleteTemplate1", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteTemplate1(String templateName, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		File file = new File(BaseConfig.TEMPLATE_ROOT + File.separator + templateName);
		if (file.isFile()) {
			if (file.getAbsoluteFile().delete()) {
				map.put("notice", true);
				map.put("message", templateName + "模板文件删除成功!");
			} else {
				map.put("notice", false);
				map.put("message", templateName + "模板文件删除失败!");
			}
		} else {
			map.put("notice", false);
			map.put("message", templateName + "模板文件删除失败!");
		}
		return map;
	}
	
	@RequestMapping(value = "/saveCss1", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveCss1(String cssContent, String cssName, HttpServletRequest request) {
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
	
	 /* ***************************************************************** */
	 							
}
