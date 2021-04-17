package com.electric.exception;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.electric.controller.common.ExceptionAdvice;

/**
 * 项目异常基类
 * 
 * @author: luochao
 * @since: 2016年7月4日 下午7:05:09
 * @history:
 */
public class BaseException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

	/** 错误编码 */
	private String errorCode;

	/** 错误信息 */
	private String errorMessage;

	/** 异常解决方案url地址 */
	private String answerUrl;

	/**
	 * 错误参数,使用<code>String.format()</code>的方式替换errorMessage中的特殊字符
	 * <p>
	 * %s:字符串
	 * </p>
	 * <p>
	 * %b:布尔类型，如true
	 * </p>
	 * <p>
	 * %d:整数类型(十进制)
	 * </p>
	 * <p>
	 * %f:浮点类型
	 * </p>
	 * <p>
	 * %tF%n:年-月-日格式日期
	 * </p>
	 * <p>
	 * </p>
	 */
	private Object[] args;

	/**
	 * 构造方法
	 * 
	 * @param msg 异常信息
	 */
	public BaseException(String msg) {
		this.errorMessage = msg;
	}

	/**
	 * 构造方法
	 * 
	 * @param msg 异常信息
	 * @param ex  Throwable
	 */
	public BaseException(String msg, Throwable ex) {
		super(ex);
		this.errorMessage = msg;
	}

	/**
	 * 构造方法
	 * 
	 * @param msg       消息
	 * @param answerUrl 异常解决方案地址
	 */
	public BaseException(String msg, String answerUrl) {
		this.errorMessage = msg;
		this.answerUrl = answerUrl;
	}

	/**
	 * 构造方法
	 * 
	 * @param errorCode 异常编码
	 * @param msg       消息
	 * @param answerUrl 异常解决方案地址
	 */
	public BaseException(String errorCode, String msg, String answerUrl) {
		this.errorMessage = msg;
		this.errorCode = errorCode;
		this.answerUrl = answerUrl;
	}

	/**
	 * 构造方法
	 * 
	 * @param msg       异常信息
	 * @param answerUrl 异常解决方案地址
	 * @param ex        Throwable
	 */
	public BaseException(String msg, String answerUrl, Throwable ex) {
		super(ex);
		this.errorMessage = msg;
		this.answerUrl = answerUrl;
	}

	/**
	 * 构造方法
	 * 
	 * @param errorCode    错误码
	 * @param errorMessage 错误信息
	 * @param args         参数
	 */
	public BaseException(String errorCode, String errorMessage, Object... args) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.args = args;
	}

	/**
	 * 构造方法
	 * 
	 * @param errorCode    错误码
	 * @param errorMessage 错误信息
	 * @param ex           Throwable
	 * @param args         参数
	 */
	public BaseException(String errorCode, String errorMessage, Throwable ex, Object... args) {
		super(ex);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.args = args;
	}

	/**
	 * 获取消息
	 */
	public String getMessage() {
		String msg = "";
		if (StringUtils.isEmpty(errorMessage)) {
			return msg;
		}
		if (args == null || args.length == 0) {
			return errorMessage;
		}
		try {
			msg = String.format(errorMessage, args);
		} catch (Exception e) {
			logger.error("转换异常,errorMessage-->" + errorMessage + "|args" + args, e);
		}
		return msg;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getAnswerUrl() {
		return answerUrl;
	}

	public void setAnswerUrl(String answerUrl) {
		this.answerUrl = answerUrl;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}

}
