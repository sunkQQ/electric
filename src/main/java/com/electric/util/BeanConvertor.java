package com.electric.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.alibaba.fastjson.JSON;

/**
 * Bean转化类
 * 
 * @author:
 * @since: 2016年7月7日 上午10:38:44
 * @history:
 */
public class BeanConvertor {

	private static final Logger LOGGER = LoggerFactory.getLogger(BeanConvertor.class);

	public static final String CONVERT_ERROR = "类型转换失败";

	/**
	 * 对象转换(org.springframework.beans.BeanUtils的封装),优先使用，性能优。
	 * 注:不同类型无法转换；无法与map相互转换，请使用mapToObject
	 * 
	 * @param source           原对象
	 * @param target           目标对象
	 * @param ignoreProperties 排除要copy的属性
	 * @return
	 * @create 2016年7月18日 上午11:14:35 luochao
	 * @history
	 */
	public static <T> T copy(Object source, Class<T> target, String... ignoreProperties) {
		if (source == null) {
			return null;
		}
		try {
			T targetInstance = target.newInstance();
			BeanUtils.copyProperties(source, targetInstance, ignoreProperties);
			return targetInstance;
		} catch (Exception e) {
			LOGGER.error(CONVERT_ERROR, e);
			throw new RuntimeException(CONVERT_ERROR);
		}
	}

	/**
	 * 对象集合转换(org.springframework.beans.BeanUtils的封装),优先使用，性能优。
	 * 注:不同类型无法转换;无法与map相互转换,请使用mapListToObjectList
	 * 
	 * @param list             原数据
	 * @param target           目标对象
	 * @param ignoreProperties 排除要copy的属性
	 * @return
	 * @create 2016年7月18日 上午11:15:42 luochao
	 * @history
	 */
	public static <T, E> List<T> copyList(List<E> list, Class<T> target, String... ignoreProperties) {
		if (CollectionUtils.isEmpty(list)) {
			return new ArrayList<T>();
		}
		List<T> targetList = new ArrayList<T>(list.size());
		for (E e : list) {
			targetList.add(copy(e, target, ignoreProperties));
		}
		return targetList;
	}

	/**
	 * map转对象(org.apache.commons.beanutils.BeanUtils的封装)
	 *
	 * @param <T>
	 * @param map
	 * @param target
	 * @return
	 * @create 2020年6月28日 下午8:26:04 luochao
	 * @history
	 */
	public static <T> T mapToObject(Map<String, Object> map, Class<T> target) {
		if (map == null) {
			return null;
		}
		try {
			T targetInstance = target.newInstance();
			org.apache.commons.beanutils.BeanUtils.populate(targetInstance, map);
			return targetInstance;
		} catch (Exception e) {
			LOGGER.error(CONVERT_ERROR, e);
			throw new RuntimeException(CONVERT_ERROR);
		}
	}

	/**
	 * map集合转对象集合(org.apache.commons.beanutils.BeanUtils的封装)
	 *
	 * @param <T>
	 * @param selectList
	 * @param clazz
	 * @return
	 * @create 2020年6月28日 下午8:44:31 luochao
	 * @history
	 */
	public static <T> List<T> mapListToObjectList(List<Map<String, Object>> selectList, Class<T> clazz) {
		if (CollectionUtils.isEmpty(selectList)) {
			return new ArrayList<T>();
		}
		List<T> tList = new ArrayList<T>(selectList.size());
		for (Map<String, Object> map : selectList) {
			tList.add(mapToObject(map, clazz));
		}
		return tList;
	}

	/**
	 * 对象转map(org.apache.commons.beanutils.BeanUtils.describe)
	 *
	 * @param obj
	 * @return
	 * @history
	 */
	public static Map<String, String> objectToMap(Object obj) {
		try {
			Map<String, String> describe = org.apache.commons.beanutils.BeanUtils.describe(obj);
			return describe;
		} catch (Exception e) {
			LOGGER.error(CONVERT_ERROR, e);
			throw new RuntimeException(CONVERT_ERROR);
		}
	}

