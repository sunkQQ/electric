package com.electric.controller.jiaofei;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.electric.param.jiaofei.tucheng.JiaofeiTuChengPayParam;
import com.electric.param.jiaofei.tucheng.JiaofeiTuChengTokenParam;
import com.electric.response.jiaofei.tucheng.JiaofeiTuChengBaseResult;
import com.electric.response.jiaofei.tucheng.JiaofeiTuChengDebtResult;
import com.electric.util.DateUtil;
import com.electric.util.Md5Util;
import com.electric.util.SnowflakeIdGenerator;

import lombok.extern.slf4j.Slf4j;

/**
 * 图城缴费接口
 *
 * @author sunk
 * @date 2024/11/13
 */
@Slf4j
@RestController
@RequestMapping("/service")
public class JiaofeiTuChengController {

    private final static List<JiaofeiTuChengDebtResult> LIST = new ArrayList<>();
    static {
        JiaofeiTuChengDebtResult detailList2 = new JiaofeiTuChengDebtResult();
        detailList2.setLibcode("HGZZ");
        detailList2.setFeetype("滞纳金");
        detailList2.setFee(new BigDecimal("0.4"));
        detailList2.setBarcode("000332208");
        detailList2.setTitle("盛放．半夏的暮光");
        detailList2.setAuthor("良石编著");
        detailList2.setIsbn("978-7-5168-0385-1");
        detailList2.setPublisher("台海出版社");
        detailList2.setCallno("I247.7/19");
        detailList2.setLoandate("2024-09-24 19:29:52");
        detailList2.setRegtime("2024-10-28 09:58:32");
        detailList2.setTranid("202410280958327814112");
        LIST.add(detailList2);

        JiaofeiTuChengDebtResult detailList1 = new JiaofeiTuChengDebtResult();
        detailList1.setLibcode("HGZZ");
        detailList1.setFeetype("滞纳金");
        detailList1.setFee(new BigDecimal("10.0"));
        detailList1.setBarcode("000332221");
        detailList1.setTitle("福尔摩斯探案集");
        detailList1.setAuthor("阿瑟·柯南·道尔");
        detailList1.setIsbn("978-7-5168-0385-2");
        detailList1.setPublisher("台海出版社");
        detailList1.setCallno("I247.7/20");
        detailList1.setLoandate("2024-09-24 19:29:52");
        detailList1.setRegtime("2024-10-28 09:58:32");
        detailList1.setTranid("202410280958327814113");
        //LIST.add(detailList1);
    }

    @RequestMapping(value = "/barcode/token")
    public String getToken(JiaofeiTuChengTokenParam param) {
        String token1 = Md5Util.md5(param.getAppid() + DateUtil.getTimeNow());
        String token2 = Md5Util.md5(param.getAppid() + DateUtil.getTimeNow());
        JSONObject json = new JSONObject();
        json.put("success", true);

        JSONArray array = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", "R00065");
        jsonObject.put("time", DateUtil.dateToString(DateUtil.addDays(1L)));
        jsonObject.put("message", "认证成功");
        jsonObject.put("token", token1 + "-" + token2);

        array.add(jsonObject);

        json.put("messagelist", array);
        return json.toJSONString();
    }

    @RequestMapping(value = "/reader/searchreaderlist")
    public String getUserInfo(String token, String selecttype, String queryvalue) {

        JSONObject json = new JSONObject();
        json.put("success", true);

        JSONArray array = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("rdname", "测试");
        jsonObject.put("rdsex", "男");
        jsonObject.put("rdClusterCode", null);
        jsonObject.put("rdintime", "2024-09-14");
        jsonObject.put("rdloginid", null);
        jsonObject.put("rdlib", "HGZZ");
        jsonObject.put("rdemail", "");
        jsonObject.put("rdcertify", "150403200601163615");
        jsonObject.put("rdlibname", "黄冈中等职业学校图书馆");
        jsonObject.put("rdcfstate", 1);
        jsonObject.put("rdtype", "XS-GZ2");
        jsonObject.put("operator", "");
        jsonObject.put("rdid", "2241484");
        jsonObject.put("rdphone", "18748059742");
        jsonObject.put("rdstartdate", "2024-09-14");
        jsonObject.put("rdenddate", "2030-10-23");
        array.add(jsonObject);

        json.put("pagedata", array);
        return json.toJSONString();
    }

