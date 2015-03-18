package com.module.appVersionCheck.dao;

import org.springframework.stereotype.Repository;

import com.common.dao.impl.BaseDaoDB;
import com.module.appVersionCheck.model.AppInfo;

@Repository("appInfoDao")
public class AppInfoDao extends BaseDaoDB<AppInfo> {
}
