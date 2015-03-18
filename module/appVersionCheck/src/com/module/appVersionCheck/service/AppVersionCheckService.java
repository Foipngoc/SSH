package com.module.appVersionCheck.service;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.junit.runner.Result;
import org.springframework.stereotype.Service;

import com.common.action.BaseResult;
import com.common.service.BaseService;
import com.common.utils.DateTimeUtil;
import com.module.appVersionCheck.dao.AppInfoDao;
import com.module.appVersionCheck.dao.AppVersionInfoDao;
import com.module.appVersionCheck.model.AppInfo;
import com.module.appVersionCheck.model.AppVersionInfo;

@Service("appVersionCheckService")
@SuppressWarnings("all")
public class AppVersionCheckService extends BaseService {

	@Resource
	private AppInfoDao appInfoDao;

	@Resource
	private AppVersionInfoDao appVersionInfoDao;

	/**
	 * 获得所有已发布的应用信息
	 * 
	 * @return
	 */
	public List<AppInfo> queryAppInfos() {
		return (List<AppInfo>) this.appInfoDao.findOrderBy(new AppInfo(),
				"createdate", true).getData();
	}

	/**
	 * 获取某一已发布的应用信息
	 * 
	 * @param appid
	 * @return
	 */
	public AppInfo queryAppInfo(int appid) {
		return this.appInfoDao.findUnique(new AppInfo(), "id", appid);
	}

	/**
	 * 发布新的应用
	 * 
	 * @param appVersionCheckInfo
	 */
	public BaseResult publishApp(AppInfo appInfo) {
		// 检查应用名是否重复
		List<AppInfo> appInfos = (List<AppInfo>) this.appInfoDao.find(
				new AppInfo(), "appname", appInfo.getAppname()).getData();

		if (appInfos != null && appInfos.size() > 0) {
			return new BaseResult(1, "应用名已存在");
		}
		appInfo.setCreatedate(DateTimeUtil.getCurrDate());
		appInfo.setNewestappvid(-1);
		this.appInfoDao.save(appInfo);
		BaseResult result = new BaseResult(0, "发布成功");
		return result;
	}

	/**
	 * 删除已发布的应用
	 * 
	 * @param appid
	 */
	public void delApp(int appid) {
		AppInfo appInfo = this.queryAppInfo(appid);
		if (appInfo != null) {
			// 删除应用的所有版本
			this.deleteAppVersion(appid);
			// 刪除该应用
			this.appInfoDao.delete(appInfo);
		}
	}

	/**
	 * 更新已发布应用
	 * 
	 * @param appInfo
	 */
	public BaseResult modifyApp(int appid, String appname, String appdesc) {

		AppInfo appInfo = this.queryAppInfo(appid);

		if (appInfo == null)
			return new BaseResult(2, "未找到应用");
		if (appname != null && !appname.equals(appInfo.getAppname())) {
			// 检查应用名是否重复
			List<AppInfo> appInfos = (List<AppInfo>) this.appInfoDao.find(
					new AppInfo(), "appname", appname).getData();

			if (appInfos != null && appInfos.size() > 0) {
				return new BaseResult(1, "应用名已存在");
			}
			appInfo.setAppname(appname);
		}

		if (appdesc != null) {
			appInfo.setAppdesc(appdesc);
		}
		this.appInfoDao.update(appInfo);
		BaseResult result = new BaseResult(0, "更新成功");
		return result;
	}

	// /////////////////////////////////////////////////////////////////////////////////

	/**
	 * 发布应用新版本
	 * 
	 * @param appid
	 * @param appVersionInfo
	 * @return
	 */
	public BaseResult publishAppVersion(int appid, AppVersionInfo appVersionInfo) {
		// 版本号必须比上一次发布的版本号增大
		int maxvcode = this.appVersionInfoDao.getMaxVersionCode();
		if (appVersionInfo.getVersioncode() <= maxvcode)
			return new BaseResult(1, "版本号必须大于当前发布版本中最大版本号：" + maxvcode);

		appVersionInfo.setAppid(appid);
		appVersionInfo.setUpdatedate(DateTimeUtil.getCurrDate());
		this.appVersionInfoDao.save(appVersionInfo);

		// 更新应用最新版为此次发布
		AppInfo appInfo = this.queryAppInfo(appid);
		appInfo.setNewestappvid(appVersionInfo.getId());
		this.appInfoDao.update(appInfo);

		return new BaseResult(0, "发布成功");
	}

