package com.electric.controller.excel.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Excel2Sheet1
 *
 * @author sunk
 * @date 2023/07/01
 */
@Data
@AllArgsConstructor
@ExcelIgnoreUnannotated
public class Excel2Sheet1 {

    @ExcelProperty(value = "姓名", order = 1)
    private String realName;
    @ExcelProperty(value = "政治面貌", order = 3)
    private String politicalStatus;
}
