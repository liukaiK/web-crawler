package com.esd.collection;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class DbFile {

	private String id;
//	private String userId = "0001";
	private String fileName;
	private byte[] fileByte;
	private String filedir;
	private String md5File;
//	private Date createDate;
	private String updateDate;
	private String siteName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

//	public String getUserId() {
//		return userId;
//	}
//
//	public void setUserId(String userId) {
//		this.userId = userId;
//	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getFileByte() {
		return fileByte;
	}

	public void setFileByte(byte[] fileByte) {
		this.fileByte = fileByte;
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

//	public Date getCreateDate() {
//		return createDate;
//	}
//
//	public void setCreateDate(Date createDate) {
//		this.createDate = createDate;
//	}


	public String getSiteName() {
		return siteName;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
}
