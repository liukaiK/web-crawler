package com.esd.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;

import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.esd.common.CatDao;
import com.esd.config.BaseConfig;
import com.esd.config.PageConfig;
import com.esd.util.Md5;

public class Test {

	private static Logger logger = Logger.getLogger(Test.class);

	private CatDao dao = new CatDao();

	@org.junit.Test
	public void getSource() throws IOException {
		String url = "http://www.baic.gov.cn/zxbs/qtyw2/cpyzlzs/";
		Document htmlSource = Jsoup.connect(url).get();
		System.out.println(htmlSource);
	}

	@org.junit.Test
	public void findPageConfigFile() {
		String url = "http://www.baic.gov.cn/zxbs/qtyw2/cpyzlzs/";
		BaseConfig.PG_ROOT = "E:\\apache-tomcat-iac\\webapps\\iac\\baic" + File.separator + "db";
		dao.collectPageConfig();
		PageConfig pageConfig = dao.findPageConfig(url);
		System.out.println(pageConfig.getDb());
	}

	@org.junit.Test
	public void getHtmlSource() throws IOException {
		String url = "http://www.baic.gov.cn/zxbs/htsfwb/shxfl/";
		Connection jsoup = Jsoup.connect(url);
		Element htmlSource = jsoup.get().select(".htwbtable").get(0);
		Elements links = htmlSource.select("a");
		for (Element link : links) {
			String onclick = link.attr("onclick");
			String str[] = onclick.split(",");
			String href = str[1].substring(str[1].indexOf("'") + 3, str[1].lastIndexOf("'"));
			System.out.println("http://www.baic.gov.cn/zxbs/htsfwb/shxfl/" + href);
		}
	}

	
	@org.junit.Test
	public void getMd5() {
		StringBuffer str;
		str = new StringBuffer("http://www.baic.gov.cn/zxbs/djzc/bgxz/djzc_mc/");
		Md5 md5 = new Md5();
		System.out.println(md5.getMd5(str));
	}

	public static void main(String[] args) throws IOException {
		// 这里的数后面加“D”是表明它是Double类型，否则相除的话取整，无法正常使用
		double percent = 50.5D / 150D;
		// 输出一下，确认你的小数无误
		System.out.println("小数：" + percent);
		// 获取格式化对象
		NumberFormat nt = NumberFormat.getPercentInstance();
		// 设置百分数精确度2即保留两位小数
		nt.setMinimumFractionDigits(2);
		// 最后格式化并输出
		System.out.println("百分数：" + nt.format(percent));
		
	}

	public static Document downLoadTemple(String templePath) {
		String str = "";
		StringBuffer sb = new StringBuffer();
		try {
			InputStreamReader read = new InputStreamReader(new FileInputStream(templePath), "UTF-8");
			BufferedReader br = new BufferedReader(read);
			while ((str = br.readLine()) != null) {
				sb.append(str);
			}
			read.close();
			br.close();
		} catch (Exception e) {
			logger.error(e);
		}
		Document doc = Jsoup.parse(sb.toString());
		return doc;
	}

	public static void createHTMLFile(String content, String filePath) {
		try {
			OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8");
			BufferedWriter bw = new BufferedWriter(write);
			bw.write(content);
			bw.close();
			write.close();
		} catch (Exception e) {
			logger.error(e);
		}
	}

}
