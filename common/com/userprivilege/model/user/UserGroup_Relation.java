package com.userprivilege.model.user;

public class UserGroup_Relation {
	private int id;//组关系 ID
	private int groupid;//父组ID
	private int subgroupid;//子组ID

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGroupid() {
		return groupid;
	}

	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}

	public int getSubgroupid() {
		return subgroupid;
	}

	public void setSubgroupid(int subgroupid) {
		this.subgroupid = subgroupid;
	}
}
