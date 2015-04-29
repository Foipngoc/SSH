package com.module.appversioncheck.model;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class AppInfo {
	private int id; // 自增ID
	private String appname; // 应用名
	private String appdesc; // 应用描述
	private Date createdate; // 创建时间
	private int newestappvid; // 最新版版本

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}

	public String getAppdesc() {
		return appdesc;
	}

	public void setAppdesc(String appdesc) {
		this.appdesc = appdesc;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public int getNewestappvid() {
		return newestappvid;
	}

	public void setNewestappvid(int newestappvid) {
		this.newestappvid = newestappvid;
	}
}
