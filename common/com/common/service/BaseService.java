package com.common.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.common.utils.CXFFilter;

/**
 * BaseService已实现事务，继承该类自动继承事务
 * 
 * @author DJ
 *
 */
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
