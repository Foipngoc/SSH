package com.example.dao;

import org.springframework.stereotype.Repository;

import com.common.dao.impl.BaseDaoDB;
import com.example.model.Example;

@Repository("exampleDao") //声明bean
public class ExampleDao extends BaseDaoDB<Example>{
	
}
