package com.common.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.common.utils.CXFFilter;

@Service("baseService")
@Transactional
public class BaseService {
	/**
	 * 获得当前项目上下文路径
	 * @return
	 */
	public String getContextPath() {
		return CXFFilter.contextPath;
	}
}
