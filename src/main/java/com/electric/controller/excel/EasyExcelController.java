package com.electric.controller.excel;

import com.alibaba.excel.util.ListUtils;
import com.electric.model.constant.Numbers;
import com.electric.controller.excel.util.EasyExcelUtil;
import com.electric.controller.excel.util.ExcelUtils;
import com.electric.controller.excel.vo.*;
import com.electric.controller.export.ExcelExportUtil;
import com.electric.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * 到处controller
 *
 * @author sunk
 * @date 2023/07/01
 */
@RestController
@RequestMapping("/excel/easy")
public class EasyExcelController {

    @GetMapping("/eaysexcelUtil")
    public void exportEasy(HttpServletResponse response) {
        List<List<Object>> list = dataList();
        List<List<String>> head = head();
        Map<String, String> commentMap = new HashMap<>();
        commentMap.put("字符串2", "这是个测试批注");
        commentMap.put("日期", "这是个测试日期");
        com.electric.controller.excel.EasyExcelUtil.exportExcel(list, "测试.xlsx", head, "测试", response, commentMap);
    }

    private List<List<String>> head() {
        List<List<String>> list = ListUtils.newArrayList();
        List<String> head0 = ListUtils.newArrayList();
        //head0.add("字符串");
        head0.add("字符串2");
        List<String> head1 = ListUtils.newArrayList();
        //head1.add("字符串");
        head1.add("数字" + System.currentTimeMillis());
        List<String> head2 = ListUtils.newArrayList();
        //head2.add("字符串");
        head2.add("日期");
        List<String> head3 = ListUtils.newArrayList();
        //head3.add("字符串");
        head3.add("字符串2" + System.currentTimeMillis());
        list.add(head0);
        //list.add(head1);
        list.add(head2);
        list.add(head3);
        return list;
    }

