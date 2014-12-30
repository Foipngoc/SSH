package com.privilege.dao;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import com.privilege.model.Permission;
import com.privilege.model.User_permission_relation;
import com.user.model.User;

@SuppressWarnings("all")
@Repository("user_permission_relationDao")
public class User_permission_relationDao extends
		BaseDaoDB<User_permission_relation> {

	public List<Permission> findUser_Perms(int userid) {
		String hql;
		hql = "select p from User_permission_relation u, Permission p "
				+ "where u.permid=p.id and u.userid=" + userid;
		return (List<Permission>) super.find(new HQL(hql));
	}
	
	public List<User> findPerm_Users(int permid) {
		String hql;
		hql = "select u from User_permission_relation r, User u "
				+ "where r.userid=u.id and r.permid=" + permid;
		return (List<User>) super.find(new HQL(hql));
	}
	
	public List<User> findPerm_Users(int permid,int page,int rows) {
		String hql;
		hql = "select u from User_permission_relation r, User u "
				+ "where r.userid=u.id and r.permid=" + permid;
		return (List<User>) super.find(new HQL(hql),page,rows);
	}

	public List<Permission> findUser_Perm(int userid, int permtype) {
		String hql;
		hql = "select p from User_permission_relation u, Permission p "
				+ "where u.permid=p.id and u.userid=" + userid
				+ " and p.permtype=" + permtype;
		return (List<Permission>) super.find(new HQL(hql));
	}

	public User_permission_relation findUser_Perm_relation(int userid,
			int permid) {
		return super.findUnique(new User_permission_relation(),
				Restrictions.eq("permid", permid),
				Restrictions.eq("userid", userid));
	}

	/**
	 * 为用户绑定某一私有权限
	 * 
	 * @throws Exception
	 */
	public void addUser_Perm(int userid, int permid) throws Exception {
		// 检查用户是否已经绑定此权限
		String hql = "from User_permission_relation u where u.permid=" + permid
				+ " and u.userid" + userid;
		List<?> list = super.find(new HQL(hql));
		if (list.size() > 0)
			throw new Exception("用户已绑定该角色");

		User_permission_relation user_permission_relation = new User_permission_relation();
		user_permission_relation.setPermid(permid);
		user_permission_relation.setUserid(userid);

		super.save(user_permission_relation);
	}

	/**
	 * 删除某一用户所绑定的某一私有权限
	 */
	public void delUser_Perm(int userid, int permid) {
		String hql = "delete from User_permission_relation u where u.userid="
				+ userid + "and u.permid=" + permid;
		super.delete(new HQL(hql));
	}

	public void delUser_Perms(int userid) {
		String hql = "delete from User_permission_relation u where u.userid="
				+ userid;
		super.delete(new HQL(hql));
	}
	
	public void delPerm_Users(int permid) {
		String hql = "delete from User_permission_relation u where u.permid="
				+ permid;
		super.delete(new HQL(hql));
	}
}
