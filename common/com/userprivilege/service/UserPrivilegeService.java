package com.userprivilege.service;

import java.util.List;

import com.userprivilege.exception.SelfReferenceException;
import com.userprivilege.model.privilege.Function;
import com.userprivilege.model.privilege.Permission;
import com.userprivilege.model.user.User;
import com.userprivilege.model.user.UserGroup;

/**
 * 用户接口，包括用户管理,用户组管理,权限管理
 * 
 * @author DongJun
 * 
 */
public interface UserPrivilegeService {
	/************************************* 用户管理 ***********************************/
	/**
	 * 添加新用户
	 */
	public void addUser(User user);

	/**
	 * 删除用户，同时删除用户与权限的关联，用户与用户组的关联
	 */
	public void delUser(int userid);

	/**
	 * 修改用户
	 */
	public void modifyUser(User user);

	/**
	 * 查询用户
	 */
	public User findUser(int userid);

	/**
	 * 通过用户名查找，用户名为unique键
	 */
	public User findUser(String username);

	/**
	 * 查询全部用户，带分页
	 */
	public List<User> findUsers(int page, int rows);

	/**
	 * 查询状态为userstatus的全部用户，带分页
	 */
	public List<User> findUsers(int userstatus, int page, int rows);

	/**
	 * 查询状态不为notuserstatus的全部用户，带分页
	 */
	public List<User> findUsersByNotStatus(int notuserstatus, int page, int rows);

	
	/**
	 * 获得用户总数
	 */
	public long countUsers();

	/**
	 * 获得某状态用户总数
	 */
	public long countUsers(int userstatus);

	/**
	 * 获得状态不为notuserstatusr的用户总数
	 */
	public long countUsersByNotStatus(int notuserstatus);

	/************************************* 用户组管理 *******************************************/
	/**
	 * 添加用户组
	 */
	public UserGroup addGroup(String groupname, int grouptype, String groupdesc);

	/**
	 * 添加子用户组
	 */
	public UserGroup addBindSubGroup(int parentgroupid, String groupname,
			int grouptype, String groupdesc) throws SelfReferenceException;

	/**
	 * 添加父用户组
	 */
	public UserGroup addBindParentGroup(int subgroupid, String groupname,
			int grouptype, String groupdesc) throws SelfReferenceException;

	/**
	 * 绑定子用户组
	 */
	public void bindSubGroup(int groupid, int subgroupid)
			throws SelfReferenceException;

	/**
	 * 绑定父用户组
	 */
	public void bindParentGroup(int groupid, int parentgroupid)
			throws SelfReferenceException;

	/**
	 * 解除子用户组
	 */
	public void delBindSubGroup(int groupid, int subgroupid);

	/**
	 * 解除父用户组
	 */
	public void delBindParentGroup(int groupid, int parentgroupid);

	/**
	 * 解除所有子用户组
	 */
	public void delBindSubGroups(int groupid);

	/**
	 * 解除所有父用户组
	 */
	public void delBindParentGroups(int groupid);

	/**
	 * 解除所有父子用户组
	 */
	public void delBindGroups(int groupid);

	/**
	 * 删除用户组，同时删除用户组与用户的关联，用户组与父子用户组的关联
	 */
	public void delGroup(int groupid);

	/**
	 * 修改用户组信息
	 */
	public UserGroup modifyGroup(int groupid, String groupname,
			Integer grouptype, String groupdesc);

	/**
	 * 查找叶子用户组
	 */
	public List<UserGroup> findLeafGroups();

	/**
	 * 查找某类型叶子用户组
	 */
	public List<UserGroup> findLeafGroups(int grouptype);

	/**
	 * 查找根用户组
	 */
	public List<UserGroup> findRootGroups();

	/**
	 * 查找某类型根用户组
	 */
	public List<UserGroup> findRootGroups(int grouptype);

	/**
	 * 查询某用户组的信息
	 */
	public UserGroup findGroup(int groupid);

	/**
	 * 查找子用户组，不包括子组的子组
	 */
	public List<UserGroup> findSubGroups(int groupid);

	/**
	 * 查找某类型子用户组，不包括子组的子组
	 */
	public List<UserGroup> findSubGroups(int groupid, int grouptype);

