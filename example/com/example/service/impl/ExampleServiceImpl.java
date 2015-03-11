package com.example.service.impl;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.stereotype.Service;
import com.common.dao.BaseQueryRecords;
import com.common.service.BaseService;
import com.example.dao.ExampleDao;
import com.example.service.ExampleService;

@Service("exampleservice")
public class ExampleServiceImpl extends BaseService implements ExampleService {

	@Resource(name="exampledao")
	private ExampleDao exampleDao;
	
	
	@Override
	public BaseQueryRecords getExamples(int page, int rows) {
		return this.exampleDao.getExamples(page, rows);
	}
	
	/**
	 * Junit4  单元测试,不带参数
	 */
	@Test
	public void getExamples() {
		System.out.println("size"+this.getExamples(-1, -1).getData().size());
	}
}
