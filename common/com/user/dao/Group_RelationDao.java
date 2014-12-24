package com.user.dao;

import org.springframework.stereotype.Repository;

import com.common.dao.HQL;
import com.common.dao.impl.BaseDaoImpl;
import com.user.model.Group_Relation;

@Repository("group_RelationDao")
public class Group_RelationDao extends BaseDaoImpl<Group_Relation> {
	public void delGroup_Relation(int groupid, int subgroupid) {
		super.deleteHql(new HQL(
				"delete from Group_Relation r where r.groupid=? and r.subgroupid=?",
				groupid, subgroupid));
	}
}
