package com.userprivilege.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.userprivilege.dao.privilege.FunctionDao;
import com.userprivilege.dao.privilege.PermissionDao;
import com.userprivilege.dao.privilege.Permission_function_relationDao;
import com.userprivilege.dao.privilege.Permission_relationDao;
import com.userprivilege.dao.privilege.User_permission_relationDao;
import com.userprivilege.dao.user.UserDao;
import com.userprivilege.dao.user.UserGroupDao;
import com.userprivilege.dao.user.UserGroup_RelationDao;
import com.userprivilege.dao.user.UserGroup_User_RelationDao;
import com.userprivilege.exception.SelfReferenceException;
import com.userprivilege.model.privilege.Function;
import com.userprivilege.model.privilege.Permission;
import com.userprivilege.model.privilege.Permission_function_relation;
import com.userprivilege.model.privilege.Permission_relation;
import com.userprivilege.model.privilege.User_permission_relation;
import com.userprivilege.model.user.User;
import com.userprivilege.model.user.UserGroup;
import com.userprivilege.model.user.UserGroup_Relation;
import com.userprivilege.model.user.UserGroup_User_Relation;
import com.userprivilege.service.UserPrivilegeService;

@Transactional
@Service("userPrivilegeService")
@SuppressWarnings("all")
public class UserPrivilegeServiceImpl implements UserPrivilegeService {
	@Resource
	private UserGroupDao userGroupDao;
	@Resource
	private UserGroup_RelationDao userGroup_RelationDao;
	@Resource
	private UserGroup_User_RelationDao userGroup_User_RelationDao;
	@Resource
	private UserDao userDao;
	@Resource
	private FunctionDao functionDao;
	@Resource
	private PermissionDao permissionDao;
	@Resource
	private Permission_relationDao permission_relationDao;
	@Resource
	private Permission_function_relationDao permission_function_relationDao;
	@Resource
	private User_permission_relationDao user_permission_relationDao;

	/************************************* 用户管理 ***********************************/
	/**
	 * 添加新用户
	 */
	public void addUser(User user) {
		this.userDao.save(user);
	}

	/**
	 * 删除用户，同时删除用户与权限的关联，用户与用户组的关联
	 */
	public void delUser(int userid) {
		// 删除用户与权限的关联
		this.user_permission_relationDao.delUser_Perms(userid);
		// 删除用户与用户组的关联
		this.userGroup_User_RelationDao.delGroup_User_Relations(userid);
		this.userDao.delUser(userid);
	}

	/**
	 * 修改用户
	 */
	public void modifyUser(User user) {
		this.userDao.update(user);
	}

	/**
	 * 查询用户
	 */
	public User findUser(int userid) {
		return this.userDao.findUser(userid);
	}

	/**
	 * 通过用户名查找，用户名为unique键
	 */
	public User findUser(String username) {
		return this.userDao.findUnique(new User(), "username", username);
	}

	/**
	 * 查询全部用户，带分页
	 */
	public List<User> findUsers(int page, int rows) {
		return this.userDao.find(new User(), page, rows);
	}

	/**
	 * 查询状态为userstatus的全部用户，带分页
	 */
	public List<User> findUsers(int userstatus, int page, int rows) {
		return this.userDao.find(new User(), "userstatus", userstatus, page,
				rows);
	}

	/**
	 * 查询状态不为notuserstatus的全部用户，带分页
	 */
	public List<User> findUsersByNotStatus(int notuserstatus, int page, int rows) {
		return this.userDao.findByNotStatus(notuserstatus, page, rows);
	}

	/**
	 * 获得用户总数
	 */
	public long countUsers() {
		return this.userDao.count(new User());
	}

	/**
	 * 获得某状态用户总数
	 */
	public long countUsers(int userstatus) {
		return this.userDao.count(new User(), "userstatus", userstatus);
	}

	/**
	 * 获得状态不为notuserstatusr的用户总数
	 */
	public long countUsersByNotStatus(int notuserstatus) {
		return this.userDao.countByNotStatus(notuserstatus);
	}

