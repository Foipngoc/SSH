package com.productinfo.service.impl;

import org.springframework.stereotype.Service;

import com.common.utils.PropertyLoader;
import com.productinfo.service.ProductInfoService;

@Service("productInfoService")
public class ProductInfoServiceImpl implements ProductInfoService{

	@Override
	public String getVersionName() {
		return PropertyLoader.getProperty("productinfo.properties","product.versionName");
	}

	@Override
	public String getVersionCode() {
		return PropertyLoader.getProperty("productinfo.properties","product.versionCode");
	}

	@Override
	public String getProductName() {
		return PropertyLoader.getProperty("productinfo.properties","product.productName");
	}

	@Override
	public String getCopyRight() {
		return PropertyLoader.getProperty("productinfo.properties","product.companyName");
	}

	@Override
	public String getCompanyName() {
		return PropertyLoader.getProperty("productinfo.properties","product.copyRight");
	}
	
}
