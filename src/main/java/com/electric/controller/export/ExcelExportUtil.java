package com.electric.controller.export;

import cn.hutool.core.io.resource.ResourceUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.AbstractColumnWidthStyleStrategy;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import com.electric.controller.export.constant.ExportConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Objects;

/**
 * excel导出工具类
 *
 * @author sunk
 * @date 2023/06/28
 */
@Slf4j
@Component
public class ExcelExportUtil<T> {


    /**
     * excel文件导出, 固定表头(通过实体指定属性的方式)
     * @param exportData 需要导出数据
     * @param sheetName sheet页的名称，为空则默认以:sheet1命名
     * @param head  导出表头
     * @param fileName 导出文件名不带后缀
     * @param response response
     *
     */
    public static <T> void exportFile(List<T> exportData, String sheetName, Class<T> head, String fileName, HttpServletResponse response) {
        if (Objects.isNull(response) || StringUtils.isBlank(fileName) || head == null) {
            log.info("ExcelExportUtil exportFile required param can't be empty");
            return;
        }
        ExcelWriter writer = null;
        try {
            response.setContentType(ExportConstant.EXCEL_CONTENT_TYPE);
            response.setCharacterEncoding(ExportConstant.UTF_8);
            response.setHeader(ExportConstant.CONTENT_DISPOSITION,
                    ExportConstant.ATTACHMENT_FILENAME + URLEncoder.encode(fileName, "UTF-8") + ExportConstant.XLSX_SUFFIX);
            // 设置导出的表格样式
            HorizontalCellStyleStrategy horizontalCellStyleStrategy = getExportDefaultStyle();
            writer = EasyExcel.write(response.getOutputStream()).registerWriteHandler(horizontalCellStyleStrategy).build();
            // 表头数据
            // sheet页的数据

            WriteSheet sheet = EasyExcel.writerSheet(0, StringUtils.isBlank(sheetName) ? "sheet1" : sheetName).head(head).build();
            writer.write(exportData, sheet);
        } catch (Exception e) {
            log.error("ExcelExportUtil exportFile in error:{}", e);
        } finally {
            if (null != writer) {
                writer.finish();
            }
        }
    }

    /**
     * excel文件导出(可以包含多个sheet页)，固定表头(通过实体指定属性的方式)
     *
     * @param response
     * @param fileName   导出文件名
     * @param head       导出表头(多个sheet页就是多个集合元素)
     * @param exportData 需要导出数据
     * @param sheetNames sheet页的名称，为空则默认以:sheet + 数字规则命名
     */
    public static <T> void exportFileMultiple(String fileName, List<T> head, List<List<T>> exportData, List<String> sheetNames,
                                              HttpServletResponse response) {
        if (Objects.isNull(response) || org.apache.commons.lang3.StringUtils.isBlank(fileName) || CollectionUtils.isEmpty(head)) {
            log.info("ExcelExportUtil exportFile required param can't be empty");
            return;
        }
        ExcelWriter writer = null;
        try {
            response.setContentType(ExportConstant.EXCEL_CONTENT_TYPE);
            response.setCharacterEncoding(ExportConstant.UTF_8);
            response.setHeader(ExportConstant.CONTENT_DISPOSITION, ExportConstant.ATTACHMENT_FILENAME + fileName + ExportConstant.XLSX_SUFFIX);
            // 设置导出的表格样式
            HorizontalCellStyleStrategy horizontalCellStyleStrategy = getExportDefaultStyle();
            writer = EasyExcel.write(response.getOutputStream()).registerWriteHandler(horizontalCellStyleStrategy).build();
            for (int itemIndex = 0; itemIndex < exportData.size(); itemIndex++) {
                // 表头数据
                Object headData = head.get(itemIndex);
                // sheet页的数据
                List<T> list = exportData.get(itemIndex);
                WriteSheet sheet = EasyExcel
                        .writerSheet(itemIndex, CollectionUtils.isEmpty(sheetNames) ? ExportConstant.SHEET_NAME + itemIndex + 1 : sheetNames.get(itemIndex))
                        .head(headData.getClass()).build();
                writer.write(list, sheet);
            }
        } catch (Exception e) {
            log.error("ExcelExportUtil exportFile in error:{}", e);
        } finally {
            if (null != writer) {
                writer.finish();
            }
        }
    }

