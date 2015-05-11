package com.common.utils.tree.model;

import java.util.ArrayList;
import java.util.List;

public class Tree<E> {
	private E node;
	private List<Tree<E>> children = new ArrayList<>();
	private List<Tree<E>> parents = new ArrayList<>();

	public E getNode() {
		return node;
	}

	public void setNode(E node) {
		this.node = node;
	}

	public List<Tree<E>> getParents() {
		return parents;
	}

	public void setParents(List<Tree<E>> parents) {
		this.parents = parents;
	}

	public List<Tree<E>> getChildren() {
		return children;
	}

	public void setChildren(List<Tree<E>> children) {
		this.children = children;
	}
}
