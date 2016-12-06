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
	public void indexPage(HttpServletRequest request,HttpServletResponse response) {
		//获取访问url的端口
		//int webPort = request.getServerPort();
		String webPort = request.getLocalPort() + "";
		//int webPort2 = request.getRemotePort();

		System.out.println("webPort1："+webPort);
		
		//Criteria criatira = new Criteria();
		//criatira.andOperator(Criteria.where("port").is(webPort));
		Criteria criatira = Criteria.where("port").is(webPort);
		
		Site  site = mdu.findOneByCollectionName("sites", criatira, Site.class);
		String htmlC = null;
		try {
			htmlC = new String(site.getIndex(),"gbk");
			System.out.println(htmlC);	
			//StringBuffer sbHtml = new StringBuffer();
	        //sbHtml.append(htmlC);
			//response.setCharacterEncoding("utf-8");           
		    response.setContentType("text/html; charset=utf-8");
			
		    PrintWriter writer = response.getWriter();           
		    writer.write(htmlC);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

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

}
