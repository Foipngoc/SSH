package com.common.dao;

import java.util.List;

public interface BaseDao<E> {
	/****************** 增 *****************/
	/**
	 * 保存一个对象
	 * 
	 * @param o
	 * @return
	 */
	public E saveObj(E o);

	/****************** 删 *******************/
	/**
	 * 删除一个对象
	 * 
	 * @param o
	 */
	public void deleteObj(E o);

	/**
	 * 使用SQL语句删除
	 * 
	 * @param hql
	 * @return 响应数目
	 */
	public Integer deleteSql(SQL sql);

	/**
	 * 使用HQL语句删除
	 * 
	 * @param hql
	 * @return 响应数目
	 */
	public Integer deleteHql(HQL hql);

	/************************ 修改 ************************/

	/**
	 * 更新一个对象
	 * 
	 * @param o
	 */
	public E updateObj(E o);

	/**
	 * 保存或更新对象
	 * 
	 * @param o
	 */
	public E saveOrUpdateObj(E o);

	/**
	 * 使用SQL语句更新
	 * 
	 * @param hql
	 * @return 响应数目
	 */
	public Integer updateSql(SQL sql);

	/**
	 * 使用HQL语句更新
	 * 
	 * @param hql
	 * @return 响应数目
	 */
	public Integer updateHql(HQL hql);

	/********************** 查 ***************************/
	/**
	 * 查询
	 * 
	 * @param hql
	 * @return
	 */
	public List<?> findHql(HQL hql);

	/**
	 * 查询集合(带分页)
	 * 
	 * @param hql
	 * @param param
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<?> findHql(HQL hql, Integer page, Integer rows);

	/**
	 * 查询
	 * 
	 * @param hql
	 * @return
	 */
	public List<?> findSql(SQL sql);

	/**
	 * 查询集合(带分页)
	 * 
	 * @param hql
	 * @param param
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<?> findSql(SQL sql, Integer page, Integer rows);

	/**
	 * 获得记录数
	 * 
	 * @param hql
	 * @return
	 */
	public Long countHql(HQL hql);

	/**
	 * 获得记录数
	 * 
	 * @param hql
	 * @return
	 */
	public Long countSql(SQL sql);
}
