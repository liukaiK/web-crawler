package com.esd.config;

import java.io.File;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class BaseConfig extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public static String[] str = {};

	public static String[] filterSuffix = { ".jpg", ".png", ".gif", ".exe",
			".jpeg", ".doc", ".docx", ".xlsx", ".xls", ".ppt", ".rar", ".zip",
			".pdf", "wbk", "xml" };

	public static String ROOT;

	public static String IMAGE_ROOT;

	public static String JS_ROOT;

	public static String CSS_ROOT;

	public static String PG_ROOT;

	public static String TEMPLATE_ROOT;

	public static String HTML_ROOT;

	public static String TEST_ROOT;
	
	public static String TEMP_ROOT;

	public static final String[] INDEX_URL = { "http://www.szft.gov.cn/" };

	public static final String MESSAGE = "message";

	public static final String USER = "username";

	public static String time = "03:00:00";

	public void init() throws ServletException {
		ROOT = getServletContext().getRealPath("/") + "szft";
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
