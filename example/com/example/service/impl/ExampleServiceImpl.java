package com.example.service.impl;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.stereotype.Service;
import com.common.dao.BaseQueryRecords;
import com.common.service.BaseService;
import com.example.dao.ExampleDao;
import com.example.service.ExampleService;

/**
 * ExampleService实现类。 使用注解声明为exampleservice bean交由spring托管
 * 
 * 注：
 * 
 * 需要继承了BaseService，
 * 
 * @author DJ
 * 
 */
@Service("exampleservice")
public class ExampleServiceImpl extends BaseService implements ExampleService {

	/**
	 * 使用注解从spring托管库中注入exampledao， 也就是ExampleDaoImpl类的实例
	 */
	@Resource(name = "exampledao")
	private ExampleDao exampleDao;

	@Override
	public BaseQueryRecords getExamples(int page, int rows) {
		return this.exampleDao.getExamples(page, rows);
	}

	/**
	 * Junit4 单元测试,不带参数
	 */
	@Test
	public void getExamples() {
		System.out.println("size" + this.getExamples(-1, -1).getData().size());
	}
}
