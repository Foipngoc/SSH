package com.common.utils;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;

public class CXFFilter extends StrutsPrepareAndExecuteFilter {
	public static String contextPath = "";
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		if (request.getRequestURI().contains("webservice")) {
			contextPath = request.getSession().getServletContext().getRealPath("");
			// 可以直接放行的路径即使用自定义拦截器的路径
			chain.doFilter(req, res);
		} else { // 使用默认拦截器的路径
			super.doFilter(req, res, chain);
		}
	}
}
