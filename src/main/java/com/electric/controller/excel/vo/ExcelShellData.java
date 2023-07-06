package com.electric.controller.excel.vo;

import java.util.List;

/**
 *  sheet数据
 * @author sunk
 * @date 2023/07/01
 */
public class ExcelShellData<T> {

    private List<T> list;
    private String sheetName;
    private Class<T> clazz;

    public ExcelShellData(List<T> list, String sheetName, Class<T> clazz) {
        this.list = list;
        this.sheetName = sheetName;
        this.clazz = clazz;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }
}
