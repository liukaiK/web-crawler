package com.esd.config;

import java.io.File;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class Configure extends HttpServlet {

	public static String ROOT;

	public static String IMAGE_ROOT;

	public static String JS_ROOT;

	public static String CSS_ROOT;

	public static String PG_ROOT;

	public static String TEMPLATE_ROOT;

	public static String HTML_ROOT;

	public static String TEST_ROOT;
	
	public static String TEMP_ROOT;
	
	public void init() throws ServletException {
		ROOT = getServletContext().getRealPath("/") + "web";
		IMAGE_ROOT = ROOT + File.separator + "etc" + File.separator + "image";
		JS_ROOT = ROOT + File.separator + "etc" + File.separator + "js";
		CSS_ROOT = ROOT + File.separator + "etc" + File.separator + "styles";
		PG_ROOT = ROOT + File.separator + "db";
		TEMPLATE_ROOT = ROOT + File.separator + "template";
		HTML_ROOT = ROOT + File.separator + "html";
		TEST_ROOT = ROOT + File.separator + "test";
		TEMP_ROOT = ROOT + File.separator + "temp";
	}
	
}
