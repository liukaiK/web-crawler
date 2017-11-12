package com.crawler.download;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;

import com.crawler.config.PageConfig;

public class EsdDownLoadHtml {
	private static Logger log = Logger.getLogger(EsdDownLoadHtml.class);

	public Document downloadHtml(PageConfig pageConfig) {

		Document doc = null;
		String url = pageConfig.getUrl();
		if (url == null) {
			return null;
		}
		try {
			if (pageConfig.isJavaScriptEnabled() == false) {
				JsoupDownLoadHtml jsoup = new JsoupDownLoadHtml();
				doc = jsoup.download(pageConfig);

			} else {
				HtmlunitDownLoadHtml unit = new HtmlunitDownLoadHtml();
				doc = unit.download(pageConfig);
			}
		} catch (IOException e) {
			log.error(url + "----下载html源代码错误!!!!");
		}
		return doc;

	}
}
