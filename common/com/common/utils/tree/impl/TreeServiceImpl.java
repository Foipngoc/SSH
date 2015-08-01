package com.common.utils.tree.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.common.dao.BaseQueryRecords;
import com.common.service.BaseService;
import com.common.utils.tree.TreeDao;
import com.common.utils.tree.TreeService;
import com.common.utils.tree.model.Tree;
import com.common.utils.tree.model.TreeNode;
import com.common.utils.tree.model.TreeNodeRelation;

/**
 * 以treenode/treenoderelation为模型的一种实现,支持treenode/treenoderelation的子类
 * 
 * @author DJ
 * 
 */
public abstract class TreeServiceImpl<E extends TreeNode, R extends TreeNodeRelation>
		extends BaseService implements TreeService<E, R> {

	@Override
	public E addBindChildrenNode(E pnode, E newnode) {
		E nodenew = addNode(newnode);
		bindChildrenNode(pnode, nodenew);
		return nodenew;
	}

	@Override
	public E addBindParentNode(E snode, E newnode) {
		E nodenew = addNode(newnode);
		bindParentNode(snode, nodenew);
		return nodenew;
	}

	@Override
	public boolean bindChildrenNode(E pnode, E snode) {
		if (ifNodeEqual(pnode, snode)) {
			return false;
		}

		// 是否已有关联
		if (ifTwoNodeHasRelation(pnode, snode)
				|| ifTwoNodeHasRelation(snode, pnode)) {
			return false;
		}

		// 防止节点回路,检查子节点的子节点列表中是否有父节点
		List<E> subpermsubs = findChildrenNodes_r(snode);
		for (int i = 0; i < subpermsubs.size(); i++) {
			if (ifNodeEqual(subpermsubs.get(i), pnode)) {
				return false;
			}
		}

		return bindTwoNode(pnode, snode);
	}

	@Override
	public boolean bindParentNode(E snode, E pnode) {
		return bindChildrenNode(pnode, snode);
	}

	@Override
	public void delBindParentNode(E snode, E pnode) {
		delBindChildrenNode(pnode, snode);
	}

	@Override
	public void delBindNodes(E node) {
		delBindParentNodes(node);
		delBindChildrenNodes(node);
	}

	/**
	 * 以递归的方式所有子节点
	 */
	private List<E> _findChildrenNodes_r(E nd, String name) {
		List<E> childs = new ArrayList<>();

		// 添加符合条件的一级子节点
		childs.addAll(findChildrenNodes(nd, name).getData());

		// 查询子节点的所有子节点
		List<E> listsall = findChildrenNodes(nd).getData();
		for (int i = 0; i < listsall.size(); i++) {
			childs.addAll(_findChildrenNodes_r(listsall.get(i), name));
		}
		return childs;
	}

	/**
	 * 以递归的方式所有子节点
	 */
	private List<E> _findChildrenNodes_r(E nd, String name, int type) {
		List<E> childs = new ArrayList<>();

		// 添加符合条件的一级子节点
		childs.addAll(findChildrenNodes(nd, name, type).getData());

		// 查询子节点的所有子节点
		List<E> listsall = findChildrenNodes(nd).getData();
		for (int i = 0; i < listsall.size(); i++) {
			childs.addAll(_findChildrenNodes_r(listsall.get(i), name, type));
		}
		return childs;
	}

	/**
	 * 以递归的方式所有子节点
	 */
	private List<E> _findChildrenNodes_r(E nd) {
		List<E> childs = new ArrayList<>();

		List<E> lists = findChildrenNodes(nd).getData();

		// 添加符合条件的一级子节点
		childs.addAll(lists);
		// 查询子节点的所有子节点
		for (int i = 0; i < lists.size(); i++) {
			childs.addAll(_findChildrenNodes_r(lists.get(i)));
		}
		return childs;
	}

	/**
	 * 以递归的方式所有父节点
	 */
	private List<E> _findParentNodes_r(E nd) {
		List<E> parents = new ArrayList<>();

		// 查询子节点的所有父节点
		List<E> listsall = findParentNodes(nd).getData();

		// 添加符合条件的一级父节点
		parents.addAll(listsall);

		for (int i = 0; i < listsall.size(); i++) {
			parents.addAll(_findParentNodes_r(listsall.get(i)));
		}
		return parents;
	}

	/**
	 * 以递归的方式所有子节点
	 */
	private List<E> _findChildrenNodes_r(E nd, int type) {
		List<E> childs = new ArrayList<>();

		// 添加符合条件的一级子节点
		childs.addAll(findChildrenNodes(nd, type).getData());

		// 查询所有子节点的所有符合条件子节点
		List<E> listsall = findChildrenNodes(nd).getData();
		for (int i = 0; i < listsall.size(); i++) {
			childs.addAll(_findChildrenNodes_r(listsall.get(i), type));
		}
		return childs;
	}

	@Override
	public List<E> findChildrenNodes_r(E nd) {
		List<E> lists = _findChildrenNodes_r(nd);

		// 去重复
		Map<String, E> map = new HashMap<String, E>();
		Iterator<E> it = lists.iterator();
		while (it.hasNext()) {
			E node = it.next();

			if (map.containsKey("" + node.getId())) {
				it.remove();
			} else {
				map.put("" + node.getId(), node);
			}
		}
		return lists;
	}

	@Override
	public List<E> findChildrenNodes_r(E nd, int type) {
		List<E> lists = _findChildrenNodes_r(nd, type);
		// 去重复
		Map<String, E> map = new HashMap<String, E>();
		Iterator<E> it = lists.iterator();
		while (it.hasNext()) {
			E node = it.next();

			if (map.containsKey("" + node.getId())) {
				it.remove();
			} else {
				map.put("" + node.getId(), node);
			}
		}
		return lists;
	}

	@Override
	public List<E> findChildrenNodes_r(E nd, String name) {
		List<E> lists = _findChildrenNodes_r(nd, name);
		// 去重复
		Map<String, E> map = new HashMap<String, E>();
		Iterator<E> it = lists.iterator();
		while (it.hasNext()) {
			E node = it.next();

			if (map.containsKey("" + node.getId())) {
				it.remove();
			} else {
				map.put("" + node.getId(), node);
			}
		}
		return lists;
	}

	@Override
	public List<E> findChildrenNodes_r(E nd, String name, int type) {
		List<E> lists = _findChildrenNodes_r(nd, name, type);
		// 去重复
		Map<String, E> map = new HashMap<String, E>();
		Iterator<E> it = lists.iterator();
		while (it.hasNext()) {
			E node = it.next();

			if (map.containsKey("" + node.getId())) {
				it.remove();
			} else {
				map.put("" + node.getId(), node);
			}
		}
		return lists;
	}

	@Override
	public Tree<E> findNodeTree(E nd, int lv) {
		Tree<E> tree = new Tree<E>();
		// 先查询本节点
		E node = this.findNode(nd);
		tree.setNode(node);
		if (lv == -1 || lv > 0) {
			List<E> children = this.findChildrenNodes(nd).getData();

			if (children != null) {
				for (int i = 0; i < children.size(); i++) {
					E child = children.get(i);
					// 查询第一个子节点的树
					Tree<E> subTree = findNodeTree(child, lv - 1);
					tree.getChildren().add(subTree);
					subTree.getParents().add(tree);
				}
			}
		}
		return tree;
	}

	@Override
	public boolean ifNodeLeaf(E node) {
		BaseQueryRecords<E> children = this.findChildrenNodes(node);
		if (children == null || children.getData() == null
				|| children.getData().size() <= 0)
			return true;
		return false;
	}

	@Override
	public boolean ifNodeRoot(E node) {
		BaseQueryRecords<E> parent = this.findParentNodes(node);
		if (parent == null || parent.getData() == null
				|| parent.getData().size() <= 0)
			return true;
		return false;
	}

	/****************************************************/

	/**
	 * 获取treedao的实现
	 * 
	 */
	public abstract TreeDao<E, R> getTreeDao();

	@Override
	public boolean ifNodeEqual(E nodea, E nodeb) {
		return getTreeDao()._ifNodeEqual(nodea, nodeb);
	}

	@Override
	public boolean ifTwoNodeHasRelation(E pnode, E snode) {
		return getTreeDao()._ifTwoNodeHasRelation(pnode, snode);
	}

	@Override
	public boolean bindTwoNode(E pnode, E snode) {
		return getTreeDao()._bindTwoNode(pnode, snode);
	}

	@Override
	public E addNode(E node) {
		return getTreeDao()._addNode(node);
	}

	@Override
	public void delBindChildrenNode(E pnode, E snode) {
		getTreeDao()._delbindTwoNode(pnode, snode);
	}

	@Override
	public void delBindChildrenNodes(E node) {
		getTreeDao()._delBindChildrenNodes(node);
	}

	@Override
	public void delBindParentNodes(E node) {
		getTreeDao()._delBindParentNodes(node);
	}

	@Override
	public void modifyNode(E node) {
		getTreeDao()._modifyNode(node);
	}

	@Override
	public void delNode(E node) {
		getTreeDao()._delNode(node);
		delBindNodes(node);
	}

	@Override
	public void delNode_r(E node) {
		List<E> chiledsall = findChildrenNodes_r(node);
		for (int i = 0; i < chiledsall.size(); i++) {
			delNode(chiledsall.get(i));
		}
		delNode(node);
	}

	@Override
	public E findNode(E node) {
		return (E) getTreeDao()._findNode(node);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseQueryRecords<E> findRootNodes() {
		return (BaseQueryRecords<E>) getTreeDao()._findRootNodes();
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseQueryRecords<E> findRootNodes(int page, int rows) {
		return (BaseQueryRecords<E>) getTreeDao()._findRootNodes(page, rows);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseQueryRecords<E> findRootNodes(int type) {
		return (BaseQueryRecords<E>) getTreeDao()._findRootNodes(type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseQueryRecords<E> findRootNodes(int type, int page, int rows) {
		return (BaseQueryRecords<E>) getTreeDao()._findRootNodes(type, page,
				rows);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseQueryRecords<E> findRootNodes(String name) {
		return (BaseQueryRecords<E>) getTreeDao()._findRootNodes(name);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseQueryRecords<E> findRootNodes(String name, int page, int rows) {
		return (BaseQueryRecords<E>) getTreeDao()._findRootNodes(name, page,
				rows);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseQueryRecords<E> findRootNodes(String name, int type) {
		return (BaseQueryRecords<E>) getTreeDao()._findRootNodes(name, type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseQueryRecords<E> findRootNodes(String name, int type, int page,
			int rows) {
		return (BaseQueryRecords<E>) getTreeDao()._findRootNodes(name, type,
				page, rows);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseQueryRecords<E> findLeafNodes() {
		return (BaseQueryRecords<E>) getTreeDao()._findLeafNodes();
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseQueryRecords<E> findLeafNodes(int page, int rows) {
		return (BaseQueryRecords<E>) getTreeDao()._findLeafNodes(page, rows);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseQueryRecords<E> findLeafNodes(int type) {
		return (BaseQueryRecords<E>) getTreeDao()._findLeafNodes(type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseQueryRecords<E> findLeafNodes(int type, int page, int rows) {
		return (BaseQueryRecords<E>) getTreeDao()._findLeafNodes(type, page,
				rows);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseQueryRecords<E> findLeafNodes(String name) {
		return (BaseQueryRecords<E>) getTreeDao()._findLeafNodes(name);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseQueryRecords<E> findLeafNodes(String name, int page, int rows) {
		return (BaseQueryRecords<E>) getTreeDao()._findLeafNodes(name, page,
				rows);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseQueryRecords<E> findLeafNodes(String name, int type) {
		return (BaseQueryRecords<E>) getTreeDao()._findLeafNodes(name, type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseQueryRecords<E> findLeafNodes(String name, int type, int page,
			int rows) {
		return (BaseQueryRecords<E>) getTreeDao()._findLeafNodes(name, type,
				page, rows);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseQueryRecords<E> findNodes() {
		return (BaseQueryRecords<E>) getTreeDao()._findNodes();
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseQueryRecords<E> findNodes(int page, int rows) {
		return (BaseQueryRecords<E>) getTreeDao()._findNodes(page, rows);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseQueryRecords<E> findNodes(int type) {
		return (BaseQueryRecords<E>) getTreeDao()._findNodes(type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseQueryRecords<E> findNodes(int type, int page, int rows) {
		return (BaseQueryRecords<E>) getTreeDao()._findNodes(type, page, rows);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseQueryRecords<E> findNodes(String name) {
		return (BaseQueryRecords<E>) getTreeDao()._findNodes(name);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseQueryRecords<E> findNodes(String name, int page, int rows) {
		return (BaseQueryRecords<E>) getTreeDao()._findNodes(name, page, rows);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseQueryRecords<E> findNodes(String name, int type) {
		return (BaseQueryRecords<E>) getTreeDao()._findNodes(name, type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseQueryRecords<E> findNodes(String name, int type, int page,
			int rows) {
		return (BaseQueryRecords<E>) getTreeDao()._findNodes(name, type, page,
				rows);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseQueryRecords<E> findChildrenNodes(E node) {
		return (BaseQueryRecords<E>) getTreeDao()._findChildrenNodes(node);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseQueryRecords<E> findChildrenNodes(E node, int page, int rows) {
		return (BaseQueryRecords<E>) getTreeDao()._findChildrenNodes(node,
				page, rows);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseQueryRecords<E> findChildrenNodes(E node, int type) {
		return (BaseQueryRecords<E>) getTreeDao()
				._findChildrenNodes(node, type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseQueryRecords<E> findChildrenNodes(E node, int type, int page,
			int rows) {
		return (BaseQueryRecords<E>) getTreeDao()._findChildrenNodes(node,
				type, page, rows);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseQueryRecords<E> findParentNodes(E node) {
		return (BaseQueryRecords<E>) getTreeDao()._findParentNodes(node);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseQueryRecords<E> findParentNodes(E node, int page, int rows) {
		return (BaseQueryRecords<E>) getTreeDao()._findParentNodes(node, page,
				rows);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseQueryRecords<E> findParentNodes(E node, int type) {
		return (BaseQueryRecords<E>) getTreeDao()._findParentNodes(node, type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseQueryRecords<E> findParentNodes(E node, int type, int page,
			int rows) {
		return (BaseQueryRecords<E>) getTreeDao()._findParentNodes(node, type,
				page, rows);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseQueryRecords<E> findParentNodes(E node, String name) {
		return (BaseQueryRecords<E>) getTreeDao()._findParentNodes(node, name);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseQueryRecords<E> findParentNodes(E node, String name, int type) {
		return (BaseQueryRecords<E>) getTreeDao()._findParentNodes(node, name,
				type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseQueryRecords<E> findParentNodes(E node, String name, int page,
			int rows) {
		return (BaseQueryRecords<E>) getTreeDao()._findParentNodes(node, name,
				page, rows);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseQueryRecords<E> findParentNodes(E node, String name, int type,
			int page, int rows) {
		return (BaseQueryRecords<E>) getTreeDao()._findParentNodes(node, name,
				type, page, rows);
	}

	@Override
	public List<E> findParentNodes_r(E nd) {
		List<E> lists = _findParentNodes_r(nd);

		// 去重复
		Map<String, E> map = new HashMap<String, E>();
		Iterator<E> it = lists.iterator();
		while (it.hasNext()) {
			E node = it.next();

			if (map.containsKey("" + node.getId())) {
				it.remove();
			} else {
				map.put("" + node.getId(), node);
			}
		}
		return lists;
	}

	@Override
	public Tree<E> findOnlyParentPath(E node) {
		Tree<E> tree = new Tree<>();
		tree.setNode(node);

		Tree<E> current = tree;
		while (true) {
			List<E> parents = findParentNodes(current.getNode()).getData();
			if (parents.size() > 0) {
				Tree<E> parentTree = new Tree<E>();
				parentTree.setNode(parents.get(0));
				List<Tree<E>> childTreeList = new ArrayList<>();
				childTreeList.add(current);
				parentTree.setChildren(childTreeList);
				current = parentTree;
				continue;
			} else {
				break;
			}
		}
		return current;
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseQueryRecords<E> findChildrenNodes(E node, String name) {
		BaseQueryRecords<E> result = (BaseQueryRecords<E>) getTreeDao()
				._findChildrenNodes(node, name);

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseQueryRecords<E> findChildrenNodes(E node, String name, int type) {
		return (BaseQueryRecords<E>) getTreeDao()._findChildrenNodes(node,
				name, type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseQueryRecords<E> findChildrenNodes(E node, String name, int type,
			int page, int rows) {
		return (BaseQueryRecords<E>) getTreeDao()._findChildrenNodes(node,
				name, type, page, rows);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseQueryRecords<E> findChildrenNodes(E node, String name, int page,
			int rows) {
		return (BaseQueryRecords<E>) getTreeDao()._findChildrenNodes(node,
				name, page, rows);
	}

}
