package com.log.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.common.action.BaseAction;
import com.log.model.LogInfo;
import com.log.service.LogInfoService;

@Controller
@Scope("prototype")
public class LogInfoAction extends BaseAction {
	@Resource
	private LogInfoService logInfoService;
	private int page = -1;
	private int pages;
	private int rows = -1;
	private LogInfo logInfo;
	private List<LogInfo> logInfos;
	private static final long serialVersionUID = 2219172723902314616L;

	/**
	 * 添加log信息
	 * 
	 * @return
	 */
	public String addLogInfo() {
		logInfo.setLogstatus(LogInfo.LOG_ALIVED);
		logInfoService.addLogInfo(logInfo);
		return SUCCESS;
	}

	/**
	 * 删除log信息
	 * 
	 * @return
	 */
	public String delLogInfo() {
		logInfoService.delLogInfo(logInfo.getId());
		return SUCCESS;
	}

	/**
	 * 查询日志信息，带分页
	 * 
	 * @return
	 */
	public String queryLogInfos() {
		if (page == -1 || rows == -1) {
			logInfos = logInfoService.queryLogInfos();
			page = 1;
			pages = 1;
			rows = logInfos.size();
		} else {
			if (page < 1)
				page = 1;
			if (rows < 1)
				rows = 1;
			logInfos = logInfoService.queryLogInfos(page, rows);
			int cnt = (int) logInfoService.queryLogInfosCnt();
			if (cnt % rows == 0)
				pages = cnt / rows;
			else
				pages = cnt / rows + 1;
		}

		return SUCCESS;
	}

	/**
	 * 查询某条日志信息
	 * 
	 * @return
	 */
	public String queryLogInfo() {
		logInfo = logInfoService.queryLogInfo(logInfo.getId());
		return SUCCESS;
	}

	/**
	 * 查询日志信息，带分页
	 * 
	 * @return
	 */
	public String queryLogInfosByType() {
		if (page == -1 || rows == -1) {
			logInfos = logInfoService.queryLogInfos(logInfo.getLogtype());
			page = 1;
			pages = 1;
			rows = logInfos.size();
		} else {
			if (page < 1)
				page = 1;
			if (rows < 1)
				rows = 1;
			logInfos = logInfoService.queryLogInfos(logInfo.getLogtype(), page,
					rows);
			int cnt = (int) logInfoService.queryLogInfosCnt(logInfo
					.getLogtype());
			if (cnt % rows == 0)
				pages = cnt / rows;
			else
				pages = cnt / rows + 1;
		}

		return SUCCESS;
	}

	public LogInfo getLogInfo() {
		return logInfo;
	}

	public void setLogInfo(LogInfo logInfo) {
		this.logInfo = logInfo;
	}

	public List<LogInfo> getLogInfos() {
		return logInfos;
	}

	public void setLogInfos(List<LogInfo> logInfos) {
		this.logInfos = logInfos;
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
}
