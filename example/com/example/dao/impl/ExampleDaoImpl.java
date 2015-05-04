package com.example.dao.impl;

import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.stereotype.Repository;

import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.BaseDaoDB;
import com.example.dao.ExampleDao;
import com.example.model.Student;

/**
 * ExampleDao的实现类， 使用Repository注解声明成exampledao bean。交由spring托管。
 * 
 * 注： 该类继承了BaseDaoDB, 并且实现了ExampleDao。
 * 
 * @author DJ
 * 
 */
@Repository("exampledao")
public class ExampleDaoImpl extends BaseDaoDB implements ExampleDao {

	@SuppressWarnings("unchecked")
	@Override
	public BaseQueryRecords<Student> getExamples(int page, int rows) {
		return (BaseQueryRecords<Student>) super.find(new Student(), page, rows);
	}

	/**
	 * Junit4 单元测试,不带参数
	 */
	@Test
	@Transactional
	public void getExamples() {
		System.out.println("size" + this.getExamples(-1, -1).getData().size());
	}
}
