package com.privilege.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.privilege.dao.FunctionDao;
import com.privilege.dao.PermissionDao;
import com.privilege.dao.Permission_function_relationDao;
import com.privilege.dao.Permission_relationDao;
import com.privilege.dao.User_permission_relationDao;
import com.privilege.model.Function;
import com.privilege.model.Permission;
import com.privilege.model.Permission_function_relation;
import com.privilege.model.Permission_relation;
import com.privilege.model.User_permission_relation;
import com.privilege.service.PrivilegeService;

@Service("privilegeService")
public class PrivilegeServiceImpl implements PrivilegeService {
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
	
	private static PrivilegeServiceImpl instance;

	/**
	 * @return the instance
	 */
	public static PrivilegeServiceImpl getInstance() {
		if (instance == null) {
			synchronized (PrivilegeServiceImpl.class) {
				instance = new PrivilegeServiceImpl();
			}
		}
		return instance;
	}

	public PrivilegeServiceImpl() {
		if (instance != null) {
			throw new IllegalStateException("PrivilegeService 不能创建多实例");
		}
		instance = this;
	}

	public void setPermissionDao(PermissionDao permissionDao) {
		this.permissionDao = permissionDao;
	}

	public void setFunctionDao(FunctionDao functionDao) {
		this.functionDao = functionDao;
	}

	public void setPermission_function_relationDao(
			Permission_function_relationDao permission_function_relationDao) {
		this.permission_function_relationDao = permission_function_relationDao;
	}

	public void setUser_permission_relationDao(
			User_permission_relationDao user_permission_relationDao) {
		this.user_permission_relationDao = user_permission_relationDao;
	}

	public void setPermission_relationDao(
			Permission_relationDao permission_relationDao) {
		this.permission_relationDao = permission_relationDao;
	}

	/**
	 * 新增权限
	 */
	public Permission addPerm(String permname, int permtype, String permdesc)
			throws Exception {
		Permission perm = new Permission();
		perm.setPermdesc(permdesc);
		perm.setPermname(permname);
		perm.setPermtype(permtype);
		return this.permissionDao.saveObj(perm);
	}

	/**
	 * 新增权限关联
	 */
	public Permission_relation addPermRelation(int parentpermid, int subpermid)
			throws Exception {
		if (parentpermid == subpermid)
			throw new Exception("不能添加自己为子权限");
		// 防止权限回路,检查子权限的子权限列表中是否有父权限
		List<Permission> subpermsubs = this.queryPermSubPermsAll(subpermid);
		for (int i = 0; i < subpermsubs.size(); i++) {
			if (subpermsubs.get(i).getId() == parentpermid) {
				throw new Exception("权限不能存在回路");
			}
		}
		// 是否已有关联
		List<Permission_relation> list = this.permission_relationDao
				.queryPermission_relation(parentpermid, subpermid);
		if (list.size() > 1) {
			throw new Exception("数据库权限关联表出现异常");
		}
		// 已有关联，则返回此关联
		if (list.size() == 1) {
			return list.get(0);
		}

		// 否则添加关联
		Permission_relation permission_relation = new Permission_relation();
		permission_relation.setPermid(parentpermid);
		permission_relation.setSubpermid(subpermid);
		return this.permission_relationDao.saveObj(permission_relation);
	}

	/**
	 * 删除权限关联
	 */
	public void delPermRelation(int parentpermid, int subpermid)
			throws Exception {
		this.permission_relationDao.delPermission_relation(parentpermid,
				subpermid);
	}

	/**
	 * 删除权限,删除权限后， 该权限与功能的关联关系会取消 该权限与父子权限之间的关系取消
	 */
	@Transactional
	public void delPerm(int permid) throws Exception {
		// 删除该权限的与功能的关联
		delPermFuncAll(permid);

		// 删除此权限的父子关联权限关系
		this.permission_relationDao.delPermission_relation(permid);

		// 删除此权限
		this.permissionDao.delPerm(permid);
	}

	/**
	 * 查询某权限的子权限列表，不包括子权限下的子权限
	 */
	public List<Permission> queryPermSubPerms(int permid) throws Exception {
		return this.permissionDao.queryPermSubPerms(permid);
	}

	/**
	 * 查询某权限的类型为permtype的子权限列表，不包括子权限下的子权限
	 */
	public List<Permission> queryPermSubPerms(int permid, int permtype)
			throws Exception {
		return this.permissionDao.queryPermSubPerms(permid, permtype);
	}

