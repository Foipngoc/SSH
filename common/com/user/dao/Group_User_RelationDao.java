package com.user.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.common.dao.HQL;
import com.common.dao.impl.BaseDaoImpl;
import com.user.model.Group_User_Relation;
import com.user.model.User;


@SuppressWarnings("all")
@Repository("group_User_RelationDao")
public class Group_User_RelationDao extends BaseDaoImpl<Group_User_Relation> {
	public void delGroup_User_Relation(int groupid, int userid) {
		super.deleteHql(new HQL(
				"delete from Group_User_Relation u where u.groupid=? and u.userid=?",
				groupid, userid));
	}

	public List<Group_User_Relation> queryGroupUsers(int groupid) {
		return (List<Group_User_Relation>) super.findHql(new HQL(
				"select u from Group_User_Relation u where u.groupid="
						+ groupid));
	}
}
