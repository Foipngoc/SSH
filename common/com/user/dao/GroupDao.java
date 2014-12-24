package com.user.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.common.dao.HQL;
import com.common.dao.impl.BaseDaoImpl;
import com.user.model.Group;

@Repository("groupDao")
@SuppressWarnings("all")
public class GroupDao extends BaseDaoImpl<Group> {
	public void delGroup(int groupid) {
		super.deleteHql(new HQL("delete from Group g where g.id=" + groupid));
	}

	public List<Group> queryLeafGroup() {
		String hql = "select g from Group g where g.id not in"
				+ "(select r.groupid from Group_Relation r)";
		return (List<Group>) super.findHql(new HQL(hql));
	}

	public List<Group> queryLeafGroup(int grouptype) {
		String hql = "select g from Group g where g.id not in"
				+ "(select r.groupid from Group_Relation r) and g.grouptype="
				+ grouptype;
		return (List<Group>) super.findHql(new HQL(hql));
	}

	public List<Group> queryRootGroup() {
		String hql = "select g from Group g where g.id not in"
				+ "(select r.subgroupid from Group_Relation r)";
		return (List<Group>) super.findHql(new HQL(hql));
	}

	public List<Group> queryRootGroup(int grouptype) {
		String hql = "select g from Group g where g.id not in"
				+ "(select r.subgroupid from Group_Relation r) and g.grouptype="
				+ grouptype;
		return (List<Group>) super.findHql(new HQL(hql));
	}

	public Group queryGroup(int groupid) {
		List<Group> groups = (List<Group>) super.findHql(new HQL(
				"select g from Group g where g.id=" + groupid));
		if (groups != null && groups.size() > 0)
			return groups.get(0);
		return null;
	}

	public List<Group> querySubGroup(int groupid) {
		String hql = "select g from Group_Relation r,Group g "
				+ "where r.subgroupid=g.id and r.groupid=" + groupid;
		return (List<Group>) super.findHql(new HQL(hql));
	}

	public List<Group> querySubGroup(int groupid, int grouptype) {
		String hql = "select g from Group_Relation r,Group g "
				+ "where r.subgroupid=g.id and r.groupid=" + groupid
				+ " and g.grouptype=" + grouptype;
		return (List<Group>) super.findHql(new HQL(hql));
	}

	public List<Group> queryGroups() {
		return (List<Group>) super.findHql(new HQL("select g from Group g"));
	}

	public List<Group> queryGroups(int grouptype) {
		return (List<Group>) super.findHql(new HQL(
				"select g from Group g where g.grouptype=" + grouptype));
	}
	
	
}
