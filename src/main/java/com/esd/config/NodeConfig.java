package com.esd.config;

import org.jsoup.nodes.Element;

import java.io.Serializable;

public class NodeConfig implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -7044726743607359193L;
	private String des;// 备注
	private String parent;// 父类
	private String tag;// 标签
	private String type; // class id 类型
	private String name;// 名称
	private int index; // 第几个标签 序号
	private String anchorId; // 装入的模版ID 。目前只支持ID
	private Element src; // 文本源;

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getAnchorId() {
		return anchorId;
	}

	public void setAnchorId(String anchorId) {
		this.anchorId = anchorId;
	}

	public Element getSrc() {
		return src;
	}

	public void setSrc(Element src) {
		this.src = src;
	}

	public void setIndex(String index) {
		if (index == null || index.equals("") || index.equals("0")) {
			return;
		} else {
			this.index = Integer.valueOf(index);
		}
	}

}
