package com.electric.controller.excel;

import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.handler.context.RowWriteHandlerContext;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.electric.controller.excel.adapter.HeadCommentWriteHandler;

import lombok.Getter;
import lombok.Setter;

/**
 * easyexcel工具类
 *
 * @author sunk
 * @date 2024/04/30
 */
public class EasyExcelUtil {

    /**
     * 同步导入(适用于小数据量)
     *
     * @param is 输入流
     * @return   转换后集合
     */
    public static <T> List<T> importExcel(InputStream is, Class<T> clazz) {
        return EasyExcel.read(is).head(clazz).sheet().doReadSync();
    }

    /**
     * 下载校验失败的数据 Workbook转String
     * @param listExcels
     * @param clazz
     * @return
     * @throws IOException
     */
    public static Workbook downErrorImportFile(List<?> listExcels, Class<?> clazz) throws IOException {
        File excelTempFile = File.createTempFile("result_", ".xlsx");
        exportExcel(listExcels, excelTempFile, clazz, "result");
        Workbook workbook = WorkbookFactory.create(excelTempFile);
        excelTempFile.delete();
        return workbook;
    }

    /**
     * 下载校验失败的数据
     * @param listExcels 原始数据
     * @param errorMap 错误数据标注
     * @param clazz 导入class
     * @param response response
     */
    public static void downErrorImportFile(List<?> listExcels, Map<Integer, List<ExcelError>> errorMap, Class<?> clazz,
                                           HttpServletResponse response) {
        String fileName = "result.xls";
        try {
            response.reset();
            setAttachmentResponseHeader(response, fileName);
            if (listExcels != null && errorMap != null) {
                CommentWriteHandler commentWriteHandler = new CommentWriteHandler();
                commentWriteHandler.setExcelErrorMap(errorMap);

                EasyExcel.write(response.getOutputStream(), clazz).inMemory(Boolean.TRUE).sheet("sheet1")
                    //注册批注拦截器
                    .registerWriteHandler(commentWriteHandler).doWrite(listExcels);
            }
        } catch (Exception e) {
            throw new RuntimeException("导出Excel异常", e);
        }

    }

    /**
     * 导出excel
     *
     * @param list      导出数据集合
     * @param filename  工作表的名称
     * @param clazz     导出表头类
     * @param sheetName sheet名称
     * @param response  response
     */
    public static void exportExcel(List<?> list, String filename, Class<?> clazz, String sheetName, HttpServletResponse response) {
        exportExcel(list, filename, clazz, sheetName, null, response);
    }

