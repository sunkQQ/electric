package com.electric.controller.excel.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Excel1Sheet1
 *
 * @author sunk
 * @date 2023/07/01
 */
@Data
@AllArgsConstructor
@ExcelIgnoreUnannotated
public class Excel1Sheet1 {

    @ExcelProperty(value = "用户账号", order = 2)
    private String userName;

    @ExcelProperty(value = "用户密码", order = 3)
    private String password;
}
