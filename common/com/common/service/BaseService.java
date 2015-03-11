package com.common.service;

import javax.transaction.Transactional;

import org.junit.runner.RunWith;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.common.utils.CXFFilter;

/**
 * BaseService已实现事务，继承该类自动继承事务
 * 
 * @author DJ
 *
 */
@Service("baseService")
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class BaseService {
	/**
	 * 获得当前项目上下文路径
	 * @return
	 */
	public String getContextPath() {
		return CXFFilter.contextPath;
	}
}
