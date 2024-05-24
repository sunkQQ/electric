package com.electric.controller.excel.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

import com.alibaba.excel.util.BooleanUtils;
import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.handler.context.RowWriteHandlerContext;

/**
 * @author sunk
 * @date 2024/04/30
 */
public class HeadCommentWriteHandler implements RowWriteHandler {

    protected Map<String, String> commentMap = new HashMap<>();

    private List<List<String>>    head       = new ArrayList<>();

    public void setCommentMap(Map<String, String> commentMap, List<List<String>> head) {
        this.commentMap = commentMap;
        this.head = head;
    }

    @Override
    public void afterRowDispose(RowWriteHandlerContext context) {
        if (BooleanUtils.isTrue(context.getHead())) {
            Sheet sheet = context.getWriteSheetHolder().getSheet();
            Drawing<?> drawingPatriarch = sheet.createDrawingPatriarch();
            for (int i = 0; i < head.size(); i++) {
                List<String> heads = head.get(i);
                for (int j = 0; j < heads.size(); j++) {
                    String value = commentMap.get(heads.get(j));
                    if (StringUtils.isEmpty(value)) {
                        continue;
                    }
                    System.out.println("i:" + i + ",j:" + j);
                    //setCellCommon(sheet, i, j, value);
                    CreationHelper factory = sheet.getWorkbook().getCreationHelper();
                    ClientAnchor anchor = factory.createClientAnchor();
                    Comment comment = drawingPatriarch.createCellComment(anchor);
                    // 在第一行 第二列创建一个批注
                    //Comment comment = drawingPatriarch.createCellComment(new XSSFClientAnchor(0, 0, 0, 0, (short) 1, 0, (short) 2, 1));
                    // 输入批注信息
                    comment.setString(new XSSFRichTextString(value));
                    // 将批注添加到单元格对象中

                    sheet.getRow(0).getCell(i).setCellComment(comment);
                }
            }

        }
    }

}
