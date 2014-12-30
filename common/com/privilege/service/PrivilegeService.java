package com.privilege.service;

import java.util.List;

import com.privilege.model.Function;
import com.privilege.model.Permission;
import com.user.model.User;

/**
 * 权限管理模块。
 * 
 * 权限与权限为树关系。
 * 
 * @author DongJun
 * 
 */
public interface PrivilegeService {
	/******************** 权限相关接口 ***********************/

	/**
	 * 添加权限
	 */
	public Permission addPerm(String permname, int permtype, String permdesc)
			throws Exception;

	/**
	 * 添加子权限
	 */
	public Permission addBindSubPerm(int parentpermid,String permname, int permtype,
			String permdesc) throws Exception;

	/**
	 * 添加父权限
	 */
	public Permission addBindParentPerm(int subpermid,String permname, int permtype,
			String permdesc) throws Exception;

	/**
	 * 绑定子权限
	 */
	public void bindSubPerm(int permid, int subpermid) throws Exception;

	/**
	 * 绑定父权限
	 */
	public void bindParentPerm(int permid, int parentpermid) throws Exception;

	/**
	 * 解绑子权限
	 */
	public void delBindSubPerm(int permid, int subpermid) throws Exception;

	/**
	 * 解绑父权限
	 */
	public void delBindParentPerm(int permid, int parentid)
			throws Exception;

	/**
	 * 解绑所有子权限
	 */
	public void delBindSubPerms(int permid) throws Exception;

	/**
	 * 解绑所有父权限
	 */
	public void delBindParentPerms(int permid) throws Exception;

	/**
	 * 解绑所有父子权限
	 */
	public void delBindPerms(int permid) throws Exception;

	/**
	 * 删除权限
	 */
	public void delPerm(int permid) throws Exception;

	/**
	 * 查询权限
	 */
	public Permission findPerm(int permid) throws Exception;

	/**
	 * 查询子权限列表，不包括子权限下的子权限
	 */
	public List<Permission> findSubPerms(int permid) throws Exception;

	/**
	 * 查询类型为permtype的子权限列表，不包括子权限下的子权限
	 */
	public List<Permission> findSubPerms(int permid, int permtype)
			throws Exception;

	/**
	 * 查询子权限列表，包括所有子权限下的子权限
	 */
	public List<Permission> findSubPerms_Recursion(int permid) throws Exception;

	/**
	 * 查询类型为permtype的子权限列表，包括所有子权限下的子权限
	 */
	public List<Permission> findSubPerms_Recursion(int permid, int permtype)
			throws Exception;

	/**
	 * 查询所有根权限列表
	 */
	public List<Permission> findPermRoot() throws Exception;

	/**
	 * 查询所有类型为permtype的根权限列表
	 */
	public List<Permission> findPermRoot(int permtype) throws Exception;

	/**
	 * 查询所有叶子权限列表
	 */
	public List<Permission> findPermLeaf() throws Exception;

	/**
	 * 查询所有类型为permtype的叶子权限列表
	 */
	public List<Permission> findPermLeaf(int permtype) throws Exception;

	/**
	 * 查询系统所有权限
	 */
	public List<Permission> findPerms() throws Exception;

	/**
	 * 查询系统所有类型为permtype的权限
	 */
	public List<Permission> findPerms(int permtype) throws Exception;

	/**
	 * 绑定功能
	 */
	public void bindFunc(int permid, int funcid) throws Exception;

	/**
	 * 解绑功能
	 */
	public void delBindFunc(int permid, int funcid) throws Exception;

	/**
	 * 解绑所有功能
	 */
	public void delBindFuncs(int permid) throws Exception;

	/**
	 * 查询功能列表，不包括子权限的功能列表
	 */
	public List<Function> findFuncs(int permid) throws Exception;

	/**
	 * 查询功能列表，包括子权限的功能列表
	 */
	public List<Function> findFuncs_Recursion(int permid) throws Exception;

	/***************************** 用户相关接口 *******************/
	/**
	 * 绑定用户
	 */
	public void bindUser(int permid, int userid) throws Exception;

	/**
	 * 解绑用户
	 */
	public void delBindUser(int permid, int userid) throws Exception;

	/**
	 * 解绑所有用户
	 */
	public void delBindUsers(int permid) throws Exception;
	
	/**
	 * 查看权限所绑定的用户
	 */
	public List<User> findUsers(int permid) throws Exception;
	
	/**
	 * 查看权限所绑定的用户
	 */
	public List<User> findUsers(int permid,int page,int rows) throws Exception;
}