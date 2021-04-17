package com.electric.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.apache.commons.lang.StringUtils;

import com.electric.enums.BaseExceptionEnum;
import com.electric.exception.BizException;
import com.electric.exception.ValidationException;
import com.electric.exception.ValidationResult;

/**
 * 参数校验工具类
 *
 * @author: luochao
 * @since: 2016年7月4日 下午2:42:21
 * @history:
 */
public class ValidationUtil {

	// 初始化validator实例
	private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	/**
	 * 判断参数不为空，为空时抛出参数错误异常
	 * 
	 * @param object
	 * @create 2017年4月1日 上午9:44:48 panweiqiang
	 * @history
	 */
	public static void assertNotNull(Object object) {
		doAssert(object != null, BaseExceptionEnum.PARAM_ERROR);
	}

	/**
	 * 
	 * assertNotBlank:判断String类型参数不为空，为空时抛出参数错误异常.
	 * 
	 * @author wangyibin
	 * @date 2017年12月25日 下午3:25:23
	 * @param str
	 */
	public static void assertNotBlank(String str) {
		if (StringUtils.isBlank(str)) {
			throw new BizException(BaseExceptionEnum.PARAM_ERROR.getCode(), BaseExceptionEnum.PARAM_ERROR.getMsg());
		}
	}

	/**
	 * 对象校验 此方法对加了注解的属性进行校验
	 * 
	 * @param obj 被校验对象
	 * @return ValidationResult
	 * @history
	 */
	public static <T> ValidationResult validateEntity(T obj) {
		return validateEntity(obj, Default.class);

	}

	/**
	 * 对象校验 此方法对加入group的参数进行校验
	 * 
	 * @param obj 被校验对象
	 * @return ValidationResult
	 * @history
	 */
	public static <T> ValidationResult validateEntity(T obj, Class<?>... cls) {
		ValidationResult result = new ValidationResult();
		Set<ConstraintViolation<T>> set = validator.validate(obj, cls);
		assembly(result, set);
		return result;
	}

	/**
	 * 对象中指定参数校验
	 *
	 * @param obj          被校验对象
	 * @param propertyName 校验参数
	 * @return ValidationResult
	 * @history
	 */
	public static <T> ValidationResult validateProperty(T obj, String propertyName) {
		ValidationResult result = new ValidationResult();
		Set<ConstraintViolation<T>> set = validator.validateProperty(obj, propertyName, Default.class);
		assembly(result, set);
		return result;
	}

	/**
	 * 对象校验,抛出校验异常 此方法对加了注解的属性进行校验
	 * 
	 * @param obj 被校验对象
	 * @throws ValidationException
	 * @history
	 */
	public static <T> void validateEntityThrows(T obj) throws ValidationException {
		ValidationResult result = validateEntity(obj, Default.class);
		throwParamException(result);
	}

	/**
	 * 对象校验，抛出异常 此方法对加入group的参数进行校验
	 * 
	 * @param obj 被校验对象
	 * @history
	 */
	public static <T> void validateEntityThrows(T obj, Class<?>... cls) throws ValidationException {
		ValidationResult result = new ValidationResult();
		Set<ConstraintViolation<T>> set = validator.validate(obj, cls);
		assembly(result, set);
		throwParamException(result);
	}

	/**
	 * 对象中指定参数校验，抛出异常
	 *
	 * @param obj          被校验对象
	 * @param propertyName 校验参数
	 * @history
	 */
	public static <T> void validatePropertyThrows(T obj, String propertyName) throws ValidationException {
		ValidationResult result = new ValidationResult();
		Set<ConstraintViolation<T>> set = validator.validateProperty(obj, propertyName, Default.class);
		assembly(result, set);
		throwParamException(result);
	}

	/**
	 * 参数组装
	 *
	 * @param result 返回对象
	 * @param set    校验结果
	 * @history
	 */
	private static <T> void assembly(ValidationResult result, Set<ConstraintViolation<T>> set) {
		if (set == null || set.isEmpty()) {
			return;
		}
		result.setHasErrors(true);
		Map<String, String> errorMsg = new HashMap<String, String>();
		for (ConstraintViolation<T> cv : set) {
			errorMsg.put(cv.getPropertyPath().toString(), cv.getMessage());
		}
		result.setErrorMsg(errorMsg);
	}

	/**
	 * 抛出参数异常
	 *
	 * @param result 校验类
	 * @throws ValidationException
	 * @history
	 */
	private static void throwParamException(ValidationResult result) throws ValidationException {
		if (result.isHasErrors()) {
			throw new ValidationException(BaseExceptionEnum.PARAM_ERROR.getCode(), result.getMessage());
		}
	}

	/**
	 * 校验失败时抛异常
	 * 
	 * @param result
	 * @param baseExceptionEnum
	 * @create 2017年4月1日 上午9:44:06 panweiqiang
	 * @history
	 */
	private static void doAssert(Boolean result, BaseExceptionEnum baseExceptionEnum) {
		if (!result) {
			throw new ValidationException(baseExceptionEnum.getCode(), baseExceptionEnum.getMsg());
		}
	}
}
