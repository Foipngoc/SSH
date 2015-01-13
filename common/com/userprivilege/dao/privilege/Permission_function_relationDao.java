package com.userprivilege.dao.privilege;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import com.userprivilege.model.privilege.Function;
import com.userprivilege.model.privilege.Permission_function_relation;

@SuppressWarnings("all")
@Repository("Permission_function_relationDao")
public class Permission_function_relationDao extends
		BaseDaoDB<Permission_function_relation> {

	/**
	 * 查找功能列表
	 */
	public List<Function> findPerm_Funcs(int permid) {
		String hql = "select f from Function f, Permission_function_relation p "
				+ "where f.id = p.funcid and p.permid = " + permid;
		return (List<Function>) super.find(new HQL(hql));
	}

	/**
	 * 查找功能关联
	 */
	public Permission_function_relation findPerm_func_relation(int permid,
			int funcid) {
		return (Permission_function_relation) super
				.findUnique(new HQL(
						"from Permission_function_relation r where r.permid=? and r.funcid=?",
						permid, funcid));
	}

	/**
	 * 删除权限功能关联
	 */
	public void delPerm_Func(int permid, int funcid) {
		String hql = "delete from Permission_function_relation p "
				+ "where p.permid=" + permid + " and p.funcid=" + funcid;
		super.delete(new HQL(hql));
	}

	/**
	 * 删除权限permid与功能的所有关联
	 */
	public void delPerm_Funcs(int permid) {
		String hql = "delete from Permission_function_relation p "
				+ "where p.permid=" + permid;
		super.delete(new HQL(hql));
	}
}
