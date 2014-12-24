package com.productinfo.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.common.action.BaseAction;
import com.productinfo.service.ProductInfoService;

/**
 * 获取版本信息
 * 
 * @author DongJun
 * 
 */
@Controller
@Scope("prototype")
public class ProductInfoAction extends BaseAction {
	private static final long serialVersionUID = 8124824578798111195L;
	@Resource
	private ProductInfoService productInfoService;
	private String versionCode;
	private String versionName;
	private String productName;
	private String copyRight;
	private String companyName;

	public String getProductInfo() {
		// 版本名
		versionName = productInfoService.getVersionName();

		// 版本代码
		versionCode = productInfoService.getVersionCode();

		// 产品名
		productName = productInfoService.getProductName();

		// 版权
		copyRight = productInfoService.getCopyRight();

		// 公司名
		companyName = productInfoService.getCompanyName();

		return SUCCESS;
	}

	public String getProductName() {
		return productName;
	}

	public String getCopyRight() {
		return copyRight;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public String getVersionName() {
		return versionName;
	}
}
