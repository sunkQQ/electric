package com.electric.exception;

import java.util.Map;

/**
 * 校验结果类
 *
 * @author: luochao
 * @since: 2016年7月4日 下午2:43:22
 * @history:
 */
public class ValidationResult {

	// 校验结果是否有错，默认校验通过
	private boolean hasErrors = false;

	// 校验错误信息
	private Map<String, String> errorMsg;

	public boolean isHasErrors() {
		return hasErrors;
	}

	public void setHasErrors(boolean hasErrors) {
		this.hasErrors = hasErrors;
	}

	public Map<String, String> getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(Map<String, String> errorMsg) {
		this.errorMsg = errorMsg;
	}

	/**
	 * getMessage:(这里用一句话描述这个方法的作用).
	 * 
	 * @author luochao
	 * @date 2017年5月22日 下午2:21:57
	 * @return
	 */
	public String getMessage() {
		if (errorMsg == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, String> entry : errorMsg.entrySet()) {
			sb.append(entry.getValue());
			// sb.append(";");
			// PANWEIQIANG 只提示第一个错误
			break;
		}
		return sb.toString();
	}

	@Override
	public String toString() {
		return "ValidationResult [hasErrors=" + hasErrors + ", errorMsg=" + errorMsg + "]";
	}

}
