package com.common.dao;

import java.util.List;

/**
 * 用于返回数据库查询的结果，分页信息等。
 * 
 * @author DongJun
 * 
 */
public class BaseQueryRecords {
	private List<?> data;// 返回数据
	private int total;// 记录总数
	private int page;// 页码
	private int rows;// 每页记录行数
	private int pages;// 总页数

	public BaseQueryRecords() {

	}

	public BaseQueryRecords(List<?> data, int total, int page, int rows) {
		this.data = data;
		this.total = total;
		this.page = page;
		this.rows = rows;
		this.pages = generatePages(this.total, this.rows);
	}

	public BaseQueryRecords(List<?> data) {
		this.data = data;
		this.total = data.size();
		this.page = 1;
		this.rows = this.total;
		this.pages = generatePages(this.total, this.rows);
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	private int generatePages(int total, int rows) {
		int pages = total / rows;
		if (total % rows != 0) {
			pages++;
		}
		return pages;
	}

	public List<?> getData() {
		return data;
	}

	public void setData(List<?> data) {
		this.data = data;
	}
}
