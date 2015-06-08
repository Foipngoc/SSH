package com.common.utils.tree;

import java.util.List;

import com.common.dao.BaseQueryRecords;
import com.common.utils.tree.model.Tree;

/**
 * @author DJ
 * 
 * @param <E>
 *            节点实体
 * @param <R>
 *            节点实体关系
 */
public interface TreeService<E, R> {
	/**
	 * 判断两个节点是否相同
	 */
	public boolean ifNodeEqual(E nodea, E nodeb);

	/**
	 * 判断两个节点是否有父子关联
	 */
	public boolean ifTwoNodeHasRelation(E pnode, E snode);

	/**
	 * 绑定两个的父子关系
	 */
	public boolean bindTwoNode(E pnode, E snode);

	/**
	 * 添加节点
	 */
	public E addNode(E node);

	/**
	 * 添加节点,并绑定为已有节点的子节点
	 */
	public E addBindChildrenNode(E pnode, E newnode);

	/**
	 * 添加节点,并绑定为已有节点的父节点
	 */
	public E addBindParentNode(E snode, E newnode);

	/**
	 * 将某节点sid绑定节点pid的子节点
	 */
	public boolean bindChildrenNode(E pnode, E snode);

	/**
	 * 将某节点pid绑定节点sid的父节点
	 */
	public boolean bindParentNode(E snode, E pnode);

	/**
	 * 删除父节点pid与子节点sid的绑定关系
	 */
	public void delBindChildrenNode(E pnode, E snode);

	/**
	 * 删除子节点sid与父节点pid的绑定关系
	 */
	public void delBindParentNode(E snode, E pnode);

	/**
	 * 删除节点id与所有子节点的绑定关系
	 */
	public void delBindChildrenNodes(E node);

	/**
	 * 删除节点id与所有父节点的绑定关系
	 */
	public void delBindParentNodes(E node);

	/**
	 * 删除节点id与所有父子节点的绑定关系
	 */
	public void delBindNodes(E node);

	/**
	 * 修改节点内容
	 */
	public void modifyNode(E node);

	/**
	 * 删除节点，同时删除节点与父子节点的绑定关系
	 */
	public void delNode(E node);

	/**
	 * 删除节点，同时删除所有的子节点及关联关系
	 */
	public void delNode_r(E node);

	/**
	 * 查询某一节点
	 */
	public E findNode(E node);

	/**
	 * 查询所有根节点列表
	 */
	public BaseQueryRecords<E> findRootNodes();

	/**
	 * 分页查询所有根节点列表
	 */
	public BaseQueryRecords<E> findRootNodes(int page, int rows);

	/**
	 * 查询所有类型为type的根节点列表
	 */
	public BaseQueryRecords<E> findRootNodes(int type);

	/**
	 * 分页查询所有类型为type的根节点列表
	 */
	public BaseQueryRecords<E> findRootNodes(int type, int page, int rows);

	/**
	 * 查询所有根节点列表
	 */
	public BaseQueryRecords<E> findRootNodes(String name);

	/**
	 * 分页查询所有根节点列表
	 */
	public BaseQueryRecords<E> findRootNodes(String name, int page, int rows);

	/**
	 * 查询所有类型为type的根节点列表
	 */
	public BaseQueryRecords<E> findRootNodes(String name, int type);

	/**
	 * 分页查询所有类型为type的根节点列表
	 */
	public BaseQueryRecords<E> findRootNodes(String name, int type, int page,
			int rows);

	/**
	 * 查询所有叶子节点列表
	 */
	public BaseQueryRecords<E> findLeafNodes();

	/**
	 * 分页查询所有叶子节点列表
	 */
	public BaseQueryRecords<E> findLeafNodes(int page, int rows);

	/**
	 * 查询所有类型为type的叶子节点列表
	 */
	public BaseQueryRecords<E> findLeafNodes(int type);

	/**
	 * 分页查询所有类型为type的叶子节点列表
	 */
	public BaseQueryRecords<E> findLeafNodes(int type, int page, int rows);

	/**
	 * 查询所有叶子节点列表
	 */
	public BaseQueryRecords<E> findLeafNodes(String name);

	/**
	 * 分页查询所有叶子节点列表
	 */
	public BaseQueryRecords<E> findLeafNodes(String name, int page, int rows);

