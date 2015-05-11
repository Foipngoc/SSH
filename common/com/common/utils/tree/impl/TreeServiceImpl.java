package com.common.utils.tree.impl;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.common.dao.BaseQueryRecords;
import com.common.service.BaseService;
import com.common.utils.tree.TreeService;
import com.common.utils.tree.model.Tree;

public abstract class TreeServiceImpl<E> extends BaseService implements
		TreeService<E> {
	private Logger log = Logger.getLogger(getClass().getName());

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
			log.debug("两个节点一样，绑定失败");
			return false;
		}

		// 是否已有关联
		if (ifTwoNodeHasRelation(pnode, snode)
				|| ifTwoNodeHasRelation(snode, pnode)) {
			log.debug("已存在关联，绑定失败");
			return false;
		}

		// 防止节点回路,检查子节点的子节点列表中是否有父节点
		List<E> subpermsubs = findChildrenNodes_r(snode);
		for (int i = 0; i < subpermsubs.size(); i++) {
			if (ifNodeEqual(subpermsubs.get(i), pnode)) {
				log.debug("出现回路，绑定失败");
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
	private List<E> _findChildrenNodes_r(E nd) {
		// 查询自己的子节点
		List<E> lists = findChildrenNodes(nd).getData();

		// 查询子节点的所有子节点
		for (int i = 0; i < lists.size(); i++) {
			lists.addAll(_findChildrenNodes_r(lists.get(i)));
		}
		return lists;
	}

	/**
	 * 以递归的方式所有子节点
	 */
	private List<E> _findChildrenNodes_r(E nd, int type) {
		// 查询自己的子节点
		List<E> lists = findChildrenNodes(nd, type).getData();

		// 查询子节点的所有子节点
		for (int i = 0; i < lists.size(); i++) {
			lists.addAll(_findChildrenNodes_r(lists.get(i), type));
		}
		return lists;
	}

	@Override
	public List<E> findChildrenNodes_r(E nd) {
		List<E> lists = _findChildrenNodes_r(nd);

		// 去重复
		Iterator<E> it = lists.iterator();
		while (it.hasNext()) {
			E node = it.next();

			Iterator<E> its = lists.iterator();
			while (its.hasNext()) {
				E nodep = it.next();
				if (ifNodeEqual(node, nodep) == true) {
					it.remove();
					break;
				}
			}
		}
		return lists;
	}

	@Override
	public List<E> findChildrenNodes_r(E nd, int type) {
		List<E> lists = _findChildrenNodes_r(nd, type);
		// 去重复
		Iterator<E> it = lists.iterator();
		while (it.hasNext()) {
			E node = it.next();

			Iterator<E> its = lists.iterator();
			while (its.hasNext()) {
				E nodep = it.next();
				if (ifNodeEqual(node, nodep) == true) {
					it.remove();
					break;
				}
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
		BaseQueryRecords<E> parent = this.findChildrenNodes(node);
		if (parent == null || parent.getData() == null
				|| parent.getData().size() <= 0)
			return true;
		return false;
	}
}
