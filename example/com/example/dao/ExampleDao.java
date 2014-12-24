package com.example.dao;

import org.springframework.stereotype.Repository;

import com.common.dao.impl.BaseDaoImpl;
import com.example.model.Example;

@Repository("exampleDao") //声明bean
public class ExampleDao extends BaseDaoImpl<Example>{
	
}
