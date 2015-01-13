package com.userprivilege.dao.user;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import com.userprivilege.model.user.User;
import com.userprivilege.model.user.UserGroup;
import com.userprivilege.model.user.UserGroup_User_Relation;

@SuppressWarnings("all")
@Repository("userGroup_User_RelationDao")
public class UserGroup_User_RelationDao extends
		BaseDaoDB<UserGroup_User_Relation> {
	public void delGroup_User_Relation(int groupid, int userid) {
		super.delete(new HQL(
				"delete from UserGroup_User_Relation u where u.groupid=? and u.userid=?",
				groupid, userid));
	}

	public void delGroup_User_Relations(int userid) {
		super.delete(new HQL(
				"delete from UserGroup_User_Relation u where u.userid=?",
				userid));
	}

	public void delGroup_Users(int groupid) {
		super.delete(new HQL(
				"delete from UserGroup_User_Relation u where u.groupid=?",
				groupid));
	}

	public List<User> findGroupUsers(int groupid) {
		return (List<User>) super
				.find(new HQL(
						"select u from UserGroup_User_Relation r, User u where r.userid=u.id and r.groupid="
								+ groupid));
	}

	public List<User> findGroupUsers(int groupid, int page, int rows) {
		return (List<User>) super
				.find(new HQL(
						"select u from UserGroup_User_Relation r, User u where r.userid=u.id and r.groupid="
								+ groupid), page, rows);
	}

	public List<User> findAllGroupUsersOrderByNotStatus(int notstatus,
			int page, int rows) {
		String hql = "select u from UserGroup_User_Relation r, User u "
				+ "where r.userid=u.id and u.userstatus !=? order by r.groupid desc";

		return (List<User>) super.find(new HQL(hql, notstatus), page, rows);
	}

	public List<User> findGroupUsersByNotStatus(int groupid, int notuserstatus,
			int page, int rows) {
		return (List<User>) super
				.find(new HQL(
						"select u from UserGroup_User_Relation r, User u where r.userid=u.id and r.groupid=? and u.userstatus != ?",
						groupid, notuserstatus), page, rows);
	}

	public long countGroupUsersByNotStatus(int groupid, int notuserstatus) {
		return super
				.count(new HQL(
						"select count(u) from UserGroup_User_Relation r, User u where r.userid=u.id and r.groupid=? and u.userstatus != ?",
						groupid, notuserstatus));
	}

	public List<User> findGroupUsers(int groupid, int userstatus, int page,
			int rows) {
		return (List<User>) super
				.find(new HQL(
						"select u from UserGroup_User_Relation r, User u where r.userid=u.id and r.groupid=? and u.userstatus = ?",
						groupid, userstatus), page, rows);
	}

	public List<UserGroup> findUserGroups(int userid) {
		return (List<UserGroup>) super.find(new HQL(
				"select g from UserGroup_User_Relation u, UserGroup g "
						+ "where u.groupid = g.id and u.userid=" + userid));
	}

	public List<UserGroup> findUserGroup(int userid, int grouptype) {
		return (List<UserGroup>) super
				.find(new HQL(
						"select g from UserGroup_User_Relation u, UserGroup g "
								+ "where u.groupid = g.id and u.userid=? and g.grouptype=?",
						userid, grouptype));
	}

	public UserGroup_User_Relation findUserGroup_User_Relation(int groupid,
			int userid) {
		return (UserGroup_User_Relation) super
				.findUnique(new HQL(
						"from UserGroup_User_Relation r where r.groupid=? and r.userid=?",
						groupid, userid));
	}
}
