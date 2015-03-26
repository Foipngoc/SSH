package com.module.appVersionCheck.action;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;

import com.common.action.BaseAction;
import com.common.action.BaseResult;
import com.module.appVersionCheck.model.AppInfo;
import com.module.appVersionCheck.model.AppVersionInfo;
import com.module.appVersionCheck.service.AppVersionCheckService;

@Namespace(value = "/module/AppVersionCheck")
public class AppVersionCheckAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7479732448969184794L;

	@Resource
	private AppVersionCheckService appVersionCheckService;

	private int appvid = -1;
	private int versioncode = -1; // 版本号
	private String versionname; // 版本名，用于显示
	private String updatelog; // 更新日志
	private int updatetype; // 该版本相对上一版本的更新方式
	private String downloadpath; // APP下载到本地路径
	private int autoopen = -1; // 更新后是否自动打开

	private String clientinfo; // 更新客户端信息

	private boolean autoset;

	private int appid = -1;
	private String appname;
	private String appdesc;

	private BaseResult result;
	private InputStream inputStream;
	private String filename;
	private List<File> file;
	private List<String> fileFileName;
	private List<String> fileContentType;

	/**
	 * 获得所有已发布的应用信息
	 */
	@Action(value = "queryApps", results = { @Result(name = "success", type = "json") })
	public String queryApps() {
		List<?> appInfos = this.appVersionCheckService.queryApps();
		result = new BaseResult();
		result.addToMap("appinfos", appInfos);
		return SUCCESS;
	}

	/**
	 * 发布新的应用
	 */
	@Action(value = "publishApp", results = { @Result(name = "success", type = "json") })
	public String publishApp() {
		if (appname == null || appname.equals("") || appdesc == null
				|| appdesc.equals("")) {
			result = new BaseResult(-1, "参数错误");
			return SUCCESS;
		}
		AppInfo appInfo = new AppInfo();
		appInfo.setAppname(appname);
		appInfo.setAppdesc(appdesc);
		result = this.appVersionCheckService.publishApp(appInfo);
		return SUCCESS;
	}

	/**
	 * 删除已发布的应用
	 * 
	 * @return
	 */
	@Action(value = "delApp", results = { @Result(name = "success", type = "json") })
	public String delApp() {
		this.appVersionCheckService.delApp(appid);
		return SUCCESS;
	}

	/**
	 * 更新已发布应用
	 */
	@Action(value = "updateApp", results = { @Result(name = "success", type = "json") })
	public String updateApp() {
		result = this.appVersionCheckService.updateApp(appid, appname, appdesc);
		return SUCCESS;
	}

	/**
	 * 发布应用新版本
	 */
	@Action(value = "publishAppVersion", results = { @Result(name = "success", type = "json") })
	public String publishAppVersion() {
		if (versioncode == -1 || versionname == null || versionname.equals("")
				|| updatelog == null || updatelog.equals("")
				|| downloadpath == null || downloadpath.equals("")
				|| autoopen == -1)
			result = new BaseResult(-1, "参数错误");
		result = this.appVersionCheckService.publishAppVersion(appid,
				versioncode, versionname, updatelog, updatetype, autoset,
				downloadpath, autoopen);
		return SUCCESS;
	}

	/**
	 * 添加应用版本资源 ， 如果已存在，则替换
	 */
	@Action(value = "addAppVersionRes", results = { @Result(name = "success", type = "json") })
	public String addAppVersionRes() {
		result = this.appVersionCheckService.addAppVersionRes(appvid,
				file.get(0), fileFileName.get(0));
		return SUCCESS;
	}

	/**
	 * 设置APP最新的版本为哪个
	 */
	@Action(value = "updateNewestAppVersion", results = { @Result(name = "success", type = "json") })
	public String updateNewestAppVersion() {
		result = this.appVersionCheckService.setNewestAppVersion(appid, appvid);
		return SUCCESS;
	}

	/**
	 * 更新应用已发布版本信息
	 */
	@Action(value = "updateAppVersion", results = { @Result(name = "success", type = "json") })
	public String updateAppVersion() {
		result = this.appVersionCheckService.updateAppVersion(appvid,
				versionname, updatelog, updatetype, downloadpath, autoopen);
		return SUCCESS;
	}

	/**
	 * 删除应用已发布的版本, 注： 如果当前用户已安装了被删除的版本，则用户更新时会提醒强制更新
	 */
	@Action(value = "deleteAppVersion", results = { @Result(name = "success", type = "json") })
	public String deleteAppVersion() {
		this.appVersionCheckService.deleteAppVersion(appvid);
		return SUCCESS;
	}

	/**
	 * 获取某应用发布的所有版本信息
	 */
	@Action(value = "queryAppVersions", results = { @Result(name = "success", type = "json") })
	public String queryAppVersions() {
		List<AppVersionInfo> appVersionInfos = this.appVersionCheckService
				.queryAppVersions(appid);
		result = new BaseResult();
		result.addToMap("appversioninfos", appVersionInfos);
		return SUCCESS;
	}

	/**
	 * 获取某应用的某版本信息
	 */
	@Action(value = "queryAppVersion", results = { @Result(name = "success", type = "json") })
	public String queryAppVersion() {
		AppVersionInfo appVersionInfoR = this.appVersionCheckService
				.queryAppVersion(appvid);
		result = new BaseResult();
		result.addToMap("appVersionInfo", appVersionInfoR);
		return SUCCESS;
	}

	/**
	 * 检查新版本信息
	 */
	@Action(value = "checkNewestAppVersion", results = { @Result(name = "success", type = "json") })
	public String checkNewestAppVersion() {
		result = this.appVersionCheckService.checkNewestAppVersion(appid,
				versioncode);
		return SUCCESS;
	}

	/**
	 * 下载最新版本
	 */
	@Action(value = "downloadNewestAppVersionRes", results = {
			@Result(name = "stream", type = "stream", params = {
					"contentDisposition", "attachment;filename='${filename}'",
					"bufferSize", "4096" }),
			@Result(name = "json", type = "json") })
	public String downloadNewestAppVersionRes() {
		BaseResult result2 = this.appVersionCheckService
				.downloadNewestAppVersionRes(appid, versioncode, clientinfo);

		if (result2.getResultcode() == 2) {
			inputStream = (InputStream) result2.getObj();
			filename = (String) result2.getFromMap("filename");
			return "stream";
		} else {
			result = result2;
			return "json";
		}
	}

	public BaseResult getResult() {
		return result;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setFile(List<File> file) {
		this.file = file;
	}

	public void setFileFileName(List<String> fileFileName) {
		this.fileFileName = fileFileName;
	}

	public void setFileContentType(List<String> fileContentType) {
		this.fileContentType = fileContentType;
	}

	public List<File> getFile() {
		return file;
	}

	public List<String> getFileFileName() {
		return fileFileName;
	}

	public List<String> getFileContentType() {
		return fileContentType;
	}

	public String getFilename() {
		return filename;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}

	public void setAppdesc(String appdesc) {
		this.appdesc = appdesc;
	}

	public void setAppid(int appid) {
		this.appid = appid;
	}

	public void setAppvid(int appvid) {
		this.appvid = appvid;
	}

	public void setVersioncode(int versioncode) {
		this.versioncode = versioncode;
	}

	public void setVersionname(String versionname) {
		this.versionname = versionname;
	}

	public void setUpdatelog(String updatelog) {
		this.updatelog = updatelog;
	}

	public void setDownloadpath(String downloadpath) {
		this.downloadpath = downloadpath;
	}

	public void setAutoopen(int autoopen) {
		this.autoopen = autoopen;
	}

	public void setUpdatetype(int updatetype) {
		this.updatetype = updatetype;
	}

	public void setAutoset(boolean autoset) {
		this.autoset = autoset;
	}

	public void setClientinfo(String clientinfo) {
		this.clientinfo = clientinfo;
	}
}
