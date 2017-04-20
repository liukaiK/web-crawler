package com.esd.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.esd.collection.DbFile;
import com.esd.collection.Site;
import com.esd.common.MongoDBUtil;
import com.esd.config.BaseConfig;
import com.esd.controller.site.SiteController;
import com.esd.dao.MongoDBDao;
import com.esd.dao.MongoDBLink;
import com.esd.util.Util;

@Controller
public class indexController {

	private Logger logger = Logger.getLogger(indexController.class);
	@Resource
	private MongoDBUtil mdu;

	/**
	 * 
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/indexPage", method = RequestMethod.GET)
	public void indexPage(HttpServletRequest request,HttpServletResponse response,HttpSession session) {
		System.out.println("我是indexPage");
		//获取访问url的端口
		//int webPort = request.getServerPort();
		int webPort = request.getLocalPort();
		//int webPort2 = request.getRemotePort();
		session.setAttribute("webPort", webPort);
		BaseConfig.webport = request.getLocalPort();
//		连接池传入访问的端口号
		MongoDBLink.setPort(webPort);
		System.out.println("webPort1："+webPort);
		
		//MongoDBDao mdd = new MongoDBDao();
		//mdd.setMongoTemplate(MongoDBLink.link());
		
		//Criteria criatira = new Criteria();
		//criatira.andOperator(Criteria.where("port").is(webPort));
		
		String webPort1 = webPort + "";
		Criteria criatira = Criteria.where("port").is(webPort1);
		
		Site  site = mdu.findOneByCollectionName("sites", criatira, Site.class);
		session.setAttribute("siteId", site.getId());
		//
//		Criteria criatira1 = Criteria.where("id").is("58e47fb17a564009bc0e52e2");
//		DbFile df = mdu.findOneByCollectionName("58e449287a56400c587e362e_html", criatira1, DbFile.class);
		
		String htmlC = null;
		try {
			htmlC = new String(site.getIndex(),"gbk");
			//htmlC = new String(df.getFileByte(),"gbk");
			
			//System.out.println(htmlC);	
			//StringBuffer sbHtml = new StringBuffer();
	        //sbHtml.append(htmlC);
			//response.setCharacterEncoding("utf-8");           
		    response.setContentType("text/html; charset=utf-8");
			
		    PrintWriter writer = response.getWriter();           
		    writer.write(htmlC);
		    
		    response.flushBuffer();
		    writer.flush();
		    writer.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	@RequestMapping(value = "/showPage", method = RequestMethod.GET)
	public void showPage(String fileName,HttpServletRequest request,HttpServletResponse response,HttpSession session) {
		System.out.println("我是showPage");
		
		String siteId = session.getAttribute("siteId").toString();
		String name = fileName + ".html"; 
		Criteria criatira = Criteria.where("fileName").is(name);
		DbFile  df = mdu.findOneByCollectionName(siteId+"_html", criatira, DbFile.class);
	
		String htmlC = null;
		try {
			htmlC = new String(df.getFileByte(),"gbk");   
		    response.setContentType("text/html; charset=utf-8");
			
		    PrintWriter writer = response.getWriter();           
		    writer.write(htmlC);
		    
		    response.flushBuffer();
		    writer.flush();
		    writer.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	@RequestMapping(value = "/showTemplate", method = RequestMethod.GET)
	public void showTemplate(String fileName,HttpServletRequest request,HttpServletResponse response,HttpSession session) {
		System.out.println("我是showTemplate");
		
		String siteId = session.getAttribute("siteId").toString();
		Criteria criatira = Criteria.where("fileName").is(fileName);
		DbFile  df = mdu.findOneByCollectionName(siteId+"_template", criatira, DbFile.class);
	
		String htmlC = null;
		try {
			htmlC = new String(df.getFileByte(),"gbk");   
		    response.setContentType("text/html; charset=utf-8");
			
		    PrintWriter writer = response.getWriter();           
		    writer.write(htmlC);
		    
		    response.flushBuffer();
		    writer.flush();
		    writer.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	@RequestMapping(value = "/showImage", method = RequestMethod.GET)
	public void showImage(String fileName,HttpServletRequest request,HttpServletResponse response,HttpSession session) {
		System.out.println("我是showImage");
		String fileType = fileName.split("\\.")[(fileName.split("\\.").length - 1)];
		String contentType = null;
		if(fileType == "png"){
			contentType = "image/png; charset=utf-8";
		}
		if(fileType == "bmp"){
			contentType = "image/bmp; charset=utf-8";
		}
		if(fileType == "gif"){
			contentType = "image/gif; charset=utf-8";
		}
		if(fileType == "jpg"){
			contentType = "image/jpeg; charset=utf-8";
		}
		String siteId = session.getAttribute("siteId").toString();
		Criteria criatira = Criteria.where("fileName").is(fileName);
		DbFile  df = mdu.findOneByCollectionName(siteId+"_image", criatira, DbFile.class);
	
		try {  
		    response.setContentType(contentType);
		    response.getOutputStream().write(df.getFileByte());
		    
		    response.flushBuffer();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
		
		//response.setHeader("Content-Type", "image/jped");
		

	
	/**
	 * 退出登录
	 * liukai-2016-10.12
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/logout1")
	public ModelAndView logout1(HttpSession session) {
		session.removeAttribute(BaseConfig.USER);
		logger.debug("-------------------清除session 退出用户!----------------");
		return new ModelAndView("redirect:login");
	}
	@RequestMapping(value = "/js")
	public void JS(String name,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException  {
		System.out.println("js___name:"+name);
		String siteId = session.getAttribute("siteId").toString();
		Criteria criatira = Criteria.where("fileName").is(name);
		
		DbFile  df = mdu.findOneByCollectionName(siteId+"_js", criatira, DbFile.class);
		String js = null;
		
		js = new String(df.getFileByte(),"gbk");
		
		response.setContentType("text/javascript; charset=utf-8");
		
	    PrintWriter writer = response.getWriter();           
	    writer.write(js);
	    
	    response.flushBuffer();
	    writer.flush();
	    writer.close();
	}
	@RequestMapping(value = "/css")
	public void CSS(String name,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException  {
		System.out.println("css____name:"+name);
		String siteId = session.getAttribute("siteId").toString();
		Criteria criatira = Criteria.where("fileName").is(name);
		
		DbFile  df = mdu.findOneByCollectionName(siteId+"_css", criatira, DbFile.class);
		String css = null;
		
		css = new String(df.getFileByte(),"utf-8");
		
		response.setContentType("text/css; charset=utf-8");
		
	    PrintWriter writer = response.getWriter();           
	    writer.write(css);
	    
	    response.flushBuffer();
	    writer.flush();
	    writer.close();
	}

}
