package com.module.appVersionCheck.dao.impl;

import org.springframework.stereotype.Repository;

import com.common.dao.impl.BaseDaoDB;
import com.module.appVersionCheck.dao.AppDownloadInfoDao;
import com.module.appVersionCheck.model.AppDownloadInfo;

@Repository("appDownloadInfoDao")
public class AppDownloadInfoDaoImpl extends BaseDaoDB<AppDownloadInfo>
		implements AppDownloadInfoDao {
}
