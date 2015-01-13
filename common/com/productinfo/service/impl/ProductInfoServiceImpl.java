package com.productinfo.service.impl;

import org.springframework.stereotype.Service;

import com.common.utils.PropertyLoader;
import com.productinfo.service.ProductInfoService;

@Service("productInfoService")
public class ProductInfoServiceImpl implements ProductInfoService {

	/**
	 * 从文档productinfo.properties获取产品信息
	 */
	@Override
	public String getProductInfo(String infoname) {
		return PropertyLoader.getProperty("productinfo.properties", infoname);
	}

}