	/************************************* 用户组管理 *******************************************/
	/**
	 * 添加用户组
	 */
	public UserGroup addGroup(String groupname, int grouptype, String groupdesc) {
		UserGroup userGroup = new UserGroup();
		userGroup.setGroupname(groupname);
		userGroup.setGroupdesc(groupdesc);
		userGroup.setGrouptype(grouptype);
		this.userGroupDao.save(userGroup);
		return userGroup;
	}

	/**
	 * 添加子用户组
	 */
	public UserGroup addBindSubGroup(int parentgroupid, String groupname,
			int grouptype, String groupdesc) throws SelfReferenceException {
		UserGroup group = addGroup(groupname, grouptype, groupdesc);
		bindParentGroup(group.getId(), parentgroupid);
		return group;
	}

	/**
	 * 添加父用户组
	 */
	public UserGroup addBindParentGroup(int subgroupid, String groupname,
			int grouptype, String groupdesc) throws SelfReferenceException {
		UserGroup group = addGroup(groupname, grouptype, groupdesc);
		bindSubGroup(group.getId(), subgroupid);
		return group;
	}

	/**
	 * 绑定子用户组
	 */
	public void bindSubGroup(int groupid, int subgroupid)
			throws SelfReferenceException {
		bindParentGroup(subgroupid, groupid);
	}

	/**
	 * 绑定父用户组
	 */
	public void bindParentGroup(int groupid, int parentgroupid)
			throws SelfReferenceException {
		if (groupid == parentgroupid)
			throw new SelfReferenceException();

		// 如果已绑定，则直接返回
		if (userGroup_RelationDao.find(parentgroupid, groupid) != null)
			return;

		// 如果子组的子孙中有父，则返回，避免无限嵌套
		List<UserGroup> userGroups = findSubGroups_Recursion(groupid);
		for (int i = 0; i < userGroups.size(); i++) {
			if (userGroups.get(i).getId() == parentgroupid) {
				throw new SelfReferenceException();
			}
		}

		UserGroup_Relation userGroup_Relation = new UserGroup_Relation();
		userGroup_Relation.setGroupid(parentgroupid);
		userGroup_Relation.setSubgroupid(groupid);
		this.userGroup_RelationDao.save(userGroup_Relation);

	}

	/**
	 * 解除子用户组
	 */
	public void delBindSubGroup(int groupid, int subgroupid) {
		this.userGroup_RelationDao.delGroup_Relation(groupid, subgroupid);
	}

	/**
	 * 解除父用户组
	 */
	public void delBindParentGroup(int groupid, int parentgroupid) {
		this.userGroup_RelationDao.delGroup_Relation(parentgroupid, groupid);
	}

	/**
	 * 解除所有子用户组
	 */
	public void delBindSubGroups(int groupid) {
		this.userGroup_RelationDao.delSubGroup_Relations(groupid);
	}

	/**
	 * 解除所有父用户组
	 */
	public void delBindParentGroups(int groupid) {
		this.userGroup_RelationDao.delParentGroup_Relations(groupid);
	}

	/**
	 * 解除所有父子用户组
	 */
	public void delBindGroups(int groupid) {
		delBindSubGroups(groupid);
		delBindParentGroups(groupid);
	}

	/**
	 * 删除用户组，同时删除用户组与用户的关联，用户组与父子用户组的关联
	 */
	public void delGroup(int groupid) {
		// 删除组与组的关系
		delBindSubGroups(groupid);
		delBindParentGroups(groupid);

		// 删除组与用户的关系
		delBindGroupUsers(groupid);
		this.userGroupDao.delGroup(groupid);
	}

	/**
	 * 修改用户组信息
	 */
	public UserGroup modifyGroup(int groupid, String groupname,
			Integer grouptype, String groupdesc) {
		UserGroup userGroup = this.userGroupDao.findGroup(groupid);
		userGroup.setGroupdesc(groupdesc);
		userGroup.setGroupname(groupname);
		userGroup.setGrouptype(grouptype);
		this.userGroupDao.update(userGroup);

		return userGroup;
	}

	/**
	 * 查找叶子用户组
	 */
	public List<UserGroup> findLeafGroups() {
		return this.userGroup_RelationDao.findLeafGroups();
	}

	/**
	 * 查找某类型叶子用户组
	 */
	public List<UserGroup> findLeafGroups(int grouptype) {
		return this.userGroup_RelationDao.findLeafGroups(grouptype);
	}

