package com.privilege.dao;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import com.privilege.model.Permission;
import com.privilege.model.Permission_relation;

@SuppressWarnings("all")
@Repository("permission_relationDao")
public class Permission_relationDao extends BaseDaoDB<Permission_relation> {

	public Permission_relation findPerm_relation(int permid, int subpermid)
			throws Exception {
		return super.findUnique(new Permission_relation(),
				Restrictions.eq("permid", permid),
				Restrictions.eq("subpermid", subpermid));
	}

	public List<Permission> findSubPerms(int permid) throws Exception {
		String hql = "select p from Permission_relation r,Permission p "
				+ "where r.subpermid = p.id and r.permid=" + permid;
		return (List<Permission>) super.find(new HQL(hql));
	}

	/**
	 * 查找某类型所有子权限
	 */
	public List<Permission> findSubPerms(int permid, int permtype)
			throws Exception {
		String hql = "select p from Permission_relation r,Permission p "
				+ "where r.subpermid = p.id and r.permid=" + permid
				+ " and p.permtype=" + permtype;
		return (List<Permission>) super.find(new HQL(hql));
	}

	public void delSubPermissions(int permid) throws Exception {
		String hql = "delete from Permission_relation where permid=" + permid;
		super.delete(new HQL(hql));
	}

	public void delParentPermissions(int permid) throws Exception {
		String hql = "delete from Permission_relation where subpermid="
				+ permid;
		super.delete(new HQL(hql));
	}

	public List<Permission> findPermRoot() throws Exception {
		String hql = "select p from Permission p where p.id not in "
				+ "(select r.subpermid from Permission_relation r)";
		return (List<Permission>) super.find(new HQL(hql));
	}

	public List<Permission> findPermRoot(int permtype) throws Exception {
		String hql = "select p from Permission p where p.id not in "
				+ "(select r.subpermid from Permission_relation r) and p.permtype="
				+ permtype;
		return (List<Permission>) super.find(new HQL(hql));
	}

	public List<Permission> findPermLeaf() throws Exception {
		String hql = "select p from Permission p where p.id not in "
				+ "(select r.permid from Permission_relation r)";
		return (List<Permission>) super.find(new HQL(hql));
	}

	public List<Permission> findPermLeaf(int permtype) throws Exception {
		String hql = "select p from Permission p where p.id not in "
				+ "(select r.permid from Permission_relation r) and p.permtype="
				+ permtype;
		return (List<Permission>) super.find(new HQL(hql));
	}
}
