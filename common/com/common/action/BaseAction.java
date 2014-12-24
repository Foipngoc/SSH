package com.common.action;

import java.util.Map;

import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 * BaseAction
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
	protected Map<String, Object> request;
	protected Map<String, Object> session;
	protected Map<String, Object> application;

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
}
