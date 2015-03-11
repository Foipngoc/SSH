package com.example.dao.impl;

import javax.transaction.Transactional;

import org.junit.Test;
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

	/**
	 * Junit4  单元测试,不带参数
	 */
	@Test
	@Transactional
	public void getExamples() {
		System.out.println("size"+this.getExamples(-1, -1).getData().size());
	}
}
