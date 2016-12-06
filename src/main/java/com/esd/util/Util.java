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
import org.springframework.data.mongodb.core.query.Criteria;

import com.esd.collection.DbFile;
import com.esd.collection.Site;
import com.esd.common.MongoDBUtil;
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

	public static boolean isOutUrl(String url,String siteId) {
		MongoDBUtil mongoDBUtil = (MongoDBUtil)SpringContextUtil.getBean("mongoDBUtil");
		Criteria criatira = new Criteria();
		criatira.andOperator(Criteria.where("id").is(siteId));
		Site site = mongoDBUtil.findOneByCollectionName(BaseConfig.SITES, criatira, Site.class);
		String domain = site.getDomainName();
		if (url.startsWith(domain)) {
			return false;
		}
//		for (String str : BaseConfig.INDEX_URL) {
//			if (url.startsWith(str)) {
//				return false;
//			}
//		}
		return true;
	}
	
	public static void doWithOutUrl(String url,String siteId) {
		Document doc;
		try {
			//doc = Util.loadTemplate(BaseConfig.TEMPLATE_ROOT + File.separator + "error.html");
			doc = Util.loadTemplate("error.html",siteId,2);
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

	public static Document loadTemplate(String fileName,String siteId,int m) throws IOException {
		String str = "";
		if(m == 1){
			StringBuffer sb = new StringBuffer();
			InputStreamReader read = new InputStreamReader(new FileInputStream(fileName), "UTF-8");
			BufferedReader br = new BufferedReader(read);
			while ((str = br.readLine()) != null) {
				sb.append(str);
			}
			read.close();
			br.close();
		}else if(m == 2){
			MongoDBUtil mongoDBUtil = (MongoDBUtil)SpringContextUtil.getBean("mongoDBUtil");
			Criteria criatira = new Criteria();
			criatira.andOperator(Criteria.where("fileName").is(fileName));
			DbFile df = mongoDBUtil.findOneByCollectionName(siteId+"_template", criatira, DbFile.class);
			str = new String(df.getFileByte(),"utf-8");
		}	
		Document doc = Jsoup.parse(str);
		return doc;
	}
	/**
	 * 20161031-31
	 * @param content
	 * @param filePath
	 * @throws IOException
	 */
	public static void createNewFile(String content, String filePath) throws IOException {
		OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8");
		BufferedWriter bw = new BufferedWriter(write);
		bw.write(content);
		bw.close();
		write.close();
	}
	
}
