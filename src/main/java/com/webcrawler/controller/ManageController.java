package com.webcrawler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class ManageController {

	/**
	 * 跳转到管理页面manage.jsp
	 * 
	 * @return
	 */
	@RequestMapping(value = "/manage")
	public ModelAndView manage() {
		return new ModelAndView("manage");
	}

}
