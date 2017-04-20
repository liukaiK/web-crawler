package com.esd.collection;

import org.springframework.data.mongodb.core.mapping.Document;

import com.esd.config.PageConfig;

@Document
public class DbPgFile {
	private String id;
//	private String userId;
	private String fileName;
	private PageConfig pageConfig;	
	private String filedir;
	private String md5File;
//	private String createDate;
	private String updateDate;
	private String siteName;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public PageConfig getPageConfig() {
		return pageConfig;
	}
	public void setPageConfig(PageConfig pageConfig) {
		this.pageConfig = pageConfig;
	}
	public String getFiledir() {
		return filedir;
	}
	public void setFiledir(String filedir) {
		this.filedir = filedir;
	}
	public String getMd5File() {
		return md5File;
	}
	public void setMd5File(String md5File) {
		this.md5File = md5File;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	
	
	
	
}
