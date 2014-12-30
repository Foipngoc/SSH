package com.user.service;

import java.util.List;

import com.user.model.UserGroup;

public interface UserGroupService {
	/**
	 * 添加用户组
	 */
	public UserGroup addGroup(String groupname, int grouptype, String groupdesc) throws Exception;

	/**
	 * 添加子用户组
	 */
	public UserGroup addBindSubGroup(int parentgroupid,String groupname, int grouptype, String groupdesc)throws Exception;
	
	/**
	 * 添加父用户组
	 */
	public UserGroup addBindParentGroup(int subgroupid,String groupname, int grouptype, String groupdesc)throws Exception;
	
	/**
	 * 绑定子用户组
	 */
	public void bindSubGroup(int groupid, int subgroupid) throws Exception;

	/**
	 * 绑定父用户组
	 */
	public void bindParentGroup(int groupid,int parentgroupid) throws Exception;
	
	/**
	 * 解除子用户组
	 */
	public void delBindSubGroup(int groupid, int subgroupid)throws Exception;

	/**
	 * 解除父用户组
	 */
	public void delBindParentGroup(int groupid, int parentgroupid)throws Exception;

	/**
	 * 解除所有子用户组
	 */
	public void delBindSubGroups(int groupid)throws Exception;
	
	/**
	 * 解除所有父用户组
	 */
	public void delBindParentGroups(int groupid)throws Exception;
	
	/**
	 * 解除所有用户组
	 */
	public void delBindGroups(int groupid)throws Exception;
	
	/**
	 * 删除用户组
	 */
	public void delGroup(int groupid)throws Exception;

	/**
	 * 修改用户组信息
	 */
	public UserGroup modifyGroup(int groupid, String groupname,
			Integer grouptype, String groupdesc)throws Exception;

	/**
	 * 查找叶子用户组
	 */
	public List<UserGroup> findLeafGroup()throws Exception;

	/**
	 * 查找某类型叶子用户组
	 */
	public List<UserGroup> findLeafGroup(int grouptype)throws Exception;

	/**
	 * 查找根用户组
	 */
	public List<UserGroup> findRootGroup()throws Exception;

	/**
	 * 查找某类型根用户组
	 */
	public List<UserGroup> findRootGroup(int grouptype)throws Exception;

	/**
	 * 查询某用户组的信息
	 */
	public UserGroup findGroup(int groupid)throws Exception;

	/**
	 * 查找子用户组，不包括子组的子组
	 */
	public List<UserGroup> findSubGroup(int groupid)throws Exception;

	/**
	 * 查找某类型子用户组，不包括子组的子组
	 */
	public List<UserGroup> findSubGroup(int groupid, int grouptype)throws Exception;

	/**
	 * 查找子用户组，包括子组的子组
	 */
	public List<UserGroup> findSubGroup_Recursion(int groupid)throws Exception;

	/**
	 * 查找某类型子用户组，包括子组的子组
	 */
	public List<UserGroup> findSubGroup_Recursion(int groupid, int grouptype)throws Exception;

	/**
	 * 查找所有组
	 */
	public List<UserGroup> findGroups()throws Exception;

	/**
	 * 查找某类型的所有组
	 */
	public List<UserGroup> findGroups(int grouptype)throws Exception;
	
	/**
	 * 绑定组用户
	 */
	public void addBindUser(int groupid,int userid)throws Exception;
	
	/**
	 * 解绑组用户
	 */
	public void delBindUser(int groupid,int userid)throws Exception;
	
	/**
	 * 解绑所有组用户
	 */
	public void delBindUsers(int groupid)throws Exception;
	
	/**
	 * 查寻组内用户
	 */
	public void findUsers(int groupid)throws Exception;
}