    /**
     * 导出excel
     *
     * @param dataList 导出数据集合
     * @param fileName 工作表的名称
     * @param head 导出表头
     * @param sheetName sheet名称
     * @param response response
     */
    public static void exportExcel(List<List<Object>> dataList, String fileName, List<List<String>> head, String sheetName,
                                   HttpServletResponse response) {
        try {
            response.reset();
            setAttachmentResponseHeader(response, fileName);
            ServletOutputStream os = response.getOutputStream();
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");
            EasyExcel.write(os).head(head)
                // 自动适配
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                // 大数值自动转换 防止失真
                .registerConverter(new ExcelBigNumberConvert()).sheet(sheetName).doWrite(dataList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 导出excel
     *
     * @param dataList 导出数据集合
     * @param fileName 工作表的名称
     * @param head 导出表头
     * @param sheetName sheet名称
     * @param response response
     */
    public static void exportExcel(List<List<Object>> dataList, String fileName, List<List<String>> head, String sheetName,
                                   HttpServletResponse response, Map<String, String> commentMap) {
        try {
            response.reset();
            setAttachmentResponseHeader(response, fileName);
            ServletOutputStream os = response.getOutputStream();
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");
            HeadCommentWriteHandler handler = new HeadCommentWriteHandler();
            handler.setCommentMap(commentMap, head);
            EasyExcel.write(os).head(head)
                // 自动适配
                .registerWriteHandler(handler).registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                // 大数值自动转换 防止失真
                .registerConverter(new ExcelBigNumberConvert()).sheet(sheetName).doWrite(dataList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建批注锚点
     *
     * @param workbook
     * @return
     * @date  2024年4月30日 上午9:16:04 luochao
     *
     */
    private static ClientAnchor newClientAnchor() {
        //xls
        //xlsx
        return new XSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 6);
    }

    /**
     * 导出excel
     *
     * @param dataList 导出数据集合
     * @param fileName 工作表的名称
     * @param head 导出表头
     * @param sheetName sheet名称
     * @param response response
     */
    public static void exportExcel(List<List<Object>> dataList, String fileName, String[] head, String sheetName, HttpServletResponse response) {
        List<List<String>> headList = setHeadList(head);
        exportExcel(dataList, fileName, headList, sheetName, response);
    }

    /**
     * 导出excel
     *
     * @param list      导出数据集合
     * @param filename  工作表的名称
     * @param clazz     导出表头类
     * @param sheetName sheet名称
     * @param password  密码
     * @param response  response
     */
    public static void exportExcel(List<?> list, String filename, Class<?> clazz, String sheetName, String password, HttpServletResponse response) {
        try {
            response.reset();
            setAttachmentResponseHeader(response, filename);
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");
            ServletOutputStream os = response.getOutputStream();
            EasyExcel.write(os, clazz).password(StringUtils.isNotBlank(password) ? password : null)
                // 自动适配
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                // 根据数据合并
                //.registerWriteHandler(new ExcelFillCellMergeStrategy(0, new int[] { 0, 1, 2 }, null))
                // 大数值自动转换 防止失真
                .registerConverter(new ExcelBigNumberConvert()).sheet(sheetName).doWrite(list);
        } catch (IOException e) {
            throw new RuntimeException("导出Excel异常", e);
        }
    }

    /**
     * 导出到file
     *
     * @author sunk
     * @date 2023/07/19 17:50
     * @param listData   导出数据集合
     * @param file  文件
     * @param clazz 导出表头类
     * @param sheetName sheet名称
     */
    public static void exportExcel(List<?> listData, File file, Class<?> clazz, String sheetName) {
        EasyExcel.write(file, clazz)
            // 自动适配
            .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
            // 大数值自动转换 防止失真
            .registerConverter(new EasyExcelUtil.ExcelBigNumberConvert()).sheet(sheetName).doWrite(listData);
    }

    /**
     * 导出多个sheet到一个excel文件  支持模板和直接创建
     * @param excelData excel数据
     * @param response response
     */
    public static void exportExcel(ExcelData excelData, HttpServletResponse response) {
        ExcelWriter excelWriter = null;
        try {
            String filename = excelData.getFilename();
            response.reset();
            setAttachmentResponseHeader(response, filename);
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");
            ServletOutputStream os = response.getOutputStream();
            ExcelWriterBuilder writerBuilder = EasyExcel.write(os)
                // 自动适配
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                // 大数值自动转换 防止失真
                .registerConverter(new ExcelBigNumberConvert());
            if (excelData.getTemplateFile() != null) {
                writerBuilder.withTemplate(excelData.getTemplateFile());
            }
            excelWriter = writerBuilder.build();
            if (excelData.getTemplateFile() != null) {
                // 注意sheet名称要与模板文件的sheet名称一一对应
                for (ExcelShellData<?> shellData : excelData.getShellDataList()) {
                    WriteSheet writeSheet = EasyExcel.writerSheet(shellData.getSheetName()).build();
                    //FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
                    excelWriter.fill(shellData.getList(), writeSheet);
                }
            } else {
                //开始写入excel
                for (ExcelShellData<?> shellData : excelData.getShellDataList()) {
                    WriteSheet writeSheet = EasyExcel.writerSheet(shellData.getSheetName()).head(shellData.getClazz()).build();
                    excelWriter.write(shellData.getList(), writeSheet);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("导出Excel异常", e);
        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }

    /**
     * 导出动态表头数据(支持多表单)，表头列不固定，根据程序或者读取数据库生成
     *
     * @param exportData 需要导出的数据
     * @param fileName   导出文件名
     * @param head       导出表头列
     * @param sheetName sheet页名称
     * @param response   响应流
     */
    public static <T> void exportWithDynamicData(List<List<List<?>>> exportData, String fileName, List<List<String>> head, String sheetName,
                                                 HttpServletResponse response) throws IOException {
        ExcelWriter writer = null;
        try {
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
            writer = EasyExcel.write(response.getOutputStream()).build();
            WriteSheet sheet = EasyExcel.writerSheet(0, sheetName).head(head).build();
            writer.write(exportData, sheet);
        } finally {
            if (Objects.nonNull(writer)) {
                writer.finish();
            }
        }
    }

    /**
     * 单sheet页写入Excel-合并策略
     * @param outputStream          输出流
     * @param clazz                 导出实体类型
     * @param list                  导出数据集合
     * @param sheetName             sheet页名称
     * @param cellWriteHandlerList  合并策略集合
     */
    public static void exportExcelMerge(ServletOutputStream outputStream, Class<?> clazz, List<?> list, String sheetName,
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
     * 设置批注集合
     *
     * @param excelErrorMap 失败数据标注
     * @param rowsNum       行数
     * @param cellIndex     单元格索引
     * @param msg           错误信息
     */
    public static void setExcelErrorMaps(Map<Integer, List<ExcelError>> excelErrorMap, int rowsNum, int cellIndex, String msg) {
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

    /**
     * 设置标题头
     * @param head 标题头以逗号[,]隔开
     * @return 标题头
     */
    public static List<List<String>> setHeadList(String head) {
        List<List<String>> headList = new ArrayList<>();
        for (String headStr : head.split(",")) {
            if (StringUtils.isNotBlank(headStr)) {
                headList.add(Collections.singletonList(headStr.trim()));
            }
        }
        return headList;
    }

    /**
     * 设置标题头
     * @param headStrs 标题头数组
     * @return 标题头
     */
    public static List<List<String>> setHeadList(String[] headStrs) {
        List<List<String>> headList = new ArrayList<>();
        for (String headStr : headStrs) {
            if (StringUtils.isNotBlank(headStr)) {
                headList.add(Collections.singletonList(headStr.trim()));
            }
        }
        return headList;
    }

    /**
     * 设置标头
     * @param response response
     * @param filename 文件名
     * @throws UnsupportedEncodingException 异常
     */
    private static void setAttachmentResponseHeader(HttpServletResponse response, String filename) throws UnsupportedEncodingException {
        String encode = URLEncoder.encode(filename, StandardCharsets.UTF_8.toString());
        String percentEncodedFileName = encode.replaceAll("\\+", "%20");
        String contentDispositionValue = "attachment; filename=" + percentEncodedFileName + ";filename*=utf-8''" + percentEncodedFileName;

        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition,download-filename");
        response.setHeader("Content-disposition", contentDispositionValue);
        response.setHeader("download-filename", percentEncodedFileName);
    }

    /**
     * 将参数校验失败的Exccel，添加批注后导出
     *
     * @author sunk
     * @date 2023/07/14 9:10
     */
    public static class HeadBuildComment implements RowWriteHandler {
        @Override
        public void afterRowDispose(RowWriteHandlerContext context) {
            if (context.getHead()) {
                Sheet sheet = context.getWriteSheetHolder().getSheet();
                Drawing<?> drawingPatriarch = sheet.createDrawingPatriarch();
                // 在第一行 第二列创建一个批注
                Comment comment = drawingPatriarch.createCellComment(new XSSFClientAnchor(0, 0, 0, 0, (short) 1, 0, (short) 2, 1));
                // 输入批注信息
                comment.setString(new XSSFRichTextString("创建批注!"));
                // 将批注添加到单元格对象中
                sheet.getRow(0).getCell(1).setCellComment(comment);
            }
        }
    }

    /**
     * 将参数校验失败的Exccel，添加批注后导出
     *
     * @author sunk
     * @date 2023/07/14 9:10
     */
    public static class CommentWriteHandler extends AbstractRowWriteHandlerAdapter {

        @Override
        public void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Integer relativeRowIndex,
                                    Boolean isHead) {
            if (!isHead) {
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

    /**
     * AbstractRowWriteHandler适配器
     *
     * @author sunk
     * @date 2023/07/14 9:10
     */
    public static abstract class AbstractRowWriteHandlerAdapter implements RowWriteHandler {

        protected Map<Integer, List<ExcelError>> excelErrorMap = new HashMap<>();

        public void setExcelErrorMap(Map<Integer, List<ExcelError>> excelErrorMap) {
            this.excelErrorMap = excelErrorMap;
        }

        CellStyle cellStyle;

        /**
         * 设置单元格批注
         * @param sheet sheet
         * @param rowIndex 行索引
         * @param colIndex 列索引
         * @param value 批注
         */
        protected void setCellCommon(Sheet sheet, int rowIndex, int colIndex, String value) {
            Workbook workbook = sheet.getWorkbook();
            if (cellStyle == null) {
                cellStyle = workbook.createCellStyle();

            }
            //CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                return;
            }
            Cell cell = row.getCell(colIndex);
            if (cell == null) {
                cell = row.createCell(colIndex);
            }
            if (value == null) {
                cell.removeCellComment();
                return;
            }
            Drawing<?> drawing = sheet.createDrawingPatriarch();
            CreationHelper factory = sheet.getWorkbook().getCreationHelper();
            ClientAnchor anchor = factory.createClientAnchor();
            Row row1 = sheet.getRow(anchor.getRow1());
            if (row1 != null) {
                Cell cell1 = row1.getCell(anchor.getCol1());
                if (cell1 != null) {
                    cell1.removeCellComment();
                }
            }
            Comment comment = drawing.createCellComment(anchor);
            RichTextString str = factory.createRichTextString(value);
            comment.setString(str);
            comment.setAuthor("admin");
            cell.setCellComment(comment);
            cell.setCellStyle(cellStyle);
        }
    }

    /**
     *  Excel 数值长度位15位 大于15位的数值转换位字符串
     *
     * @author sunk
     * @date 2023/07/14 9:11
     */
    public static class ExcelBigNumberConvert implements Converter<Long> {

        @Override
        public Class<Long> supportJavaTypeKey() {
            return Long.class;
        }

        @Override
        public CellDataTypeEnum supportExcelTypeKey() {
            return CellDataTypeEnum.STRING;
        }

        @Override
        public Long convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
            Object data = cellData.getData();
            if (data == null) {
                return null;
            }
            String s = String.valueOf(data);
            if (s.matches("^\\d+$")) {
                return Long.parseLong(s);
            }
            return null;
        }

        @Override
        public WriteCellData<Object> convertToExcelData(Long object, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
            if (object != null) {
                String str = object.toString();
                if (str.length() > 15) {
                    return new WriteCellData<>(str);
                }
            }
            WriteCellData<Object> cellData = new WriteCellData<>(new BigDecimal(object));
            cellData.setType(CellDataTypeEnum.NUMBER);
            return cellData;
        }
    }

    /**
     * excel数据
     *
     * @author sunk
     * @date 2023/07/14 9:11
     */
    public static class ExcelData {

        private List<ExcelShellData<?>> shellDataList = new ArrayList<>();
        private String                  filename      = "export.xlsx";
        private String                  templateFilename;

        public List<ExcelShellData<?>> getShellDataList() {
            return shellDataList;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getTemplateFilename() {
            return templateFilename;
        }

        public void setTemplateFilename(String templateFilename) {
            this.templateFilename = templateFilename;
        }

        public File getTemplateFile() {
            if (templateFilename == null) {
                return null;
            }
            File templateFile = new File(templateFilename);
            if (templateFile.exists() && templateFile.isFile()) {
                return templateFile;
            }
            Resource resource = new ClassPathResource(templateFilename);
            if (resource.exists()) {
                File file = null;
                try {
                    file = resource.getFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (file != null) {
                    if (file.exists() && file.isFile()) {
                        return file;
                    }
                    return null;
                }
            }
            return null;
        }

        public void setShellDataList(List<ExcelShellData<?>> shellDataList) {
            if (shellDataList != null) {
                this.shellDataList = shellDataList;
            }
        }

        public String getFilename() {
            if (filename == null || filename.isEmpty()) {
                filename = "export.xlsx";
            } else {
                String fn = filename.toLowerCase(Locale.ROOT);
                if (!(fn.endsWith(".xlsx") || fn.endsWith(".xls"))) {
                    filename = filename + ".xlsx";
                }
            }
            return filename;
        }

        public void addShellData(ExcelShellData<?> excelShellData) {
            this.shellDataList.add(excelShellData);
        }
    }

    /**
     * 批注错误实体类
     *
     * @author sunk
     * @date 2023/07/14 9:12
     */
    @Getter
    public static class ExcelError implements Serializable {

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

        @Override
        public String toString() {
            return "ExcelError{" + "row=" + row + ", column=" + column + ", errorMsg='" + errorMsg + '\'' + '}';
        }
    }

    /**
     * sheet数据
     *
     * @author sunk
     * @date 2023/07/14 9:12
     */
    @Getter
    @Setter
    public static class ExcelShellData<T> {

        /**
         * 数据列表
         */
        private List<T>  list;

        /**
         * sheet名称
         */
        private String   sheetName;

        /**
         * excel解析模板类
         */
        private Class<T> clazz;

        public ExcelShellData(List<T> list, String sheetName, Class<T> clazz) {
            this.list = list;
            this.sheetName = sheetName;
            this.clazz = clazz;
        }
    }

}
