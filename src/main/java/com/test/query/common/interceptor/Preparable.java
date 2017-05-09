package com.test.query.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
/**
 * preparable接口
 */
public interface Preparable {

	boolean prepare(HttpServletRequest request,
                    HttpServletResponse response);
	void postHandle(HttpServletRequest request,
                    HttpServletResponse response, Object handler, ModelAndView modelAndView);
	void afterCompletion(HttpServletRequest request,
                         HttpServletResponse response, Object handler, Exception ex);
}
