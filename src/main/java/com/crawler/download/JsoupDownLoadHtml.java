package com.crawler.download;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.crawler.config.PageConfig;

public class JsoupDownLoadHtml {

	public JsoupDownLoadHtml() {
		// TODO Auto-generated constructor stub
	}

	public Document download(PageConfig pageConfig) throws IOException {
		Connection jsoup = Jsoup.connect(pageConfig.getUrl());
		return jsoup.get();
	}

	public static void main(String[] args) throws IOException {
		String url = "http://www.szft.gov.cn/xxgk/zdly/spgg/ggxx/";
		Connection jsoup = Jsoup.connect(url);
		Document doc = jsoup.get();
		System.out.println(doc);
		
		
//		Elements tables = doc.getElementsByClass("cont");
//		for (Element table : tables) {
//			Elements as = table.getElementsByTag("a");
//			System.out.println("<h2><p>" + as.get(0).text() + "</p><ul>");
//			for (int i = 1; i <= as.size() - 1; i++) {
//				System.out.println("<li><a href=\""+as.get(i).attr("abs:href")+"\" title=\""+as.get(i).text()+"\">"+as.get(i).text()+"</a></li>");
//			}
//			System.out.println("</ul></h2>");
//		}
//		
//		
		
		
		
		
		
		
		
		
		
		
//		Element element = doc.getElementsByClass("box").get(0);
//		for (int i = 1; i <= 15; i++) {
//			Element show_a = element.getElementById("Show_a_0" + i + "");
//			Element html = show_a.select("td").get(1);
//			System.out.println("<h2><p>" + show_a.select("h1").get(0).text() + "</p><ul>");
//			for (int j = 0; j <= html.select("h2").size() - 1; j++) {
//				System.out.println("<li><a href=\"" + html.select("h3").get(j).select("a").attr("abs:href") + "\" title=\"" + html.select("h2").get(j).text() + "\">" + html.select("h2").get(j).text() + "</a></li>");
//			}
//			System.out.println("</ul></h2>");
//		}
	}
}