	/**
	 * 查询某权限的子权限列表，包括所有子权限下的子权限 递归
	 */
	public List<Permission> queryPermSubPermsAll(int permid) throws Exception {
		List<Permission> list = new ArrayList<Permission>();

		List<Permission> subs = this.permissionDao.queryPermSubPerms(permid);
		list.addAll(subs);

		// 查询子权限的子权限
		for (int i = 0; i < subs.size(); i++) {
			Permission p = subs.get(i);

			List<Permission> subsubs = this.permissionDao.queryPermSubPerms(p
					.getId());
			list.addAll(subsubs);
		}

		// 去重复
		Map<String, Object> map = new HashMap<String, Object>();

		Iterator<Permission> it = list.iterator();
		while (it.hasNext()) {
			Permission permission = it.next();

			if (map.containsKey("" + permission.getId())) {
				it.remove();
			} else {
				map.put("" + permission.getId(), permission);
			}
		}
		return list;
	}

	/**
	 * 查询某权限的类型为permtype的子权限列表，包括所有子权限下的子权限
	 */
	public List<Permission> queryPermSubPermsAll(int permid, int permtype)
			throws Exception {
		List<Permission> list = new ArrayList<Permission>();

		List<Permission> subs = this.permissionDao.queryPermSubPerms(permid,
				permtype);
		list.addAll(subs);

		// 查询子权限的子权限
		for (int i = 0; i < subs.size(); i++) {
			Permission p = subs.get(i);

			List<Permission> subsubs = this.permissionDao.queryPermSubPerms(
					p.getId(), permtype);
			list.addAll(subsubs);
		}

		// 去重复
		Map<String, Object> map = new HashMap<String, Object>();

		Iterator<Permission> it = list.iterator();
		while (it.hasNext()) {
			Permission permission = it.next();

			if (map.containsKey("" + permission.getId())) {
				it.remove();
			} else {
				map.put("" + permission.getId(), permission);
			}
		}
		return list;
	}

	/**
	 * 查询所有根权限列表 ,即该权限不是任何权限的子权限
	 */
	public List<Permission> queryPermRoot() throws Exception {

		return this.permissionDao.queryPermRoot();
	}

	/**
	 * 查询所有类型为permtype的根权限列表 ,即该权限不是任何权限的子权限
	 */
	public List<Permission> queryPermRoot(int permtype) throws Exception {
		return this.permissionDao.queryPermRoot(permtype);
	}

	/**
	 * 查询所有叶子节点，既该权限没有子权限
	 */
	public List<Permission> queryPermLeaf() throws Exception {
		return this.permissionDao.queryPermLeaf();
	}

	/**
	 * 查询所有类型为permtype的叶子节点，既该权限没有子权限
	 */
	public List<Permission> queryPermLeaf(int permtype) throws Exception {
		return this.permissionDao.queryPermLeaf(permtype);
	}

	/**
	 * 查询系统所有权限
	 */
	public List<Permission> queryPermAll() throws Exception {
		return this.permissionDao.queryAll();
	}

	/**
	 * 查询系统所有类型为permtype的权限
	 */
	public List<Permission> queryPermAll(int permtype) throws Exception {
		return this.permissionDao.queryAll(permtype);
	}

	/**
	 * 添加权限功能
	 */
	public Permission_function_relation addPermFunc(int permid, int funcid)
			throws Exception {
		// 查看是否已存在
		List<Permission_function_relation> list = this.permission_function_relationDao
				.queryPermFuncs_relation(permid, funcid);
		if (list.size() > 1) {
			throw new Exception("数据库权限功能关系出现异常");
		}
		if (list.size() == 1) {
			return list.get(0);
		}
		return this.permission_function_relationDao.addPermFunc(permid, funcid);
	}

	/**
	 * 删除某权限某功能
	 */
	@Transactional
	public void delPermFunc(int permid, int funcid) throws Exception {
		this.permission_function_relationDao.delPermFunc(permid, funcid);
	}

	/**
	 * 删除权限所有功能
	 */
	@Transactional
	public void delPermFuncAll(int permid) throws Exception {
		this.permission_function_relationDao.delPermFuncsAll(permid);
	}

