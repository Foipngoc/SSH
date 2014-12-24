package com.privilege.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.common.dao.HQL;
import com.common.dao.impl.BaseDaoImpl;
import com.privilege.model.Permission_relation;

@Repository("permission_relationDao")
public class Permission_relationDao extends BaseDaoImpl<Permission_relation> {
	/**
	 * 查询所有
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Permission_relation> queryAll() throws Exception {
		return (List<Permission_relation>) super.findHql(new HQL("select p from Permission_relation p"));
	}

	/**
	 * 根据permid,subpermid查询
	 */
	@SuppressWarnings("unchecked")
	public List<Permission_relation> queryPermission_relation(int permid, int subpermid)
			throws Exception {
		String hql = "select p from Permission_relation p where p.permid=" + permid
				+ " and p.subpermid=" + subpermid;
		return (List<Permission_relation>) super.findHql(new HQL(hql));
	}
	
	public void delPermission_relation(int permid,int subpermid) throws Exception{
		String hql = "delete from Permission_relation where permid=" + permid
				+ " and subpermid=" + subpermid;
		super.deleteHql(new HQL(hql));
	}
	
	/**
	 * 删除与permid相关的所有关联
	 */
	public void delPermission_relation(int permid) throws Exception{
		String hql = "delete from Permission_relation where permid=" + permid
				+ " or subpermid=" + permid;
		super.deleteHql(new HQL(hql));
	}
}
