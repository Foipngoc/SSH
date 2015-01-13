package com.userprivilege.dao.privilege;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import com.userprivilege.model.privilege.Permission;

@SuppressWarnings("all")
@Repository("permissionDao")
public class PermissionDao extends BaseDaoDB<Permission> {

	public Permission findPerm(int permid) {
		return super.findUnique(new Permission(), "id", permid);
	}

	public Permission findPerm(int permid, int permtype) {
		return (Permission) super.findUnique(new HQL(
				"from Permission p where p.id=? and p.permtype=?", permid,
				permtype));
	}

	/**
	 * 查找某种类型的权限
	 */
	public List<Permission> findByType(int permtype) {
		return super.find(new Permission(), "permtype", permtype);
	}

	/**
	 * 删除权限permid
	 */
	public void delPerm(int permid) {
		super.delete(new HQL("delete from Permission p where p.id=?", permid));
	}
}
