package com.esd.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PageConfig implements Serializable {

	private static final long serialVersionUID = 5218668022804330547L;

	private boolean javaScriptEnabled = false;
	private boolean cssEnabled = false;
	private List<String> urls;
	private String url;
	private String template;
	private long sleep;
	private List<NodeConfig> list = new ArrayList<NodeConfig>(3);
	private String rule;
	private String db;

	public String getDb() {
		return db;
	}

	public void setDb(String db) {
		this.db = db;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public long getSleep() {
		return sleep;
	}

	public void setSleep(long sleep) {
		this.sleep = sleep;
	}

	public boolean isJavaScriptEnabled() {
		return javaScriptEnabled;
	}

	public void setJavaScriptEnabled(boolean javaScriptEnabled) {
		this.javaScriptEnabled = javaScriptEnabled;
	}

	public boolean isCssEnabled() {
		return cssEnabled;
	}

	public void setCssEnabled(boolean cssEnabled) {
		this.cssEnabled = cssEnabled;
	}

	public List<String> getUrls() {
		return urls;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public List<NodeConfig> getList() {
		return list;
	}

	public void setList(List<NodeConfig> list) {
		this.list = list;
	}

}
