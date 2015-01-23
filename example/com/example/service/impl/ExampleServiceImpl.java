package com.example.service.impl;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.common.dao.BaseQueryRecords;
import com.example.dao.ExampleDao;
import com.example.service.ExampleService;

@Service("exampleservice")
@Transactional
public class ExampleServiceImpl implements ExampleService{

	@Resource(name="exampledao")
	private ExampleDao exampleDao;
	
	@Override
	public BaseQueryRecords getExamples(int page, int rows) {
		return this.exampleDao.getExamples(page, rows);
	}

}
