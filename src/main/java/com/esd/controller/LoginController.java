package com.esd.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.esd.config.BaseConfig;
import com.esd.util.Util;

@Controller
public class LoginController {

	private Logger log = Logger.getLogger(LoginController.class);

	@Value("${username}")
	private String username;

	@Value("${password}")
	private String password;

	@RequestMapping(value = "/admin/login", method = RequestMethod.GET)
	public ModelAndView adminlogin(HttpServletRequest request) {
		return new ModelAndView("/access");
	}
	
	@RequestMapping(value = "/admin/manage", method = RequestMethod.GET)
	public ModelAndView managelogin(HttpServletRequest request) {
		return new ModelAndView("/admin");
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView admin(HttpServletRequest request) {
		return new ModelAndView("/login");
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView adminPost(HttpServletRequest request) {

		if (request.getSession().getAttribute(BaseConfig.USER) != null) {
			return new ModelAndView("/access");
		}
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String code = request.getParameter("code");
		// log.debug("\n用户名: " + username + "\n密码: " + password + "\n验证码: " +
		// code);
		if (Util.isNull(username, password, code)) {
			if (code.equals(request.getSession().getAttribute("randomCode"))) {
				if (this.username.equals(username) || this.password.equals(password)) {
					request.getSession().setAttribute(BaseConfig.USER, username);
					log.debug("登录成功");
					return new ModelAndView("redirect:/admin/login");
					// return new ModelAndView("/admin");
				} else {
					log.debug("用户名或密码错误!");
					request.getSession().setAttribute(BaseConfig.MESSAGE, "用户名或密码错误!");
				}
			} else {
				log.debug("验证码错误!");
				request.getSession().setAttribute(BaseConfig.MESSAGE, "验证码错误!");
			}
		} else {
			log.debug("用户信息和验证码不能为空!");
			request.getSession().setAttribute(BaseConfig.MESSAGE, "用户信息和验证码不能为空!");
		}
		log.debug("返回登录页面");
		return new ModelAndView("redirect:/login");
	}

	/**
	 * 退出方法
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/logout", method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request) {
		log.debug("-----------退出当前用户-------------");
		request.getSession().removeAttribute(BaseConfig.USER);
		log.debug("-----------清除session--------------");
		return new ModelAndView("redirect:/login");
	}


}
