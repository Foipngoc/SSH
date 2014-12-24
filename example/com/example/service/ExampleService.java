package com.example.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.ExampleDao;
import com.example.model.Example;

@Transactional //声明事务，必须
@Service("exampleService") //声明Bean
public class ExampleService {
	@Resource //注入Bean
	private ExampleDao exampleDao;
	
	public void addExample() {
		Example e = new Example();
		e.setAge(111);
		e.setName("asd12212fasdf");
		exampleDao.saveObj(e);
	}

	public void setExampleDao(ExampleDao exampleDao) {
		this.exampleDao = exampleDao;
	}
}
