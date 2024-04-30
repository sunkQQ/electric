package com.electric.controller.excel.merge.demo1.util;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;

/**
 * @author sunk
 * @date 2024/04/30
 */
public class EasyExcel111Util {

    /**
     * 下载文件时，针对不同浏览器，进行附件名的编码
     *
     * @param filename 下载文件名
     *
     * @param agent 客户端浏览器
     *
     * @return 编码后的下载附件名
     * @throws IOException
     */
    public static String encodeDownloadFilename(String filename, String agent) throws IOException {
        if (agent.contains("Firefox")) { // 火狐浏览器
            filename = "=?UTF-8?B?" + Arrays.toString(Base64.getEncoder().encode(filename.getBytes("utf-8"))) + "?=";
            filename = filename.replaceAll("\r\n", "");
        } else { // IE及其他浏览器
            filename = URLEncoder.encode(filename, "utf-8");
            filename = filename.replace("+", " ");
        }
        return filename;
    }

    /**
     * 设置导出Excel格式
     * @param response
     * @param request
     * @param filename
     * @throws IOException
     */
    public static void setExportExcelFormat(HttpServletResponse response, HttpServletRequest request, String filename) throws IOException {
        String agent = request.getHeader("user-agent");//获得游览器
        filename = filename + ".xlsx";
        String downloadFilename = encodeDownloadFilename(filename, agent); //使用工具类解决文件乱码的问题
        response.setCharacterEncoding("UTF-8");
        // 设置响应输出的头类型
        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称
        response.setHeader("Content-Disposition", "attachment;filename=" + downloadFilename);
        response.setHeader("Pragma", "public");
        response.setHeader("Cache-Control", "no-store");
        response.addHeader("Cache-Control", "max-age=0");
    }

    /**
     * 单sheet页写入Excel-合并策略
     * @param outputStream          输出流
     * @param clazz                 导出实体类型
     * @param list                  导出数据集合
     * @param sheetName             sheet页名称
     * @param cellWriteHandlerList  合并策略集合
     */
    public static void writeExcelMerge(ServletOutputStream outputStream, Class<?> clazz, List<?> list, String sheetName,
                                       List<CellWriteHandler> cellWriteHandlerList) {
        ExcelWriterBuilder excelWriterBuilder = EasyExcel.write(outputStream, clazz).registerWriteHandler(new LongestMatchColumnWidthStyleStrategy());
        if (!CollectionUtils.isEmpty(cellWriteHandlerList)) {
            for (CellWriteHandler cellWriteHandler : cellWriteHandlerList) {
                excelWriterBuilder.registerWriteHandler(cellWriteHandler);
            }
        }
        excelWriterBuilder.sheet(sheetName).doWrite(list);
    }

    /**
     * 多sheet页写入Excel
     * @param excelWriter   excelWriter写出对象
     * @param clazz         导出实体类型
     * @param list          导出数据集合
     * @param num           sheet页码
     * @param sheetName     sheet页名称
     */
    public static void writerSheetExcel(ExcelWriter excelWriter, Class<?> clazz, List<?> list, Integer num, String sheetName) {
        WriteSheet writeSheet = EasyExcel.writerSheet(num, sheetName).head(clazz).registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
            .build();
        excelWriter.write(list, writeSheet);
    }

    /**
     * 多sheet页写入Excel-合并策略
     * @param excelWriter           excelWriter写出对象
     * @param clazz                 导出实体类型
     * @param list                  导出数据集合
     * @param num                   sheet页码
     * @param sheetName             sheet页名称
     * @param cellWriteHandlerList  合并策略集合
     */
    public static void writerSheetExcelMerge(ExcelWriter excelWriter, Class<?> clazz, List<?> list, Integer num, String sheetName,
                                             List<CellWriteHandler> cellWriteHandlerList) {
        ExcelWriterSheetBuilder excelWriterSheetBuilder = EasyExcel.writerSheet(num, sheetName).head(clazz)
            .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy());
        if (!CollectionUtils.isEmpty(cellWriteHandlerList)) {
            for (CellWriteHandler cellWriteHandler : cellWriteHandlerList) {
                excelWriterSheetBuilder.registerWriteHandler(cellWriteHandler);
            }
        }
        WriteSheet writeSheet = excelWriterSheetBuilder.build();
        excelWriter.write(list, writeSheet);
    }
}
