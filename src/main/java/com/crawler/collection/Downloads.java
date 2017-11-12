package com.crawler.collection;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Downloads {
	private String id;
	private String url;

	public Downloads(String url) {
		this.url = url;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
