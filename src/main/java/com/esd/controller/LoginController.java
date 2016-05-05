package com.esd.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.esd.config.BaseConfig;
import com.esd.util.Util;

@Controller
public class LoginController {

	private Logger logger = Logger.getLogger(LoginController.class);

	@Value("${username}")
	private String username;

	@Value("${password}")
	private String password;

	@RequestMapping(value = "/admin/login")
	public ModelAndView adminlogin(HttpServletRequest request) {
		return new ModelAndView("/access");
	}
	
	@RequestMapping(value = "/admin/manage")
	public ModelAndView managelogin(HttpServletRequest request) {
		return new ModelAndView("/admin");
	}

	@RequestMapping(value = "/login")
	public ModelAndView login(HttpServletRequest request) {
		if (request.getSession().getAttribute(BaseConfig.USER) != null) {
			return new ModelAndView("/access");
		}
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String code = request.getParameter("code");
		if (Util.isNull(username, password, code)) {
			if (code.equals(request.getSession().getAttribute("randomCode"))) {
				if (this.username.equals(username) || this.password.equals(password)) {
					request.getSession().setAttribute(BaseConfig.USER, username);
					logger.debug("------------------登录成功!----------------");
					return new ModelAndView("/access");
				} else {
					request.getSession().setAttribute(BaseConfig.MESSAGE, "用户名或密码错误!");
					logger.debug("------------------用户名或密码错误!----------------");
					return new ModelAndView("/login");
				}
			} else {
				request.getSession().setAttribute(BaseConfig.MESSAGE, "验证码错误!");
				logger.debug("------------------验证码错误!----------------");
				return new ModelAndView("/login");
			}
		} else {
			return new ModelAndView("/login");
		}
	}

	@RequestMapping(value = "/admin/logout")
	public ModelAndView logout(HttpServletRequest request) {
		request.getSession().removeAttribute(BaseConfig.USER);
		logger.debug("-------------------清除session 退出用户!----------------");
		return new ModelAndView("/login");
	}

}
