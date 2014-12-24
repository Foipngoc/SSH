package com.privilege.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.common.dao.HQL;
import com.common.dao.impl.BaseDaoImpl;
import com.privilege.model.Function;

@Repository("functionDao")
public class FunctionDao extends BaseDaoImpl<Function> {

	/**
	 * 查询所有功能
	 */
	@SuppressWarnings("unchecked")
	public List<Function> queryAll() throws Exception {
		return (List<Function>) super.findHql(new HQL("select f from Function f"));
	}

	/**
	 * 根据ID查询功能
	 */
	@SuppressWarnings("unchecked")
	public List<Function> query(int id) throws Exception {
		return (List<Function>) super.findHql(new HQL("select f from Function f where f.id=?",id));
	}

	/**
	 * 根据功能名查询功能
	 */
	@SuppressWarnings("unchecked")
	public List<Function> query(String funcname) throws Exception {
		return (List<Function>) super.findHql(new HQL("select f from Function f where f.funcname=?",funcname));
	}

	/**
	 * 根据ID删除功能
	 */
	public void delete(int id) throws Exception {
		super.deleteHql(new HQL("delete from Function f where f.id=?",id));
	}

	/**
	 * 查询权限功能
	 */
	@SuppressWarnings("unchecked")
	public List<Function> queryPermFuncs(int permid) throws Exception {
		String hql = "select f from Permission_function_relation p, Function f"
				+ " where p.funcid = f.id and p.permid=" + permid;
		return (List<Function>) super.findHql(new HQL(hql));
	}
}
