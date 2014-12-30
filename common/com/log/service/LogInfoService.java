package com.log.service;

import java.util.List;

import com.log.model.LogInfo;

public interface LogInfoService {
	/**
	 * 添加 log
	 * 
	 * @param logInfo
	 * @return
	 */
	public void addLogInfo(LogInfo logInfo);
	
	/**
	 * 删除log
	 * 
	 * @param logid
	 */
	public void delLogInfo(int logid);

	/**
	 * 查找所有log
	 * 
	 * @return
	 */
	public List<LogInfo> queryLogInfos();

	/**
	 * 查找所有log,带分页
	 * 
	 * @return
	 */
	public List<LogInfo> queryLogInfos(int page, int rows);

	/**
	 * 查寻某种类型的log
	 */
	public List<LogInfo> queryLogInfos(int logtype);

	/**
	 * 查寻某种类型的log
	 */
	public List<LogInfo> queryLogInfos(int logtype, int page, int rows);

	/**
	 * 获得日志总数
	 */
	public long queryLogInfosCnt();

	/**
	 * 获得某类型日志总数
	 */
	public long queryLogInfosCnt(int logtype);

	/**
	 * 查找某条Log信息
	 * 
	 * @param logid
	 * @return
	 */
	public LogInfo queryLogInfo(int logid);
}