    @RequestMapping(value = "/reader/searchdebt", method = RequestMethod.POST)
    public String getFeeItem(String token, String rdid) {
        JSONObject json = new JSONObject();

        if (CollectionUtils.isEmpty(LIST)) {
            json.put("success", false);
            JSONArray array = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", "R00092");
            jsonObject.put("message", "没有找到欠款记录！");
            array.add(jsonObject);
            json.put("messagelist", array);
            return json.toJSONString();
        }
        json.put("success", true);
        BigDecimal total = BigDecimal.ZERO;
        for (JiaofeiTuChengDebtResult debt : LIST) {
            total = total.add(debt.getFee());
        }
        json.put("totaldebt", total);
        json.put("debtdetails", JSONArray.parseArray(JSONArray.toJSONString(LIST)));

        return json.toJSONString();
    }

    @RequestMapping(value = "/reader/paymoney", method = RequestMethod.POST)
    public String payMoney(JiaofeiTuChengPayParam param) {
        BigDecimal total = LIST.stream().map(JiaofeiTuChengDebtResult::getFee).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        if (total.compareTo(new BigDecimal(param.getMoney())) != 0) {
            JSONObject json = new JSONObject();
            json.put("success", false);

            List<JiaofeiTuChengBaseResult> list = new ArrayList<>();
            list.add(new JiaofeiTuChengBaseResult("R00021", "金额不匹配"));

            json.put("messagelist", JSONArray.toJSONString(list));
            return json.toJSONString();
        }
        LIST.clear();
        JSONObject json = new JSONObject();
        json.put("success", true);

        List<JiaofeiTuChengBaseResult> list = new ArrayList<>();
        list.add(new JiaofeiTuChengBaseResult("R00093", "支付欠款成功！"));

        json.put("messagelist", JSONArray.toJSONString(list));
        return json.toJSONString();
    }

    @RequestMapping(value = "/barcode/refreshDeBt")
    public String refreshDeBt(String str) {
        JiaofeiTuChengDebtResult detailList2 = new JiaofeiTuChengDebtResult();
        detailList2.setLibcode("HGZZ");
        detailList2.setFeetype("滞纳金");
        detailList2.setFee(new BigDecimal("0.4"));
        detailList2.setBarcode("000332208");
        detailList2.setTitle("盛放．半夏的暮光");
        detailList2.setAuthor("良石编著");
        detailList2.setIsbn("978-7-5168-0385-1");
        detailList2.setPublisher("台海出版社");
        detailList2.setCallno("I247.7/19");
        detailList2.setLoandate("2024-09-24 19:29:52");
        detailList2.setRegtime("2024-10-28 09:58:32");
        SnowflakeIdGenerator idWorker = new SnowflakeIdGenerator(1, 1);
        detailList2.setTranid(String.valueOf(idWorker.nextId()));
        LIST.add(detailList2);
        if ("2".equals(str)) {

            JiaofeiTuChengDebtResult detailList1 = new JiaofeiTuChengDebtResult();
            detailList1.setLibcode("HGZZ");
            detailList1.setFeetype("滞纳金");
            detailList1.setFee(new BigDecimal("10.0"));
            detailList1.setBarcode("000332221");
            detailList1.setTitle("福尔摩斯探案集");
            detailList1.setAuthor("阿瑟·柯南·道尔");
            detailList1.setIsbn("978-7-5168-0385-2");
            detailList1.setPublisher("台海出版社");
            detailList1.setCallno("I247.7/20");
            detailList1.setLoandate("2024-09-24 19:29:52");
            detailList1.setRegtime("2024-10-28 09:58:32");
            detailList1.setTranid(String.valueOf(idWorker.nextId()));
            LIST.add(detailList1);
        }
        return "success";
    }

}
