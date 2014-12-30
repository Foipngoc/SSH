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
import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.common.dao.BaseDao;

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
	 * 保存一个对象
	 * 
	 * @param o
	 * @return
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
	 * 删除一个对象
	 * 
	 * @param o
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
	 * 更新一个对象
	 * 
	 * @param o
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
	 * 查找对象
	 */
	@Override
	public List<E> find(E o) {
		try {
			Criteria criteria = getCurrentSession()
					.createCriteria(o.getClass());
			return criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 查找对象
	 */
	@Override
	public List<E> find(E o, Integer page, Integer rows) {
		try {
			if (page < 1)
				page = 1;
			if (rows < 1)
				rows = 1;
			Criteria criteria = getCurrentSession()
					.createCriteria(o.getClass());
			criteria.setFirstResult((page - 1) * rows);
			criteria.setMaxResults(rows);
			return criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 查找对象
	 */
	@Override
	public List<E> find(E o, String key, Object value) {
		return this.find(o, Restrictions.eq(key, value));
	}

	/**
	 * 查找对象
	 */
	@Override
	public List<E> find(E o, String key, Object value, Integer page,
			Integer rows) {
		if (page < 1)
			page = 1;
		if (rows < 1)
			rows = 1;
		return this.find(o, page, rows, Restrictions.eq(key, value));
	}

	/**
	 * 查找唯一对象，如果对象不存在，返回NULL
	 * @param o
	 * @param key
	 * @param Value
	 * @return
	 */
	public E findUnique(E o,String key,Object value){
		List<E> lists = find(o,key,value);
		if (lists.size() > 0) {
			return lists.get(0);
		}
		return null;
	}
	
	/**
	 * 获得记录数
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
	 * 获得记录数
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
	 * @return
	 */
	protected Criteria getCriteria(E o) {
		return getCurrentSession().createCriteria(o.getClass());
	}

	/**
	 * 查找对象
	 */
	protected List<E> find(E o, Integer page, Integer rows,
			Criterion... contidions) {
		try {
			if (page < 1)
				page = 1;
			if (rows < 1)
				rows = 1;
			Criteria criteria = getCurrentSession()
					.createCriteria(o.getClass());

			for (int i = 0; i < contidions.length; i++) {
				criteria.add(contidions[i]);
			}
			criteria.setFirstResult((page - 1) * rows);
			criteria.setMaxResults(rows);
			return criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 查找对象
	 */
	protected List<E> find(E o, Criterion... conditions) {
		try {
			Criteria criteria = getCurrentSession()
					.createCriteria(o.getClass());

			for (int i = 0; i < conditions.length; i++) {
				criteria.add(conditions[i]);
			}
			return criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 查找对象
	 */
	protected E findUnique(E o, Criterion... conditions) {
		List<E> lists = find(o,conditions);
		if (lists.size() > 0) {
			return lists.get(0);
		}
		return null;
	}
	
	/**
	 * 获得记录数
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
	 * @param hql
	 * @return 响应数目
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
	 * @return 响应数目
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
	 * @param hql
	 * @return 响应数目
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
	 * @return 响应数目
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
	 * 查询
	 * 
	 * @param hql
	 * @return
	 */
	protected List<?> find(HQL hql) {
		return this.find(hql, -1, -1);
	}
	
	/**
	 * 查询唯一，如果不存在，返回NULL
	 * @param hql
	 * @return
	 */
	protected Object findUnique(HQL hql) {
		List<?> lists = find(hql);
		if (lists.size() > 0) {
			return lists.get(0);
		}
		return null;
	}
	

	/**
	 * 查询集合(带分页)
	 * 
	 * @param hql
	 * @param param
	 * @param page
	 * @param rows
	 * @return
	 */
	protected List<?> find(HQL hql, Integer page, Integer rows) {
		try {
			if (page < 1)
				page = 1;
			if (rows < 1)
				rows = 1;
			Query q = getCurrentSession().createQuery(hql.toString());
			if (page != -1 && rows != -1) {
				q.setFirstResult((page - 1) * rows);
				q.setMaxResults(rows);
			}
			return q.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 查询
	 * 
	 * @param hql
	 * @return
	 */
	protected List<?> find(SQL sql) {
		return find(sql, -1, -1);
	}

	/**
	 * 查找唯一，如果不存在，返回NULL
	 * @param sql
	 * @return
	 */
	protected Object findUnique(SQL sql) {
		List<?> lists = find(sql);
		if (lists.size() > 0) {
			return lists.get(0);
		}
		return null;
	}
	/**
	 * 查询集合(带分页)
	 * 
	 * @param hql
	 * @param param
	 * @param page
	 * @param rows
	 * @return
	 */
	protected List<?> find(SQL sql, Integer page, Integer rows) {
		try {
			if (page < 1)
				page = 1;
			if (rows < 1)
				rows = 1;
			Query q = getCurrentSession().createSQLQuery(sql.toString());

			if (page != -1 && rows != -1) {
				q.setFirstResult((page - 1) * rows);
				q.setMaxResults(rows);
			}
			return q.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 获得记录数
	 * 
	 * @param hql
	 * @return
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
	 * 获得记录数
	 * 
	 * @param hql
	 * @return
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
	 * @return
	 */
	private Integer executeSql(SQL sql) {
		int ret = 0;
		Query q = getCurrentSession().createSQLQuery(sql.toString());
		ret = q.executeUpdate();
		return ret;
	}

	/**
	 * 执行hql语句
	 * 
	 * @param hql
	 * @return
	 */
	private Integer executeHql(HQL hql) {
		int ret = 0;
		Query q = getCurrentSession().createQuery(hql.toString());
		ret = q.executeUpdate();
		return ret;
	}

}
