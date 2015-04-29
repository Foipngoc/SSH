package com.common.dao;

import java.util.List;

/**
 * 用于返回数据库查询的结果，分页信息等。
 * 
 * @author DongJun
 * 
 */
public class BaseQueryRecords<T> {
	private List<T> data;// 返回数据
	private int total;// 记录总数
	private int page;// 页码
	private int rows;// 每页记录行数
	private int pages;// 总页数

	/**
	 * 默认构造
	 */
	public BaseQueryRecords() {

	}

	/**
	 * 构造函数， 指定分页信息
	 * 
	 * @param data
	 * @param total
	 * @param page
	 * @param rows
	 */
	public BaseQueryRecords(List<T> data, int total, int page, int rows) {
		this.data = data;
		this.total = total;
		this.page = page;
		this.rows = rows;
		this.pages = generatePages(this.total, this.rows);
	}

	/**
	 * 构造函数， 默认数据页数为1
	 * 
	 * @param data
	 */
	public BaseQueryRecords(List<T> data) {
		this.data = data;
		this.total = data.size();
		this.page = 1;
		// 解決 当数据为空的时候， 出现除0错误
		if (this.total == 0)
			this.rows = 1;
		else
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

	/**
	 * 通过总数与每页行数获得总页数
	 * 
	 * @param total
	 * @param rows
	 * @return
	 */
	private int generatePages(int total, int rows) {
		int pages = total / rows;
		if (total % rows != 0) {
			pages++;
		}
		return pages;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
}
