package com.test.query.common.exception;
/**
 * @description 自定义业务异常
 * @className BusinessException
 * @author xiahaokai
 * @createDate 2015年6月17日  上午9:35:15
 */
public class BusinessException extends RuntimeException {
	
	private static final long serialVersionUID = -6446898666757755578L;
	
	private String viewName;
	
	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public BusinessException() {
		super();
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String viewName, String message) {
		super(message);
		this.viewName = viewName;
	}

	public BusinessException(Throwable throwable) {
		super(throwable);
	}

	public BusinessException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
