package com.productinfo.service;

public interface ProductInfoService {
	/**
	 * 获得版本名
	 * @return
	 */
	public String getVersionName();

	/**
	 * 获取版本代码
	 * @return
	 */
	public String getVersionCode();
	
	/**
	 * 获得产品名
	 * @return
	 */
	public String getProductName();
	
	/**
	 * 获得版权
	 * @return
	 */
	public String getCopyRight();
	
	/**
	 * 获得公司名
	 * @return
	 */
	public String getCompanyName();
}
