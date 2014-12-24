package com.user.dao;

import org.springframework.stereotype.Repository;

import com.common.dao.HQL;
import com.common.dao.impl.BaseDaoImpl;
import com.user.model.UserExtension;

@Repository("userExtensionDao")
public class UserExtensionDao extends BaseDaoImpl<UserExtension> {

	public int delUser(int userid) {
		return super.deleteHql(new HQL(
				"delete from UserExtension ue where ue.userid=?", userid));
	}
}
