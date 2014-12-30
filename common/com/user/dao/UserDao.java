package com.user.dao;

import org.springframework.stereotype.Repository;

import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import com.user.model.User;

@Repository("userBasicDao")
public class UserDao extends BaseDaoDB<User>{
	public int delUser(int userid) {
		return super.delete(new HQL("delete from User u where u.id=?",userid));
	}
}
