package com.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.user.dao.GroupDao;
import com.user.dao.Group_RelationDao;
import com.user.dao.Group_User_RelationDao;
import com.user.dao.UserBasicDao;
import com.user.dao.UserExtensionDao;
import com.user.model.Group;
import com.user.model.Group_Relation;
import com.user.model.Group_User_Relation;
import com.user.model.User;
import com.user.model.UserExtension;
import com.user.service.UserService;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
	@Resource
	private UserBasicDao userBasicDao;
	@Resource
	private UserExtensionDao userExtensionDao;
	@Resource
	private Group_RelationDao group_RelationDao;
	@Resource
	private Group_User_RelationDao group_User_RelationDao;
	@Resource
	private GroupDao groupDao;

	@Override
	public Group addGroup(Group group) {
		return groupDao.saveObj(group);
	}

	@Override
	public void delGroup(int groupid) {
		groupDao.delGroup(groupid);
	}

	@Override
	public Group modifyGroup(Group group) {
		return groupDao.updateObj(group);
	}

	@Override
	public List<Group> queryLeafGroup() {
		return groupDao.queryLeafGroup();
	}

	@Override
	public List<Group> queryLeafGroup(int grouptype) {
		return groupDao.queryLeafGroup(grouptype);
	}

	@Override
	public List<Group> queryRootGroup() {
		return groupDao.queryRootGroup();
	}

	@Override
	public List<Group> queryRootGroup(int grouptype) {
		return groupDao.queryRootGroup(grouptype);
	}

	@Override
	public Group queryGroup(int groupid) {
		return groupDao.queryGroup(groupid);
	}

	@Override
	public List<Group> querySubGroup(int groupid) {
		return groupDao.querySubGroup(groupid);
	}

	@Override
	public List<Group> querySubGroup(int groupid, int grouptype) {
		return groupDao.querySubGroup(groupid, grouptype);
	}

	@Override
	public List<Group> querySubGroupAll(int groupid) {
		List<Group> groups = new ArrayList<Group>();

		List<Group> subGroups = groupDao.querySubGroup(groupid);

		groups.addAll(subGroups);

		for (int i = 0; i < subGroups.size(); i++) {
			Group group = subGroups.get(i);

			List<Group> subsubGroups = querySubGroupAll(group.getId());
			groups.addAll(subsubGroups);
		}
		return groups;
	}

	@Override
	public List<Group> querySubGroupAll(int groupid, int grouptype) {
		List<Group> groups = new ArrayList<Group>();

		List<Group> subGroups = groupDao.querySubGroup(groupid, grouptype);

		groups.addAll(subGroups);

		for (int i = 0; i < subGroups.size(); i++) {
			Group group = subGroups.get(i);

			List<Group> subsubGroups = querySubGroupAll(group.getId(),
					grouptype);
			groups.addAll(subsubGroups);
		}
		return groups;
	}

	@Override
	public List<Group> queryGroups() {
		return groupDao.queryGroups();
	}

	@Override
	public List<Group> queryGroups(int grouptype) {
		return groupDao.queryGroups(grouptype);
	}

	@Override
	public Group_Relation addGroup_Relation(int groupid, int subgroupid) {
		Group_Relation group_Relation = new Group_Relation();

		group_Relation.setGroupid(groupid);
		group_Relation.setSubgroupid(subgroupid);
		return group_RelationDao.saveObj(group_Relation);
	}

	@Override
	public void delGroup_Relation(int groupid, int subgroupid) {
		group_RelationDao.delGroup_Relation(groupid, subgroupid);
	}

	@Override
	public Group_User_Relation addGroup_User_Relation(int groupid, int userid) {
		Group_User_Relation group_User_Relation = new Group_User_Relation();
		group_User_Relation.setUserid(userid);
		group_User_Relation.setGroupid(groupid);
		return group_User_RelationDao.saveObj(group_User_Relation);
	}

	@Override
	public void delGroup_User_Relation(int groupid, int userid) {
		group_User_RelationDao.delGroup_User_Relation(groupid, userid);
	}

	@Override
	public List<User> queryGroupUsers(int groupid) {
		List<Group_User_Relation> group_User_Relations = group_User_RelationDao
				.queryGroupUsers(groupid);

		List<User> users = new ArrayList<User>();
		for (int i = 0; i < group_User_Relations.size(); i++) {
			users.add(queryUser(group_User_Relations.get(i).getUserid()));
		}
		return users;
	}

	@Override
	public List<User> queryGroupUsersAll(int groupid) {
		List<User> users = new ArrayList<User>();
		//查询本组下的用户
		users.addAll(queryGroupUsers(groupid));
		
		//查找子组
		List<Group> groups = querySubGroup(groupid);
		for (int i = 0; i < groups.size(); i++) {
			users.addAll(queryGroupUsersAll(groups.get(i).getId()));
		}
		return users;
	}

	@Override
	public User addUser(User user) {
		userBasicDao.saveObj(user.getUserBasic());
		
		for (int i = 0; i < user.getUserExtensions().size(); i++) {
			UserExtension userExtension = user.getUserExtensions().get(i);
			
			userExtension.setUserid(user.getUserBasic().getId());
			userExtensionDao.saveObj(userExtension);
		}
		return user;
	}

	@Override
	public int delUser(int userid) {
		userExtensionDao.delUser(userid);
		userBasicDao.delUser(userid);
		return 0;
	}

	@Override
	public void modifyUser(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public User queryUser(int userid) {
		// TODO Auto-generated method stub
		return null;
	}
}
