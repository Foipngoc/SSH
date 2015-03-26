package com.module.appVersionCheck.model;

import java.util.Date;

/**
 * APP下载信息
 * 
 * @author DJ
 * 
 */
public class AppDownloadInfo {
	private int id; // 自增主键
	private int appid; // 应用id
	private int oldappvid; // 原版本id
	private int appvid; // 版本id
	private Date updatedate; // 更新时间
	private String clientinfo; // 客户端信息

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAppid() {
		return appid;
	}

	public void setAppid(int appid) {
		this.appid = appid;
	}

	public int getOldappvid() {
		return oldappvid;
	}

	public void setOldappvid(int oldappvid) {
		this.oldappvid = oldappvid;
	}

	public int getAppvid() {
		return appvid;
	}

	public void setAppvid(int appvid) {
		this.appvid = appvid;
	}

	public Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public String getClientinfo() {
		return clientinfo;
	}

	public void setClientinfo(String clientinfo) {
		this.clientinfo = clientinfo;
	}
}
