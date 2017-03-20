package com.esd.bs.entity;

import java.util.ArrayList;
import java.util.List;

import com.esd.collection.History;

public class SearchResult {
	private List<History> history = new ArrayList<History>();
	private String serCon;// 搜索内容
	private int currentPage;// 当前页数
	private Long total;// 搜索总条数
	private int totalPage;// 总共多少页

	public List<History> getHistory() {
		return history;
	}

	public void setHistory(List<History> history) {
		this.history = history;
	}

	public String getSerCon() {
		return serCon;
	}

	public void setSerCon(String serCon) {
		this.serCon = serCon;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

}
