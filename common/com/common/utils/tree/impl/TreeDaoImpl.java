package com.common.utils.tree.impl;

import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import com.common.utils.tree.TreeDao;
import com.common.utils.tree.model.TreeNode;
import com.common.utils.tree.model.TreeNodeRelation;

public abstract class TreeDaoImpl extends BaseDaoDB implements TreeDao {
	public abstract Class<?> getEntryClass();

	public abstract Class<?> getEntryRelationClass();

	public BaseQueryRecords<?> findRootNodes() {
		return this.findRootNodes(-1, -1);
	}

	public BaseQueryRecords<?> findRootNodes(int page, int rows) {
		String hql = "select a from ? a where a.id not in "
				+ "(select r.sid from ? r)";

		return super.find(new HQL(hql, getEntryClass().getSimpleName(),
				getEntryRelationClass().getSimpleName()), page, rows);
	}

	public BaseQueryRecords<?> findRootNodes(int type) {
		return findRootNodes(type, -1, -1);
	}

	public BaseQueryRecords<?> findRootNodes(int type, int page, int rows) {
		String hql = "select a from ? a where a.id not in "
				+ "(select r.sid from ? r)" + " and a.type=?";

		return super.find(new HQL(hql, getEntryClass().getSimpleName(),
				getEntryRelationClass().getSimpleName(), type), page, rows);
	}

	public BaseQueryRecords<?> findLeafNodes() {
		return findLeafNodes(-1, -1);
	}

	public BaseQueryRecords<?> findLeafNodes(int page, int rows) {
		String hql = "select a from ? a where a.id not in "
				+ "(select r.pid from ? r)";

		return super.find(new HQL(hql, getEntryClass().getSimpleName(),
				getEntryRelationClass().getSimpleName()), page, rows);
	}

	public BaseQueryRecords<?> findLeafNodes(int type) {
		return findLeafNodes(type, -1, -1);
	}

	public BaseQueryRecords<?> findLeafNodes(int type, int page, int rows) {
		String hql = "select a from ? a where a.id not in "
				+ "(select r.pid from ? r)" + " and a.type=?";

		return super.find(new HQL(hql, getEntryClass().getSimpleName(),
				getEntryRelationClass().getSimpleName(), type), page, rows);
	}

	public BaseQueryRecords<?> findNodes() {
		return findNodes(-1, -1);
	}

	public BaseQueryRecords<?> findNodes(int page, int rows) {
		String hql = "select a from ? a";

		return super.find(new HQL(hql, getEntryClass().getSimpleName()), page,
				rows);
	}

	public BaseQueryRecords<?> findNodes(int type) {
		return findNodes(type, -1, -1);
	}

	public BaseQueryRecords<?> findNodes(int type, int page, int rows) {
		String hql = "select a from ? a where a.type=?";

		return super.find(new HQL(hql, getEntryClass().getSimpleName(), type),
				page, rows);
	}

	public BaseQueryRecords<?> findChildrenNodes(TreeNode node) {
		return findChildrenNodes(node, -1, -1);
	}

	public BaseQueryRecords<?> findChildrenNodes(TreeNode node, int page,
			int rows) {
		String hql = "select a from ? b,? a " + "where b.sid=a.id and b.pid=?";

		return super.find(new HQL(hql, getEntryRelationClass().getSimpleName(),
				getEntryClass().getSimpleName(), node.getId()));
	}

	public BaseQueryRecords<?> findChildrenNodes(TreeNode node, int type) {
		return findChildrenNodes(node, type, -1, -1);
	}

	public BaseQueryRecords<?> findChildrenNodes(TreeNode node, int type,
			int page, int rows) {
		String hql = "select a from ? b,? a "
				+ "where b.sid=a.id and b.pid=? and a.type=?";

		return super.find(new HQL(hql, getEntryRelationClass().getSimpleName(),
				getEntryClass().getSimpleName(), node.getId(), type));
	}

	public TreeNode _addNode(TreeNode node) {
		super.save(node);
		return node;
	}

	public boolean _delNode(TreeNode node) {
		super.delete(new HQL("delete from ? where id=?", getEntryClass()
				.getSimpleName(), node.getId()));
		return true;
	}

	public TreeNode _updateNode(TreeNode node) {
		super.update(node);
		return node;
	}

	public TreeNode _findNode(TreeNode node) {
		return (TreeNode) super.findUnique(new HQL(
				"select a from ? a where a.id=?", getEntryClass()
						.getSimpleName(), node.getId()));
	}

	public boolean _bindTwoNode(TreeNode pnode, TreeNode snode) {
		try {
			TreeNodeRelation relation;
			relation = (TreeNodeRelation) getEntryRelationClass().newInstance();
			relation.setPid(pnode.getId());
			relation.setSid(snode.getId());
			super.save(relation);
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	public boolean _delbindTwoNode(TreeNode pnode, TreeNode snode) {
		super.delete(new HQL("delete from ? where pid=? and sid=?",
				getEntryRelationClass().getSimpleName(), pnode.getId(), snode
						.getId()));
		return true;
	}

	public boolean _delBindChildrenNodes(TreeNode node) {
		String hql = "delete from ? where pid=?";
		super.delete(new HQL(hql, getEntryRelationClass().getSimpleName(), node
				.getId()));
		return true;
	}

	public boolean _delBindParentNodes(TreeNode node) {
		String hql = "delete from ? where sid=?";
		super.delete(new HQL(hql, getEntryRelationClass().getSimpleName(), node
				.getId()));
		return true;
	}

	public boolean _ifTwoNodeHasRelation(TreeNode pnode, TreeNode snode) {
		TreeNodeRelation relation = (TreeNodeRelation) super
				.findUnique(new HQL(
						"select a from ? a where a.pid=? and a.sid=?",
						getEntryRelationClass().getSimpleName(), pnode.getId(), snode
								.getId()));
		return relation == null ? false : true;
	}

	public BaseQueryRecords<?> findParentNodes(TreeNode node) {
		String hql = "select a from ? b,? a " + "where b.pid=a.id and b.sid=?";

		return super.find(new HQL(hql, getEntryRelationClass().getSimpleName(),
				getEntryClass().getSimpleName(), node.getId()));
	}
}
