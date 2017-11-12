package com.crawler.download;

import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.crawler.config.PageConfig;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class HtmlunitDownLoadHtml {
	private static Logger log = Logger.getLogger(HtmlunitDownLoadHtml.class);
	private static WebClient webClient = null;

	public static void close() {
		if (webClient != null) {
			webClient.close();
		}
	}

	public HtmlunitDownLoadHtml() {
		if (webClient == null) {
			webClient = new WebClient(BrowserVersion.CHROME);
		}
	}

	public Document download(PageConfig pageConfig) {
		try {
			if (pageConfig.isJavaScriptEnabled()) {
				webClient.getOptions().setJavaScriptEnabled(true); // 启用JS解释器，默认为true
			} else {
				webClient.getOptions().setJavaScriptEnabled(false); // 启用JS解释器，默认为true
			}
			if (pageConfig.isCssEnabled()) {
				webClient.getOptions().setCssEnabled(true); // 禁用css支持
			} else {
				webClient.getOptions().setCssEnabled(false); // 禁用css支持
			}
			webClient.getOptions().setThrowExceptionOnScriptError(false); // js运行错误时，是否抛出异常
			webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
			webClient.getOptions().setTimeout(10000); // 设置连接超时时间
			HtmlPage htmlPage = webClient.getPage(pageConfig.getUrl());
			Thread.sleep(pageConfig.getSleep());
			Document document = Jsoup.parse(htmlPage.asXml(), pageConfig.getUrl());
			htmlPage = null;
			return document;
		} catch (InterruptedException e) {
			log.error(e);
		} catch (FailingHttpStatusCodeException e) {
			log.error(e);
		} catch (MalformedURLException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		} finally {
			if (webClient != null) {
				webClient.close();
			}
		}
		return null;

	}

}
