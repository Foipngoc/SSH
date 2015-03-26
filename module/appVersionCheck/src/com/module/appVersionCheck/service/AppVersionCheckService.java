package com.module.appVersionCheck.service;

import java.io.File;
import java.util.List;

import com.common.action.BaseResult;
import com.module.appVersionCheck.model.AppInfo;
import com.module.appVersionCheck.model.AppVersionInfo;

public interface AppVersionCheckService {
	/**
	 * 获得所有已发布的应用信息
	 * 
	 * @return
	 */
	public List<AppInfo> queryApps();

	/**
	 * 获取某一已发布的应用信息
	 * 
	 * @param appid
	 * @return
	 */
	public AppInfo queryApp(int appid);

	/**
	 * 发布新的应用
	 * 
	 * @param appVersionCheckInfo
	 */
	public BaseResult publishApp(AppInfo appInfo);

	/**
	 * 删除已发布的应用
	 * 
	 * @param appid
	 */
	public void delApp(int appid);

	/**
	 * 更新已发布应用
	 * 
	 * @param appInfo
	 */
	public BaseResult updateApp(int appid, String appname, String appdesc);

	/**
	 * 发布应用新版本
	 * 
	 * @param appid
	 * @param appVersionInfo
	 * @return
	 */
	public BaseResult publishAppVersion(int appid, int versioncode,
			String versionname, String updatelog, int updatetype,
			boolean autoset, String downloadpath, int autoopen);

	/**
	 * 添加应用版本资源 ， 如果已存在，则替换
	 * 
	 * @return
	 */
	public BaseResult addAppVersionRes(int appvid, File file, String filename);

	/**
	 * 设置APP最新的版本为哪个
	 * 
	 * @param appid
	 * @param appvid
	 * @return
	 */
	public BaseResult setNewestAppVersion(int appid, int appvid);

	/**
	 * 更新应用已发布版本信息
	 * 
	 * @param appVersionInfo
	 */
	public BaseResult updateAppVersion(int appvid, String versionname,
			String updatelog, int updatetype, String downloadpath, int autoopen);

	/**
	 * 删除应用已发布的版本, 注： 如果当前用户已安装了被删除的版本，则用户更新时会提醒强制更新
	 * 
	 * @param appvid
	 */
	public void deleteAppVersion(int appvid);

	/**
	 * 获取某应用发布的所有版本信息
	 * 
	 * @param appid
	 * @return
	 */
	public List<AppVersionInfo> queryAppVersions(int appid);

	/**
	 * 获取某应用的某版本信息
	 * 
	 * @param appid
	 * @param appvid
	 * @return
	 */
	public AppVersionInfo queryAppVersion(int appvid);

	/**
	 * 通过版本号获取某应用的版本信息
	 * 
	 * @param appid
	 * @param vcode
	 * @return
	 */
	public AppVersionInfo queryAppVersion(int appid, int vcode);

	/**
	 * 检查新版本信息
	 * 
	 * @param appid
	 * @param oldversioncode
	 * @return
	 */
	public BaseResult checkNewestAppVersion(int appid, int oldversioncode);

	/**
	 * 下载最新版本
	 * 
	 * @param appid
	 * @return
	 */
	public BaseResult downloadNewestAppVersionRes(int appid, int oldvesioncode,
			String clientinfo);
}