	/**
	 * 
	 * 对象转map,目前使用场景:数据库listParams查询 注：此方法无法获取到父类参数
	 * 
	 * @author luochao
	 * @date 2017年8月10日 上午9:38:30
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> objectToMap2(Object obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Field[] fields = obj.getClass().getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				map.put(field.getName(), field.get(obj));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return map;
	}

	/***
	 * 反射的方式进行对象转换(包含父类),static修饰不转换，一般用在云马加解密,其它场景请阅读代码看是否符合要求
	 * 注意：对返回值进行了toString处理,所有返回值必须是Map<String, String>
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 * @create 2018年5月3日 下午1:14:58 luochao
	 * @history
	 */
	public static Map<String, String> objectToMap3(Object obj) {
		try {
			Class<? extends Object> cls = obj.getClass();
			List<Field> fieldList = new ArrayList<>();
			while (cls != null) {// 当父类为null的时候说明到达了最上层的父类(Object类).
				fieldList.addAll(Arrays.asList(cls.getDeclaredFields()));
				cls = cls.getSuperclass(); // 得到父类,然后赋给自己
			}
			Map<String, String> map = new HashMap<String, String>();
			for (Field field : fieldList) {
				if (Modifier.isStatic(field.getModifiers())) {
					continue;
				} else {
					field.setAccessible(true);
					if (field.get(obj) != null) {
						map.put(field.getName(), field.get(obj).toString());
					}
				}
			}
			return map;
		} catch (Exception e) {
			LOGGER.error(CONVERT_ERROR, e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 功能说明：拷贝第一个参数的非空值到第二个参数
	 * 
	 * @param source
	 * @param target
	 */
	public static void copyNonNull(Object source, Object target) {
		if (source == null || target == null) {
			return;
		}
		String[] ignoreProperties = getNullPropertyNames(source);
		BeanUtils.copyProperties(source, target, ignoreProperties);
	}

	/**
	 * 功能说明：获取为空的属性
	 * 
	 * @param source
	 * @return String[]
	 */
	public static String[] getNullPropertyNames(Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
		Set<String> emptyNames = new HashSet<String>();
		for (java.beans.PropertyDescriptor pd : pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null) {
				emptyNames.add(pd.getName());
			}
		}
		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}

	/**
	 * 对象转byte数组，注意对象必须序列化
	 *
	 * @param <T>
	 * @param obj
	 * @return
	 * @create 2019年12月25日 下午4:53:38 luochao
	 * @history
	 */
	public static <T> Optional<byte[]> objectToBytes(T obj) {
		if (obj instanceof Serializable) {
			byte[] bytes = null;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			try {
				ObjectOutputStream sOut = new ObjectOutputStream(out);
				sOut.writeObject(obj);
				sOut.flush();
				bytes = out.toByteArray();
			} catch (IOException e) {
				LOGGER.error(CONVERT_ERROR, e);
			}
			return Optional.ofNullable(bytes);
		} else {
			throw new RuntimeException(obj.getClass() + "未序列化");
		}

	}

	/**
	 * 通用转换，支持不同类型之间，深层次copy，通过fastjson实现，性能不高请优先使用copy,copyList,mapToObject,mapListToObjectList,objectToMap
	 * 注意：忽略某些字段不复制是通过注解实现，具体请查看fastjson的api
	 * 
	 * @param <T>
	 * @param object
	 * @param entityClass
	 * @return
	 * @create 2020年6月29日 上午10:43:55 luochao
	 * @history
	 */
	public static <T> T commonCopy(Object object, Class<T> entityClass) {
		if (object == null) {
			return null;
		}
		return JSON.parseObject(JSON.toJSONString(object), entityClass);
	}

	/**
	 * 通用转换，支持不同类型之间，深层次copy，通过fastjson实现，性能不高请优先使用copy,copyList,mapToObject,mapListToObjectList,objectToMap
	 * 注意：忽略某些字段不复制是通过注解实现，具体请查看fastjson的api
	 * 
	 * @param <T>
	 * @param objList
	 * @param entityClass
	 * @return
	 * @create 2020年6月29日 上午10:53:16 luochao
	 * @history
	 */
	public static <T> List<T> commonListCopy(List<?> objList, Class<T> entityClass) {
		if (CollectionUtils.isEmpty(objList)) {
			return new ArrayList<T>();
		}
		List<T> resultList = new ArrayList<T>();
		for (Object obj : objList) {
			resultList.add(commonCopy(obj, entityClass));
		}
		return resultList;
	}

}
