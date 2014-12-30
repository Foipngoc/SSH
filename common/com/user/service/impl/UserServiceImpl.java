package com.user.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.privilege.dao.User_permission_relationDao;
import com.privilege.model.Permission;
import com.privilege.model.User_permission_relation;
import com.privilege.service.PrivilegeService;
import com.user.dao.GroupDao;
import com.user.dao.Group_RelationDao;
import com.user.dao.Group_User_RelationDao;
import com.user.dao.UserDao;
import com.user.model.User;
import com.user.model.UserGroup;
import com.user.model.UserGroup_User_Relation;
import com.user.service.UserService;

@Transactional
@Service("userService")
public class UserServiceImpl implements UserService {
	@Resource
	private GroupDao groupDao;
	@Resource
	private Group_User_RelationDao group_User_RelationDao;
	@Resource
	private Group_RelationDao group_RelationDao;
	@Resource
	private UserDao userDao;
	@Resource
	private User_permission_relationDao user_permission_relationDao;

	@Resource(name = "privilegeService")
	private PrivilegeService privilegeService;

	@Override
	public void addUser(User user) throws Exception {
		this.userDao.save(user);
	}

	@Override
	public void delUser(int userid) throws Exception {
		this.userDao.delUser(userid);
	}

	@Override
	public void modifyUser(User user) throws Exception {
		this.userDao.update(user);
	}

	@Override
	public User findUser(int userid) throws Exception {
		return this.userDao.findUnique(new User(), "id", userid);
	}

	@Override
	public void bindUserGroup(int userid, int groupid) throws Exception {
		UserGroup_User_Relation userGroup_User_Relation = this.group_User_RelationDao
				.findUserGroup_User_Relation(groupid, userid);
		if (userGroup_User_Relation == null) {
			UserGroup_User_Relation newuserGroup_User_Relation = new UserGroup_User_Relation();
			newuserGroup_User_Relation.setGroupid(groupid);
			newuserGroup_User_Relation.setUserid(userid);
			this.group_User_RelationDao.save(newuserGroup_User_Relation);
		}
	}

	@Override
	public void delBindUserGroup(int userid, int groupid) throws Exception {
		this.group_User_RelationDao.delGroup_User_Relation(groupid, userid);
	}

	@Override
	public void delBindUserGroups(int userid) throws Exception {
		this.group_User_RelationDao.delGroup_User_Relations(userid);
	}

	@Override
	public List<UserGroup> findUserGroups(int userid) throws Exception {
		return this.group_User_RelationDao.findUserGroups(userid);
	}

	@Override
	public void bindPermission(int userid, int permid) throws Exception {
		if (this.user_permission_relationDao.findUser_Perm_relation(userid,
				permid) == null) {
			User_permission_relation user_permission_relation = new User_permission_relation();
			user_permission_relation.setPermid(permid);
			user_permission_relation.setUserid(userid);
			this.user_permission_relationDao.save(user_permission_relation);
		}
	}

	@Override
	public void delBindPermission(int userid, int permid) throws Exception {
		this.user_permission_relationDao.delUser_Perm(userid, permid);
	}

	@Override
	public void delBindPermissions(int userid) throws Exception {
		this.user_permission_relationDao.delUser_Perms(userid);
	}

	@Override
	public List<Permission> findPermissoins(int userid) throws Exception {
		return this.user_permission_relationDao.findUser_Perms(userid);
	}

	@Override
	public List<Permission> findPermissoins_Recursion(int userid)
			throws Exception {
		// 查找用户的权限
		List<Permission> permissions = this.findPermissoins(userid);

		for (int i = 0; i < permissions.size(); i++) {
			permissions.addAll(privilegeService
					.findSubPerms_Recursion(permissions.get(i).getId()));
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
}
