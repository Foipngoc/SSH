package com.common.utils.tree;

import com.common.dao.BaseQueryRecords;
import com.common.utils.tree.model.TreeNode;

public interface TreeDao {
	public BaseQueryRecords<?> findRootNodes();

	public BaseQueryRecords<?> findRootNodes(int page, int rows);

	public BaseQueryRecords<?> findRootNodes(int type);

	public BaseQueryRecords<?> findRootNodes(int type, int page, int rows);

	public BaseQueryRecords<?> findLeafNodes();

	public BaseQueryRecords<?> findLeafNodes(int page, int rows);

	public BaseQueryRecords<?> findLeafNodes(int type);

	public BaseQueryRecords<?> findLeafNodes(int type, int page, int rows);

	public BaseQueryRecords<?> findNodes();

	public BaseQueryRecords<?> findNodes(int page, int rows);

	public BaseQueryRecords<?> findNodes(int type);

	public BaseQueryRecords<?> findNodes(int type, int page, int rows);

	public BaseQueryRecords<?> findChildrenNodes(TreeNode node);

	public BaseQueryRecords<?> findChildrenNodes(TreeNode node, int page,
			int rows);

	public BaseQueryRecords<?> findChildrenNodes(TreeNode node, int type);

	public BaseQueryRecords<?> findChildrenNodes(TreeNode node, int type,
			int page, int rows);

	public TreeNode _addNode(TreeNode node);

	public boolean _delNode(TreeNode node);

	public TreeNode _updateNode(TreeNode node);

	public TreeNode _findNode(TreeNode node);

	public boolean _bindTwoNode(TreeNode pnode, TreeNode snode);

	public boolean _delbindTwoNode(TreeNode pnode, TreeNode snode);

	public boolean _delBindChildrenNodes(TreeNode node);

	public boolean _delBindParentNodes(TreeNode node);

	public boolean _ifTwoNodeHasRelation(TreeNode pnode, TreeNode snode);

	public BaseQueryRecords<?> findParentNodes(TreeNode node);
}
