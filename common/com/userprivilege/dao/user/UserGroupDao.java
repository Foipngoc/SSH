package com.userprivilege.dao.user;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import com.userprivilege.model.user.UserGroup;

@Repository("userGroupDao")
@SuppressWarnings("all")
public class UserGroupDao extends BaseDaoDB<UserGroup> {
	public void delGroup(int groupid) {
		super.delete(new HQL("delete from UserGroup g where g.id=" + groupid));
	}

	public UserGroup findGroup(int groupid) {
		return super.findUnique(new UserGroup(), "id", groupid);
	}

	public List<UserGroup> findGroups() {
		return (List<UserGroup>) super
				.find(new HQL("select g from UserGroup g"));
	}

	public List<UserGroup> findGroups(int grouptype) {
		return (List<UserGroup>) super.find(new HQL(
				"select g from UserGroup g where g.grouptype=" + grouptype));
	}

}
