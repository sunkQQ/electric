package com.electric.controller.excel.merge.demo1.util;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 列合并的工具类
 *
 * @author sunk
 * @date 2024/04/30
 */
@Data
@AllArgsConstructor
public class CellLineRange {

    /**
     * 起始列
     */
    private int firstCol;

    /**
     * 结束列
     */
    private int lastCol;
}