	/**
	 * 查找根用户组
	 */
	public List<UserGroup> findRootGroups() {
		return this.userGroup_RelationDao.findRootGroups();
	}

	/**
	 * 查找某类型根用户组
	 */
	public List<UserGroup> findRootGroups(int grouptype) {
		return this.userGroup_RelationDao.findRootGroups(grouptype);
	}

	/**
	 * 查询某用户组的信息
	 */
	public UserGroup findGroup(int groupid) {
		return this.userGroupDao.findGroup(groupid);
	}

	/**
	 * 查找子用户组，不包括子组的子组
	 */
	public List<UserGroup> findSubGroups(int groupid) {
		return this.userGroup_RelationDao.findSubGroups(groupid);
	}

	/**
	 * 查找某类型子用户组，不包括子组的子组
	 */
	public List<UserGroup> findSubGroups(int groupid, int grouptype) {
		return this.userGroup_RelationDao.findSubGroups(groupid, grouptype);
	}

	/**
	 * 以递归的方式获得所有的子组，可能重复
	 */
	private List<UserGroup> _findSubGroups_Recursion(int groupid) {
		List<UserGroup> userGroups = this.findSubGroups(groupid);

		for (int i = 0; i < userGroups.size(); i++) {
			userGroups.addAll(_findSubGroups_Recursion(userGroups.get(i)
					.getId()));
		}
		return userGroups;
	}

	/**
	 * 查找子用户组，包括子组的子组
	 */
	public List<UserGroup> findSubGroups_Recursion(int groupid) {
		List<UserGroup> userGroups = _findSubGroups_Recursion(groupid);

		// 去重复
		Map<String, Object> map = new HashMap<String, Object>();
		Iterator<UserGroup> it = userGroups.iterator();
		while (it.hasNext()) {
			UserGroup userGroup = it.next();

			if (map.containsKey("" + userGroup.getId())) {
				it.remove();
			} else {
				map.put("" + userGroup.getId(), userGroup);
			}

		}
		return userGroups;
	}

	/**
	 * 查找子用户组，包括子组的子组
	 */
	private List<UserGroup> _findSubGroups_Recursion(int groupid, int grouptype) {
		List<UserGroup> userGroups = this.findSubGroups(groupid, grouptype);

		for (int i = 0; i < userGroups.size(); i++) {
			userGroups.addAll(_findSubGroups_Recursion(userGroups.get(i)
					.getId(), grouptype));
		}
		return userGroups;
	}

	/**
	 * 查找某类型子用户组，包括子组的子组
	 */
	public List<UserGroup> findSubGroups_Recursion(int groupid, int grouptype) {
		List<UserGroup> userGroups = _findSubGroups_Recursion(groupid,
				grouptype);

		// 去重复
		Map<String, Object> map = new HashMap<String, Object>();
		Iterator<UserGroup> it = userGroups.iterator();
		while (it.hasNext()) {
			UserGroup userGroup = it.next();

			if (map.containsKey("" + userGroup.getId())) {
				it.remove();
			} else {
				map.put("" + userGroup.getId(), userGroup);
			}

		}
		return userGroups;
	}

	/**
	 * 查找所有组
	 */
	public List<UserGroup> findGroups() {
		return this.userGroupDao.findGroups();
	}

	/**
	 * 查找某类型的所有组
	 */
	public List<UserGroup> findGroups(int grouptype) {
		return this.userGroupDao.findGroups(grouptype);
	}

	/************************************ 角色权限管理 ***************************************/
	/**
	 * 添加权限
	 */
	public Permission addPerm(String permname, int permtype, String permdesc) {
		Permission permission = new Permission();
		permission.setPermname(permname);
		permission.setPermtype(permtype);
		permission.setPermdesc(permdesc);
		permissionDao.save(permission);
		return permission;
	}

	/**
	 * 添加子权限
	 */
	public Permission addBindSubPerm(int parentpermid, String permname,
			int permtype, String permdesc) throws SelfReferenceException {
		Permission permission = addPerm(permname, permtype, permdesc);
		bindSubPerm(parentpermid, permission.getId());
		return permission;
	}

	/**
	 * 添加父权限
	 */
	public Permission addBindParentPerm(int subpermid, String permname,
			int permtype, String permdesc) throws SelfReferenceException {
		Permission permission = addPerm(permname, permtype, permdesc);
		bindParentPerm(subpermid, permission.getId());
		return permission;
	}

