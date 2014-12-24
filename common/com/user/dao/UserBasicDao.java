package com.user.dao;

import org.springframework.stereotype.Repository;

import com.common.dao.HQL;
import com.common.dao.impl.BaseDaoImpl;
import com.user.model.UserBasic;

@Repository("userBasicDao")
public class UserBasicDao extends BaseDaoImpl<UserBasic>{
	public int delUser(int userid) {
		return super.deleteHql(new HQL("delete from UserBasic ub where ub.id=?",userid));
	}
}
