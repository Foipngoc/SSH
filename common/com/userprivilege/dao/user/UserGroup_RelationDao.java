package com.userprivilege.dao.user;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import com.userprivilege.model.user.UserGroup;
import com.userprivilege.model.user.UserGroup_Relation;

@SuppressWarnings("all")
@Repository("userGroup_RelationDao")
public class UserGroup_RelationDao extends BaseDaoDB<UserGroup_Relation> {
	public void delGroup_Relation(int groupid, int subgroupid) {
		super.delete(new HQL(
				"delete from UserGroup_Relation r where r.groupid=? and r.subgroupid=?",
				groupid, subgroupid));
	}

	public void delSubGroup_Relations(int groupid) {
		super.delete(new HQL(
				"delete from UserGroup_Relation r where r.groupid=?", groupid));
	}

	public void delParentGroup_Relations(int groupid) {
		super.delete(new HQL(
				"delete from UserGroup_Relation r where r.subgroupid=?",
				groupid));
	}

	public UserGroup_Relation find(int groupid, int subgroupid) {
		return (UserGroup_Relation) super
				.findUnique(new HQL(
						"from UserGroup_Relation r where r.groupid=? and r.subgroupid=?",
						groupid, subgroupid));
	}

	public List<UserGroup> findLeafGroups() {
		String hql = "select g from UserGroup g where g.id not in"
				+ "(select r.groupid from UserGroup_Relation r)";
		return (List<UserGroup>) super.find(new HQL(hql));
	}

	public List<UserGroup> findLeafGroups(int grouptype) {
		String hql = "select g from UserGroup g where g.id not in"
				+ "(select r.groupid from UserGroup_Relation r) and g.grouptype="
				+ grouptype;
		return (List<UserGroup>) super.find(new HQL(hql));
	}

	public List<UserGroup> findRootGroups() {
		String hql = "select g from UserGroup g where g.id not in"
				+ "(select r.subgroupid from UserGroup_Relation r)";
		return (List<UserGroup>) super.find(new HQL(hql));
	}

	public List<UserGroup> findRootGroups(int grouptype) {
		String hql = "select g from UserGroup g where g.id not in"
				+ "(select r.subgroupid from UserGroup_Relation r) and g.grouptype="
				+ grouptype;
		return (List<UserGroup>) super.find(new HQL(hql));
	}

	public List<UserGroup> findSubGroups(int groupid) {
		String hql = "select g from UserGroup_Relation r,UserGroup g "
				+ "where r.subgroupid=g.id and r.groupid=" + groupid;
		return (List<UserGroup>) super.find(new HQL(hql));
	}

	public List<UserGroup> findSubGroups(int groupid, int grouptype) {
		String hql = "select g from UserGroup_Relation r,UserGroup g "
				+ "where r.subgroupid=g.id and r.groupid=" + groupid
				+ " and g.grouptype=" + grouptype;
		return (List<UserGroup>) super.find(new HQL(hql));
	}
}
