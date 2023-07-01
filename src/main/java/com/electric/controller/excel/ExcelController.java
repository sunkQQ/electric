package com.electric.controller.excel;

import com.alibaba.excel.EasyExcel;
import com.electric.controller.excel.adapter.CommentWriteHandler;
import com.electric.controller.excel.listener.SendListListener;
import com.electric.controller.excel.util.ExcelUtils;
import com.electric.controller.excel.vo.ExcelError;
import com.electric.controller.excel.vo.SendListExcel;
import com.electric.util.DateUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author sunk
 * @date 2023/06/28
 */
@RestController
@RequestMapping("/excel/test")
public class ExcelController {
    @Autowired
    GsonBuilder                 gsonBuilder;

    private static final String PREFIX          = "easyExcel_";
    public static final String  SEND_LIST       = "sendList_";
    public static final String  SEND_LIST_ERROR = "_error";
    public static final Long    EXPIRE_TIME     = 60 * 10L;

    private Map<String, Object> map             = new HashMap<>();

    @PostMapping(value = "/importExcel")
    public String importExcel(HttpServletResponse response, @RequestParam(value = "file", required = true) MultipartFile file) throws IOException {

        String start = DateUtil.getTimeNow();
        System.out.println("start：" + start);
        SendListListener sendListListener = new SendListListener();
        // 读取到的数据
        List<SendListExcel> listExcels = EasyExcel.read(file.getInputStream(), SendListExcel.class, sendListListener).sheet().doReadSync();

        //List<SendListExcel> listExcels = sendListListener.getListExcels();
        if (sendListListener.getExcelErrorMap().size() > 0) {
            String uuid = UUID.randomUUID().toString();
            //String key = PREFIX + SEND_LIST + uuid;

            //map.put(key, gson.toJson(listExcels));
            //map.put(key + SEND_LIST_ERROR, gson.toJson(sendListListener.getExcelErrorMap()));

            String fileName = DateUtil.getTimeNow();
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

            if (listExcels != null && sendListListener.getExcelErrorMap() != null) {
                CommentWriteHandler commentWriteHandler = new CommentWriteHandler();
                Map<Integer, List<ExcelError>> errorMap = sendListListener.getExcelErrorMap();
                commentWriteHandler.setExcelErrorMap(errorMap);

                EasyExcel.write(response.getOutputStream(), SendListExcel.class).inMemory(Boolean.TRUE).sheet("sheet1")
                    //注册批注拦截器
                    .registerWriteHandler(commentWriteHandler).doWrite(listExcels);
            }

            System.out.println("size：" + listExcels.size());
            System.out.println("end：" + DateUtil.getTimeNow());
            return uuid;
        }
        //listExcels.forEach(System.out::println);
        System.out.println("size：" + listExcels.size());

        System.out.println("end：" + DateUtil.getTimeNow());
        return "success";
    }

    /**
     * 下载Excel模板
     */
    @GetMapping("/downloadExcelTemplate")
    public void export(HttpServletResponse response) throws Exception {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = "发送名单管理模板.xlsx";
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        EasyExcel.write(response.getOutputStream(), SendListExcel.class).sheet("sheet1").doWrite(new ArrayList<SendListExcel>());
    }

    /**
     * 下载失败文件
     * @param response
     * @param uuid
     * @throws IOException
     */
    @GetMapping("downloadErrorExcel")
    public void downloadErrorExcel(HttpServletResponse response, @RequestParam(value = "uuid", required = true) String uuid) throws IOException {
        System.out.println("downloadError-start：" + DateUtil.getTimeNow());
        SimpleDateFormat fDate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String fileName = fDate.format(new Date());
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        CommentWriteHandler commentWriteHandler = new CommentWriteHandler();
        String key = PREFIX + SEND_LIST + uuid;
        String listExcelJson = (String) map.get(key);
        String listExcelErrorlJson = (String) map.get(key + SEND_LIST_ERROR);
        Gson gson = gsonBuilder.create();
        if (listExcelJson != null && listExcelErrorlJson != null) {
            Type listExcelJsonType = new TypeToken<List<SendListExcel>>() {
            }.getType();
            List<SendListExcel> sendListExcels = gson.fromJson(listExcelJson, listExcelJsonType);
            Type listExcelErrorlJsonType = new TypeToken<Map<Integer, List<ExcelError>>>() {
            }.getType();
            Map<Integer, List<ExcelError>> errorMap = gson.fromJson(listExcelErrorlJson, listExcelErrorlJsonType);
            commentWriteHandler.setExcelErrorMap(errorMap);
            EasyExcel.write(response.getOutputStream(), SendListExcel.class).inMemory(Boolean.TRUE).sheet("sheet1")
                //注册批注拦截器
                .registerWriteHandler(commentWriteHandler).doWrite(sendListExcels);
        }
        System.out.println("downloadError-end：" + DateUtil.getTimeNow());
    }

    /**
     * 下载数据
     * @param response
     * @return
     * @throws IOException
     */
    @PostMapping(value = "/downloadExcel")
    public String downloadExcel(HttpServletResponse response) throws IOException {
        System.out.println("start：" + DateUtil.getTimeNow());
        List<SendListExcel> list = new ArrayList<>();
        for (int i = 0; i <= 100000; i++) {
            SendListExcel excel = new SendListExcel();
            excel.setAccount(String.valueOf(2307620152773775371L + i));
            excel.setAccountType("msg");
            excel.setTemplateCode("code " + i);
            list.add(excel);
        }
        //ExcelExportUtil.exportFile(list, "sheet1", SendListExcel.class, "dddddd", response);
        ExcelUtils.exportExcel(list, "dddddd.xlsx", SendListExcel.class, response);
        System.out.println("end：" + DateUtil.getTimeNow());
        return "success";
    }

}
