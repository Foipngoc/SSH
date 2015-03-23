package com.module.appVersionCheck.dao.impl;

import org.springframework.stereotype.Repository;

import com.common.dao.impl.BaseDaoDB;
import com.module.appVersionCheck.dao.AppInfoDao;
import com.module.appVersionCheck.model.AppInfo;

@Repository("appInfoDao")
public class AppInfoDaoImpl extends BaseDaoDB<AppInfo> implements AppInfoDao {
}
