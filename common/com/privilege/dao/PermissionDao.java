package com.privilege.dao;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import com.privilege.model.Permission;

@SuppressWarnings("all")
@Repository("permissionDao")
public class PermissionDao extends BaseDaoDB<Permission> {

	public Permission findPerm(int permid) throws Exception {
		return super
				.findUnique(new Permission(), Restrictions.eq("id", permid));
	}

	/**
	 * 查找某种类型的权限
	 */
	public List<Permission> findByType(int permtype) throws Exception {
		return super.find(new Permission(),
				Restrictions.eq("permtype", permtype));
	}

	/**
	 * 删除权限permid
	 */
	public void delPerm(int permid) throws Exception {
		super.delete(new HQL("delete from Permission p where p.id=?", permid));
	}
}
