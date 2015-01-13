package com.common.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据返回结果集，常用于json返回或service层与action层数据交换
 * 
 * @author DongJun
 * 
 */
public class BaseResult {
	private int resultcode;// 常用于返回操作结果码
	private String resultdesc;// 常用于返回操作结果描述

	private Map<String, Object> map = null;// 返回自定义数据
	private Object obj = null;// 返回自定义数据
	private Object[] objs = null;// 返回自定义数据
	private List<Object> list = null;// 返回自定义数据

	/**
	 * 默认构造
	 */
	public BaseResult() {
	}

	/**
	 * 带结果码与结果描述构造函数
	 * 
	 * @param code
	 * @param desc
	 */
	public BaseResult(int code, String desc) {
		this.resultcode = code;
		this.resultdesc = desc;
	}

	/**
	 * 获取结果码
	 * 
	 * @return
	 */
	public int getResultcode() {
		return resultcode;
	}

	/**
	 * 设置结果码
	 * 
	 * @param resultcode
	 */
	public void setResultcode(int resultcode) {
		this.resultcode = resultcode;
	}

	/**
	 * 获得结果描述
	 * 
	 * @return
	 */
	public String getResultdesc() {
		return resultdesc;
	}

	/**
	 * 设置结果描述
	 * 
	 * @param resultdesc
	 */
	public void setResultdesc(String resultdesc) {
		this.resultdesc = resultdesc;
	}

	/**
	 * 获得自定义map数据
	 * 
	 * @return
	 */
	public Map<String, Object> getMap() {
		return this.map;
	}

	/**
	 * 设置自定义map数据
	 * 
	 * @param map
	 */
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	/**
	 * 添加自定义数据到map中
	 * 
	 * @param key
	 * @param val
	 */
	public void addToMap(String key, Object val) {
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		map.put(key, val);
	}

	/**
	 * 从map中获得自定义数据
	 * 
	 * @param key
	 * @return
	 */
	public Object getFromMap(String key) {
		if (map != null) {
			return map.get(key);
		}
		return null;
	}

	/**
	 * 获得自定义对象
	 * 
	 * @return
	 */
	public Object getObj() {
		return obj;
	}

	/**
	 * 设置自定义对象
	 * 
	 * @param obj
	 */
	public void setObj(Object obj) {
		this.obj = obj;
	}

	/**
	 * 获得自定义对象数组
	 * 
	 * @return
	 */
	public Object[] getObjs() {
		return objs;
	}

	/**
	 * 设置自定义对象数组
	 * 
	 * @param objs
	 */
	public void setObjs(Object[] objs) {
		this.objs = objs;
	}

	/**
	 * 获得自定义列表list
	 * 
	 * @return
	 */
	public List<Object> getList() {
		return list;
	}

	/**
	 * 设置自定义列表
	 * 
	 * @param list
	 */
	public void setList(List<Object> list) {
		this.list = list;
	}

	/**
	 * 添加自定义对象到列表中
	 * 
	 * @param obj
	 */
	public void addToList(Object obj) {
		if (this.list == null)
			this.list = new ArrayList<>();

		this.list.add(obj);
	}

	/**
	 * 从列表中获得自定义对象
	 * 
	 * @param index
	 * @return
	 */
	public Object getFromList(int index) {
		if (this.list == null) {
			return null;
		}
		try {
			return this.list.get(index);
		} catch (Exception e) {
			return null;
		}
	}
}
