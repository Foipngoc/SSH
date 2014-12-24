package com.privilege.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.common.dao.HQL;
import com.common.dao.impl.BaseDaoImpl;
import com.privilege.model.Permission;
import com.privilege.model.User_permission_relation;

@Repository("user_permission_relationDao")
public class User_permission_relationDao extends
		BaseDaoImpl<User_permission_relation> {

	/**
	 * 查询某一用户的私有权限
	 */
	@SuppressWarnings("unchecked")
	public List<Permission> queryUserPerm(int userid) {
		String hql;
		hql = "select p from User_permission_relation u, Permission p "
				+ "where u.permid=p.id and u.userid=" + userid;
		return (List<Permission>) super.findHql(new HQL(hql));
	}

	@SuppressWarnings("unchecked")
	public List<Permission> queryUserPerm(int userid, int permtype) {
		String hql;
		hql = "select p from User_permission_relation u, Permission p "
				+ "where u.permid=p.id and u.userid=" + userid
				+ " and p.permtype=" + permtype;
		return (List<Permission>) super.findHql(new HQL(hql));
	}

	/**
	 * 查询某一用户的私有权限
	 */
	@SuppressWarnings("unchecked")
	public List<User_permission_relation> queryUserPerm_relation(int userid,
			int permid) {
		String hql = "select u from User_permission_relation u "
				+ "where u.permid=" + permid + " and u.userid=" + userid;
		return (List<User_permission_relation>) super.findHql(new HQL(hql));
	}

	/**
	 * 为用户绑定某一私有权限
	 * 
	 * @throws Exception
	 */
	public void addUserPerm(int userid, int permid) throws Exception {
		// 检查用户是否已经绑定此权限
		String hql = "from User_permission_relation u where u.permid=" + permid
				+ " and u.userid" + userid;
		List<?> list = super.findHql(new HQL(hql));
		if (list.size() > 0)
			throw new Exception("用户已绑定该角色");

		User_permission_relation user_permission_relation = new User_permission_relation();
		user_permission_relation.setPermid(permid);
		user_permission_relation.setUserid(userid);

		super.saveObj(user_permission_relation);
	}

	/**
	 * 删除某一用户所绑定的某一私有权限
	 */
	public void delUserPerm(int userid, int permid) {
		String hql = "delete from User_permission_relation u where u.userid="
				+ userid + "and u.permid=" + permid;
		super.deleteHql(new HQL(hql));
	}

	public void delUserPermAll(int userid) {
		String hql = "delete from User_permission_relation u where u.userid="
				+ userid;
		super.deleteHql(new HQL(hql));
	}
}
