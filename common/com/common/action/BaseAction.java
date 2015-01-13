package com.common.action;

import java.util.Map;

import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 * BaseAction 提供访问用户action的通用类。
 * 
 * 支持session,request,application的使用； 支持session对象中变量的存取； 支持通用结果集的返回；
 * 
 * @author DongJun
 * 
 */
public class BaseAction extends ActionSupport implements SessionAware,
		RequestAware, ApplicationAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3903080751207945592L;
	protected Map<String, Object> request; // request对象
	protected Map<String, Object> session; // session对象
	protected Map<String, Object> application; // application对象
	protected BaseResult result; //通用结果返回

	@Override
	public void setApplication(Map<String, Object> application) {
		this.application = application;
	}

	@Override
	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public BaseResult getResult() {
		return result;
	}

	public void setResult(BaseResult result) {
		this.result = result;
	}

	/**
	 * 获取session中的值
	 * 
	 * @param key
	 * @return
	 */
	public Object getSessionParamObj(String key) {
		return this.session.get(key);
	}

	/**
	 * 获取session中的值
	 * 
	 * @param key
	 * @param defaultval
	 * @return
	 */
	public String getSessionParamString(String key, String defaultval) {
		Object object = this.session.get(key);
		if (object != null)
			return (String) object;
		else
			return defaultval;
	}

	/**
	 * 获取session中的值
	 * 
	 * @param key
	 * @param defaultval
	 * @return
	 */
	public int getSessionParamInteger(String key, int defaultval) {
		Object object = this.session.get(key);
		if (object != null)
			return (Integer) object;
		else
			return defaultval;
	}

	/**
	 * 设置session中的值
	 * 
	 * @param key
	 * @param value
	 */
	public void setSessionParam(String key, Object value) {
		this.session.put(key, value);
	}

	/**
	 * 设置session中的值
	 * 
	 * @param key
	 */
	public void removeSessionParam(String key) {
		this.session.remove(key);
	}
}
