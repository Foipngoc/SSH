package com.productinfo.service;

public interface ProductInfoService {

	/**
	 * 从文档productinfo.properties获取产品信息
	 * @param infoname
	 * @return
	 */
	public String getProductInfo(String infoname);
}
