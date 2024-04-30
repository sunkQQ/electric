package com.electric.controller.excel.merge.demo2;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.electric.controller.excel.EasyExcelUtil;

import cn.hutool.core.util.ObjectUtil;

public class ExcelMergeUtil implements CellWriteHandler {
    private int[] mergeColumnIndex;
    private int   mergeRowIndex;

    public ExcelMergeUtil() {
    }

    public ExcelMergeUtil(int mergeRowIndex, int[] mergeColumnIndex) {
        this.mergeRowIndex = mergeRowIndex;
        this.mergeColumnIndex = mergeColumnIndex;
    }

    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Head head, Integer columnIndex,
                                 Integer relativeRowIndex, Boolean isHead) {

    }

    @Override
    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell, Head head, Integer relativeRowIndex,
                                Boolean isHead) {

    }

    @Override
    public void afterCellDataConverted(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, WriteCellData<?> cellData, Cell cell,
                                       Head head, Integer relativeRowIndex, Boolean isHead) {

    }

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<WriteCellData<?>> cellDataList, Cell cell,
                                 Head head, Integer relativeRowIndex, Boolean isHead) {

        //当前行
        int curRowIndex = cell.getRowIndex();
        //当前列
        int curColIndex = cell.getColumnIndex();

        if (curRowIndex > mergeRowIndex) {
            for (int i = 0; i < mergeColumnIndex.length; i++) {
                if (curColIndex == mergeColumnIndex[i]) {
                    mergeWithPrevRow(writeSheetHolder, cell, curRowIndex, curColIndex);
                    break;
                }
            }
        }
    }

    /**
     * 当前单元格向上合并
     *
     * @param writeSheetHolder
     * @param cell             当前单元格
     * @param curRowIndex      当前行
     * @param curColIndex      当前列
     */
    private void mergeWithPrevRow(WriteSheetHolder writeSheetHolder, Cell cell, int curRowIndex, int curColIndex) {
        Object curData = cell.getCellType() == CellType.STRING ? cell.getStringCellValue() : cell.getNumericCellValue();
        Cell preCell = cell.getSheet().getRow(curRowIndex - 1).getCell(curColIndex);
        Object preData = preCell.getCellType() == CellType.STRING ? preCell.getStringCellValue() : preCell.getNumericCellValue();
        // 将当前单元格数据与上一个单元格数据比较
        Boolean dataBool = preData.equals(curData);
        //此处需要注意：因为我是按照主体名称确定是否需要合并的，所以获取每一行第二列数据和上一行第一列数据进行比较，如果相等合并，getCell里面的值，是主体名称所在列的下标，不能大于需要合并列数组的第一个下标
        Boolean bool = cell.getRow().getCell(0).getStringCellValue().equals(cell.getSheet().getRow(curRowIndex - 1).getCell(0).getStringCellValue());
        if (dataBool && bool) {
            Sheet sheet = writeSheetHolder.getSheet();
            List<CellRangeAddress> mergeRegions = sheet.getMergedRegions();
            boolean isMerged = false;
            for (int i = 0; i < mergeRegions.size() && !isMerged; i++) {
                CellRangeAddress cellRangeAddr = mergeRegions.get(i);
                // 若上一个单元格已经被合并，则先移出原有的合并单元，再重新添加合并单元
                if (cellRangeAddr.isInRange(curRowIndex - 1, curColIndex)) {
                    sheet.removeMergedRegion(i);
                    cellRangeAddr.setLastRow(curRowIndex);
                    sheet.addMergedRegion(cellRangeAddr);
                    isMerged = true;
                }
            }
            // 若上一个单元格未被合并，则新增合并单元
            if (!isMerged) {
                CellRangeAddress cellRangeAddress = new CellRangeAddress(curRowIndex - 1, curRowIndex, curColIndex, curColIndex);
                sheet.addMergedRegion(cellRangeAddress);
            }
        }
    }

    /**
     * 头部样式
     *
     * @return
     */
    public static WriteCellStyle getHeadWriteCellStyle() {
        // 这里需要设置不关闭流
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        //设置背景颜色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        //设置头字体
        WriteFont headWriteFont = new WriteFont();
        //字体大小
        headWriteFont.setFontHeightInPoints((short) 13);
        //是否加粗
        headWriteFont.setBold(true);
        headWriteCellStyle.setWriteFont(headWriteFont);
        //设置头居中
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        return headWriteCellStyle;
    }

    /**
     * 内容样式
     *
     * @return
     */
    public static WriteCellStyle getContentWriteCellStyle() {
        //内容策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        //设置 水平居中
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        //自动换行
        contentWriteCellStyle.setWrapped(true);
        //垂直居中
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //设置左边框
        contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        //设置右边框
        contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
        //设置上边框
        contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
        //设置下边框
        contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);
        return contentWriteCellStyle;
    }

    /**
     * 合并单元格导出excel工具
     *
     * @param response         响应头
     * @param fileName         文件名称
     * @param lsit             需要导出的数据
     * @param data             对应的excel导出类
     * @param mergeColumeIndex 需要合并的下标
     * @param mergeRowIndex    从第几行开始合并
     * @throws IOException
     */
    public static void exportExcel(HttpServletResponse response, String fileName, List<?> lsit, Class<?> data, int[] mergeColumeIndex,
                                   int mergeRowIndex) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileNamePath = URLEncoder.encode(fileName + System.currentTimeMillis(), "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileNamePath + ".xlsx");
        // 调用合并单元格工具类，此工具类是根据工程名称相同则合并后面数据
        ExcelMergeUtil excelFillCellMergeStrategy = null;
        //是否需要合并
        if (ObjectUtil.isNotEmpty(mergeColumeIndex) && mergeRowIndex != -1) {
            excelFillCellMergeStrategy = new ExcelMergeUtil(mergeRowIndex, mergeColumeIndex);
        }

        //头部样式
        WriteCellStyle headWriteCellStyle = ExcelMergeUtil.getHeadWriteCellStyle();
        //内容样式
        WriteCellStyle contentWriteCellStyle = ExcelMergeUtil.getContentWriteCellStyle();
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
        //合并列，可以实现最后一行合计的效果
        //ExcelFillCellMergePrevColUtils column = new ExcelFillCellMergePrevColUtils();
        //column.add(lsit.size() + 1, 0, 1);
        EasyExcel.write(response.getOutputStream(), data).registerConverter(new EasyExcelUtil.ExcelBigNumberConvert())
            .registerWriteHandler(horizontalCellStyleStrategy).registerWriteHandler(excelFillCellMergeStrategy)
            .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
            //.registerWriteHandler(column)
            //sheet显示的名字
            .autoCloseStream(Boolean.TRUE).sheet(fileName).doWrite(lsit);
    }
}