	/**
	 * 查找子用户组，包括子组的子组
	 */
	public List<UserGroup> findSubGroups_Recursion(int groupid);

	/**
	 * 查找某类型子用户组，包括子组的子组
	 */
	public List<UserGroup> findSubGroups_Recursion(int groupid, int grouptype);

	/**
	 * 查找所有组
	 */
	public List<UserGroup> findGroups();

	/**
	 * 查找某类型的所有组
	 */
	public List<UserGroup> findGroups(int grouptype);

	/************************************ 角色权限管理 ***************************************/
	/**
	 * 添加权限
	 */
	public Permission addPerm(String permname, int permtype, String permdesc);

	/**
	 * 添加子权限
	 */
	public Permission addBindSubPerm(int parentpermid, String permname,
			int permtype, String permdesc) throws SelfReferenceException;

	/**
	 * 添加父权限
	 */
	public Permission addBindParentPerm(int subpermid, String permname,
			int permtype, String permdesc) throws SelfReferenceException;

	/**
	 * 绑定子权限
	 */
	public void bindSubPerm(int permid, int subpermid)
			throws SelfReferenceException;

	/**
	 * 绑定父权限
	 */
	public void bindParentPerm(int permid, int parentpermid)
			throws SelfReferenceException;

	/**
	 * 解绑子权限
	 */
	public void delBindSubPerm(int permid, int subpermid);

	/**
	 * 解绑父权限
	 */
	public void delBindParentPerm(int permid, int parentid);

	/**
	 * 解绑所有子权限
	 */
	public void delBindSubPerms(int permid);

	/**
	 * 解绑所有父权限
	 */
	public void delBindParentPerms(int permid);

	/**
	 * 解绑所有父子权限
	 */
	public void delBindPerms(int permid);

	/**
	 * 删除权限，同时删除权限与父子权限的关联，删除权限与用户的关联,删除权限与功能的关联
	 */
	public void delPerm(int permid);

	/**
	 * 修改权限
	 */
	public void modifyPerm(Permission perm);
	
	/**
	 * 查询权限
	 */
	public Permission findPerm(int permid);

	/**
	 * 查询权限
	 */
	public Permission findPerm(int permid,int permtype);

	/**
	 * 查询子权限列表，不包括子权限下的子权限
	 */
	public List<Permission> findSubPerms(int permid);

	/**
	 * 查询父权限列表
	 */
	public List<Permission> findParentPerms(int permid);
	
	/**
	 * 查询类型为permtype的子权限列表，不包括子权限下的子权限
	 */
	public List<Permission> findSubPerms(int permid, int permtype);

	/**
	 * 查询子权限列表，不包括子权限下的子权限
	 */
	public List<Permission> findSubPerms(int permid,int page,int rows);

	/**
	 * 查询类型为permtype的子权限列表，不包括子权限下的子权限
	 */
	public List<Permission> findSubPerms(int permid, int permtype,int page,int rows);

	/**
	 * 计数类型为permtype的子权限列表，不包括子权限下的子权限
	 */
	public long countSubPerms(int permid, int permtype);
	
	/**
	 * 查询子权限列表，包括所有子权限下的子权限
	 */
	public List<Permission> findSubPerms_Recursion(int permid);

	/**
	 * 查询类型为permtype的子权限列表，包括所有子权限下的子权限
	 */
	public List<Permission> findSubPerms_Recursion(int permid, int permtype);

	/**
	 * 查询所有根权限列表
	 */
	public List<Permission> findRootPerms();

	/**
	 * 查询所有类型为permtype的根权限列表
	 */
	public List<Permission> findRootPerms(int permtype);

	/**
	 * 查询所有叶子权限列表
	 */
	public List<Permission> findLeafPerms();

	/**
	 * 查询所有类型为permtype的叶子权限列表
	 */
	public List<Permission> findLeafPerms(int permtype);

	/**
	 * 查询系统所有权限
	 */
	public List<Permission> findPerms();
	
	/**
	 * 查询系统所有权限
	 */
	public List<Permission> findPerms(int page,int rows);

	/**
	 * 查询系统所有类型为permtype的权限
	 */
	public List<Permission> findPerms(int permtype);
	
	/**
	 * 查询系统所有类型为permtype的权限
	 */
	public List<Permission> findPerms(int permtype,int page,int rows);

