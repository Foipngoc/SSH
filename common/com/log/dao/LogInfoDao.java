package com.log.dao;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import com.log.model.LogInfo;

@SuppressWarnings("all")
@Repository("logInfoDao")
public class LogInfoDao extends BaseDaoDB<LogInfo> {
	public List<LogInfo> find(int logtype, int logstatus) {
		return super.find(new LogInfo(),
				Restrictions.eq("logstatus", logstatus),
				Restrictions.eq("logtype", logtype));
	}

	public List<LogInfo> find(int logtype, int logstatus, int page, int rows) {
		return super.find(new LogInfo(), page, rows,
				Restrictions.eq("logstatus", logstatus),
				Restrictions.eq("logtype", logtype));
	}

	public long count(int logtype, int logstatus) {
		return super.count(new LogInfo(),
				Restrictions.eq("logstatus", logstatus),
				Restrictions.eq("logtype", logtype));
	}

	public LogInfo findByID(int logid, int logstatus) {
		return find(new LogInfo(), Restrictions.eq("id", logid),
				Restrictions.eq("logstatus", logstatus)).get(0);
	}
}
