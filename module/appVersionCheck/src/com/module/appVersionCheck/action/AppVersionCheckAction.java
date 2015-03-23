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

	private AppVersionInfo appVersionInfo;
	private AppInfo appInfo;

	private BaseResult result;
	private InputStream inputStream;
	private String filename;
	private List<File> file;
	private List<String> fileFileName;
	private List<String> fileContentType;

	/**
	 * 获得所有已发布的应用信息
	 */
	@Action(value = "queryAppInfos", results = { @Result(name = "success", type = "json") })
	public String queryAppInfos() {
		List<AppInfo> appInfos = this.appVersionCheckService.queryApps();
		result = new BaseResult();
		result.addToMap("appinfos", appInfos);
		return SUCCESS;
	}

	/**
	 * 发布新的应用
	 */
	@Action(value = "publishAppInfos", results = { @Result(name = "success", type = "json") })
	public String publishAppInfos() {
		this.appVersionCheckService.publishApp(appInfo);
		return SUCCESS;
	}

	/**
	 * 删除已发布的应用
	 * 
	 * @return
	 */
	@Action(value = "delApp", results = { @Result(name = "success", type = "json") })
	public String delApp() {
		this.appVersionCheckService.delApp(appInfo.getId());
		return SUCCESS;
	}

	/**
	 * 更新已发布应用
	 */
	@Action(value = "modifyApp", results = { @Result(name = "success", type = "json") })
	public String modifyApp() {
		result = this.appVersionCheckService.modifyApp(appInfo.getId(),
				appInfo.getAppname(), appInfo.getAppdesc());
		return SUCCESS;
	}

	/**
	 * 发布应用新版本
	 */
	@Action(value = "publishAppVersion", results = { @Result(name = "success", type = "json") })
	public String publishAppVersion() {
		if (file != null && file.size() > 0)
			result = this.appVersionCheckService.publishAppVersion(
					appVersionInfo.getAppid(), appVersionInfo, file.get(0),
					fileFileName.get(0));
		else
			result = this.appVersionCheckService.publishAppVersion(
					appVersionInfo.getAppid(), appVersionInfo, null, null);

		return SUCCESS;
	}

	/**
	 * 设置APP最新的版本为哪个
	 */
	@Action(value = "modifyNewestAppVersion", results = { @Result(name = "success", type = "json") })
	public String modifyNewestAppVersion() {
		result = this.appVersionCheckService.setNewestAppVersion(
				appInfo.getId(), appVersionInfo.getId());
		return SUCCESS;
	}

	/**
	 * 更新应用已发布版本信息
	 */
	@Action(value = "updateAppVersion", results = { @Result(name = "success", type = "json") })
	public String updateAppVersion() {
		result = this.appVersionCheckService.updateAppVersion(appVersionInfo);
		return SUCCESS;
	}

	/**
	 * 删除应用已发布的版本, 注： 如果当前用户已安装了被删除的版本，则用户更新时会提醒强制更新
	 */
	@Action(value = "deleteAppVersion", results = { @Result(name = "success", type = "json") })
	public String deleteAppVersion() {
		this.appVersionCheckService.deleteAppVersion(appVersionInfo.getId());
		return SUCCESS;
	}

	/**
	 * 获取某应用发布的所有版本信息
	 */
	@Action(value = "queryAppVersions", results = { @Result(name = "success", type = "json") })
	public String queryAppVersions() {
		List<AppVersionInfo> appVersionInfos = this.appVersionCheckService
				.queryAppVersions(appInfo.getId());
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
				.queryAppVersion(appVersionInfo.getId());
		result = new BaseResult();
		result.addToMap("appVersionInfo", appVersionInfoR);
		return SUCCESS;
	}

	/**
	 * 检查新版本信息
	 */
	@Action(value = "checkNewestAppVersion", results = { @Result(name = "success", type = "json") })
	public String checkNewestAppVersion() {
		result = this.appVersionCheckService.checkNewestAppVersion(
				appInfo.getId(), appVersionInfo.getVersioncode());
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
				.downloadNewestAppVersionRes(appInfo.getId(),
						appVersionInfo.getVersioncode());

		if (result2.getResultcode() == 2) {
			inputStream = (InputStream) result2.getObj();
			filename = (String) result2.getFromMap("filename");
			return "stream";
		} else {
			result = result2;
			return "json";
		}
	}

	public void setAppVersionInfo(AppVersionInfo appVersionInfo) {
		this.appVersionInfo = appVersionInfo;
	}

	public BaseResult getResult() {
		return result;
	}

	public void setAppInfo(AppInfo appInfo) {
		this.appInfo = appInfo;
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
}
