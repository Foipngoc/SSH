package com.log.model;

import java.util.Date;

//日志信息
public class LogInfo {
	private int id;// 日志id
	private int logtype;// 日志类型
	private String loguser;// 日志用户
	private String logtitle;// 日志标题
	private String logcontent;// 日志内容
	private Date logtime;// 日志时间
	private int logstatus;// 日志状态
	
	//LOG已删除
	public static int LOG_DELETED = 0;
	//LOG未删除
	public static int LOG_ALIVED = 1;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLogtype() {
		return logtype;
	}

	public void setLogtype(int logtype) {
		this.logtype = logtype;
	}

	public String getLoguser() {
		return loguser;
	}

	public void setLoguser(String loguser) {
		this.loguser = loguser;
	}

	public String getLogtitle() {
		return logtitle;
	}

	public void setLogtitle(String logtitle) {
		this.logtitle = logtitle;
	}

	public String getLogcontent() {
		return logcontent;
	}

	public void setLogcontent(String logcontent) {
		this.logcontent = logcontent;
	}

	public int getLogstatus() {
		return logstatus;
	}

	public void setLogstatus(int logstatus) {
		this.logstatus = logstatus;
	}

	public Date getLogtime() {
		return logtime;
	}

	public void setLogtime(Date logtime) {
		this.logtime = logtime;
	}
}
