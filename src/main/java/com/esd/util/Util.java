package com.esd.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.esd.common.AutoDiscern;
import com.esd.common.InputStreamUtils;
import com.esd.config.BaseConfig;
import com.gargoylesoftware.htmlunit.html.HtmlImage;

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
