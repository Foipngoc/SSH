package com.common.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.common.dao.BaseDao;
import com.common.dao.HQL;
import com.common.dao.SQL;

@Repository("baseDao") //默认声明baseDao Bean.
@SuppressWarnings("all")
public class BaseDaoImpl<E> implements BaseDao<E> {

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Autowired //注入sessionFacory Bean
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * 保存一个对象
	 * 
	 * @param o
	 * @return
	 */
	@Override
	public E saveObj(E o) {
		return (E) getCurrentSession().save(o);
	}

	/**
	 * 删除一个对象
	 * 
	 * @param o
	 */
	@Override
	public void deleteObj(E o) {
		getCurrentSession().delete(o);
	}

	/**
	 * 使用SQL语句删除
	 * 
	 * @param hql
	 * @return 响应数目
	 */
	@Override
	public Integer deleteSql(SQL sql) {
		return this.executeSql(sql);
	}

	/**
	 * 使用HQL语句删除
	 * 
	 * @param hql
	 * @return 响应数目
	 */
	@Override
	public Integer deleteHql(HQL hql) {
		return this.executeHql(hql);
	}

	/**
	 * 更新一个对象
	 * 
	 * @param o
	 */
	@Override
	public E updateObj(E o) {
		getCurrentSession().update(o);
		return o;
	}

	/**
	 * 保存或更新对象
	 * 
	 * @param o
	 */
	@Override
	public E saveOrUpdateObj(E o) {
		getCurrentSession().saveOrUpdate(o);
		return o;
	}

	/**
	 * 使用SQL语句更新
	 * 
	 * @param hql
	 * @return 响应数目
	 */
	@Override
	public Integer updateSql(SQL sql) {
		return this.executeSql(sql);
	}

	/**
	 * 使用HQL语句更新
	 * 
	 * @param hql
	 * @return 响应数目
	 */
	@Override
	public Integer updateHql(HQL hql) {
		return this.executeHql(hql);
	}

	/**
	 * 查询
	 * 
	 * @param hql
	 * @return
	 */
	@Override
	public List<?> findHql(HQL hql) {
		return this.findHql(hql, -1, -1);
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
	@Override
	public List<?> findHql(HQL hql, Integer page, Integer rows) {
		Query q = getCurrentSession().createQuery(hql.toString());
		if (page != -1 && rows != -1) {
			q.setFirstResult((page - 1) * rows);
			q.setMaxResults(rows);
		}
		return q.list();
	}

	/**
	 * 查询
	 * 
	 * @param hql
	 * @return
	 */
	@Override
	public List<?> findSql(SQL sql) {
		return findSql(sql, -1, -1);
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
	@Override
	public List<?> findSql(SQL sql, Integer page, Integer rows) {
		Query q = getCurrentSession().createSQLQuery(sql.toString());

		if (page != -1 && rows != -1) {
			q.setFirstResult((page - 1) * rows);
			q.setMaxResults(rows);
		}
		return q.list();
	}

	/**
	 * 获得记录数
	 * 
	 * @param hql
	 * @return
	 */
	@Override
	public Long countHql(HQL hql) {
		Long cnt = 0L;
		Query q = getCurrentSession().createQuery(hql.toString());
		cnt = (Long) q.uniqueResult();
		return cnt;
	}

	/**
	 * 获得记录数
	 * 
	 * @param hql
	 * @return
	 */
	@Override
	public Long countSql(SQL sql) {
		Long cnt = 0L;
		Query q = getCurrentSession().createSQLQuery(sql.toString());
		cnt = (Long) q.uniqueResult();
		return cnt;
	}

	private Integer executeSql(SQL sql) {
		int ret = 0;
		Query q = getCurrentSession().createSQLQuery(sql.toString());
		ret = q.executeUpdate();
		return ret;
	}

	private Integer executeHql(HQL hql) {
		int ret = 0;
		Query q = getCurrentSession().createQuery(hql.toString());
		ret = q.executeUpdate();
		return ret;
	}
}
