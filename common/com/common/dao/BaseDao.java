package com.common.dao;

/**
 * BaseDao定义一些常用的接口，此类接口不应与数据持久层是何种而不同
 * 约定调用者也知道查询时返回的数据类型是多少
 * 
 * @author DongJun
 * 
 * @param <?>
 */
public interface BaseDao {
	/**
	 * 保存对象
	 */
	public void save(Object o);

	/**
	 * 删除对象
	 */
	public void delete(Object o);

	/**
	 * 更新对象
	 */
	public void update(Object o);

	/**
	 * 保存或更新对象
	 */
	public void saveOrUpdate(Object o);

	/**
	 * 查找所有对象
	 */
	public BaseQueryRecords<?> find(Object o);

	/**
	 * 查找所有对象，并排序
	 */
	public BaseQueryRecords<?> findOrderBy(Object o, String orderby,
			boolean ifdesc);

	/**
	 * 查找所有对象 带分页
	 */
	public BaseQueryRecords<?> find(Object o, int page, int rows);

	/**
	 * 查找所有对象 带分页, 并排序
	 */
	public BaseQueryRecords<?> findOrderBy(Object o, String orderby,
			boolean ifdesc, int page, int rows);

	/**
	 * 查找满足某一条件的所有对象
	 */
	public BaseQueryRecords<?> find(Object o, String key, Object value);

	/**
	 * 查找满足某一条件的所有对象, 并排序
	 */
	public BaseQueryRecords<?> findOrderBy(Object o, String key, Object value,
			String orderby, boolean ifdesc);

	/**
	 * 查找满足某一条件的所有对象 带分布
	 */
	public BaseQueryRecords<?> find(Object o, String key, Object value,
			int page, int rows);

	/**
	 * 查找满足某一条件的所有对象 带分布,并排序
	 */
	public BaseQueryRecords<?> findOrderBy(Object o, String key, Object value,
			String orderby, boolean ifdesc, int page, int rows);

	/**
	 * 查找唯一对象，如果对象不存在，返回NULL
	 */
	public Object findUnique(Object o, String key, Object value);

	/**
	 * 获得记录数
	 */
	public Long count(Object o);

	/**
	 * 获得记录数
	 */
	public Long count(Object o, String key, Object value);
}
