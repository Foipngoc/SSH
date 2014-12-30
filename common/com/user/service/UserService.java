package com.user.service;

import java.util.List;

import com.privilege.model.Permission;
import com.user.model.User;
import com.user.model.UserGroup;

/**
 * 用户接口，包括用户管理，用户组管理
 * @author DongJun
 *
 */
public interface UserService {
	/*********************用户自身管理***************************/
	/**
	 * 添加用户
	 */
	public void addUser(User user)throws Exception;
	
	/**
	 * 删除用户
	 */
	public void delUser(int userid)throws Exception;
	
	/**
	 * 修改用户
	 */
	public void modifyUser(User user)throws Exception;
	
	/**
	 * 查询用户
	 */
	public User findUser(int userid)throws Exception;
	
	/**********************用户与用户组管理************************/
	/**
	 * 绑定到某用户组
	 */
	public void bindUserGroup(int userid,int groupid)throws Exception;
	
	/**
	 * 解绑某用户组
	 */
	public void delBindUserGroup(int userid,int groupid)throws Exception;
	
	/**
	 * 解绑所有用户组
	 */
	public void delBindUserGroups(int userid)throws Exception;
	
	/**
	 * 查找用户所绑定的用户组
	 */
	public List<UserGroup> findUserGroups(int userid)throws Exception;
	
	/***********************用户与权限***************************/
	/**
	 * 绑定用户到某权限
	 */
	public void bindPermission(int userid,int permid)throws Exception;
	
	/**
	 * 解绑某权限
	 */
	public void delBindPermission(int userid,int permid)throws Exception;
	
	/**
	 * 解绑所有权限
	 */
	public void delBindPermissions(int userid)throws Exception;
	
	/**
	 * 查寻用户绑定的权限
	 */
	public List<Permission> findPermissoins(int userid)throws Exception;
	
	/**
	 * 查寻用户绑定的权限，包括子权限
	 */
	public List<Permission> findPermissoins_Recursion (int userid)throws Exception;
}
