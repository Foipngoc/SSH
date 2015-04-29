package com.example.service;

import com.common.dao.BaseQueryRecords;
import com.example.model.Student;

public interface ExampleService {
	public BaseQueryRecords<Student> getExamples(int page,int rows);
}