	/**
	 * 绑定子权限
	 */
	public void bindSubPerm(int permid, int subpermid)
			throws SelfReferenceException {
		if (permid == subpermid)
			throw new SelfReferenceException();

		// 是否已有关联
		if (this.permission_relationDao.findPerm_relation(permid, subpermid) != null)
			return;

		// 防止权限回路,检查子权限的子权限列表中是否有父权限
		List<Permission> subpermsubs = this.findSubPerms_Recursion(subpermid);
		for (int i = 0; i < subpermsubs.size(); i++) {
			if (subpermsubs.get(i).getId() == permid) {
				throw new SelfReferenceException();
			}
		}
		// 是否已有关联
		Permission_relation permission_relation = this.permission_relationDao
				.findPerm_relation(permid, subpermid);

		// 如果没有关联
		if (permission_relation == null) {
			Permission_relation newpermission_relation = new Permission_relation();
			newpermission_relation.setPermid(permid);
			newpermission_relation.setSubpermid(subpermid);
			this.permission_relationDao.save(newpermission_relation);
		}
	}

	/**
	 * 绑定父权限
	 */
	public void bindParentPerm(int permid, int parentpermid)
			throws SelfReferenceException {
		bindSubPerm(parentpermid, permid);
	}

	/**
	 * 解绑子权限
	 */
	public void delBindSubPerm(int permid, int subpermid) {
		this.permission_relationDao.deletePerm_relation(permid, subpermid);
	}

	/**
	 * 解绑父权限
	 */
	public void delBindParentPerm(int permid, int parentid) {
		delBindSubPerm(parentid, permid);
	}

	/**
	 * 解绑所有子权限
	 */
	public void delBindSubPerms(int permid) {
		this.permission_relationDao.delSubPermissions(permid);
	}

	/**
	 * 解绑所有父权限
	 */
	public void delBindParentPerms(int permid) {
		this.permission_relationDao.delParentPermissions(permid);
	}

	/**
	 * 解绑所有父子权限
	 */
	public void delBindPerms(int permid) {
		delBindParentPerms(permid);
		delBindSubPerms(permid);
	}

	/**
	 * 修改权限
	 */
	public void modifyPerm(Permission perm) {
		this.permissionDao.update(perm);
	}

	/**
	 * 删除权限，同时删除权限与父子权限的关联，删除权限与用户的关联,删除权限与功能的关联
	 */
	public void delPerm(int permid) {
		// 删除该权限的与功能的关联
		delBindPermFuncs(permid);

		// 删除此权限的父子关联权限关系
		delBindPerms(permid);

		// 删除权限与用户的关联
		delBindPermUsers(permid);

		// 删除此权限
		this.permissionDao.delPerm(permid);
	}

	/**
	 * 查询权限
	 */
	public Permission findPerm(int permid) {
		return this.permissionDao.findPerm(permid);
	}

	/**
	 * 查询权限
	 */
	public Permission findPerm(int permid, int permtype) {
		return this.permissionDao.findPerm(permid, permtype);
	}

	/**
	 * 查询子权限列表，不包括子权限下的子权限
	 */
	public List<Permission> findSubPerms(int permid) {
		return this.permission_relationDao.findSubPerms(permid);
	}

	/**
	 * 查询子权限列表，不包括子权限下的子权限
	 */
	public List<Permission> findSubPerms(int permid, int page, int rows) {
		return this.permission_relationDao.findSubPerms(permid, page, rows);
	}

	/**
	 * 查询类型为permtype的子权限列表，不包括子权限下的子权限
	 */
	public List<Permission> findSubPerms(int permid, int permtype, int page,
			int rows) {
		return this.permission_relationDao.findSubPerms(permid, permtype, page,
				rows);
	}
	
	/**
	 * 计数类型为permtype的子权限列表，不包括子权限下的子权限
	 */
	public long countSubPerms(int permid, int permtype){
		return this.permission_relationDao.countSubPerms(permid, permtype);
	}

	/**
	 * 查询类型为permtype的子权限列表，不包括子权限下的子权限
	 */
	public List<Permission> findSubPerms(int permid, int permtype) {
		return this.permission_relationDao.findSubPerms(permid, permtype);
	}

