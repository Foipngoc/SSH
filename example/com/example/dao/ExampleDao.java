package com.example.dao;

import com.common.dao.BaseDao;
import com.common.dao.BaseQueryRecords;
import com.example.model.Student;

/**
 * 注： 该接口实现了BaseDao， 这样Service层代码就可以直接使用BaseDao中的相关接口。
 * 常用的以对象的方式进行操作都可以直接在Service层，这样可以避免为了写一个save方法，还需要在ExampleDao中写上接口并实现的问题。
 * 提高代码效率
 * 
 * @author DJ
 * 
 */
public interface ExampleDao extends BaseDao<Student> {
	public BaseQueryRecords getExamples(int page, int rows);
}