	/**
	 * 查询所有类型为type的叶子节点列表
	 */
	public BaseQueryRecords<E> findLeafNodes(String name, int type);

	/**
	 * 分页查询所有类型为type的叶子节点列表
	 */
	public BaseQueryRecords<E> findLeafNodes(String name, int type, int page,
			int rows);

	/**
	 * 查询系统所有节点
	 */
	public BaseQueryRecords<E> findNodes();

	/**
	 * 分页查询系统所有节点
	 */
	public BaseQueryRecords<E> findNodes(int page, int rows);

	/**
	 * 查询系统所有类型为type的节点
	 */
	public BaseQueryRecords<E> findNodes(int type);

	/**
	 * 分页查询系统所有类型为type的节点
	 */
	public BaseQueryRecords<E> findNodes(int type, int page, int rows);

	/**
	 * 查询系统所有节点
	 */
	public BaseQueryRecords<E> findNodes(String name);

	/**
	 * 分页查询系统所有节点
	 */
	public BaseQueryRecords<E> findNodes(String name, int page, int rows);

	/**
	 * 查询系统所有类型为type的节点
	 */
	public BaseQueryRecords<E> findNodes(String name, int type);

	/**
	 * 分页查询系统所有类型为type的节点
	 */
	public BaseQueryRecords<E> findNodes(String name, int type, int page,
			int rows);

	/**
	 * 查询节点id的子节点列表，不包括子节点下的子节点
	 */
	public BaseQueryRecords<E> findChildrenNodes(E node);

	/**
	 * 分页查询节点id的子节点列表，不包括子节点下的子节点
	 */
	public BaseQueryRecords<E> findChildrenNodes(E node, int page, int rows);

	/**
	 * 查询节点id的子节点中类型为type的所有子节点，不包括子节点下的子节点
	 */
	public BaseQueryRecords<E> findChildrenNodes(E node, int type);

	/**
	 * 分页查询节点id的子节点中类型为type的所有子节点，不包括子节点下的子节点
	 */
	public BaseQueryRecords<E> findChildrenNodes(E node, int type, int page,
			int rows);

	/**
	 * 查询节点id的子节点中名字模糊匹配name的列表，不包括子节点下的子节点
	 */
	public BaseQueryRecords<E> findChildrenNodes(E node, String name);

	/**
	 * 查询节点id的子节点中名字模糊匹配name的列表，不包括子节点下的子节点
	 */
	public BaseQueryRecords<E> findChildrenNodes(E node, String name, int type);

	/**
	 * 查询节点id的子节点中名字模糊匹配name的列表，不包括子节点下的子节点
	 */
	public BaseQueryRecords<E> findChildrenNodes(E node, String name, int type,
			int page, int rows);

	/**
	 * 查询节点id的子节点中名字模糊匹配name的列表，不包括子节点下的子节点
	 */
	public BaseQueryRecords<E> findChildrenNodes(E node, String name, int page,
			int rows);

	/**
	 * 查找节点的父节点
	 */
	public BaseQueryRecords<E> findParentNodes(E node);

	/**
	 * 递归查询节点id的子节点列表，包括子节点下的子节点，无法作分页
	 */
	public List<E> findChildrenNodes_r(E node);

	/**
	 * 递归查询节点id的子节点中类型为type的所有子节点，包括子节点下的子节点，无法作分页
	 */
	public List<E> findChildrenNodes_r(E node, int type);

	/**
	 * 递归查询节点id的子节点中名字模糊匹配name的列表，包括子节点下的子节点，无法作分页
	 */
	public List<E> findChildrenNodes_r(E node, String name);

	/**
	 * 递归查询节点id的子节点中类型为type的所有子节点中名字模糊匹配name的，包括子节点下的子节点，无法作分页
	 */
	public List<E> findChildrenNodes_r(E node, String name, int type);

	/**
	 * 查询节点id对应的子树, lv = -1 搜索到叶子节点， lv=0 返回该节点， lv=1返回该节点与子节点，lv=2.....
	 * 
	 * lv 查询层次
	 */
	public Tree<E> findNodeTree(E node, int lv);

	/**
	 * 判断节点是否为根节点
	 */
	public boolean ifNodeRoot(E node);

	/**
	 * 判断节点是否为叶子节点
	 */
	public boolean ifNodeLeaf(E node);
}
