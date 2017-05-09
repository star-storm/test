package com.test.query.common.exception.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.test.query.common.exception.BusinessException;
/** 
 * @Description 统一异常处理类
 * @ClassName TrafficExceptionHandler
 * @author  xiahaokai 
 * @createDate 2015年6月17日 上午9:13:20  
 */
public class TrafficExceptionHandler implements HandlerExceptionResolver {
	
	private static final Log logger = LogFactory.getLog(TrafficExceptionHandler.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		ModelAndView modelAndView = new ModelAndView();
		logger.error("您的程序出错了 ---》 " + ex,ex);
		StringBuffer sBuf = getTraceInfo(ex);
		logger.error(sBuf.toString(),ex);
		ex.printStackTrace();
		if (ex instanceof BusinessException) {
			BusinessException businessException = (BusinessException) ex;
			if (StringUtils.isNotBlank(businessException.getViewName())) {
				modelAndView.setViewName(businessException.getViewName());
			} 
		}
		modelAndView.setViewName("common/bussiness_exception");
		modelAndView.addObject("errMsg", ex.getMessage());	
		return modelAndView;
	}
	
	private StringBuffer getTraceInfo(Exception ex) {  
		StringBuffer sb = new StringBuffer("异常定位---》");  
		StackTraceElement[] stacks = ex.getStackTrace();  
		sb.append(stacks[0].getClassName()+".").append(stacks[0].getMethodName())  
	    .append("()，第 ").append(stacks[0].getLineNumber()+"行！");  	
		return sb;
	}  
	
	 
}
