package com.electric.controller.excel.util;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

/**
 * EasyExcel工具类
 *
 * @author sunk
 * @date 2023/06/28
 */
@Slf4j
public class EasyExcelUtil {

    /**
     * 获取Excel单元格的索引
     *
     * @param obj        JavaBean对象
     * @param fieldValue JavaBean字段值
     * @return
     */
    public static Integer getCellIndex(Object obj, String fieldValue) {
        try {
            Field declaredField = obj.getClass().getDeclaredField(fieldValue);
            ExcelProperty annotation = declaredField.getAnnotation(ExcelProperty.class);
            if (annotation == null) {
                return null;
            }
            return annotation.index();
        } catch (NoSuchFieldException e) {
            log.error("error:", e);
        }
        return null;
    }
}
