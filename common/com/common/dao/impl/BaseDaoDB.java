package com.common.dao.impl;

import java.util.List;
import java.util.concurrent.locks.Condition;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.junit.runner.Result;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.common.dao.BaseDao;
import com.common.dao.BaseQueryRecords;

@Repository("baseDaoDB")
// 默认声明baseDao Bean.
@SuppressWarnings("all")
public class BaseDaoDB<E> implements BaseDao<E> {

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Autowired
	// 注入sessionFacory Bean
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * 保存对象
	 * 
	 * @param o
	 *            : 待保存对象
	 * @return: 通过参数o返回对象信息，如自增ID
	 */
	@Override
	public void save(E o) {
		try {
			getCurrentSession().save(o);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 删除对象
	 * 
	 * @param o
	 *            : 待删除对象
	 */
	@Override
	public void delete(E o) {
		try {
			getCurrentSession().delete(o);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 更新对象
	 * 
	 * @param o
	 *            : 待更新的对象
	 */
	@Override
	public void update(E o) {
		try {
			getCurrentSession().update(o);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 保存或更新对象
	 * 
	 * @param o
	 *            : 待保存或更新的对象
	 */
	@Override
	public void saveOrUpdate(E o) {
		try {
			getCurrentSession().saveOrUpdate(o);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 查找所有类型为E的对象集
	 * 
	 * @param o
	 *            : 待查找的对象类型
	 * @return: 对象集
	 */
	@Override
	public BaseQueryRecords find(E o) {
		return this.find(o, -1, -1);
	}

	/**
	 * 查找所有类型为E的对象集，带分页
	 * 
	 * @param o
	 *            : 待查找的对象类型
	 * @param page
	 *            : 页码
	 * @param rows
	 *            : 每页条数
	 * @return: 对象集
	 */
	@Override
	public BaseQueryRecords find(E o, int page, int rows) {
		try {
			Criteria criteria = getCurrentSession()
					.createCriteria(o.getClass());

			// page和rows 都 >0 时返回分页数据
			if (page > 0 && rows > 0) {
				int total = criteria.list().size();
				criteria.setFirstResult((page - 1) * rows);
				criteria.setMaxResults(rows);
				return new BaseQueryRecords(criteria.list(), total, page, rows);
			} else {
				return new BaseQueryRecords(criteria.list());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 查找满足某一条件的所有类型为E的对象
	 * 
	 * @param o
	 *            : 待查找对象类型
	 * @param key
	 *            : 条件名
	 * @param value
	 *            : 条件值
	 * @return: 对象集
	 */
	@Override
	public BaseQueryRecords find(E o, String key, Object value) {
		return this.find(o, Restrictions.eq(key, value));
	}

	/**
	 * 查找满足某一条件的所有类型为E的对象，带分页
	 * 
	 * @param o
	 *            : 待查找对象类型
	 * @param key
	 *            : 条件名
	 * @param value
	 *            ： 条件值
	 * @param page
	 *            : 页码
	 * @param rows
	 *            : 每页行数
	 * @return： 对象集
	 */
	@Override
	public BaseQueryRecords find(E o, String key, Object value, int page,
			int rows) {
		return this.find(o, page, rows, Restrictions.eq(key, value));
	}

	/**
	 * 查找满足某条件的类型为E的唯一对象
	 * 
	 * @param o
	 *            : 待查找对象类型
	 * @param key
	 *            : 条件名
	 * @param value
	 *            : 条件值
	 * @return: 对象
	 */
	@Override
	public E findUnique(E o, String key, Object value) {
		List<E> lists = (List<E>) this.find(o, 1, 1,
				Restrictions.eq(key, value)).getList();
		if (lists.size() > 0) {
			return lists.get(0);
		}
		return null;
	}

	/**
	 * 获得类型为E的对象数
	 * 
	 * @param o
	 *            ： 待查找的对象类型
	 * @return： 对象的数量
	 */
	@Override
	public Long count(E o) {
		try {
			Criteria criteria = getCurrentSession()
					.createCriteria(o.getClass());
			criteria.setProjection(Projections.rowCount());
			return (long) criteria.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 获得满足某条件的类型为E的对象数
	 * 
	 * @param o
	 *            : 待查找对象类型
	 * @param key
	 *            : 条件名
	 * @param value
	 *            : 条件值
	 * @return ： 对象数量
	 */
	@Override
	public Long count(E o, String key, Object value) {
		try {
			Criteria criteria = getCurrentSession()
					.createCriteria(o.getClass());
			criteria.setProjection(Projections.rowCount());
			criteria.add(Restrictions.eq(key, value));
			return (long) criteria.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 获取Criteria,通过此方法，用户可以实现更多的自定义查询
	 * 
	 * @param o
	 *            ： 待查找的类型
	 * @return 获得查寻条件
	 */
	protected Criteria getCriteria(E o) {
		return getCurrentSession().createCriteria(o.getClass());
	}

	/**
	 * 按条件查对类型为E的对象，带分页
	 * 
	 * @param o
	 *            : 对象
	 * @param page
	 *            : 页码
	 * @param rows
	 *            : 每页条数
	 * @param contidions
	 *            : 查询条件
	 * @return: 对象集
	 */
	protected BaseQueryRecords find(E o, int page, int rows,
			Criterion... contidions) {
		try {
			Criteria criteria = getCurrentSession()
					.createCriteria(o.getClass());

			for (int i = 0; i < contidions.length; i++) {
				criteria.add(contidions[i]);
			}

			if (page > 0 && rows > 0) {
				int total = criteria.list().size();
				criteria.setFirstResult((page - 1) * rows);
				criteria.setMaxResults(rows);
				return new BaseQueryRecords(criteria.list(), total, page, rows);
			} else {
				return new BaseQueryRecords(criteria.list());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 按条件查找类型为E的对象集
	 * 
	 * @param o
	 *            ： 待查找的对象类型
	 * @param conditions
	 *            : 条件
	 * @return: 对象集
	 */
	protected BaseQueryRecords find(E o, Criterion... conditions) {
		return this.find(o, -1, -1, conditions);
	}

	/**
	 * 查找满足某条件的唯一对象
	 * 
	 * @param o
	 *            ：对象
	 * @param conditions
	 *            ： 查询条件
	 * @return: 查到到的对象，没有查到找返回 null
	 */
	protected E findUnique(E o, Criterion... conditions) {
		List<E> lists = (List<E>) find(o, 1, 1, conditions).getList();
		if (lists.size() > 0) {
			return lists.get(0);
		}
		return null;
	}

	/**
	 * 查询满足某条件的记录数
	 * 
	 * @param o
	 *            ： 对象
	 * @param conditions
	 *            ： 条件
	 * @return： 记录数
	 */
	protected Long count(E o, Criterion... conditions) {
		try {
			Criteria criteria = getCurrentSession()
					.createCriteria(o.getClass());
			criteria.setProjection(Projections.rowCount());
			for (int i = 0; i < conditions.length; i++) {
				criteria.add(conditions[i]);
			}
			return (long) criteria.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 使用SQL语句删除
	 * 
	 * @param sql
	 *            ： sql语句
	 * @return: 响应数目
	 */
	protected Integer delete(SQL sql) {
		try {
			return this.executeSql(sql);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 使用HQL语句删除
	 * 
	 * @param hql
	 *            : hql语句
	 * @return: 响应数目
	 */
	protected Integer delete(HQL hql) {
		try {
			return this.executeHql(hql);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 使用SQL语句更新
	 * 
	 * @param sql
	 *            : sql语句
	 * @return: 响应数目
	 */
	protected Integer update(SQL sql) {
		try {
			return this.executeSql(sql);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 使用HQL语句更新
	 * 
	 * @param hql
	 *            ： hql语句
	 * @return: 响应数目
	 */
	protected Integer update(HQL hql) {
		try {
			return this.executeHql(hql);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 使用HQL查询记录集
	 * 
	 * @param hql
	 *            : hql语句
	 * @return: 记录集
	 */
	protected BaseQueryRecords find(HQL hql) {
		return this.find(hql, -1, -1);
	}

	/**
	 * 使用hql语句查询唯一数据记录，如果不存在，返回NULL
	 * 
	 * @param hql
	 *            ： hql语句
	 * @return： 记录
	 */
	protected Object findUnique(HQL hql) {
		List<?> lists = find(hql, 1, 1).getList();
		if (lists.size() > 0) {
			return lists.get(0);
		}
		return null;
	}

	/**
	 * 使用hql语句查询数据集，带分页
	 * 
	 * @param hql
	 *            ： hql语句
	 * @param page
	 *            ： 页码
	 * @param rows
	 *            ： 每页行数
	 * @return： 数据集
	 */
	protected BaseQueryRecords find(HQL hql, int page, int rows) {
		try {
			Query q = getCurrentSession().createQuery(hql.toString());

			// page和rows 都 >0 时返回分页数据
			if (page > 0 && rows > 0) {
				int total = q.list().size();
				q.setFirstResult((page - 1) * rows);
				q.setMaxResults(rows);
				return new BaseQueryRecords(q.list(), total, page, rows);
			} else {
				return new BaseQueryRecords(q.list());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 使用sql语句查询数据集
	 * 
	 * @param sql
	 *            ： sql语句
	 * @return: 数据集
	 */
	protected BaseQueryRecords find(SQL sql) {
		return find(sql, -1, -1);
	}

	/**
	 * 使用sql语句查找唯一记录，如果不存在，返回NULL
	 * 
	 * @param sql
	 *            ： sql语句
	 * @return: 数据
	 */
	protected Object findUnique(SQL sql) {
		List<?> lists = find(sql, 1, 1).getList();
		if (lists.size() > 0) {
			return lists.get(0);
		}
		return null;
	}

	/**
	 * 使用sql语句查询数据 集，带分页
	 * 
	 * @param sql
	 *            ： sql语句
	 * @param page
	 *            ： 页码
	 * @param rows
	 *            ： 每页行数
	 * @return: 数据集
	 */
	protected BaseQueryRecords find(SQL sql, int page, int rows) {
		try {
			Query q = getCurrentSession().createSQLQuery(sql.toString());

			// page和rows 都 >0 时返回分页数据
			if (page > 0 && rows > 0) {
				int total = q.list().size();
				q.setFirstResult((page - 1) * rows);
				q.setMaxResults(rows);
				return new BaseQueryRecords(q.list(), total, page, rows);
			} else {
				return new BaseQueryRecords(q.list());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 使用hql语句获得记录数
	 * 
	 * @param hql
	 *            : hql语句
	 * @return: 记录数
	 */
	protected Long count(HQL hql) {
		try {
			Long cnt = 0L;
			Query q = getCurrentSession().createQuery(hql.toString());
			cnt = (Long) q.uniqueResult();
			return cnt;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 使用sql语句获得记录数
	 * 
	 * @param sql
	 *            ： sql语句
	 * @return: 记录数
	 */
	protected Long count(SQL sql) {
		try {
			Long cnt = 0L;
			Query q = getCurrentSession().createSQLQuery(sql.toString());
			cnt = (Long) q.uniqueResult();
			return cnt;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 执行sql语句
	 * 
	 * @param sql
	 *            ：sql语句
	 * @return: 响应数量
	 */
	protected Integer executeSql(SQL sql) {
		int ret = 0;
		Query q = getCurrentSession().createSQLQuery(sql.toString());
		ret = q.executeUpdate();
		return ret;
	}

	/**
	 * 执行hql语句
	 * 
	 * @param hql
	 *            : hql语句
	 * @return: 响应数量
	 */
	protected Integer executeHql(HQL hql) {
		int ret = 0;
		Query q = getCurrentSession().createQuery(hql.toString());
		ret = q.executeUpdate();
		return ret;
	}

}
