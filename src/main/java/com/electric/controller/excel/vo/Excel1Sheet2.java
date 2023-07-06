package com.electric.controller.excel.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Excel1Sheet2
 *
 * @author sunk
 * @date 2023/07/01
 */
@Data
@AllArgsConstructor
@ExcelIgnoreUnannotated
public class Excel1Sheet2 {

    @ExcelProperty(value = "姓名", order = 1)
    private String realName;
    @ExcelProperty(value = "毕业学校", order = 6)
    private String schoolName;
}
