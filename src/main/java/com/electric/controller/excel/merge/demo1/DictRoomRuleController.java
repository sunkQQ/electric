package com.electric.controller.excel.merge.demo1;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.electric.controller.excel.merge.demo1.util.CellLineRange;
import com.electric.controller.excel.merge.demo1.util.EasyExcel111Util;
import com.electric.controller.excel.merge.demo1.util.ExcelFillCelColumnMergeStrategy;
import com.electric.controller.excel.merge.demo1.util.ExcelFillCellRowMergeStrategy;

import lombok.extern.slf4j.Slf4j;

/**
 * 规则管理
 *
 * @author sunk
 * @date 2024/04/30
 */
@Lazy
@RestController
@RequestMapping(value = "/demo", produces = { "application/json;charset=UTF-8" })
@Slf4j
public class DictRoomRuleController {

    @PostMapping(value = "/exportMerge")
    public void exportMerge(HttpServletResponse response, HttpServletRequest request) {
        try {
            ServletOutputStream outputStream = response.getOutputStream();

            List<Demo> list = new ArrayList<>();
            buildData(list);

            EasyExcel111Util.setExportExcelFormat(response, request, "测试数据");

            //需要合并的列
            int[] mergeColumnIndex = { 0, 1, 2, 3, 4 };

            //设置第几行开始合并
            int mergeRowIndex = 1;

            // Excel单元格行合并处理策略
            ExcelFillCellRowMergeStrategy rowMergeStrategy = new ExcelFillCellRowMergeStrategy(mergeRowIndex, mergeColumnIndex);

            //列合并的工具实体类
            ArrayList<CellLineRange> cellLineRanges = new ArrayList<>();
            cellLineRanges.add(new CellLineRange(0, 2));
            // Excel单元格列合并处理策略
            ExcelFillCelColumnMergeStrategy celColumnMergeStrategy = new ExcelFillCelColumnMergeStrategy(0, cellLineRanges);

            List<CellWriteHandler> cellWriteHandlerList = Stream.of(celColumnMergeStrategy, rowMergeStrategy).collect(Collectors.toList());

            EasyExcel111Util.writeExcelMerge(outputStream, Demo.class, list, "测试数据", cellWriteHandlerList);

            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            log.error("楼盘估价数据导出excel Exception", e);
        }
    }

    @PostMapping(value = "/exportMergeTest")
    public void exportMergeTest(HttpServletResponse response, HttpServletRequest request) {
        try {
            ServletOutputStream outputStream = response.getOutputStream();

            List<List<Demo>> list = new ArrayList<>();
            buildListData(list);

            EasyExcel111Util.setExportExcelFormat(response, request, "KF楼盘估价");

            //需要合并的列
            int[] mergeColumnIndex = { 0, 1, 2, 3, 4 };

            //设置第几行开始合并
            int mergeRowIndex = 1;

            // Excel单元格行合并处理策略
            ExcelFillCellRowMergeStrategy rowMergeStrategy = new ExcelFillCellRowMergeStrategy(mergeRowIndex, mergeColumnIndex);

            //必须放到循环外，否则会刷新流
            ExcelWriter excelWriter = EasyExcel.write(outputStream).build();
            for (int i = 0; i < list.size(); i++) {
                //sheet页码
                int num = 1 + 1;

                //列合并的工具实体类
                ArrayList<CellLineRange> cellLineRanges = new ArrayList<>();
                cellLineRanges.add(new CellLineRange(0, 2));

                // Excel单元格列合并处理策略
                ExcelFillCelColumnMergeStrategy celColumnMergeStrategy = new ExcelFillCelColumnMergeStrategy(0, cellLineRanges);
                List<CellWriteHandler> cellWriteHandlerList = Stream.of(celColumnMergeStrategy, rowMergeStrategy).collect(Collectors.toList());
                EasyExcel111Util.writerSheetExcelMerge(excelWriter, Demo.class, list.get(i), num, "楼盘估价" + num, cellWriteHandlerList);
            }

            //刷新流
            excelWriter.finish();

            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            log.error("楼盘估价数据导出excel Exception", e);
        }
    }

