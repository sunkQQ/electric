package com.electric.controller.excel.merge.demo2;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author sunk
 * @date 2024/04/30
 */
@Data
@ColumnWidth(16)
@HeadRowHeight(20)
@EqualsAndHashCode
public class PlotBaseExcelVO {
    private static final long serialVersionUID = 1L;

    private Long              id;

    /**
     * 主体名称
     */
    @ExcelProperty(value = { "地块信息", "主体名称" })
    @ColumnWidth(value = 22)
    private String            name;
    /**
     * 所属区域
     */
    @ExcelProperty(value = { "地块信息", "所属地区" })
    @ColumnWidth(value = 35)
    private String            regionName;

    /**
     * 生产经营地址
     */
    @ExcelProperty(value = { "地块信息", "生产经营地址" })
    @ColumnWidth(value = 25)
    private String            address;

    /**
     * 地块编码
     */
    @ExcelProperty(value = { "地块信息", "地块编码" })
    private String            plotCode;

    /**
     * 地块类型
     */
    @ExcelProperty(value = { "地块信息", "地块类型" })
    private String            typeName;
    /**
     * 地块名称
     */
    @ExcelProperty(value = { "地块信息", "地块名称" })
    private Date              plotName;
    /**
     * 地块面积
     */
    @ExcelProperty(value = { "地块信息", "地块面积" })
    private String            area;
}