    /**
     * 导出动态表头数据(单表单)，表头列不固定，根据程序或者读取数据库生成
     *
     * @param fileName   导出文件名
     * @param head       导出表头列
     * @param exportData 需要导出的数据
     * @param sheetName sheet页名称
     * @param response   响应流
     */
    public static <T> void exportWithDynamicData(List<List<T>> exportData, String sheetName, List<List<String>> head, String fileName,
                                                 HttpServletResponse response) throws IOException {
        ExcelWriter writer = null;
        try {
            response.setContentType(ExportConstant.EXCEL_CONTENT_TYPE);
            response.setCharacterEncoding(ExportConstant.UTF_8);
            response.setHeader(ExportConstant.CONTENT_DISPOSITION, ExportConstant.ATTACHMENT_FILENAME + fileName + ExportConstant.XLSX_SUFFIX);
            // 设置默认样式的excel表格对象
            HorizontalCellStyleStrategy horizontalCellStyleStrategy = getExportDefaultStyle();
            AbstractColumnWidthStyleStrategy columnWidthStyleStrategy = new SimpleColumnWidthStyleStrategy(ExportConstant.DEFAULT_CELL_LENGTH);
            writer = EasyExcel.write(response.getOutputStream()).registerWriteHandler(horizontalCellStyleStrategy)
                    .registerWriteHandler(columnWidthStyleStrategy).build();
            //for (int i = 0; i < exportData.size(); i++) {
            WriteSheet sheet = EasyExcel.writerSheet(0, StringUtils.isBlank(sheetName) ? ExportConstant.SHEET_NAME + 1 : sheetName).head(head)
                    .build();
            writer.write(exportData, sheet);
            //}
        } finally {
            if (Objects.nonNull(writer)) {
                writer.finish();
            }
        }
    }

    /**
     * 导出动态表头数据(支持多表单)，表头列不固定，根据程序或者读取数据库生成
     *
     * @param fileName   导出文件名
     * @param head       导出表头列
     * @param exportData 需要导出的数据
     * @param sheetNames sheet页名称
     * @param response   响应流
     */
    public static <T> void exportWithDynamicDataMultiple(String fileName, List<List<List<String>>> head, List<List<List<T>>> exportData,
                                                         List<String> sheetNames, HttpServletResponse response) throws IOException {
        ExcelWriter writer = null;
        try {
            response.setContentType(ExportConstant.EXCEL_CONTENT_TYPE);
            response.setCharacterEncoding(ExportConstant.UTF_8);
            response.setHeader(ExportConstant.CONTENT_DISPOSITION, ExportConstant.ATTACHMENT_FILENAME + fileName + ExportConstant.XLSX_SUFFIX);
            // 设置默认样式的excel表格对象
            HorizontalCellStyleStrategy horizontalCellStyleStrategy = getExportDefaultStyle();
            AbstractColumnWidthStyleStrategy columnWidthStyleStrategy = new SimpleColumnWidthStyleStrategy(ExportConstant.DEFAULT_CELL_LENGTH);
            writer = EasyExcel.write(response.getOutputStream()).registerWriteHandler(horizontalCellStyleStrategy)
                    .registerWriteHandler(columnWidthStyleStrategy).build();
            for (int i = 0; i < exportData.size(); i++) {
                List<List<T>> tableData = exportData.get(i);
                WriteSheet sheet = EasyExcel
                        .writerSheet(i, CollectionUtils.isEmpty(sheetNames) ? ExportConstant.SHEET_NAME + i + 1 : sheetNames.get(i)).head(head.get(i))
                        .build();
                writer.write(tableData, sheet);
            }
        } finally {
            if (Objects.nonNull(writer)) {
                writer.finish();
            }
        }
    }

    /**
     * 下载指定路径下的模板
     *
     * @param filePath 文件所在路径(包含模板名称)：一般是放在项目的resources目录下的template
     * @param fileName 下载时默认的文件名称
     * @param response 响应流
     */
    public static void downloadTemplate(String filePath, String fileName, String fileSuffix, HttpServletResponse response) {
        try {
            // 设置浏览器以附件形式读取响应流中的数据
            response.setContentType(ExportConstant.EXCEL_CONTENT_TYPE);
            response.setCharacterEncoding(ExportConstant.UTF_8);
            response.setHeader(ExportConstant.CONTENT_DISPOSITION, ExportConstant.ATTACHMENT_FILENAME + fileName + fileSuffix);
            byte[] bytes = ResourceUtil.readBytes(filePath);
            response.getOutputStream().write(bytes);
        } catch (Exception e) {
            log.error("ExcelExportUtil downloadTemplate in error:{}", e);
        }
    }

    /**
     * 配置默认的excel表格样式对象
     *
     * @return
     */
    private static HorizontalCellStyleStrategy getExportDefaultStyle() {
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        //设置头字体
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setBold(true);
        headWriteCellStyle.setWriteFont(headWriteFont);
        //设置头居中
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        //内容策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        //设置 水平居中
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
        return horizontalCellStyleStrategy;
    }
}
