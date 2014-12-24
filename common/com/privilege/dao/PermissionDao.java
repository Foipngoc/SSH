package com.privilege.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.common.dao.HQL;
import com.common.dao.impl.BaseDaoImpl;
import com.privilege.model.Permission;

@Repository("permissionDao")
public class PermissionDao extends BaseDaoImpl<Permission> {

	@SuppressWarnings("unchecked")
	public List<Permission> queryAll() throws Exception {
		return (List<Permission>) super.findHql(new HQL(
				"select p from Permission p"));
	}

	@SuppressWarnings("unchecked")
	public List<Permission> queryAll(int permtype) throws Exception {
		return (List<Permission>) super.findHql(new HQL(
				"select p from Permission p where p.permtype=" + permtype));
	}

	public void delPerm(int permid) throws Exception {
		super.deleteHql(new HQL("delete from Permission p where p.id=?", permid));
	}

	@SuppressWarnings("unchecked")
	public List<Permission> queryPermSubPerms(int permid) throws Exception {
		String hql = "select p from Permission_relation r,Permission p "
				+ "where r.subpermid = p.id and r.permid=" + permid;
		return (List<Permission>) super.findHql(new HQL(hql));
	}

	@SuppressWarnings("unchecked")
	public List<Permission> queryPermSubPerms(int permid, int permtype)
			throws Exception {
		String hql = "select p from Permission_relation r,Permission p "
				+ "where r.subpermid = p.id and r.permid=" + permid
				+ " and p.permtype=" + permtype;
		return (List<Permission>) super.findHql(new HQL(hql));
	}

	@SuppressWarnings("unchecked")
	public List<Permission> queryPermRoot() throws Exception {
		String hql = "select p from Permission p where p.id not in "
				+ "(select r.subpermid from Permission_relation r)";
		return (List<Permission>) super.findHql(new HQL(hql));
	}

	@SuppressWarnings("unchecked")
	public List<Permission> queryPermRoot(int permtype) throws Exception {
		String hql = "select p from Permission p where p.id not in "
				+ "(select r.subpermid from Permission_relation r) and p.permtype="
				+ permtype;
		return (List<Permission>) super.findHql(new HQL(hql));
	}

	@SuppressWarnings("unchecked")
	public List<Permission> queryPermLeaf() throws Exception {
		String hql = "select p from Permission p where p.id not in "
				+ "(select r.permid from Permission_relation r)";
		return (List<Permission>) super.findHql(new HQL(hql));
	}

	@SuppressWarnings("unchecked")
	public List<Permission> queryPermLeaf(int permtype) throws Exception {
		String hql = "select p from Permission p where p.id not in "
				+ "(select r.permid from Permission_relation r) and p.permtype="
				+ permtype;
		return (List<Permission>) super.findHql(new HQL(hql));
	}
}
