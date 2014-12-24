package com.privilege.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.privilege.model.Function;
import com.privilege.model.Permission;
import com.privilege.model.Permission_function_relation;
import com.privilege.model.Permission_relation;
import com.privilege.model.User_permission_relation;

public interface PrivilegeService {
	/******************** 权限相关接口 ***********************/

   	/**
	 * 新增权限,该权限不为任何权限的子权限
	 */
	public Permission addPerm(String permname,int permtype,String permdesc) throws Exception;

   	/**
	 * 新增权限关联
	 */
	public Permission_relation addPermRelation(int parentpermid,int subpermid) throws Exception;

	/**
	 * 删除权限关联
	 */
	public void delPermRelation(int parentpermid,int subpermid) throws Exception;
	
	/**
	 * 删除权限,删除权限后，
	 * 该权限与功能的关联关系会取消；
	 * 该权限与父子权限之间的关系取消
	 */
	@Transactional
	public void delPerm(int permid) throws Exception;
	
	/**
	 * 查询某权限的子权限列表，不包括子权限下的子权限
	 */
	public List<Permission> queryPermSubPerms(int permid) throws Exception;

	/**
	 * 查询某权限的类型为permtype的子权限列表，不包括子权限下的子权限
	 */
	public List<Permission> queryPermSubPerms(int permid,int permtype) throws Exception;
	
	/**
	 * 查询某权限的子权限列表，包括所有子权限下的子权限
	 */
	public List<Permission> queryPermSubPermsAll(int permid) throws Exception;

	/**
	 * 查询某权限的类型为permtype的子权限列表，包括所有子权限下的子权限
	 */
	public List<Permission> queryPermSubPermsAll(int permid,int permtype) throws Exception;
	
	/**
	 * 查询所有根权限列表 ,即该权限不是任何权限的子权限
	 */
	public List<Permission> queryPermRoot() throws Exception;

	/**
	 * 查询所有类型为permtype的根权限列表 ,即该权限不是任何权限的子权限
	 */
	public List<Permission> queryPermRoot(int permtype) throws Exception;

	
	/**
	 * 查询所有叶子节点，既该权限没有子权限
	 */
	public List<Permission> queryPermLeaf() throws Exception;
	
	/**
	 * 查询所有类型为permtype的叶子节点，既该权限没有子权限
	 */
	public List<Permission> queryPermLeaf(int permtype) throws Exception;
	
	/**
	 * 查询系统所有权限
	 */
	public List<Permission> queryPermAll() throws Exception;
	
	/**
	 * 查询系统所有类型为permtype的权限
	 */
	public List<Permission> queryPermAll(int permtype) throws Exception;

	/**
	 * 添加权限功能
	 */
	public Permission_function_relation addPermFunc(int permid,int funcid) throws Exception;
	
	/**
	 * 删除某权限某功能
	 */
	@Transactional
	public void delPermFunc(int permid,int funcid) throws Exception;
	
	/**
	 * 删除权限所有功能
	 */
	@Transactional
	public void delPermFuncAll(int permid) throws Exception;
	
	/**
	 * 查询某权限功能列表，不包括子权限的功能列表
	 */
	public List<Function> queryPermFuncs(int permid) throws Exception;

	/**
	 * 查询某权限功能列表，包括子权限的功能列表
	 */
	public List<Function> queryPermFuncsAll(int permid) throws Exception;
	
	/***************************** 用户相关接口 *******************/
	/**
	 * 添加某用户某权限
	 */
	public User_permission_relation addUserPerm(int userid, int permid) throws Exception;

	/**
	 * 删除某用户某权限
	 */
	@Transactional
	public void delUserPerm(int userid, int permid) throws Exception;

	/**
	 * 删除某用户所有权限
	 */
	@Transactional
	public void delUserPermAll(int userid) throws Exception;

	/**
	 * 查询某用户的所有权限，不包括权限的子权限。
	 */
	public List<Permission> queryUserPerm(int userid) throws Exception;
	
	/**
	 * 查询某用户的所有权限，包括权限的子权限。
	 */
	public List<Permission> queryUserPermAll(int userid) throws Exception;

	/**
	 * 查询某用户的所有类型为permtype的权限，不包括权限的子权限。
	 */
	public List<Permission> queryUserPerm(int userid,int permtype) throws Exception;
	
	/**
	 * 查询某用户的所有类型为permtype的权限，包括权限的子权限。
	 */
	public List<Permission> queryUserPermAll(int userid,int permtype) throws Exception;
	
	/**
	 * 查询用户所拥有的所有功能列表
	 */
	public List<Function> queryUserFuncAll(int userid) throws Exception;
}