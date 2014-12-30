package com.user.dao;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import com.user.model.UserGroup;

@Repository("groupDao")
@SuppressWarnings("all")
public class GroupDao extends BaseDaoDB<UserGroup> {
	public void delGroup(int groupid) {
		super.delete(new HQL("delete from Group g where g.id=" + groupid));
	}

	public UserGroup findGroup(int groupid) {
		return super
				.findUnique(new UserGroup(), Restrictions.eq("id", groupid));
	}

	public List<UserGroup> findGroups() {
		return (List<UserGroup>) super.find(new HQL("select g from Group g"));
	}

	public List<UserGroup> findGroups(int grouptype) {
		return (List<UserGroup>) super.find(new HQL(
				"select g from Group g where g.grouptype=" + grouptype));
	}

}
