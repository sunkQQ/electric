package com.electric.controller.excel.vo;

import java.io.Serializable;

/**
 * 批注错误实体类
 *
 * @author sunk
 * @date 2023/06/28
 */
public class ExcelError implements Serializable {

    private static final long serialVersionUID = 8546122564327253811L;

    /** 第几行 从1开始计数 */
    private int               row;
    /** 第几列  从1开始计数 */
    private int               column;
    /** 错误消息 */
    private String            errorMsg;

    public ExcelError(int row, int column, String errorMsg) {
        this.row = row;
        this.column = column;
        this.errorMsg = errorMsg;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    @Override
    public String toString() {
        return "ExcelError{" + "row=" + row + ", column=" + column + ", errorMsg='" + errorMsg + '\'' + '}';
    }
}
