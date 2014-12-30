package com.user.dao;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import com.user.model.UserGroup;
import com.user.model.UserGroup_Relation;

@Repository("group_RelationDao")
public class Group_RelationDao extends BaseDaoDB<UserGroup_Relation> {
	public void delGroup_Relation(int groupid, int subgroupid) {
		super.delete(new HQL(
				"delete from Group_Relation r where r.groupid=? and r.subgroupid=?",
				groupid, subgroupid));
	}

	public void delSubGroup_Relations(int groupid) {
		super.delete(new HQL("delete from Group_Relation r where r.groupid=?",
				groupid));
	}

	public void delParentGroup_Relations(int groupid) {
		super.delete(new HQL(
				"delete from Group_Relation r where r.subgroupid=?", groupid));
	}

	public UserGroup_Relation find(int groupid, int subgroupid) {
		return super.findUnique(new UserGroup_Relation(),
				Restrictions.eq("groupid", groupid),
				Restrictions.eq("subgroupid", subgroupid));
	}
	

	public List<UserGroup> findLeafGroups() {
		String hql = "select g from Group g where g.id not in"
				+ "(select r.groupid from Group_Relation r)";
		return (List<UserGroup>) super.find(new HQL(hql));
	}

	public List<UserGroup> findLeafGroups(int grouptype) {
		String hql = "select g from Group g where g.id not in"
				+ "(select r.groupid from Group_Relation r) and g.grouptype="
				+ grouptype;
		return (List<UserGroup>) super.find(new HQL(hql));
	}

	public List<UserGroup> findRootGroups() {
		String hql = "select g from Group g where g.id not in"
				+ "(select r.subgroupid from Group_Relation r)";
		return (List<UserGroup>) super.find(new HQL(hql));
	}

	public List<UserGroup> findRootGroups(int grouptype) {
		String hql = "select g from Group g where g.id not in"
				+ "(select r.subgroupid from Group_Relation r) and g.grouptype="
				+ grouptype;
		return (List<UserGroup>) super.find(new HQL(hql));
	}
	
	public List<UserGroup> findSubGroups(int groupid) {
		String hql = "select g from Group_Relation r,Group g "
				+ "where r.subgroupid=g.id and r.groupid=" + groupid;
		return (List<UserGroup>) super.find(new HQL(hql));
	}

	public List<UserGroup> findSubGroups(int groupid, int grouptype) {
		String hql = "select g from Group_Relation r,Group g "
				+ "where r.subgroupid=g.id and r.groupid=" + groupid
				+ " and g.grouptype=" + grouptype;
		return (List<UserGroup>) super.find(new HQL(hql));
	}
}
