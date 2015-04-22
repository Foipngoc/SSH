package com.module.appversioncheck.dao.impl;

import org.springframework.stereotype.Repository;

import com.common.dao.impl.BaseDaoDB;
import com.module.appversioncheck.model.AppInfo;
import com.module.appversioncheck.dao.AppInfoDao;

@Repository("appInfoDao")
public class AppInfoDaoImpl extends BaseDaoDB<AppInfo> implements AppInfoDao {
}