	/**
	 * 以递归的方式所有子权限
	 */
	private List<Permission> _findSubPerms_Recursion(int permid) {
		// 查询自己的子权限
		List<Permission> lists = this.findSubPerms(permid);

		// 查询子权限的所有子权限
		for (int i = 0; i < lists.size(); i++) {
			lists.addAll(_findSubPerms_Recursion(lists.get(i).getId()));
		}
		return lists;
	}

	/**
	 * 查询子权限列表，包括所有子权限下的子权限
	 */
	public List<Permission> findSubPerms_Recursion(int permid) {
		List<Permission> lists = _findSubPerms_Recursion(permid);
		// 去重复
		Map<String, Object> map = new HashMap<String, Object>();
		Iterator<Permission> it = lists.iterator();
		while (it.hasNext()) {
			Permission permission = it.next();

			if (map.containsKey("" + permission.getId())) {
				it.remove();
			} else {
				map.put("" + permission.getId(), permission);
			}
		}
		return lists;
	}

	/**
	 * 以递归的方式所有子权限
	 */
	private List<Permission> _findSubPerms_Recursion(int permid, int permtype) {
		// 查询自己的子权限
		List<Permission> lists = this.findSubPerms(permid, permtype);

		// 查询子权限的所有子权限
		for (int i = 0; i < lists.size(); i++) {
			lists.addAll(_findSubPerms_Recursion(lists.get(i).getId(), permtype));
		}
		return lists;
	}

	/**
	 * 查询类型为permtype的子权限列表，包括所有子权限下的子权限
	 */
	public List<Permission> findSubPerms_Recursion(int permid, int permtype) {
		List<Permission> lists = _findSubPerms_Recursion(permid, permtype);
		// 去重复
		Map<String, Object> map = new HashMap<String, Object>();
		Iterator<Permission> it = lists.iterator();
		while (it.hasNext()) {
			Permission permission = it.next();

			if (map.containsKey("" + permission.getId())) {
				it.remove();
			} else {
				map.put("" + permission.getId(), permission);
			}
		}
		return lists;
	}

	/**
	 * 查询所有根权限列表
	 */
	public List<Permission> findRootPerms() {
		return this.permission_relationDao.findPermRoot();
	}

	/**
	 * 查询所有类型为permtype的根权限列表
	 */
	public List<Permission> findRootPerms(int permtype) {
		return this.permission_relationDao.findPermRoot(permtype);
	}

	/**
	 * 查询所有叶子权限列表
	 */
	public List<Permission> findLeafPerms() {
		return this.permission_relationDao.findPermLeaf();
	}

	/**
	 * 查询所有类型为permtype的叶子权限列表
	 */
	public List<Permission> findLeafPerms(int permtype) {
		return this.permission_relationDao.findPermLeaf(permtype);
	}

	/**
	 * 查询系统所有权限
	 */
	public List<Permission> findPerms() {
		return this.permissionDao.find(new Permission());
	}

	/**
	 * 查询系统所有类型为permtype的权限
	 */
	public List<Permission> findPerms(int permtype) {
		return this.permissionDao.findByType(permtype);
	}

	/************************************************* 权限与功能管理 ********************************************/
	/**
	 * 权限绑定功能
	 */
	public void bindPermFunc(int permid, int funcid) {
		if (this.permission_function_relationDao.findPerm_func_relation(permid,
				funcid) != null)
			return;
		Permission_function_relation permission_function_relation = new Permission_function_relation();
		permission_function_relation.setPermid(permid);
		permission_function_relation.setFuncid(funcid);
		this.permission_function_relationDao.save(permission_function_relation);
	}

	/**
	 * 权限与功能解绑
	 */
	public void delBindPermFunc(int permid, int funcid) {
		this.permission_function_relationDao.delPerm_Func(permid, funcid);
	}

	/**
	 * 解绑权限所有功能
	 */
	public void delBindPermFuncs(int permid) {
		this.permission_function_relationDao.delPerm_Funcs(permid);
	}

	/**
	 * 查询权限功能列表，不包括子权限的功能列表
	 */
	public List<Function> findPermFuncs(int permid) {
		return this.permission_function_relationDao.findPerm_Funcs(permid);
	}

