package com.electric.controller.excel.adapter;

import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.electric.controller.excel.vo.ExcelError;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;

/**
 * 将参数校验失败的Exccel，添加批注后导出
 *
 * @author sunk
 * @date 2023/06/28
 */
public class CommentWriteHandler extends AbstractRowWriteHandlerAdapter {

    @Override
    public void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Integer relativeRowIndex, Boolean isHead) {
        if (!isHead){
            Sheet sheet = writeSheetHolder.getSheet();
            if (excelErrorMap.containsKey(relativeRowIndex)) {
                List<ExcelError> excelErrors = excelErrorMap.get(relativeRowIndex);
                excelErrors.forEach(obj -> {
                    setCellCommon(sheet, obj.getRow() + 1, obj.getColumn(), obj.getErrorMsg());
                });
            }
        }
    }
}
