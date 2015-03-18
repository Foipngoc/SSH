package com.module.appVersionCheck.model;

import java.util.Date;

/**
 * 每一个版本的配置文件
 * 
 * @author DJ
 * 
 */
public class AppVersionInfo {
	private int id; // 自增主键
	private int appid; // 所属应用
	private int versioncode; // 版本号
	private String versionname; // 版本名，用于显示
	private String updatelog; // 更新日志
	private Date updatedate; // 更新时间
	private String respath; // 资源路径
	private int updatetype; // 该版本相对上一版本的更新方式

	/**
	 * 自动弹出更新通知，且要求用户强制更新，如用户选择不更新则不允许使用，直接退出。
	 */
	public static final int UPDATE_TYPE_POP_FORCE = 0;

	/**
	 * 自动弹出更新通知，允许用户选择更新或不更新，可以继续使用。
	 */
	public static final int UPDATE_TYPE_POP_AUTO = 1;

	/**
	 * 不自动弹出更新通知，在用户点击检查更新之后弹出更新通知，允许用户选择更新或不更新
	 */
	public static final int UPDATE_TYPE_MANUAL_MANUAL = 2;

	public String getVersionname() {
		return versionname;
	}

	public void setVersionname(String versionname) {
		this.versionname = versionname;
	}

	public String getUpdatelog() {
		return updatelog;
	}

	public void setUpdatelog(String updatelog) {
		this.updatelog = updatelog;
	}

	public Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public String getRespath() {
		return respath;
	}

	public void setRespath(String respath) {
		this.respath = respath;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getVersioncode() {
		return versioncode;
	}

	public void setVersioncode(int versioncode) {
		this.versioncode = versioncode;
	}

	public int getUpdatetype() {
		return updatetype;
	}

	public void setUpdatetype(int updatetype) {
		this.updatetype = updatetype;
	}

	public int getAppid() {
		return appid;
	}

	public void setAppid(int appid) {
		this.appid = appid;
	}
}
