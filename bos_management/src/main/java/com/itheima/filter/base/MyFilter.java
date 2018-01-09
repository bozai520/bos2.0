package com.itheima.filter.base;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;

public class MyFilter extends StrutsPrepareAndExecuteFilter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		
		//判断该请求是请求服务还是请求页面
		HttpServletRequest request = (HttpServletRequest) req;
		
		String uri = request.getRequestURI();
		
		//请求路径
		System.out.println("我当前的请求路径是：：：："+uri);
		String regex = "/services/";
		if(uri.contains(regex)){
			chain.doFilter(req, res);
		}else{
			super.doFilter(req, res, chain);
		}
	}
}
