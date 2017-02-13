package com.esd.core;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class CollectionCore {

	public String getTitle(Document htmlSource) {
		String title = htmlSource.select("title").text().trim();
		return title;
	}
	
	public Elements getLinks(Document htmlSource) {
		Elements links = htmlSource.select("a[href],area[href],iframe[src]");
		return links;
	}

}