	/**
	 * 设置APP最新的版本为哪个
	 * 
	 * @param appid
	 * @param appvid
	 * @return
	 */
	public BaseResult setNewestAppVersion(int appid, int appvid) {
		AppInfo appInfo = this.queryAppInfo(appid);
		if (appInfo == null)
			return new BaseResult(1, "应用不存在");

		AppVersionInfo appVersionInfo = this.queryAppVersionInfo(appvid);
		if (appVersionInfo.getAppid() != appid)
			return new BaseResult(2, "应用不存在该版本");
		appInfo.setNewestappvid(appvid);

		this.appInfoDao.update(appInfo);

		return new BaseResult(0, "设置成功");
	}

	/**
	 * 更新应用已发布版本信息
	 * 
	 * @param appVersionInfo
	 */
	public BaseResult updateAppVersion(AppVersionInfo appVersionInfo) {
		AppVersionInfo appVersionInfodb = this
				.queryAppVersionInfo(appVersionInfo.getId());
		if (appVersionInfodb == null)
			return new BaseResult(1, "版本不存在");

		// 更新版本名
		appVersionInfodb.setVersionname(appVersionInfo.getVersionname());
		// 更新日志
		appVersionInfodb.setUpdatelog(appVersionInfo.getUpdatelog());
		// 更新资源路径
		appVersionInfodb.setRespath(appVersionInfo.getRespath());
		// 更新更新类型
		appVersionInfodb.setUpdatetype(appVersionInfo.getUpdatetype());

		this.appVersionInfoDao.update(appVersionInfodb);
		return new BaseResult(0, "发布成功");
	}

	/**
	 * 删除应用已发布的版本, 注： 如果当前用户已安装了被删除的版本，则用户更新时会提醒强制更新
	 * 
	 * @param appvid
	 */
	public void deleteAppVersion(int appvid) {
		AppVersionInfo appVersionInfo = this.queryAppVersionInfo(appvid);
		if (appVersionInfo != null) {
			AppInfo appInfo = this.queryAppInfo(appVersionInfo.getAppid());
			if (appInfo != null) {
				// 如果删除的版本为当前最新的版本，将最新版本往前移一个版本，如果前一个版本没有，则尝试往后一个版本，不成功则设置为-1
				if (appInfo.getNewestappvid() == appVersionInfo.getId()) {
					AppVersionInfo appVersionInfoBefore = queryBeforeAppVersionInfo(
							appVersionInfo.getAppid(), appvid);
					if (appVersionInfoBefore != null)
						appInfo.setNewestappvid(appVersionInfoBefore.getId());
					else {
						AppVersionInfo appVersionInfoAfter = queryAfterAppVersionInfo(
								appVersionInfo.getAppid(), appvid);
						if (appVersionInfoAfter != null)
							appInfo.setNewestappvid(appVersionInfoAfter.getId());
						else
							appInfo.setNewestappvid(-1);
					}
					this.appInfoDao.update(appInfo);
				}
				String respath = appVersionInfo.getRespath();
				// 删除资源
				FileUtils.deleteQuietly(new File(respath));
				this.appVersionInfoDao.delete(appVersionInfo);
			}
		}
	}

	/**
	 * 获取某应用发布的所有版本信息
	 * 
	 * @param appid
	 * @return
	 */
	public List<AppVersionInfo> queryAppVersionInfos(int appid) {
		return (List<AppVersionInfo>) this.appVersionInfoDao.findOrderBy(
				new AppVersionInfo(), "appid", appid, "updatedate", true)
				.getData();
	}

	/**
	 * 获取某应用的某版本信息
	 * 
	 * @param appid
	 * @param appvid
	 * @return
	 */
	public AppVersionInfo queryAppVersionInfo(int appvid) {
		return this.appVersionInfoDao.findUnique(new AppVersionInfo(), "id",
				appvid);
	}

	/**
	 * 通过版本号获取某应用的版本信息
	 * 
	 * @param appid
	 * @param vcode
	 * @return
	 */
	public AppVersionInfo queryAppVersionInfo(int appid, int vcode) {
		return this.appVersionInfoDao.queryAppVersionInfo(appid, vcode);
	}

