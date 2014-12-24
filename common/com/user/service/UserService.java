package com.user.service;

import java.util.List;

import com.user.model.Group;
import com.user.model.Group_Relation;
import com.user.model.Group_User_Relation;
import com.user.model.User;

/**
 * 用户接口，包括用户管理，用户组管理
 * @author DongJun
 *
 */
public interface UserService {
	/**
	 * 添加组
	 */
	public Group addGroup(Group group);
	
	
	/**
	 * 删除组，同时删除组与其它组的关系，删除组与用户的关系
	 */
	public void delGroup(int groupid);
	
	/**
	 * 修改组
	 */
	public Group modifyGroup(Group group);
	
	/**
	 * 查找叶子组
	 */
	public List<Group> queryLeafGroup();
	
	/**
	 * 查找某类型叶子组
	 */
	public List<Group> queryLeafGroup(int grouptype);
	
	/**
	 * 查找根组
	 */
	public List<Group> queryRootGroup();
	
	/**
	 * 查找某类型根组
	 */
	public List<Group> queryRootGroup(int grouptype);
	
	/**
	 * 查询某组的信息
	 */
	public Group queryGroup(int groupid);
	
	/**
	 * 查找某组的子组，不包括子组的子组
	 */
	public List<Group> querySubGroup(int groupid);
	
	/**
	 * 查找某组某类型的子组，不包括子组的子组
	 */
	public List<Group> querySubGroup(int groupid,int grouptype);
	
	/**
	 * 查找某组的子组，包括子组的子组
	 */
	public List<Group> querySubGroupAll(int groupid);
	
	
	/**
	 * 查找某组某类型的子组，包括子组的子组
	 */
	public List<Group> querySubGroupAll(int groupid,int grouptype);
	
	/**
	 * 查找所有组
	 */
	public List<Group> queryGroups();
	
	/**
	 * 查找某类型的所有组
	 */
	public List<Group> queryGroups(int grouptype);
	
	/**
	 * 添加组与组关系
	 */
	public Group_Relation addGroup_Relation(int groupid,int subgroupid);
	
	/**
	 * 删除组与组关系
	 */
	public void delGroup_Relation(int groupid,int subgroupid);
	
	/**
	 * 添加组与用户关系
	 */
	public Group_User_Relation addGroup_User_Relation(int groupid,int userid);
	
	/**
	 * 删除组与用户关系
	 */
	public void delGroup_User_Relation(int groupid,int userid);
	
	/**
	 * 查询组下用户，不包括子组的用户
	 */
	public List<User> queryGroupUsers(int groupid);
	
	/**
	 * 查询组下用户，包括子组的用户
	 */
	public List<User> queryGroupUsersAll(int groupid);
	
	
	/**
	 * 添加用户
	 * 
	 * @param user
	 * @return
	 */
	public User addUser(User user);
	
	/**
	 * 删除用户
	 * 
	 * @param condition
	 * @return
	 */
	public int delUser(int userid);
	
	/**
	 * 修改用户
	 * @param user
	 */
	public void modifyUser(User user);
	
	/**
	 * 查询用户
	 * 
	 * @throws NoUserException
	 * @throws DBException
	 * @throws NotEnoughParameterException
	 */
	public User queryUser(int userid);
}
