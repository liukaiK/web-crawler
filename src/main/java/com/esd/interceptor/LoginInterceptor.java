/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.esd.config.BaseConfig;

/**
 * 用户登陆过滤器
 * 
 * @author liukaiK
 * 
 */
public class LoginInterceptor implements HandlerInterceptor {
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception arg3) throws Exception {

	}

	public void postHandle(HttpServletRequest request, HttpServletResponse arg1, Object arg2, ModelAndView arg3) throws Exception {
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) {
		if (request.getRequestURI().indexOf("/admin") != -1) {
			Object obj = request.getSession().getAttribute(BaseConfig.USER);
			if (obj == null) {
				try {
	                response.setContentType("text/html");  
	                response.setCharacterEncoding("utf-8");  
	                PrintWriter out = response.getWriter();    
	                StringBuilder builder = new StringBuilder();    
	                builder.append("<script type=\"text/javascript\" charset=\"UTF-8\">");    
	                builder.append("alert(\"请重新登陆！\");");    
	                builder.append("window.location.href=\"/iac/login\"");    
	                builder.append("</script>");    
	                out.print(builder.toString());    
	                out.close();   
					return false;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}
}
