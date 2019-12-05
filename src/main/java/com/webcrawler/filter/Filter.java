package com.webcrawler.filter;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class Filter extends BaseFilter {

	public void filter(Element element) {
		super.filter(element);
		filterMeta(element);
	}

	private void filterMeta(Element element) {
		Elements links = element.select("meta");
		for (Element link : links) {
			Element content = link.attr("http-equiv", "refresh");
			String con = content.attr("content");
			String beg = con.substring(0, con.indexOf("=") + 1);
			String end = con.substring(con.indexOf("/") + 1);
			content.attr("content", beg + element.baseUri() + end);
		}

	}
}
