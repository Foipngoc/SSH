package com.example.dao.impl;

import org.springframework.stereotype.Repository;

import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.BaseDaoDB;
import com.example.dao.ExampleDao;
import com.example.model.Example;

@Repository("exampledao")
public class ExampleDaoImpl extends BaseDaoDB<Example> implements ExampleDao{

	@Override
	public BaseQueryRecords getExamples(int page,int rows) {
		return super.find(new Example(),page,rows);
	}

}
