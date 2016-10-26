package com.esd.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.esd.config.BaseConfig;

public class Util {

	private static Logger logger = Logger.getLogger(Util.class);

	public static boolean isNull(String... parms) {
		if (parms == null)
			return true;
		else
			for (String str : parms) {
				if (str == null || str.isEmpty()) {
					return true;
				}
			}
		return false;
	}

	public static boolean isOutUrl(String url) {
		for (String str : BaseConfig.INDEX_URL) {
			if (url.startsWith(str)) {
				return false;
			}
		}
		return true;
	}
	
	public static void doWithOutUrl(String url) {
		Document doc;
		try {
			doc = Util.loadTemplate(BaseConfig.TEMPLATE_ROOT + File.separator + "error.html");
			doc.select("#error").attr("href", url);
			String mName = Util.interceptUrl(url);
			String path = BaseConfig.HTML_ROOT + File.separator + mName;
			try {
				Util.createNewFile(doc.html(), path);
			} catch (IOException e) {
				logger.error(e);
			}
		} catch (IOException e) {
			logger.error(e);
		}
	}
	
	public static String interceptUrl(String url) {
		for (String str : BaseConfig.filterSuffix) {
			if (url.endsWith(str)) {
				return url;
			}
		}
		String m = Md5.getMd5(url);
		return m + ".html";
	}

	public static Document loadTemplate(String templePath) throws IOException {
		String str = "";
		
		StringBuffer sb = new StringBuffer();
		InputStreamReader read = new InputStreamReader(new FileInputStream(templePath), "UTF-8");
		BufferedReader br = new BufferedReader(read);
		while ((str = br.readLine()) != null) {
			sb.append(str);
		}
		read.close();
		br.close();
		Document doc = Jsoup.parse(str);
		return doc;
	}

	public static void createNewFile(String content, String filePath) throws IOException {
		OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8");
		BufferedWriter bw = new BufferedWriter(write);
		bw.write(content);
		bw.close();
		write.close();
	}
	
}