	/**
	 * 查询某权限功能列表，不包括子权限的功能列表
	 */
	public List<Function> queryPermFuncs(int permid) throws Exception {
		return this.functionDao.queryPermFuncs(permid);
	}

	/**
	 * 查询某权限功能列表，包括子权限的功能列表
	 */
	public List<Function> queryPermFuncsAll(int permid) throws Exception {
		List<Function> list = new ArrayList<Function>();
		// 查询自己的功能
		List<Function> funcs = this.queryPermFuncs(permid);
		list.addAll(funcs);

		List<Permission> subperms = this.queryPermSubPermsAll(permid);
		for (int i = 0; i < subperms.size(); i++) {
			List<Function> subfuncs = this.queryPermFuncs(subperms.get(i)
					.getId());
			list.addAll(subfuncs);
		}

		Map<String, Function> map = new HashMap<String, Function>();
		Iterator<Function> it = list.iterator();
		while (it.hasNext()) {
			Function function = it.next();

			if (map.containsKey("" + function.getId())) {
				it.remove();
			} else {
				map.put("" + function.getId(), function);
			}
		}

		return list;
	}

	/**
	 * 添加某用户某权限
	 */
	public User_permission_relation addUserPerm(int userid, int permid)
			throws Exception {
		List<User_permission_relation> list = this.user_permission_relationDao
				.queryUserPerm_relation(userid, permid);
		if (list.size() > 1) {
			throw new Exception("数据库出现不一致");
		}
		if (list.size() == 1)
			return list.get(0);

		User_permission_relation user_permission_relation = new User_permission_relation();
		user_permission_relation.setPermid(permid);
		user_permission_relation.setUserid(userid);
		return this.user_permission_relationDao
				.saveObj(user_permission_relation);
	}

	/**
	 * 删除某用户某权限
	 */
	@Transactional
	public void delUserPerm(int userid, int permid) throws Exception {
		this.user_permission_relationDao.delUserPerm(userid, permid);
	}

	/**
	 * 删除某用户所有权限
	 */
	@Transactional
	public void delUserPermAll(int userid) throws Exception {
		this.user_permission_relationDao.delUserPermAll(userid);
	}

	/**
	 * 查询某用户的所有权限，不包括权限的子权限。
	 */
	public List<Permission> queryUserPerm(int userid) throws Exception {
		return this.user_permission_relationDao.queryUserPerm(userid);
	}

	/**
	 * 查询某用户的所有权限，包括权限的子权限。
	 */
	public List<Permission> queryUserPermAll(int userid) throws Exception {
		List<Permission> list = new ArrayList<Permission>();

		// 查询该用户的权限
		List<Permission> perms = this.queryUserPerm(userid);
		list.addAll(perms);

		// 查询子权限的所有权限
		for (int i = 0; i < perms.size(); i++) {
			List<Permission> subperms = this.queryPermSubPermsAll(perms.get(i)
					.getId());
			list.addAll(subperms);
		}
		return list;
	}

	/**
	 * 查询某用户的所有类型为permtype的权限，不包括权限的子权限。
	 */
	public List<Permission> queryUserPerm(int userid, int permtype)
			throws Exception {
		return this.user_permission_relationDao.queryUserPerm(userid, permtype);
	}

	/**
	 * 查询某用户的所有类型为permtype的权限，包括权限的子权限。
	 */
	public List<Permission> queryUserPermAll(int userid, int permtype)
			throws Exception {
		List<Permission> list = new ArrayList<Permission>();

		// 查询该用户的权限
		List<Permission> perms = this.queryUserPerm(userid, permtype);
		list.addAll(perms);

		// 查询子权限的所有权限
		for (int i = 0; i < perms.size(); i++) {
			List<Permission> subperms = this.queryPermSubPermsAll(perms.get(i)
					.getId(), permtype);
			list.addAll(subperms);
		}
		return list;
	}

	/**
	 * 查询用户所拥有的所有功能列表
	 */
	public List<Function> queryUserFuncAll(int userid) throws Exception {
		List<Function> list = new ArrayList<Function>();

		// 查询该用户的权限
		List<Permission> perms = this.queryUserPerm(userid);
		// 查询这些权限的功能列表

		// 查询子权限的所有权限
		for (int i = 0; i < perms.size(); i++) {
			List<Function> funcs = this.queryPermFuncsAll(perms.get(i).getId());
			list.addAll(funcs);
		}
		return list;
	}

}
