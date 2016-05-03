package com.esd.config;

import java.io.File;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class BaseConfig extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public static String[] str = {"http://www.baic.gov.cn/zxbs/htsfwb/shxfl/dx/201601/t20160108_1257161.htm", 
					"http://www.baic.gov.cn/zxbs/htsfwb/shxfl/fw/201408/t20140827_1187242.htm", 
					"http://www.baic.gov.cn/zxbs/htsfwb/shxfl/fw/201408/t20140827_1187241.htm", 
					"http://www.baic.gov.cn/zxbs/htsfwb/shxfl/fw/201408/t20140827_1187240.htm", 
					"http://www.baic.gov.cn/zxbs/htsfwb/shxfl/fw/201408/t20140827_1187239.htm", 
					"http://www.baic.gov.cn/zxbs/htsfwb/shxfl/fw/201408/t20140827_1187237.htm",
					"http://www.baic.gov.cn/zxbs/htsfwb/shxfl/fw/201408/t20140827_1187236.htm", 
					"http://www.baic.gov.cn/zxbs/htsfwb/shxfl/fw/201408/t20140827_1187234.htm", 
					"http://www.baic.gov.cn/zxbs/htsfwb/shxfl/fw/201408/t20140827_1187230.htm", 
					"http://www.baic.gov.cn/zxbs/htsfwb/shxfl/ly/201409/t20140903_1190975.htm", 
					"http://www.baic.gov.cn/zxbs/htsfwb/shxfl/ly/201409/t20140903_1190972.htm", 
					"http://www.baic.gov.cn/zxbs/htsfwb/shxfl/ly/201409/t20140903_1190970.htm",
					"http://www.baic.gov.cn/zxbs/htsfwb/shxfl/ly/201408/t20140827_1187246.htm", 
					"http://www.baic.gov.cn/zxbs/htsfwb/shxfl/ly/201408/t20140827_1187245.htm", 
					"http://www.baic.gov.cn/zxbs/htsfwb/shxfl/ly/201408/t20140827_1187244.htm", 
					"http://www.baic.gov.cn/zxbs/htsfwb/shxfl/ly/201408/t20140827_1187243.htm", 
					"http://www.baic.gov.cn/zxbs/htsfwb/shxfl/qc/201601/t20160108_1257162.htm", 
					"http://www.baic.gov.cn/zxbs/htsfwb/shxfl/qc/201408/t20140827_1187250.htm",
					"http://www.baic.gov.cn/zxbs/htsfwb/shxfl/zx/201408/t20140827_1187254.htm", 
					"http://www.baic.gov.cn/zxbs/htsfwb/shxfl/qt/201408/t20140827_1187270.htm", 
					"http://www.baic.gov.cn/zxbs/htsfwb/shxfl/qt/201408/t20140827_1187269.htm", 
					"http://www.baic.gov.cn/zxbs/htsfwb/shxfl/qt/201408/t20140827_1187264.htm", 
					"http://www.baic.gov.cn/zxbs/htsfwb/shxfl/qt/201408/t20140827_1187261.htm" };

	
	public static String[] filterSuffix = { ".jpg", ".png", ".gif", ".exe", ".jpeg", ".doc", ".docx", ".xlsx", ".xls", ".ppt", ".rar", ".zip", ".pdf", "wbk", "xml" };
	
	public static String ROOT;

	public static String IMAGE_ROOT;

	public static String JS_ROOT;

	public static String CSS_ROOT;

	public static String PG_ROOT;

	public static String TEMPLATE_ROOT;

	public static String HTML_ROOT;

	public static String TEST_ROOT;

	public static final String INDEX_URL = "http://www.baic.gov.cn";

	public static final String MESSAGE = "message";

	public static final String USER = "username";

	public static String time = "03:00:00";

	public void init() throws ServletException {
		ROOT = getServletContext().getRealPath("/") + "baic";
		IMAGE_ROOT = ROOT + File.separator + "etc" + File.separator + "image";
		JS_ROOT = ROOT + File.separator + "etc" + File.separator + "js";
		CSS_ROOT = ROOT + File.separator + "etc" + File.separator + "styles";
		PG_ROOT = ROOT + File.separator + "db";
		TEMPLATE_ROOT = ROOT + File.separator + "template";
		HTML_ROOT = ROOT + File.separator + "html";
		TEST_ROOT = ROOT + File.separator + "test";
	}

}