	/**
	 * 以递归的方式查询
	 */
	private List<Function> _findFuncs_Recursion(int permid) {
		// 查找该权限的功能
		List<Function> lists = this.findPermFuncs(permid);

		// 查找该权限的子权限
		List<Permission> perms = findSubPerms(permid);
		// 查找子权限的所有功能
		for (int i = 0; i < perms.size(); i++) {
			lists.addAll(_findFuncs_Recursion(perms.get(i).getId()));
		}
		return lists;
	}

	/**
	 * 查询权限功能列表，包括子权限的功能列表
	 */
	public List<Function> findPermFuncs_Recursion(int permid) {
		List<Function> lists = _findFuncs_Recursion(permid);

		// 去重
		Map<String, Object> map = new HashMap<String, Object>();
		Iterator<Function> it = lists.iterator();
		while (it.hasNext()) {
			Function function = it.next();
			if (map.containsKey("" + function.getId())) {
				it.remove();
			} else {
				map.put("" + function.getId(), function);
			}
		}
		return lists;
	}

	/********************************************* 权限与用户管理 ***************************************/
	/**
	 * 用户权限绑定
	 */
	public void bindUserPerm(int userid, int permid) {
		if (this.user_permission_relationDao.findUser_Perm_relation(userid,
				permid) != null)
			return;
		User_permission_relation user_permission_relation = new User_permission_relation();
		user_permission_relation.setPermid(permid);
		user_permission_relation.setUserid(userid);
		this.user_permission_relationDao.save(user_permission_relation);
	}

	/**
	 * 用户与权限解绑
	 */
	public void delBindUserPerm(int userid, int permid) {
		this.user_permission_relationDao.delUser_Perm(userid, permid);
	}

	/**
	 * 用户权限绑定
	 */
	public void bindPermUser(int permid, int userid) {
		bindUserPerm(userid, permid);
	}

	/**
	 * 权限与用户解绑
	 */
	public void delBindPermUser(int permid, int userid) {
		delBindUserPerm(userid, permid);
	}

	/**
	 * 解绑用户所有权限
	 */
	public void delBindUserPerms(int userid) {

		this.user_permission_relationDao.delUser_Perms(userid);
	}

	/**
	 * 解绑权限所有用户
	 */
	public void delBindPermUsers(int permid) {
		this.user_permission_relationDao.delPerm_Users(permid);
	}

	/**
	 * 查寻用户绑定的权限
	 */
	public List<Permission> findUserPerms(int userid) {
		return this.user_permission_relationDao.findUser_Perms(userid);
	}

	/**
	 * 查寻用户绑定的权限，包括子权限
	 */
	public List<Permission> findUserPerms_Recursion(int userid) {
		// 查找用户的权限
		List<Permission> permissions = this.findUserPerms(userid);

		for (int i = 0; i < permissions.size(); i++) {
			permissions.addAll(findSubPerms_Recursion(permissions.get(i)
					.getId()));
		}

		Map<String, Object> map = new HashMap<String, Object>();
		Iterator<Permission> it = permissions.iterator();
		while (it.hasNext()) {
			Permission permission = it.next();
			if (map.containsKey("" + permission.getId())) {
				it.remove();
			} else {
				map.put("" + permission.getId(), permission);
			}
		}
		return permissions;
	}

	/**
	 * 查看权限所绑定的用户
	 */
	public List<User> findPermUsers(int permid, int page, int rows) {
		return this.user_permission_relationDao.findPerm_Users(permid, page,
				rows);
	}

	/**
	 * 查看权限所绑定的 用户状态为userstatus的所有用户
	 */
	public List<User> findPermUsers(int permid, int userstatus, int page,
			int rows) {
		return this.user_permission_relationDao.findPerm_Users(permid,
				userstatus, page, rows);
	}

	/**
	 * 查看权限所绑定的 用户状态不为userstatus的所有用户
	 */
	public List<User> findPermUsersByNotStatus(int permid, int notuserstatus,
			int page, int rows) {
		return this.user_permission_relationDao.findPerm_UsersByNotStatus(
				permid, notuserstatus, page, rows);
	}

