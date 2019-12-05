package com.webcrawler.download;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.webcrawler.config.PageConfig;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

@Component
public class DownLoadHtml {

	private WebClient webClient = new WebClient(BrowserVersion.CHROME);
	
	public Document downloadHtml(PageConfig pageConfig) throws IOException, InterruptedException {
		Document htmlSource = null;
		String url = pageConfig.getUrl();
		if (pageConfig.isJavaScriptEnabled() == false) {
			htmlSource = Jsoup.connect(url).get();
		} else {
			webClient.getOptions().setJavaScriptEnabled(true); // 启用JS解释器，默认为true
			webClient.getOptions().setCssEnabled(false); // 禁用css支持
			webClient.getOptions().setThrowExceptionOnScriptError(false); // js运行错误时，是否抛出异常
			webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
			webClient.getOptions().setTimeout(10000); // 设置连接超时时间
			HtmlPage HtmlPage = webClient.getPage(url);
			Thread.sleep(pageConfig.getSleep());
			htmlSource = Jsoup.parse(HtmlPage.asXml(), url);
		}
		return htmlSource;
	}
	
}
