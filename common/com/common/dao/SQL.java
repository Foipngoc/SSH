package com.common.dao;

import java.util.ArrayList;
import java.util.List;

/**
 * SQL适配器
 * 
 * @author DongJun
 * 
 */
public class SQL implements BaseExpression {
	private String sql = null;
	private List<Object> mparams = null;

	/**
	 * 适配不带参数列表的sql语句
	 * 
	 * @param sql
	 */
	public SQL(String sql) {
		this.sql = sql;
	}

	/**
	 * 适配带list列表参数的sql语句,参数用?通配符替换
	 * 
	 * @param sql
	 * @param params
	 */
	public SQL(String sql, List<Object> params) {
		this.sql = sql;
		this.mparams = params;
	}

	/**
	 * 适配带可变参数的sql语句,参数用?通配符替换
	 * 
	 * @param sql
	 * @param params
	 */
	public SQL(String sql, Object... params) {
		this.sql = sql;
		this.mparams = new ArrayList<>();
		for (int i = 0; i < params.length; i++) {
			mparams.add(params[i]);
		}
	}

	/**
	 * 转换成sql语句
	 */
	@Override
	public String toString() {
		String ret = "";
		if (sql != null) {
			ret = sql;
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
