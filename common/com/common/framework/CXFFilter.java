package com.common.framework;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;

/**
 * CXF webservice 过滤类
 * 
 * 所有的url会被struts自动捕获，为了webservices能够访问，struts过滤器需要重写以自动pass掉webservice的请求；
 * 从而webservice过滤器能够捕获webservice请求。
 * 
 * @author DJ
 * 
 */
public class CXFFilter extends StrutsPrepareAndExecuteFilter {
	/**
	 * 项目上下文路径 如: "d:\tomcat\webapps\SSH" 注意 路径没有右\
	 * 
	 * http请求可以获得原始的httprequest从而获得项目上下文；
	 * 然而在webservice中没法获得原始的httprequest，所以在过滤器中获得此上下文；
	 */
	public static String contextPath = "";

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		contextPath = request.getSession().getServletContext().getRealPath("");
		if (request.getRequestURI().contains("webservice")) {
			// 可以直接放行的路径即使用自定义拦截器的路径
			chain.doFilter(req, res);
		} else { // 使用默认拦截器的路径
			super.doFilter(req, res, chain);
		}
	}
}
