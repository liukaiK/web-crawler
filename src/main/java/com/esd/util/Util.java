package com.esd.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.esd.common.AutoDiscern;
import com.esd.common.InputStreamUtils;
import com.esd.config.BaseConfig;
import com.esd.config.Configure;
import com.esd.config.PageConfig;
import com.gargoylesoftware.htmlunit.html.HtmlImage;

public class Util {

	public static boolean isOutUrl(String url) {
		for (String str : BaseConfig.INDEX_URL) {
			if (url.startsWith(str)) {
				return false;
			}
		}
		return true;
	}
	
	public static void doWithOutUrl(String url) throws IOException {
		Document doc = Util.downLoadTemple(Configure.TEMPLATE_ROOT + File.separator + "error.html");
		doc.select("#error").attr("href", url);
		String mName = Util.interceptDir(url);
		String path = BaseConfig.htmlPath + File.separator + mName;
		Util.createNewFile(doc.html(), path);
	}
	
	public static String do404Page(String url) throws IOException {
		Document doc = Util.downLoadTemple(Configure.TEMPLATE_ROOT + File.separator + "404.html");
		doc.select("#error").attr("href", url);
		String mName = Util.interceptDir(url);
		String path = BaseConfig.htmlPath + File.separator + mName;
		Util.createNewFile(doc.html(), path);
		return mName;
	}

	public static String interceptDir(String url) {
		for (int i = 0; i < BaseConfig.filterSuffix.length; i++) {
			if (url.endsWith(BaseConfig.filterSuffix[i])) {
				return url;
			}
		}
		String m = Md5.getMd5(new StringBuffer(url));
		return m + ".html";
	}
	
	public static String filterSuffix(String realUrl) {
		String url = procUlr(realUrl);
		for (String s : BaseConfig.filterSuffix) {
			if (url.endsWith(s)) {
				return "";
			}
		}
		return url;
	}
	
	private static String procUlr(String url) {
		for (String str : BaseConfig.INDEX_URL) {
			if (url.startsWith(str)) {
				if (url.endsWith("#")) {
					url = url.substring(0, url.length() - 1);
				}
				url = url.replace("./", "");
			}
		}
		return url;
	}
	
	public static Document downLoadTemple(String templePath) throws IOException {
		String str = "";
		StringBuffer sb = new StringBuffer();
		InputStreamReader read = new InputStreamReader(new FileInputStream(templePath), "UTF-8");
		BufferedReader br = new BufferedReader(read);
		while ((str = br.readLine()) != null) {
			sb.append(str);
		}
		read.close();
		br.close();
		Document doc = Jsoup.parse(sb.toString());
		return doc;
	}
	
	
	public static Document downLoadTemple(PageConfig pageConfig) throws IOException {
		return downLoadTemple(Configure.TEMPLATE_ROOT + File.separator + pageConfig.getTemplate());
	}

	public static void createNewFile(String content, String filePath) throws IOException {
		OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8");
		BufferedWriter bw = new BufferedWriter(write);
		bw.write(content);
		bw.close();
		write.close();
	}
	
	
	/**
	 * 用于破解验证码
	 * 
	 * @param htmlImage
	 * @return
	 * @throws IOException 
	 */
	public static String getCodeNum(HtmlImage htmlImage) throws IOException {
		InputStream in = htmlImage.getWebResponse(true).getContentAsStream();
		byte[] bs = InputStreamUtils.InputStreamTOByte(in);
		in.close();
		AutoDiscern autoDiscern = new AutoDiscern();
		String yzmNum = autoDiscern.discernPic(bs);
		return yzmNum;
	}
	
}
