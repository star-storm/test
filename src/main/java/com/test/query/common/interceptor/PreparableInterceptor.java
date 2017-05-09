package com.test.query.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
/**
 * prepare接口实现
 */
public class PreparableInterceptor implements HandlerInterceptor {
	
	public static final String HTML_CACHE_CONTENT_TYPE = "text/html;charset=UTF-8";

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			if (handlerMethod.getBean() instanceof Preparable) {
				return ((Preparable) handlerMethod.getBean()).prepare(request, response); // 调用prepare
			}
		}
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			if (handlerMethod.getBean() instanceof Preparable) {
				if (modelAndView != null) {
					modelAndView.addObject("request", request);
				}
				((Preparable) handlerMethod.getBean()).postHandle(request,response,handler,modelAndView);
			}
		}
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			if (handlerMethod.getBean() instanceof Preparable) {
				((Preparable) handlerMethod.getBean()).afterCompletion(request, response, handler, ex); // 调用afterCompletion
			}
		}
	}

}
