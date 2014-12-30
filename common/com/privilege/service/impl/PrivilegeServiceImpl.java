package com.privilege.service.impl;

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
import com.user.model.User;

@Transactional
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

	/******************** 权限相关接口 ***********************/

	/**
	 * 添加权限
	 */
	public Permission addPerm(String permname, int permtype, String permdesc)
			throws Exception {
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
			int permtype, String permdesc) throws Exception {
		Permission permission = addPerm(permname, permtype, permdesc);
		bindSubPerm(parentpermid, permission.getId());
		return permission;
	}

	/**
	 * 添加父权限
	 */
	public Permission addBindParentPerm(int subpermid, String permname,
			int permtype, String permdesc) throws Exception {
		Permission permission = addPerm(permname, permtype, permdesc);
		bindParentPerm(subpermid, permission.getId());
		return permission;
	}

	/**
	 * 绑定子权限
	 */
	public void bindSubPerm(int permid, int subpermid) throws Exception {
		if (permid == subpermid)
			throw new Exception("不能添加自己为子权限");
		// 防止权限回路,检查子权限的子权限列表中是否有父权限
		List<Permission> subpermsubs = this.findSubPerms_Recursion(subpermid);
		for (int i = 0; i < subpermsubs.size(); i++) {
			if (subpermsubs.get(i).getId() == permid) {
				throw new Exception("权限不能存在回路");
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
	public void bindParentPerm(int permid, int parentpermid) throws Exception {
		bindSubPerm(parentpermid, permid);
	}

	/**
	 * 解绑子权限
	 */
	public void delBindSubPerm(int permid, int subpermid) throws Exception {
		Permission_relation permission_relation = this.permission_relationDao
				.findPerm_relation(permid, subpermid);
		if (permission_relation != null)
			this.permission_relationDao.delete(permission_relation);
	}

	/**
	 * 解绑父权限
	 */
	public void delBindParentPerm(int permid, int parentid) throws Exception {
		delBindSubPerm(parentid, permid);
	}

	/**
	 * 解绑所有子权限
	 */
	public void delBindSubPerms(int permid) throws Exception {
		this.permission_relationDao.delSubPermissions(permid);
	}

	/**
	 * 解绑所有父权限
	 */
	public void delBindParentPerms(int permid) throws Exception {
		this.permission_relationDao.delParentPermissions(permid);
	}

	/**
	 * 解绑所有父子权限
	 */
	public void delBindPerms(int permid) throws Exception {
		delBindParentPerms(permid);
		delBindSubPerms(permid);
	}

	/**
	 * 删除权限
	 */
	public void delPerm(int permid) throws Exception {
		// 删除该权限的与功能的关联
		delBindFuncs(permid);

		// 删除此权限的父子关联权限关系
		delBindPerms(permid);

		// 删除此权限
		this.permissionDao.delPerm(permid);
	}

	/**
	 * 查询权限
	 */
	public Permission findPerm(int permid) throws Exception {
		return this.permissionDao.findPerm(permid);
	}

	/**
	 * 查询子权限列表，不包括子权限下的子权限
	 */
	public List<Permission> findSubPerms(int permid) throws Exception {
		return this.permission_relationDao.findSubPerms(permid);
	}

	/**
	 * 查询类型为permtype的子权限列表，不包括子权限下的子权限
	 */
	public List<Permission> findSubPerms(int permid, int permtype)
			throws Exception {
		return this.permission_relationDao.findSubPerms(permid, permtype);
	}

	private List<Permission> _findSubPerms_Recursion(int permid) throws Exception {
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
	public List<Permission> findSubPerms_Recursion(int permid) throws Exception {
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

	private List<Permission> _findSubPerms_Recursion(int permid, int permtype)
			throws Exception {
		// 查询自己的子权限
		List<Permission> lists = this.findSubPerms(permid, permtype);

		// 查询子权限的所有子权限
		for (int i = 0; i < lists.size(); i++) {
			lists.addAll(_findSubPerms_Recursion(lists.get(i).getId(), permtype));
		}
		return lists;
	}

	/**
	 * 查询某权限的类型为permtype的子权限列表，包括所有子权限下的子权限
	 */
	public List<Permission> findSubPerms_Recursion(int permid, int permtype)
			throws Exception {
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
	 * 查询所有根权限列表 ,即该权限不是任何权限的子权限
	 */
	public List<Permission> findPermRoot() throws Exception {
		return this.permission_relationDao.findPermRoot();
	}

	/**
	 * 查询所有类型为permtype的根权限列表 ,即该权限不是任何权限的子权限
	 */
	public List<Permission> findPermRoot(int permtype) throws Exception {
		return this.permission_relationDao.findPermRoot(permtype);
	}

	/**
	 * 查询所有叶子节点，既该权限没有子权限
	 */
	public List<Permission> findPermLeaf() throws Exception {
		return this.permission_relationDao.findPermLeaf();
	}

	/**
	 * 查询所有类型为permtype的叶子节点，既该权限没有子权限
	 */
	public List<Permission> findPermLeaf(int permtype) throws Exception {
		return this.permission_relationDao.findPermLeaf(permtype);
	}

	/**
	 * 查询系统所有权限
	 */
	public List<Permission> findPerms() throws Exception {
		return this.permissionDao.find(new Permission());
	}

	/**
	 * 查询系统所有类型为permtype的权限
	 */
	public List<Permission> findPerms(int permtype) throws Exception {
		return this.permissionDao.findByType(permtype);
	}

	/**
	 * 绑定功能到权限
	 */
	public void bindFunc(int permid, int funcid) throws Exception {
		Permission_function_relation temp = this.permission_function_relationDao
				.findPerm_func_relation(permid, funcid);
		if (temp != null)
			return;
		Permission_function_relation permission_function_relation = new Permission_function_relation();
		permission_function_relation.setPermid(permid);
		permission_function_relation.setFuncid(funcid);
		this.permission_function_relationDao.save(permission_function_relation);
	}

	/**
	 * 删除某权限与某功能的绑定
	 */
	public void delBindFunc(int permid, int funcid) throws Exception {
		this.permission_function_relationDao.delPerm_Func(permid, funcid);
	}

	/**
	 * 删除权限与所有功能的绑定
	 */
	public void delBindFuncs(int permid) throws Exception {
		this.permission_function_relationDao.delPerm_Funcs(permid);
	}

	/**
	 * 查询某权限功能列表，不包括子权限的功能列表
	 */
	public List<Function> findFuncs(int permid) throws Exception {
		return this.permission_function_relationDao.findPerm_Funcs(permid);
	}

	public List<Function> _findFuncs_Recursion(int permid) throws Exception {
		// 查找该权限的功能
		List<Function> lists = findFuncs(permid);

		// 查找该权限的子权限
		List<Permission> perms = findSubPerms(permid);
		// 查找子权限的所有功能
		for (int i = 0; i < perms.size(); i++) {
			lists.addAll(_findFuncs_Recursion(perms.get(i).getId()));
		}
		return lists;
	}

	/**
	 * 查询某权限功能列表，包括子权限的功能列表
	 */
	public List<Function> findFuncs_Recursion(int permid) throws Exception {
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

	/***************************** 用户相关接口 *******************/
	/**
	 * 绑定用户
	 */
	public void bindUser(int permid, int userid) throws Exception{
		User_permission_relation user_permission_relation = this.user_permission_relationDao
				.findUser_Perm_relation(userid, permid);
		if (user_permission_relation == null) {
			User_permission_relation user_permission_relation2 = new User_permission_relation();
			user_permission_relation2.setPermid(permid);
			user_permission_relation2.setUserid(userid);
			this.user_permission_relationDao.save(user_permission_relation2);
		}
	}

	/**
	 * 解绑用户
	 */
	public void delBindUser(int permid, int userid) throws Exception{
		this.user_permission_relationDao.delUser_Perm(userid, permid);
	}

	/**
	 * 解绑所有用户
	 */
	public void delBindUsers(int permid) throws Exception{
		this.user_permission_relationDao.delUser_Perms(permid);
	}
	
	/**
	 * 查看权限所绑定的用户
	 */
	public List<User> findUsers(int permid) throws Exception{
		return this.user_permission_relationDao.findPerm_Users(permid);
	}
	
	/**
	 * 查看权限所绑定的用户
	 */
	public List<User> findUsers(int permid,int page,int rows) throws Exception{
		return this.user_permission_relationDao.findPerm_Users(permid,page,rows);
	}
}
