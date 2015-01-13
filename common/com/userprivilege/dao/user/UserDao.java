package com.userprivilege.dao.user;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import com.userprivilege.model.user.User;

@SuppressWarnings("all")
@Repository("userDao")
public class UserDao extends BaseDaoDB<User> {
	public int delUser(int userid) {
		return super.delete(new HQL("delete from User u where u.id=?", userid));
	}

	public List<User> findByNotStatus(int notuserstatus, int page, int row) {
		return (List<User>) super.find(new HQL(
				"select u from User u where u.userstatus != ?", notuserstatus),
				page, row);
	}

	public long countByNotStatus(int notuserstatus) {
		return super.count(new HQL(
				"select count(u) from User u where u.userstatus !=?",
				notuserstatus));
	}

	public User findUser(int userid) {
		return super.findUnique(new User(), "id", userid);
	}
	
	public User findUser(String username) {
		return super.findUnique(new User(), "username", username);
	}
}