    private List<List<Object>> dataList() {
        List<List<Object>> list = null;
        try {
            list = ListUtils.newArrayList();
            for (int i = 0; i < 10; i++) {
                List<Object> data = ListUtils.newArrayList();
                data.add("字符串" + i);
                data.add(0.56);
                data.add(new Date());
                Arrays.asList(new URL("https://img2.baidu.com/it/u=1906732828,3160455141&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=666"),
                    new URL("https://img2.baidu.com/it/u=1906732828,3160455141&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=666"));
                //data.add(new URL(
                //        "https://img2.baidu.com/it/u=1906732828,3160455141&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=666","https://img2.baidu.com/it/u=1906732828,3160455141&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=666"));
                list.add(data);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    /**
     * 导出到一个压缩文件 包含两个excel，其中一个包含两个sheet，另一个使用模块
     * @param response response
     */
    @GetMapping("/export/zip")
    public void exportZip(HttpServletResponse response) {
        List<ExcelData> excelData = exportAllData();
        ExcelUtils.exportZip("压缩导出", excelData, response);
    }

    /**
     * 导出到一个excel文件 包含两个sheet
     * @param response response
     */
    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response) {
        List<ExcelData> excelData = exportAllData();
        ExcelData excelA = excelData.get(0);//只要excelA
        ExcelUtils.exportExcel(excelA, response);
    }

    /**
     * 下载数据
     * @param response response
     * @return string
     */
    @PostMapping(value = "/downloadExcel")
    public String downloadExcel(HttpServletResponse response) {
        List<SendListExcel> list = new ArrayList<>();
        int l = 0;
        for (int i = 0; i <= 100000; i++) {
            SendListExcel excel = new SendListExcel();
            excel.setAccount(String.valueOf(i));
            excel.setAccountType("msg");
            excel.setTemplateCode("code " + i);
            list.add(excel);
        }
        ExcelUtils.exportExcel(list, "result.xlsx", SendListExcel.class, "123456", response);
        return "success";
    }

    /**
     * 导入失败原因下载
     * @param response response
     * @param file 导入文件
     * @return string
     * @throws IOException 异常
     */
    @PostMapping(value = "/importExcel")
    public String importExcel(HttpServletResponse response, @RequestParam(value = "file", required = true) MultipartFile file) throws IOException {
        // 读取到的数据
        List<SendListExcel> listExcels = ExcelUtils.importExcel(file.getInputStream(), SendListExcel.class);
        /* 校验导入信息 */
        Map<Integer, List<ExcelError>> excelErrorMap = checkImportInfo(listExcels);

        if (excelErrorMap.size() > Numbers.INT_0) {
            ExcelUtils.downErrorImportFile(listExcels, excelErrorMap, SendListExcel.class, response);
            System.out.println("end：" + DateUtil.getTimeNow());
            return "fial";
        } else {
            // 入库
            save(listExcels);
        }
        return "success";
    }

    /**
     * 校验导入信息
     * @param listExcels 导入数据
     * @return 校验失败标注信息
     */
    private Map<Integer, List<ExcelError>> checkImportInfo(List<SendListExcel> listExcels) {
        SendListExcel sle;
        boolean isMatch;
        Map<Integer, List<ExcelError>> excelErrorMap = new HashMap<>();
        for (int i = 0; i < listExcels.size(); i++) {
            sle = listExcels.get(i);
            isMatch = true;
            Integer accountCellIndex = EasyExcelUtil.getCellIndex(sle, "account");
            if (accountCellIndex != null) {
                if (StringUtils.isAllBlank(sle.getAccount())) {
                    ExcelExportUtil.setExcelErrorMaps(excelErrorMap, i, accountCellIndex, "账号不能为空！");
                }
            }
            Integer templateCodeCellIndex = EasyExcelUtil.getCellIndex(sle, "templateCode");
            if (templateCodeCellIndex != null) {
                if (StringUtils.isAllBlank(sle.getTemplateCode())) {
                    ExcelExportUtil.setExcelErrorMaps(excelErrorMap, i, templateCodeCellIndex, "模板编号不能为空！");
                }
            }
            Integer accountTypeCellIndex = EasyExcelUtil.getCellIndex(sle, "accountType");
            if (accountTypeCellIndex != null) {
                if (StringUtils.isAllBlank(sle.getAccountType())) {
                    ExcelExportUtil.setExcelErrorMaps(excelErrorMap, i, accountTypeCellIndex, "类型不能为空！");
                } else {
                    if ("sms".equals(sle.getAccountType()) || "email".equals(sle.getAccountType()) || "wechat".equals(sle.getAccountType())) {
                        isMatch = false;
                    }
                    if (isMatch) {
                        ExcelExportUtil.setExcelErrorMaps(excelErrorMap, i, accountTypeCellIndex, "类型只允许：sms、email、wechat");
                    }
                }
            }
        }
        return excelErrorMap;
    }

    /**
     * 入库
     * @param listExcels 列表信息
     */
    private void save(List<SendListExcel> listExcels) {

    }

    /**
     * 构建数据 Excel1Sheet1和Excel1Sheet2分别是excelA的两个sheet的数据，Excel2Sheet1是excelB的数据
     * @return
     */
    private List<ExcelData> exportAllData() {

        List<ExcelData> excelDataList = new ArrayList<>();
        //excelA
        ExcelData excelA = new ExcelData();
        excelA.setFilename("excelA");
        //excelA->Excel1Sheet1
        List<Excel1Sheet1> excel1Sheet1s = new ArrayList<>();
        excel1Sheet1s.add(new Excel1Sheet1("azi", "pwd123"));
        excel1Sheet1s.add(new Excel1Sheet1("ruphy", "123456"));
        excelA.addShellData(new ExcelShellData<>(excel1Sheet1s, "Excel1Sheet1", Excel1Sheet1.class));
        //excelA->Excel1Sheet2
        List<Excel1Sheet2> excel1Sheet2s = new ArrayList<>();
        excel1Sheet2s.add(new Excel1Sheet2("阿紫", "北大"));
        excel1Sheet2s.add(new Excel1Sheet2("子安", "清华"));
        excelA.addShellData(new ExcelShellData<>(excel1Sheet2s, "Excel1Sheet2", Excel1Sheet2.class));
        excelDataList.add(excelA);

        //excelB
        ExcelData excelB = new ExcelData();
        excelB.setFilename("excelB");//设置excelB的文件名
        excelB.setTemplateFilename("excel-template/excelB.xls");//设置excelB的模板
        //excelB-Excel2Sheet1
        List<Excel2Sheet1> excel2Sheet1s = new ArrayList<>();
        excel2Sheet1s.add(new Excel2Sheet1("阿紫", "群众"));
        excel2Sheet1s.add(new Excel2Sheet1("子安", "武当会员"));
        excelB.addShellData(new ExcelShellData<>(excel2Sheet1s, "Excel2Sheet1", Excel2Sheet1.class));//设置excelB的sheet数据
        excelDataList.add(excelB);
        return excelDataList;
    }
}