	/**
	 * 获得某应用最后一次发布的版本信息，注： 最后一次发布的版本不一定是最新的版本
	 * 
	 * @param appvid
	 * @return
	 */
	private AppVersionInfo queryLastestAppVersionInfo(int appid) {
		List<AppVersionInfo> appVersionInfos = this.queryAppVersionInfos(appid);
		if (appVersionInfos != null && appVersionInfos.size() > 0) {
			return appVersionInfos.get(0);
		}
		return null;
	}

	/**
	 * 查询appvid的前一版
	 * 
	 * @param appid
	 * @param appvid
	 * @return
	 */
	private AppVersionInfo queryBeforeAppVersionInfo(int appid, int appvid) {
		AppVersionInfo appVersionInfo = null;
		List<AppVersionInfo> appVersionInfos = this.queryAppVersionInfos(appid);
		int i = 0;
		for (; i < appVersionInfos.size(); i++) {
			if (appVersionInfos.get(i).getId() == appvid)
				break;
		}

		if (i > 0)
			return appVersionInfos.get(i - 1);
		else
			return null;
	}

	/**
	 * 查询appvid的后一版
	 * 
	 * @param appid
	 * @param appvid
	 * @return
	 */
	private AppVersionInfo queryAfterAppVersionInfo(int appid, int appvid) {
		AppVersionInfo appVersionInfo = null;
		List<AppVersionInfo> appVersionInfos = this.queryAppVersionInfos(appid);
		int i = 0;
		for (; i < appVersionInfos.size(); i++) {
			if (appVersionInfos.get(i).getId() == appvid)
				break;
		}

		if (i < appVersionInfos.size() - 1)
			return appVersionInfos.get(i + 1);
		else
			return null;
	}

	/**
	 * 检查新版本信息
	 * 
	 * @param appid
	 * @param oldversioncode
	 * @return
	 */
	public BaseResult checkNewestAppVersion(int appid, int oldversioncode) {
		// 获取所有APP信息，已排序
		List<AppVersionInfo> appVersionInfos = this.queryAppVersionInfos(appid);
		// 获得最新版本APP
		AppVersionInfo newestAppVersionInfo = this
				.queryLastestAppVersionInfo(appid);

		if (appVersionInfos == null || appVersionInfos.size() == 0
				|| newestAppVersionInfo == null
				|| (newestAppVersionInfo.getVersioncode() == oldversioncode)) {
			return new BaseResult(0, "已为最新版本");
		}

		/**
		 * 如果系统没有oldversioncode这个版本，则强制更新到最新版
		 */
		AppVersionInfo oldAppVersionInfo = this.queryAppVersionInfo(appid,
				oldversioncode);
		if (oldAppVersionInfo == null) {
			newestAppVersionInfo
					.setUpdatetype(AppVersionInfo.UPDATE_TYPE_POP_FORCE);
			BaseResult result = new BaseResult(1, "有新版本可以升级");
			result.setObj(newestAppVersionInfo);
			return result;
		}

		StringBuffer updatelog = new StringBuffer();
		/**
		 * 1. 如果从oldversioncode到最新版本，存在需要强制更新的版本，则返回的版本信息中为必须强制更新 2. 更新日志需要叠加
		 */
		for (int i = 0; i < appVersionInfos.size()
				&& appVersionInfos.get(i).getVersioncode() != oldversioncode; i++) {
			AppVersionInfo appVersionInfo = appVersionInfos.get(i);

			updatelog.append(appVersionInfo.getVersionname() + "\r\n"
					+ appVersionInfo.getUpdatelog() + "\r\n");

			if (appVersionInfo.getUpdatetype() == AppVersionInfo.UPDATE_TYPE_POP_FORCE) {
				newestAppVersionInfo
						.setUpdatetype(AppVersionInfo.UPDATE_TYPE_POP_FORCE);
				break;
			}
		}
		newestAppVersionInfo.setUpdatelog(updatelog.toString());

		BaseResult result = new BaseResult(1, "有新版本可以升级");
		result.setObj(newestAppVersionInfo);
		return result;
	}

	/**
	 * 下载最新版本
	 * 
	 * @param appid
	 * @return
	 */
	public BaseResult downloadNewestAppVersion(int appid) {
		return null;
	}
}
