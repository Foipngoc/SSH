package com.userprivilege.dao.privilege;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import com.userprivilege.model.privilege.Permission;
import com.userprivilege.model.privilege.Permission_relation;

@SuppressWarnings("all")
@Repository("permission_relationDao")
public class Permission_relationDao extends BaseDaoDB<Permission_relation> {

	public Permission_relation findPerm_relation(int permid, int subpermid) {
		return (Permission_relation) super
				.findUnique(new HQL(
						"from Permission_relation r where r.permid=? and r.subpermid=?",
						permid, subpermid));
	}

	public void deletePerm_relation(int permid, int subpermid) {
		super.delete(new HQL(
				"delete from Permission_relation r where r.permid=? and r.subpermid=?",
				permid, subpermid));
	}

	public List<Permission> findSubPerms(int permid) {
		String hql = "select p from Permission_relation r,Permission p "
				+ "where r.subpermid = p.id and r.permid=" + permid;
		return (List<Permission>) super.find(new HQL(hql));
	}

	public List<Permission> findParentPerms(int permid) {
		String hql = "select p from Permission_relation r,Permission p "
				+ "where r.permid = p.id and r.subpermid=" + permid;
		return (List<Permission>) super.find(new HQL(hql));
	}

	public List<Permission> findSubPerms(int permid, int page, int rows) {
		String hql = "select p from Permission_relation r,Permission p "
				+ "where r.subpermid = p.id and r.permid=" + permid;
		return (List<Permission>) super.find(new HQL(hql), page, rows);
	}

	public List<Permission> findSubPerms(int permid, int permtype) {
		String hql = "select p from Permission_relation r,Permission p "
				+ "where r.subpermid = p.id and r.permid=" + permid
				+ " and p.permtype=" + permtype;
		return (List<Permission>) super.find(new HQL(hql));
	}

	public List<Permission> findSubPerms(int permid, int permtype, int page,
			int rows) {
		String hql = "select p from Permission_relation r,Permission p "
				+ "where r.subpermid = p.id and r.permid=" + permid
				+ " and p.permtype=" + permtype;
		return (List<Permission>) super.find(new HQL(hql), page, rows);
	}

	public long countSubPerms(int permid, int permtype) {
		String hql = "select count(p) from Permission_relation r,Permission p "
				+ "where r.subpermid = p.id and r.permid=" + permid
				+ " and p.permtype=" + permtype;
		return super.count(new HQL(hql));
	}

	public void delSubPermissions(int permid) {
		String hql = "delete from Permission_relation r where r.permid="
				+ permid;
		super.delete(new HQL(hql));
	}

	public void delParentPermissions(int permid) {
		String hql = "delete from Permission_relation r where r.subpermid="
				+ permid;
		super.delete(new HQL(hql));
	}

	public List<Permission> findPermRoot() {
		String hql = "select p from Permission p where p.id not in "
				+ "(select r.subpermid from Permission_relation r)";
		return (List<Permission>) super.find(new HQL(hql));
	}

	public List<Permission> findPermRoot(int permtype) {
		String hql = "select p from Permission p where p.id not in "
				+ "(select r.subpermid from Permission_relation r) and p.permtype="
				+ permtype;
		return (List<Permission>) super.find(new HQL(hql));
	}

	public List<Permission> findPermLeaf() {
		String hql = "select p from Permission p where p.id not in "
				+ "(select r.permid from Permission_relation r)";
		return (List<Permission>) super.find(new HQL(hql));
	}

	public List<Permission> findPermLeaf(int permtype) {
		String hql = "select p from Permission p where p.id not in "
				+ "(select r.permid from Permission_relation r) and p.permtype="
				+ permtype;
		return (List<Permission>) super.find(new HQL(hql));
	}

	public List<Permission> findPermsOrderByParent(int permtype, int page,
			int rows) {
		String hql = "select p from Permission_relation r , Permission p "
				+ "where r.subpermid = p.id and p.permtype = ? ORDER BY r.permid desc";
		return (List<Permission>) super.find(new HQL(hql,permtype), page, rows);
	}
}
