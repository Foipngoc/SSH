package com.common.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.common.dao.BaseExpression;

/**
 * HQL语句适配器
 * 
 * @author DongJun
 * 
 */
public class HQL implements BaseExpression {
	private String hql = null;
	private List<Object> mparams = null;

	/**
	 * 适配带可变参数的hql语句,参数用?通配符替换
	 * 
	 * @param hql
	 * @param params
	 */
	public HQL(String hql, Object... params) {
		this.hql = hql;
		this.mparams = new ArrayList<>();
		for (int i = 0; i < params.length; i++) {
			mparams.add(params[i]);
		}
	}

	/**
	 * 转换成hql语句
	 */
	@Override
	public String toString() {
		String ret = "";
		if (hql != null) {
			ret = hql;
			if (mparams != null) {
				// 替换通配符
				for (int i = 0; i < mparams.size(); i++) {
					Object val = mparams.get(i);
					if (val != null)
						ret = ret.replaceFirst("\\?", val.toString());
				}
			}
		}
		return ret;
	}
}
