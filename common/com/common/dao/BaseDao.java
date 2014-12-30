package com.common.dao;

import java.util.List;

/**
 * BaseDao定义一些常用的接口，此类接口不应与数据持久层是何种而不同
 * 
 * @author DongJun
 * 
 * @param <E>
 */
public interface BaseDao<E> {
	/**
	 * 保存一个对象
	 * 
	 * @param o
	 * @return
	 */
	public void save(E o);

	/**
	 * 删除一个对象
	 * 
	 * @param o
	 */
	public void delete(E o);

	/**
	 * 更新一个对象
	 * 
	 * @param o
	 */
	public void update(E o);

	/**
	 * 保存或更新对象
	 * 
	 * @param o
	 */
	public void saveOrUpdate(E o);

	/**
	 * 查找所有对象
	 * 
	 * @param o
	 * @return
	 */
	public List<E> find(E o);

	/**
	 * 查找所有对象 带分页
	 * 
	 * @param o
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<E> find(E o, Integer page, Integer rows);

	/**
	 * 查找满足某一条件的所有对象
	 * 
	 * @param o
	 * @param key
	 * @param value
	 * @return
	 */
	public List<E> find(E o, String key, Object value);

	/**
	 * 查找满足某一条件的所有对象 带分布
	 * 
	 * @param o
	 * @param key
	 * @param value
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<E> find(E o, String key, Object value, Integer page,
			Integer rows);

	/**
	 * 查找唯一对象，如果对象不存在，返回NULL
	 * @param o
	 * @param key
	 * @param Value
	 * @return
	 */
	public E findUnique(E o,String key,Object value);
	
	/**
	 * 获得记录数
	 * 
	 * @param o
	 * @return
	 */
	public Long count(E o);

	/**
	 * 获得记录数
	 * 
	 * @param o
	 * @return
	 */
	public Long count(E o, String key, Object value);
}
