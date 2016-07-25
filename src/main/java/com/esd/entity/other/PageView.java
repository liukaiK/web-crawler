package com.esd.entity.other;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 网站浏览人数
 * 
 * @author liukai
 * 
 */
@Document
public class PageView {
	private String id;
	private String pageView;

	public PageView(String pageView) {
		this.pageView = pageView;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPageView() {
		return pageView;
	}

	public void setPageView(String pageView) {
		this.pageView = pageView;
	}

}
