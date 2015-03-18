package com.module.appVersionCheck.dao;

import org.springframework.stereotype.Repository;

import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import com.module.appVersionCheck.model.AppVersionInfo;

@Repository("appVersionInfoDao")
public class AppVersionInfoDao extends BaseDaoDB<AppVersionInfo> {
	/**
	 * 获得最大的版本号
	 * 
	 * @return
	 */
	public int getMaxVersionCode() {
		return (int) this.findUnique(new HQL(
				"select max(a.versioncode) from AppVersionInfo a"));
	}

	public AppVersionInfo queryAppVersionInfo(int appid, int vcode) {
		return (AppVersionInfo) this
				.findUnique(new HQL(
						"select a from AppVersionInfo a where a.appid=? and a.versioncode=?",
						appid, vcode));
	}
}
