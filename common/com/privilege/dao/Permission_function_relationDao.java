package com.privilege.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.common.dao.HQL;
import com.common.dao.impl.BaseDaoImpl;
import com.privilege.model.Function;
import com.privilege.model.Permission_function_relation;

@Repository("Permission_function_relationDao")
public class Permission_function_relationDao extends
		BaseDaoImpl<Permission_function_relation> {

	@SuppressWarnings("unchecked")
	public List<Function> queryPermFuncs(int permid) {
		String hql = "select f from Function f, Permission_function_relation p "
				+ "where f.id = p.funcid and p.permid = " + permid;
		return (List<Function>) super.findHql(new HQL(hql));
	}

	@SuppressWarnings("unchecked")
	public List<Function> queryPermFuncs(int permid, String funcname) {
		String hql = "select f from Function f, Permission_function_relation p "
				+ "where f.id = p.funcid and p.permid = "
				+ permid
				+ " and f.funcname='" + funcname + "'";
		return (List<Function>) super.findHql(new HQL(hql));
	}

	@SuppressWarnings("unchecked")
	public List<Permission_function_relation> queryPermFuncs_relation(int permid,
			int funcid) {
		String hql = "select p from Permission_function_relation p "
				+ "where p.permid = " + permid + " and p.funcid=" + funcid;
		return (List<Permission_function_relation>) super.findHql(new HQL(hql));
	}

	public Permission_function_relation addPermFunc(int permid, int funcid) {
		Permission_function_relation permission_function_relation = new Permission_function_relation();
		permission_function_relation.setPermid(permid);
		permission_function_relation.setFuncid(funcid);
		return super.saveObj(permission_function_relation);
	}

	public void delPermFunc(int permid, int funcid) throws Exception {
		String hql = "delete from Permission_function_relation p "
				+ "where p.permid=" + permid + " and p.funcid=" + funcid;
		super.deleteHql(new HQL(hql));
	}

	public void delPermFuncsAll(int permid) throws Exception {
		String hql = "delete from Permission_function_relation p "
				+ "where p.permid=" + permid;
		super.deleteHql(new HQL(hql));
	}
}
