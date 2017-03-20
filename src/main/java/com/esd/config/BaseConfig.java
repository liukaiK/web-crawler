package com.esd.config;


public class BaseConfig {
	
	public static String htmlPath;

	public static String[] str = {};

	public static String[] filterSuffix = { ".jpg", ".png", ".gif", ".exe", ".jpeg", ".doc", ".docx", ".xlsx", ".xls", ".ppt", ".rar", ".zip", ".pdf", "wbk", "xml" };

	public static String[] filerAttr = { "alt", "title", "src", "href", "type", "value", "rowspan", "colspan", "http-equiv", "content", "flashvars" };
	
	public static final String[] INDEX_URL = { "http://www.jlsy.gov.cn/", "http://ft.jlsy.gov.cn/", "http://zt.jlsy.gov.cn/", "http://139.209.60.6/" };

	public static final String MESSAGE = "message";

	public static final String USER = "username";

	public static String time = "03:00:00";

	public String getHtmlPath() {
		return htmlPath;
	}

	public void setHtmlPath(String htmlPath) {
		BaseConfig.htmlPath = htmlPath;
	}


}
