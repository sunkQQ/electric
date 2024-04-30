package com.electric.controller.excel.merge.demo2;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

/**
 * @author sunk
 * @date 2024/04/30
 */
@RestController
@AllArgsConstructor
@RequestMapping("/plot")
public class PlotController {

    @GetMapping("/exportPlotManage")
    public void exportPlotManage(HttpServletResponse response) {
        List<PlotBaseExcelVO> plotBaseVO = buildData();

        try {
            //需要合并的列
            //int[] mergeColumeIndex = { 0, 1, 2 };
            int[] mergeColumeIndex = { 0, 1, 2, 3, 4, 5, 6 };
            //从第二行后开始合并
            int mergeRowIndex = 1;
            //调用合并单元格工具，当mergeColumeIndex 为空，mergeRowIndex为-1时候，代表不合并
            ExcelMergeUtil.exportExcel(response, "name", plotBaseVO, PlotBaseExcelVO.class, mergeColumeIndex, mergeRowIndex);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<PlotBaseExcelVO> buildData() {
        List<PlotBaseExcelVO> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            PlotBaseExcelVO demo = new PlotBaseExcelVO();
            demo.setId(2204523043230187531L);
            demo.setName("app1");
            demo.setRegionName("app1");
            demo.setAddress("深大");
            if (i < 3) {
                demo.setPlotCode("大冲国际中心");
            } else {
                demo.setPlotCode("大冲国际中心1");
            }
            demo.setTypeName("一期");
            demo.setPlotName(new Date());
            demo.setArea(100000 + i + "");
            list.add(demo);
        }

        for (int i = 0; i < 2; i++) {
            PlotBaseExcelVO demo = new PlotBaseExcelVO();
            demo.setName("app2");
            demo.setRegionName("深圳");
            demo.setAddress("前海湾");
            demo.setPlotCode("前海中心大厦");
            demo.setTypeName("一期");
            demo.setPlotName(new Date());
            demo.setArea(100000 + i + "");
            list.add(demo);
        }

        for (int i = 0; i < 2; i++) {
            PlotBaseExcelVO demo = new PlotBaseExcelVO();
            demo.setName("深圳");
            demo.setRegionName("深圳");
            demo.setAddress("后海");
            demo.setPlotCode("中国华润大厦");
            demo.setTypeName("中国华润大厦");
            demo.setPlotName(new Date());
            demo.setArea(100000 + i + "");
            list.add(demo);
        }

        for (int i = 0; i < 1; i++) {
            PlotBaseExcelVO demo = new PlotBaseExcelVO();
            demo.setName("app3");
            demo.setRegionName("宝安区");
            demo.setAddress("壹方城");
            demo.setPlotCode("壹方中心");
            demo.setTypeName("一期");
            demo.setPlotName(new Date());
            demo.setArea(100000 + i + "");
            list.add(demo);
        }
        return list;
    }
}
