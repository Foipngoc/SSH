package com.common.dao;

/**
 * BaseDao定义一些常用的接口，此类接口不应与数据持久层是何种而不同
 * 
 * @author DongJun
 * 
 * @param <E>
 */
public interface BaseDao<E> {
	/**
	 * 保存对象
	 */
	public void save(E o);

	/**
	 * 删除对象
	 */
	public void delete(E o);

	/**
	 * 更新对象
	 */
	public void update(E o);

	/**
	 * 保存或更新对象
	 */
	public void saveOrUpdate(E o);

	/**
	 * 查找所有对象
	 */
	public BaseQueryRecords find(E o);

	/**
	 * 查找所有对象 带分页
	 */
	public BaseQueryRecords find(E o, Integer page, Integer rows);

	/**
	 * 查找满足某一条件的所有对象
	 */
	public BaseQueryRecords find(E o, String key, Object value);

	/**
	 * 查找满足某一条件的所有对象 带分布
	 */
	public BaseQueryRecords find(E o, String key, Object value, Integer page,
			Integer rows);

	/**
	 * 查找唯一对象，如果对象不存在，返回NULL
	 */
	public E findUnique(E o, String key, Object value);

	/**
	 * 获得记录数
	 */
	public Long count(E o);

	/**
	 * 获得记录数
	 */
	public Long count(E o, String key, Object value);
}
