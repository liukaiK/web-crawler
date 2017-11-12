package com.crawler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AccessController {

	/**
	 * 跳转到access.jsp
	 * 
	 * @return
	 */
	@RequestMapping(value = "/access")
	public ModelAndView access() {
		return new ModelAndView("access");
	}

}
