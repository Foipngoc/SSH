package com.user.dao;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import com.user.model.UserGroup;
import com.user.model.UserGroup_User_Relation;
import com.user.model.User;

@SuppressWarnings("all")
@Repository("group_User_RelationDao")
public class Group_User_RelationDao extends BaseDaoDB<UserGroup_User_Relation> {
	public void delGroup_User_Relation(int groupid, int userid) {
		super.delete(new HQL(
				"delete from Group_User_Relation u where u.groupid=? and u.userid=?",
				groupid, userid));
	}

	public void delGroup_User_Relations(int userid) {
		super.delete(new HQL(
				"delete from Group_User_Relation u where and u.userid=?",
				userid));
	}
	
	public void delGroup_Users(int groupid) {
		super.delete(new HQL(
				"delete from Group_User_Relation u where and u.groupid=?",
				groupid));
	}

	public List<UserGroup_User_Relation> findGroupUsers(int groupid) {
		return (List<UserGroup_User_Relation>) super.find(new HQL(
				"select u from Group_User_Relation u where u.groupid="
						+ groupid));
	}

	public List<UserGroup> findUserGroups(int userid) {
		return (List<UserGroup>) super.find(new HQL(
				"select g from Group_User_Relation u, UserGroup g "
						+ "where u.groupid = g.id and u.groupid=" + userid));
	}

	public UserGroup_User_Relation findUserGroup_User_Relation(int groupid,
			int userid) {
		return super.findUnique(new UserGroup_User_Relation(),
				Restrictions.eq("groupid", groupid),
				Restrictions.eq("userid", userid));
	}
}
