package com.electric.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import com.electric.model.constant.Numbers;
import com.electric.model.constant.StringConstant;
import com.google.common.base.CaseFormat;

import lombok.extern.slf4j.Slf4j;

/**
 * 反射工具类
 *
 * @author sunk
 * @date 2024/06/05
 */
@Slf4j
public class ReflectionUtil {

    /** 方法缓存 */
    private static Map<String, Method> METHOD_MAP = new ConcurrentHashMap<>();

    /**
     * 获取对象obj名称为fieldName的Field
     *
     * @param obj
     * @param fieldName
     * @return
     * @create  2020年6月23日 下午7:58:31 luochao
     * @history
     */
    public static Field getFieldByFieldName(Object obj, String fieldName) {
        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
            }
        }
        return null;
    }

    /**
     * 获取一个字段的值并转为String（必须有get方法）
     *
     * @author panweiqiang
     * @date 2017年12月4日 下午4:14:46
     * @param target
     * @param fieldName 字段名称
     * @return
     */
    public static String getFieldToString(Object target, String fieldName) {
        Object value = getFieldValue(target, fieldName);
        return value == null ? null : value.toString();
    }

    /**
     * 获取一个字段类型为Long的值（必须有get方法）
     *
     * @author panweiqiang
     * @date 2017年12月4日 下午4:14:46
     * @param target
     * @param fieldName 字段名称
     * @return
     */
    public static Long getFieldToLong(Object target, String fieldName) {
        Object value = getFieldValue(target, fieldName);
        return value == null ? null : (Long) value;
    }

    /**
     * 获取一个字段类型为Integer的值（必须有get方法）
     *
     * @author panweiqiang
     * @date 2017年12月4日 下午4:14:46
     * @param target
     * @param fieldName 字段名称
     * @return
     */
    public static Integer getFieldToInt(Object target, String fieldName) {
        Object value = getFieldValue(target, fieldName);
        return value == null ? null : (Integer) value;
    }

    /**
     * 获取一个字段的值（必须有get方法）
     *
     * @author panweiqiang
     * @date 2017年12月4日 下午4:14:46
     * @param target
     * @param fieldName 字段名称
     * @return
     */
    public static Object getFieldValue(Object target, String fieldName) {
        Assert.notNull(target, "对象不能为空");
        Assert.notNull(fieldName, "字段名不能为空");
        String methodName = "get" + CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, fieldName);
        return invokeMothod(target, methodName);
    }

    /**
     * 反射的方式调用方法（无参）
     *
     * @author panweiqiang
     * @date 2017年12月4日 下午3:49:06
     * @param methodName 方法名称
     * @param target 调用类
     * @return
     * @see org.springframework.util.ReflectionUtils
     */
    public static Object invokeMothod(Object target, String methodName) {
        Assert.notNull(target, "对象不能为空");
        Assert.notNull(methodName, "方法名不能为空");
        Method method = findMethod(methodName, target.getClass());
        if (method == null) {
            return null;
        }
        return ReflectionUtils.invokeMethod(method, target);
    }

    /**
     * 反射的方式调用方法（有参）
     *
     * @author panweiqiang
     * @date 2017年12月4日 下午3:49:06
     * @param methodName 方法名称
     * @param target 调用类
     * @param args 参数
     * @return
     * @see org.springframework.util.ReflectionUtils
     */
    public static Object invokeMothod(Object target, String methodName, Object... args) {
        Assert.notNull(target, "对象不能为空");
        Assert.notNull(methodName, "方法名不能为空");
        Method method = null;
        if (args == null || args.length == Numbers.INT_0) {
            method = org.springframework.util.ReflectionUtils.findMethod(target.getClass(), methodName);
        } else {
            Class<?>[] paramTypes = new Class<?>[args.length];
            for (int i = 0; i < args.length; i++) {
                paramTypes[i] = args[i].getClass();
            }
            method = org.springframework.util.ReflectionUtils.findMethod(target.getClass(), methodName, paramTypes);
        }

        if (method == null) {
            return null;
        }
        return ReflectionUtils.invokeMethod(method, target, args);
    }

    /**
     * 查找方法
     *
     * @author panweiqiang
     * @date 2017年12月4日 下午3:50:32
     * @param clazz
     * @param name
     * @return
     */
    private static Method findMethod(String name, Class<?> clazz) {
        Assert.notNull(clazz, "对象不能为空");
        Assert.notNull(name, "方法名不能为空");

        // 获取缓存key
        String key = clazz.getName() + StringConstant.POINT + name;

        // 如果已缓存就从缓存中获取，否则创建一个并放入缓存
        if (METHOD_MAP.containsKey(key)) {
            return METHOD_MAP.get(key);
        } else {
            Method method = org.springframework.util.ReflectionUtils.findMethod(clazz, name);
            if (method == null) {
                log.warn("获取反射方法[" + key + "()]失败！");
                return null;
            } else {
                METHOD_MAP.put(key, method);
                return method;
            }
        }

    }

    /**
     * 设置obj对象fieldName的属性值
     * @param obj
     * @param fieldName
     * @param value
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static void setValueByFieldName(Object obj, String fieldName, Object value) throws SecurityException, NoSuchFieldException,
                                                                                       IllegalArgumentException, IllegalAccessException {
        Field field = ReflectionUtil.getFieldByFieldName(obj, fieldName);
        if (field == null) {
            log.warn("对象中无字段:{}", fieldName);
            return;
        }

        if (field.isAccessible()) {
            field.set(obj, value);
        } else {
            field.setAccessible(true);
            field.set(obj, value);
            field.setAccessible(false);
        }
    }

    /**
     * java 反射判断一个类中是否含有某个属性字段
     *
     * @param c
     * @param fieldName
     * @return
     * @create  2021年12月10日 上午10:54:46 luochao
     * @history
     */
    public static boolean hasField(Class<?> c, String fieldName) {
        Field[] fields = c.getDeclaredFields();
        for (Field f : fields) {
            if (fieldName.equals(f.getName())) {
                return true;
            }
        }
        return false;
    }

}
