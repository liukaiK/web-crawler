package com.esd.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
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
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;

public class Test {

	private static Logger logger = Logger.getLogger(Test.class);

	private CatDao dao = new CatDao();
	
	@org.junit.Test
	public void moni() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		final String URL = "http://www.caacts.org.cn:8080/struts2_spring3_hibernate3_1.0/byTianjia.action";
		WebClient webClient = new WebClient();
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setCssEnabled(false);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		
		HtmlPage page1 = (HtmlPage) webClient.getCurrentWindow().getEnclosedPage();
		page1 = webClient.getPage(URL);
		HtmlSelect provinceSelect = (HtmlSelect) page1.getElementById("province");
		
//		provinceSelect.setDefaultValue("机场");
		provinceSelect.setSelectedAttribute("4", true);
//		HtmlOption num = provinceSelect.getOptionByValue("其他");
		
		HtmlSelect citySelect = (HtmlSelect) page1.getElementById("city");
		System.out.println(citySelect.getOptions());
		
		
		
		HtmlSelect city1Select = (HtmlSelect) page1.getElementById("city1");
		
		
		
		HtmlInput conName = (HtmlInput) page1.getElementsByTagName("input").get(0);
		conName.setAttribute("value", "刘凯");
		
		HtmlInput email = (HtmlInput) page1.getElementsByTagName("input").get(2);
		email.setAttribute("value", "zhizhufan@foxmail.com");
		
		HtmlTextArea conContentTextArea = (HtmlTextArea)page1.getElementsByTagName("textarea").get(0);
		conContentTextArea.setTextContent("表扬表扬表扬表扬表扬表扬表扬表扬表扬表扬表扬表扬表扬表扬表扬表扬表扬表扬");
		
		
		HtmlAnchor submitBtn = (HtmlAnchor) page1.getElementById("submitBtn");
		HtmlPage hp = submitBtn.click();
		if (hp != null) {
			String pageUrl = hp.getUrl().toString().trim();
			
			
			
			
			
			
			
//			String m = pro(pageUrl, hp.asXml());
		} else {
		}		
		
		
	}
	
	
	

	@org.junit.Test
	public void getSource() throws IOException {
		String url = "http://www.caacca.org/lksc/hbywcs/";
		Document htmlSource = Jsoup.connect(url).get();
		Elements links = htmlSource.select("a[href],area[href],iframe[src]");
		
		for (Element e : links) {
			String href = e.attr("abs:href").trim();
			if (href.equals("")) {
				href = e.attr("abs:src").trim();
				if (href == null) {
					continue;
				}
			}
		}
	}

	@org.junit.Test
	public void findPageConfigFile() {
		String url = "http://www.caacca.org/lksc/hklxzn/201304/t20130408_4156.html";
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
		str = new StringBuffer("http://www.caac.gov.cn/");
		Md5 md5 = new Md5();
		System.out.println(md5.getMd5(str));
	}
	
	@org.junit.Test
	public void parseHTML() {
		String html = "<select>"+
		"<option value=\"其他问题\">其他问题</option>"+
		"</select>";
		
		Document doc = Jsoup.parse(html);
		Elements options = doc.getElementsByTag("select").get(0).getElementsByTag("option");
		for (Element option : options) {
			
			System.out.println("city1option.add(new Option(\""+option.text()+"\",\""+option.attr("value")+"\"));");
		}
		
		
		
		
		
		
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
