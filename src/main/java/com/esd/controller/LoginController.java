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

import com.esd.collection.Site;
import com.esd.common.MongoDBUtil;
import com.esd.config.BaseConfig;
import com.esd.dao.MongoDBDao;
import com.esd.dao.MongoDBLink;
import com.esd.util.Util;

@Controller
public class LoginController {

	private Logger logger = Logger.getLogger(LoginController.class);
	
//	@Resource
//	private MongoDBUtil mdu;

	@Value("${username}")
	private String username;

	@Value("${password}")
	private String password;

	/**
	 * 跳转到登录页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView goLogin(HttpServletRequest request) {
		System.out.println("login进来了！！！");
		if (request.getSession().getAttribute(BaseConfig.USER) != null) {
			return new ModelAndView("redirect:admin/access");
		} else {
			return new ModelAndView("login");
		}
	}

	/**
	 * 登录
	 * liukai-2016-10.12
	 * @param username
	 * @param password
	 * @param code
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(String username, String password, String code, HttpSession session,HttpServletRequest request) {
		if (!Util.isNull(username, password, code)) {
			if (code.equals(session.getAttribute("randomCode"))) {
				if (this.username.equals(username) || this.password.equals(password)) {
					session.setAttribute(BaseConfig.USER, username);
					//
					int webPort = request.getLocalPort();
					//int webPort2 = request.getRemotePort();
					session.setAttribute("webPort", webPort);
					BaseConfig.webport = request.getLocalPort();
//					连接池传入访问的端口号
					MongoDBLink.setPort(webPort);
					
					//MongoDBDao mdd = new MongoDBDao();		
					//mdd.setMongoTemplate(MongoDBLink.link());
					
					logger.debug("------------------登录成功!----------------");
					return new ModelAndView("redirect:admin/access");
				} else {
					session.setAttribute(BaseConfig.MESSAGE, "用户名或密码错误!");
					logger.debug("------------------用户名或密码错误!----------------");
					return new ModelAndView("redirect:login");
				}
			} else {
				session.setAttribute(BaseConfig.MESSAGE, "验证码错误!");
				logger.debug("------------------验证码错误!----------------");
				return new ModelAndView("redirect:login");
			}
		} else {
			return new ModelAndView("redirect:login");
		}
	}

	/**
	 * 退出登录
	 * liukai-2016-10.12
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/logout")
	public ModelAndView logout(HttpSession session) {
		session.removeAttribute(BaseConfig.USER);
		logger.debug("-------------------清除session 退出用户!----------------");
		return new ModelAndView("redirect:login");
	}
	
}
