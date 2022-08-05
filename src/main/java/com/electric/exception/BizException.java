package com.electric.exception;

/**
 * 业务异常类
 * 
 * @author: admin
 * @history:
 */
public class BizException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BizException(String msg) {
		super(msg);
	}

	public BizException(String msg, Throwable ex) {
		super(msg, ex);
	}

	public BizException(String errorCode, String errorMessage, Object... args) {
		super(errorCode, errorMessage, args);
	}

	public BizException(String errorCode, String errorMessage, Throwable cause, Object... args) {
		super(errorCode, errorMessage, cause, args);
	}

	@Override
	public void setAnswerUrl(String answerUrl) {
		super.setAnswerUrl(answerUrl);
	}

	@Override
	public String getAnswerUrl() {
		return super.getAnswerUrl();
	}

}
