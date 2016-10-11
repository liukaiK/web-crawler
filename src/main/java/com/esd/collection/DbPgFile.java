package com.esd.collection;

import java.util.Date;

import com.esd.config.PageConfig;

public class DbPgFile {
	
	private int id;
	private String userId;
	private String fileName;
	private PageConfig pageConfig;	
	private String filedir;
	private String md5File;
	private Date createDate;
	private Date updateDate;
	private String siteName;
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
}
