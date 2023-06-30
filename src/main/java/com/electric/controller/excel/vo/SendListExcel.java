package com.electric.controller.excel.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.io.Serializable;

/**
 * 导出实体类
 *
 * @author sunk
 * @date 2023/06/28
 */
@Data
@ExcelIgnoreUnannotated()
@ContentRowHeight(20)
@HeadRowHeight(20)
public class SendListExcel implements Serializable {

    private static final long serialVersionUID = 888196456620488187L;

    @ExcelProperty(value = "账号", index = 0)
    @ColumnWidth(20)
    private String            account;
    @ExcelProperty(value = "模板编号", index = 1)
    @ColumnWidth(30)
    private String            templateCode;
    @ExcelProperty(value = "类型", index = 2)
    @ColumnWidth(15)
    private String            accountType;
}
