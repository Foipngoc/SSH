package com.user.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.privilege.dao.User_permission_relationDao;
import com.privilege.service.PrivilegeService;
import com.user.dao.GroupDao;
import com.user.dao.Group_RelationDao;
import com.user.dao.Group_User_RelationDao;
import com.user.dao.UserDao;
import com.user.model.UserGroup;
import com.user.model.UserGroup_Relation;
import com.user.model.UserGroup_User_Relation;
import com.user.service.UserGroupService;

@Transactional
@Service("userGroupService")
public class UserGroupServiceImpl implements UserGroupService {
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
	public UserGroup addGroup(String groupname, int grouptype, String groupdesc)
			throws Exception {
		UserGroup userGroup = new UserGroup();

		userGroup.setGroupname(groupname);
		userGroup.setGroupdesc(groupdesc);
		userGroup.setGrouptype(grouptype);
		this.groupDao.save(userGroup);
		return userGroup;
	}

	@Override
	public UserGroup addBindSubGroup(int parentgroupid, String groupname,
			int grouptype, String groupdesc) throws Exception {
		UserGroup group = addGroup(groupname, grouptype, groupdesc);
		bindParentGroup(group.getId(), parentgroupid);
		return group;
	}

	@Override
	public UserGroup addBindParentGroup(int subgroupid, String groupname,
			int grouptype, String groupdesc) throws Exception {
		UserGroup group = addGroup(groupname, grouptype, groupdesc);
		bindSubGroup(group.getId(), subgroupid);
		return group;
	}

	@Override
	public void bindSubGroup(int groupid, int subgroupid) throws Exception {
		bindParentGroup(subgroupid, groupid);

	}

	@Override
	public void bindParentGroup(int groupid, int parentgroupid)
			throws Exception {
		if (group_RelationDao.find(parentgroupid, groupid) == null) {
			UserGroup_Relation userGroup_Relation = new UserGroup_Relation();

			userGroup_Relation.setGroupid(parentgroupid);
			userGroup_Relation.setSubgroupid(groupid);
			this.group_RelationDao.save(userGroup_Relation);
		}
	}

	@Override
	public void delBindSubGroup(int groupid, int subgroupid) throws Exception {
		this.group_RelationDao.delGroup_Relation(groupid, subgroupid);
	}

	@Override
	public void delBindParentGroup(int groupid, int parentgroupid)
			throws Exception {
		this.group_RelationDao.delGroup_Relation(parentgroupid, groupid);
	}

	@Override
	public void delBindSubGroups(int groupid) throws Exception {
		this.group_RelationDao.delSubGroup_Relations(groupid);
	}

	@Override
	public void delBindParentGroups(int groupid) throws Exception {
		this.group_RelationDao.delParentGroup_Relations(groupid);
	}

	@Override
	public void delBindGroups(int groupid) throws Exception {
		delBindSubGroups(groupid);
		delBindParentGroups(groupid);
	}

	@Override
	public void delGroup(int groupid) throws Exception {
		// 删除组与组的关系
		delBindSubGroups(groupid);
		delBindParentGroups(groupid);

		// 删除组与用户的关系
		delBindUsers(groupid);

		this.groupDao.delGroup(groupid);
	}

	@Override
	public UserGroup modifyGroup(int groupid, String groupname,
			Integer grouptype, String groupdesc) throws Exception {
		UserGroup userGroup = new UserGroup();
		userGroup.setId(groupid);
		userGroup.setGroupdesc(groupdesc);
		userGroup.setGroupname(groupname);
		userGroup.setGrouptype(grouptype);
		this.groupDao.update(userGroup);
		return userGroup;
	}

	@Override
	public List<UserGroup> findLeafGroup() throws Exception {
		return this.group_RelationDao.findLeafGroups();
	}

	@Override
	public List<UserGroup> findLeafGroup(int grouptype) throws Exception {
		return this.group_RelationDao.findLeafGroups(grouptype);
	}

	@Override
	public List<UserGroup> findRootGroup() throws Exception {
		return this.group_RelationDao.findRootGroups();
	}

	@Override
	public List<UserGroup> findRootGroup(int grouptype) throws Exception {
		return this.group_RelationDao.findRootGroups(grouptype);
	}

	@Override
	public UserGroup findGroup(int groupid) throws Exception {
		return this.groupDao.findGroup(groupid);
	}

	@Override
	public List<UserGroup> findSubGroup(int groupid) throws Exception {
		return this.group_RelationDao.findSubGroups(groupid);
	}

	@Override
	public List<UserGroup> findSubGroup(int groupid, int grouptype)
			throws Exception {
		return this.group_RelationDao.findSubGroups(groupid, grouptype);
	}

	public List<UserGroup> _findSubGroup_Recursion(int groupid)
			throws Exception {
		List<UserGroup> userGroups = this.findSubGroup(groupid);

		for (int i = 0; i < userGroups.size(); i++) {
			userGroups
					.addAll(_findSubGroup_Recursion(userGroups.get(i).getId()));
		}
		return userGroups;
	}

	@Override
	public List<UserGroup> findSubGroup_Recursion(int groupid) throws Exception {
		List<UserGroup> userGroups = _findSubGroup_Recursion(groupid);

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

	public List<UserGroup> _findSubGroup_Recursion(int groupid, int grouptype)
			throws Exception {
		List<UserGroup> userGroups = this.findSubGroup(groupid, grouptype);

		for (int i = 0; i < userGroups.size(); i++) {
			userGroups.addAll(_findSubGroup_Recursion(
					userGroups.get(i).getId(), grouptype));
		}
		return userGroups;
	}

	@Override
	public List<UserGroup> findSubGroup_Recursion(int groupid, int grouptype)
			throws Exception {
		List<UserGroup> userGroups = _findSubGroup_Recursion(groupid, grouptype);

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

	@Override
	public List<UserGroup> findGroups() throws Exception {
		return this.groupDao.findGroups();
	}

	@Override
	public List<UserGroup> findGroups(int grouptype) throws Exception {
		return this.groupDao.findGroups(grouptype);
	}

	@Override
	public void addBindUser(int groupid, int userid) throws Exception {
		if (this.group_User_RelationDao.findUserGroup_User_Relation(groupid, userid)== null) {
			UserGroup_User_Relation userGroup_User_Relation = new UserGroup_User_Relation();
			userGroup_User_Relation.setGroupid(groupid);
			userGroup_User_Relation.setUserid(userid);
			this.group_User_RelationDao.save(userGroup_User_Relation);
		}
	}

	@Override
	public void delBindUser(int groupid, int userid) throws Exception {
		this.group_User_RelationDao.delGroup_User_Relation(groupid, userid);
	}

	@Override
	public void delBindUsers(int groupid) throws Exception {
		this.group_User_RelationDao.delGroup_Users(groupid);
	}

	@Override
	public void findUsers(int groupid) throws Exception {
		this.group_User_RelationDao.findGroupUsers(groupid);
	}
}
