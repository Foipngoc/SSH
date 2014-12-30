package com.log.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.common.dao.impl.HQL;
import com.common.utils.DateTimeUtil;
import com.log.dao.LogInfoDao;
import com.log.model.LogInfo;
import com.log.service.LogInfoService;

@SuppressWarnings("all")
@Transactional
@Service("logInfoService")
public class LogInfoServiceImpl implements LogInfoService {
	@Resource
	private LogInfoDao logInfoDao;

	/**
	 * 添加 log
	 * 
	 * @param logInfo
	 * @return
	 */
	public void addLogInfo(LogInfo logInfo) {
		logInfo.setLogtime(new Date());
		logInfo.setLogstatus(LogInfo.LOG_ALIVED);
		logInfoDao.save(logInfo);
	}

	/**
	 * 删除log
	 * 
	 * @param logid
	 */
	public void delLogInfo(int logid) {
		logInfoDao.delete(logInfoDao.find(new LogInfo(), "id", logid).get(0));
	}

	/**
	 * 查找所有log
	 * 
	 * @return
	 */
	public List<LogInfo> queryLogInfos() {
		return (List<LogInfo>) logInfoDao.find(new LogInfo(), "logstatus",
				LogInfo.LOG_ALIVED);
	}

	/**
	 * 查找所有log,带分页
	 * 
	 * @return
	 */
	public List<LogInfo> queryLogInfos(int page, int rows) {
		return (List<LogInfo>) logInfoDao.find(new LogInfo(), "logstatus",
				LogInfo.LOG_ALIVED, page, rows);
	}

	/**
	 * 查寻某种类型的log
	 */
	public List<LogInfo> queryLogInfos(int logtype) {
		return logInfoDao.find(logtype, LogInfo.LOG_ALIVED);
	}

	/**
	 * 查寻某种类型的log
	 */
	public List<LogInfo> queryLogInfos(int logtype, int page, int rows) {
		return logInfoDao.find(logtype, LogInfo.LOG_ALIVED, page, rows);
	}

	/**
	 * 获得日志总数
	 */
	public long queryLogInfosCnt() {
		return logInfoDao.count(new LogInfo(), "logstatus", LogInfo.LOG_ALIVED);
	}

	/**
	 * 获得某类型日志总数
	 */
	public long queryLogInfosCnt(int logtype) {
		return logInfoDao.count(logtype, LogInfo.LOG_ALIVED);
	}

	/**
	 * 查找某条Log信息
	 * 
	 * @param logid
	 * @return
	 */
	public LogInfo queryLogInfo(int logid) {
		return logInfoDao.findByID(logid, LogInfo.LOG_ALIVED);
	}
}
