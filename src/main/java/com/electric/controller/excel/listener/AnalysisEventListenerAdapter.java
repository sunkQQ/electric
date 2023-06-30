package com.electric.controller.excel.listener;

import com.alibaba.excel.event.AnalysisEventListener;
import com.electric.controller.excel.vo.ExcelError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AnalysisEventListener适配器
 *
 * @author sunk
 * @date 2023/06/28
 */
public abstract class AnalysisEventListenerAdapter<T> extends AnalysisEventListener<T> {

    protected Map<Integer, List<ExcelError>> excelErrorMap = new HashMap<>();

    public Map<Integer, List<ExcelError>> getExcelErrorMap() {
        return excelErrorMap;
    }

    /**
     * 设置批注集合
     *
     * @param rowsNum   行数
     * @param cellIndex 单元格索引
     * @param msg       错误信息
     */
    protected void setExcelErrorMaps(int rowsNum, int cellIndex, String msg) {
        if (excelErrorMap.containsKey(rowsNum)) {
            List<ExcelError> excelErrors = excelErrorMap.get(rowsNum);
            excelErrors.add(new ExcelError(rowsNum, cellIndex, msg));
            excelErrorMap.put(rowsNum, excelErrors);
        } else {
            List<ExcelError> excelErrors = new ArrayList<>();
            excelErrors.add(new ExcelError(rowsNum, cellIndex, msg));
            excelErrorMap.put(rowsNum, excelErrors);
        }
    }

}