	/**
	 * 查询系统所有类型为permtype的权限,
	 */
	public List<Permission> findPermsOrderByParent(int permtype,int page,int rows);

	
	/**
	 * 查询系统所有类型为permtype的权限
	 */
	public long countPerms(int permtype);

	/************************************************* 权限与功能管理 ********************************************/
	/**
	 * 权限绑定功能
	 */
	public void bindPermFunc(int permid, int funcid);

	/**
	 * 权限与功能解绑
	 */
	public void delBindPermFunc(int permid, int funcid);

	/**
	 * 解绑权限所有功能
	 */
	public void delBindPermFuncs(int permid);

	/**
	 * 查询权限功能列表，不包括子权限的功能列表
	 */
	public List<Function> findPermFuncs(int permid);

	/**
	 * 查询权限功能列表，包括子权限的功能列表
	 */
	public List<Function> findPermFuncs_Recursion(int permid);

	/********************************************* 权限与用户管理 ***************************************/
	/**
	 * 用户权限绑定
	 */
	public void bindUserPerm(int userid, int permid);

	/**
	 * 用户与权限解绑
	 */
	public void delBindUserPerm(int userid, int permid);

	/**
	 * 用户权限绑定
	 */
	public void bindPermUser(int permid, int userid);

	/**
	 * 权限与用户解绑
	 */
	public void delBindPermUser(int permid, int userid);

	/**
	 * 解绑用户所有权限
	 */
	public void delBindUserPerms(int userid);

	/**
	 * 解绑权限所有用户
	 */
	public void delBindPermUsers(int permid);

	/**
	 * 查寻用户绑定的权限
	 * 
	 */
	public List<Permission> findUserPerms(int userid);

	/**
	 * 查寻用户绑定的权限，包括子权限
	 * 
	 */
	public List<Permission> findUserPerms_Recursion(int userid);

	/**
	 * 查看权限所绑定的用户
	 */
	public List<User> findPermUsers(int permid, int page, int rows);

	/**
	 * 查看权限所绑定的 用户状态为userstatus的所有用户
	 */
	public List<User> findPermUsers(int permid, int userstatus, int page,
			int rows);

	/**
	 * 查看权限所绑定的 用户状态不为userstatus的所有用户
	 */
	public List<User> findPermUsersByNotStatus(int permid, int notuserstatus,
			int page, int rows);

	/*********************************************** 用户与用户组管理 *********************************************/
	/**
	 * 用户组与用户绑定
	 */
	public void bindGroupUser(int groupid, int userid);

	/**
	 * 解绑用户组与用户
	 */
	public void delBindGroupUser(int groupid, int userid);

	/**
	 * 绑定用户与用户组
	 */
	public void bindUserGroup(int userid, int groupid);

	/**
	 * 解绑用户与用户组
	 */
	public void delBindUserGroup(int userid, int groupid);

	/**
	 * 解绑用户组所有用户
	 */
	public void delBindGroupUsers(int groupid);

	/**
	 * 解绑用户绑定的所有用户组
	 */
	public void delBindUserGroups(int userid);

	/**
	 * 查寻组中所有用户
	 */
	public List<User> findGroupUsers(int groupid);

	/**
	 * 查寻组内用户,带分页
	 */
	public List<User> findGroupUsers(int groupid, int page, int rows);

	/**
	 * 查寻所有组的用户，按组分组，带分页
	 */
	public List<User> findAllGroupUsersOrderByNotStatus(int notuserstatus,int page,int rows);
	
	/**
	 * 查寻组内状态为userstatus的用户
	 */
	public List<User> findGroupUsers(int groupid, int userstatus, int page,
			int rows);

	/**
	 * 查寻组内状态不为notuserstatus的用户
	 */
	public List<User> findGroupUsersByNotStatus(int groupid, int notuserstatus,
			int page, int rows);
	
	/**
	 * 计数组内状态不为notuserstatus的用户
	 */
	public long countGroupUsersByNotStatus(int groupid, int notuserstatus);


	/**
	 * 查询用户绑定的所有用户组
	 */
	public List<UserGroup> findUserGroups(int userid);

	/**
	 * 查询用户绑定的所有类型为grouptype的用户组
	 */
	public List<UserGroup> findUserGroups(int userid, int grouptype);
}
