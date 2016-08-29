package com.esd.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
	public void findPageConfigFile() {
		String url = "http://www.szft.gov.cn/bmxx/qgaj/zwxxgk/zcfg/crjflfg/201107/t20110715_270854.html";
		BaseConfig.PG_ROOT = "E:\\apache-tomcat-7.0.70\\webapps\\iac\\szft" + File.separator + "db";
		dao.collectPageConfig();
		PageConfig pageConfig = dao.findPageConfig(url);
		System.out.println(pageConfig.getDb());
	}
	
	@org.junit.Test
	public void ReadFile() throws IOException {
		// 创建文件输入流对象
		FileInputStream is = new FileInputStream("D:\\image1.png");
		// 设定读取的字节数
		int n = 512;
		byte buffer[] = new byte[n];
		// 读取输入流
		while ((is.read(buffer, 0, n) != -1) && (n > 0)) {
			System.out.print(new String(buffer));
		}
		System.out.println();
		// 关闭输入流
		is.close();
	}
	

	@org.junit.Test
	public void getSource() throws IOException {
		String url = "http://www.szft.gov.cn/video/spxw/";
		Document htmlSource = Jsoup.connect(url).get();
		System.out.println(htmlSource);
	}


	@org.junit.Test
	public void getHtmlSource() throws IOException {
		String url = "http://www.szft.gov.cn/video/spxw/dcft/";
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
		str = new StringBuffer("http://www.caac.gov.cn/");
		Md5 md5 = new Md5();
		System.out.println(md5.getMd5(str));
	}
	
	@org.junit.Test
	public void parseHTML() {
		String str = "";
		StringBuffer sb = new StringBuffer();
		try {
			InputStreamReader read = new InputStreamReader(new FileInputStream("D:\\test.txt"), "UTF-8");
			BufferedReader br = new BufferedReader(read);
			while ((str = br.readLine()) != null) {
				sb.append(str);
			}
			read.close();
			br.close();
			// System.out.println(sb);
			Document doc = Jsoup.parse(sb.toString());
			Elements options = doc.getElementsByTag("select").get(0).getElementsByTag("option");
			for (Element option : options) {
				System.out.println("city1option.add(new Option(\"" + option.text() + "\",\"" + option.attr("value") + "\"));");
			}

		} catch (Exception e) {
		}

	}	
	

	public static void main(String[] args) throws IOException, ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String ss = "2016-12-11";
		
		System.out.println(sdf.parse(ss).toString());
		
		
		
//		// 这里的数后面加“D”是表明它是Double类型，否则相除的话取整，无法正常使用
//		double percent = 50.5D / 150D;
//		// 输出一下，确认你的小数无误
//		System.out.println("小数：" + percent);
//		// 获取格式化对象
//		NumberFormat nt = NumberFormat.getPercentInstance();
//		// 设置百分数精确度2即保留两位小数
//		nt.setMinimumFractionDigits(2);
//		// 最后格式化并输出
//		System.out.println("百分数：" + nt.format(percent));
		
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