    /**
     * 构建数据
     * @param list
     */
    private void buildListData(List<List<Demo>> list) {
        List<Demo> list1 = new ArrayList<>();
        List<Demo> list2 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Demo demo = new Demo();
            demo.setAppName("app1");
            demo.setCityName("app1");
            demo.setRegionName("app1");
            demo.setBusinessAreaName("深大");
            demo.setGardenName("大冲国际中心");
            demo.setBuildingName("一期");
            demo.setUnitName("A座");
            demo.setPrice(100000 + i);
            list1.add(demo);
        }

        for (int i = 0; i < 2; i++) {
            Demo demo = new Demo();
            demo.setAppName("app2");
            demo.setCityName("深圳");
            demo.setRegionName("南山区");
            demo.setBusinessAreaName("前海湾");
            demo.setGardenName("前海中心大厦");
            demo.setBuildingName("一期");
            demo.setUnitName("B座");
            demo.setPrice(100000 + i);
            list1.add(demo);
        }

        for (int i = 0; i < 2; i++) {
            Demo demo = new Demo();
            demo.setAppName("深圳");
            demo.setCityName("深圳");
            demo.setRegionName("深圳");
            demo.setBusinessAreaName("后海");
            demo.setGardenName("中国华润大厦");
            demo.setBuildingName("中国华润大厦");
            demo.setUnitName("A座");
            demo.setPrice(100000 + i);
            list2.add(demo);
        }

        for (int i = 0; i < 1; i++) {
            Demo demo = new Demo();
            demo.setAppName("app3");
            demo.setCityName("深圳");
            demo.setRegionName("宝安区");
            demo.setBusinessAreaName("壹方城");
            demo.setGardenName("壹方中心");
            demo.setBuildingName("一期");
            demo.setUnitName("A座");
            demo.setPrice(100000 + i);
            list2.add(demo);
        }
        list.add(list1);
        list.add(list2);
    }

    /**
     * 构建数据
     * @param list
     */
    private void buildData(List<Demo> list) {
        for (int i = 0; i < 5; i++) {
            Demo demo = new Demo();
            demo.setAppName("app1");
            demo.setCityName("app1");
            demo.setRegionName("app1");
            demo.setBusinessAreaName("深大");
            demo.setGardenName("大冲国际中心");
            demo.setBuildingName("一期");
            demo.setUnitName("A座");
            demo.setPrice(100000 + i);
            list.add(demo);
        }

        for (int i = 0; i < 2; i++) {
            Demo demo = new Demo();
            demo.setAppName("app2");
            demo.setCityName("深圳");
            demo.setRegionName("南山区");
            demo.setBusinessAreaName("前海湾");
            demo.setGardenName("前海中心大厦");
            demo.setBuildingName("一期");
            demo.setUnitName("B座");
            demo.setPrice(100000 + i);
            list.add(demo);
        }

        for (int i = 0; i < 2; i++) {
            Demo demo = new Demo();
            demo.setAppName("深圳");
            demo.setCityName("深圳");
            demo.setRegionName("深圳");
            demo.setBusinessAreaName("后海");
            demo.setGardenName("中国华润大厦");
            demo.setBuildingName("中国华润大厦");
            demo.setUnitName("A座");
            demo.setPrice(100000 + i);
            list.add(demo);
        }

        for (int i = 0; i < 1; i++) {
            Demo demo = new Demo();
            demo.setAppName("app3");
            demo.setCityName("深圳");
            demo.setRegionName("宝安区");
            demo.setBusinessAreaName("壹方城");
            demo.setGardenName("壹方中心");
            demo.setBuildingName("一期");
            demo.setUnitName("A座");
            demo.setPrice(100000 + i);
            list.add(demo);
        }
    }
}
