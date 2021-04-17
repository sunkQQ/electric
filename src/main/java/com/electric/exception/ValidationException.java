package com.electric.exception;

/**
 * 校验异常类
 * 
 * @author: luochao
 * @since: 2016年7月4日 下午6:02:00
 * @history:
 */
public class ValidationException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ValidationException(String msg) {
		super(msg);
	}

	public ValidationException(String msg, Throwable ex) {
		super(msg, ex);
	}

	public ValidationException(String errorCode, String errorMessage, Object... args) {
		super(errorCode, errorMessage, args);
	}

	public ValidationException(String errorCode, String errorMessage, Throwable cause, Object... args) {
		super(errorCode, errorMessage, cause, args);
	}

}
