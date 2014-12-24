package com.common.dao;

/**
 * 适配器，支持以不同形式输出SQL语句或者HQL语句 通配符为?
 * 
 * @author DongJun
 * 
 */
public interface BaseExpression {
	@Override
	public String toString();
}