	/*********************************************** 用户与用户组管理 *********************************************/
	/**
	 * 用户组与用户绑定
	 */
	public void bindGroupUser(int groupid, int userid) {
		if (this.userGroup_User_RelationDao.findUserGroup_User_Relation(
				groupid, userid) == null) {
			UserGroup_User_Relation userGroup_User_Relation = new UserGroup_User_Relation();
			userGroup_User_Relation.setGroupid(groupid);
			userGroup_User_Relation.setUserid(userid);
			this.userGroup_User_RelationDao.save(userGroup_User_Relation);
		}
	}

	/**
	 * 解绑用户组与用户
	 */
	public void delBindGroupUser(int groupid, int userid) {
		this.userGroup_User_RelationDao.delGroup_User_Relation(groupid, userid);
	}

	/**
	 * 绑定用户与用户组
	 * 
	 */
	public void bindUserGroup(int userid, int groupid) {
		bindGroupUser(groupid, userid);
	}

	/**
	 * 解绑用户与用户组
	 */
	public void delBindUserGroup(int userid, int groupid) {
		delBindGroupUser(groupid, userid);
	}

	/**
	 * 解绑用户组所有用户
	 */
	public void delBindGroupUsers(int groupid) {
		this.userGroup_User_RelationDao.delGroup_Users(groupid);
	}

	/**
	 * 解绑用户绑定的所有用户组
	 */
	public void delBindUserGroups(int userid) {
		this.userGroup_User_RelationDao.delGroup_User_Relations(userid);
	}

	/**
	 * 查寻组中所有用户
	 */
	public List<User> findGroupUsers(int groupid) {
		return this.userGroup_User_RelationDao.findGroupUsers(groupid);
	}

	/**
	 * 查寻组内用户,带分页
	 */
	public List<User> findGroupUsers(int groupid, int page, int rows) {
		return this.userGroup_User_RelationDao.findGroupUsers(groupid, page,
				rows);
	}

	/**
	 * 查寻组内状态为userstatus的用户
	 */
	public List<User> findGroupUsers(int groupid, int userstatus, int page,
			int rows) {
		return this.userGroup_User_RelationDao.findGroupUsers(groupid,
				userstatus, page, rows);
	}

	/**
	 * 查寻组内状态不为notuserstatus的用户
	 */
	public List<User> findGroupUsersByNotStatus(int groupid, int notuserstatus,
			int page, int rows) {
		return this.userGroup_User_RelationDao.findGroupUsersByNotStatus(
				groupid, notuserstatus, page, rows);
	}

	/**
	 * 计数组内状态不为notuserstatus的用户
	 */
	public long countGroupUsersByNotStatus(int groupid, int notuserstatus) {
		return this.userGroup_User_RelationDao.countGroupUsersByNotStatus(
				groupid, notuserstatus);
	}

	/**
	 * 查询用户绑定的所有用户组
	 */
	public List<UserGroup> findUserGroups(int userid) {
		return this.userGroup_User_RelationDao.findUserGroups(userid);
	}

	/**
	 * 查询用户绑定的所有类型为用户组
	 */
	public List<UserGroup> findUserGroups(int userid, int grouptype) {
		return this.userGroup_User_RelationDao.findUserGroup(userid, grouptype);
	}

	@Override
	public List<Permission> findPerms(int page, int rows) {
		return this.permissionDao.find(new Permission(), page, rows);
	}

	@Override
	public List<Permission> findPerms(int permtype, int page, int rows) {
		return this.permissionDao.find(new Permission(), "permtype", permtype,
				page, rows);
	}

	/**
	 * 查询系统所有类型为permtype的权限
	 */
	public long countPerms(int permtype) {
		return this.permissionDao.count(new Permission(), "permtype", permtype);
	}

	@Override
	public List<Permission> findParentPerms(int permid) {
		return this.permission_relationDao.findParentPerms(permid);
	}

	/**
	 * 查寻所有组的用户，按组分组，带分页
	 */
	@Override
	public List<User> findAllGroupUsersOrderByNotStatus(int notstatus,int page, int rows) {
		return this.userGroup_User_RelationDao.findAllGroupUsersOrderByNotStatus(notstatus,page, rows);
	}

	@Override
	public List<Permission> findPermsOrderByParent(int permtype, int page,
			int rows) {
		return this.permission_relationDao.findPermsOrderByParent(permtype,page,rows);
	}
}
