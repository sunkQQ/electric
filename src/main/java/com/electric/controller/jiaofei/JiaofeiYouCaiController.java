package com.electric.controller.jiaofei;

import com.alibaba.fastjson.JSONObject;
import com.electric.constant.Numbers;
import com.electric.param.jiaofei.youcai.JiaofeiYoucaiAddOrderParam;
import com.electric.param.jiaofei.youcai.JiaofeiYoucaiBaseParam;
import com.electric.param.jiaofei.youcai.JiaofeiYoucaiStudentParam;
import com.electric.param.jiaofei.youcai.JiaofeiYoucaiUpdateOrderParam;
import com.electric.response.jiaofei.youcai.JiaofeiYoucaiBaseResult;
import com.electric.response.jiaofei.youcai.JiaofeiYoucaiGetFeeItemResult;
import com.electric.response.jiaofei.youcai.JiaofeiYoucaiStudentInfoResult;
import com.electric.util.Md5Util;
import com.electric.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 天津学院友财接口
 *
 * @author sunk
 * @date 2022/08/05
 */
@Slf4j
@RestController
@RequestMapping("/API")
public class JiaofeiYouCaiController {


    private static final String KEY = "123456";


    private static JiaofeiYoucaiGetFeeItemResult result = new JiaofeiYoucaiGetFeeItemResult("00", "成功");

    static {
        result.setStudentCode("YM201");
        result.setStudentName("孙康");

        List<JiaofeiYoucaiGetFeeItemResult.FeeItems> feeItemsList = new ArrayList<>();

        List<JiaofeiYoucaiGetFeeItemResult.Items> itemsList = new ArrayList<>();
        JiaofeiYoucaiGetFeeItemResult.Items items1 = new JiaofeiYoucaiGetFeeItemResult.Items();
        items1.setFeeId("bs-333450");
        items1.setIsMustPay(Boolean.TRUE);
        items1.setYear(2022);
        items1.setProjectCode("01");
        items1.setProjectName("学费");
        items1.setFeeRangCode("001");
        items1.setFeeRangName("学年");
        items1.setFee(new BigDecimal("0.01"));
        items1.setBankAccount("485858696");
        items1.setFeeType("0");
        //items1.setOrderNo("test001");
        itemsList.add(items1);
        JiaofeiYoucaiGetFeeItemResult.Items items2 = new JiaofeiYoucaiGetFeeItemResult.Items();
        items2.setFeeId("bs-333451");
        items2.setIsMustPay(Boolean.TRUE);
        items2.setYear(2022);
        items2.setProjectCode("02");
        items2.setProjectName("学2费");
        items2.setFeeRangCode("001");
        items2.setFeeRangName("学年");
        items2.setFee(new BigDecimal("0.02"));
        items2.setBankAccount("485858696");
        items2.setFeeType("0");
        itemsList.add(items2);
        JiaofeiYoucaiGetFeeItemResult.FeeItems feeItems = new JiaofeiYoucaiGetFeeItemResult.FeeItems();
        feeItems.setYear("202211");
        feeItems.setMessage("学杂费");
        feeItems.setMessage("必收项目");
        feeItems.setItems(itemsList);
        feeItemsList.add(feeItems);

        result.setFeeItems(feeItemsList);
    }

    /**
     * 获取学生信息
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/GetStudentInfo", method = RequestMethod.POST)
    public String getStudentInfo(JiaofeiYoucaiBaseParam param) {
        if (StringUtils.isBlank(param.getJson()) || StringUtils.isBlank(param.getMerchantno()) || StringUtils.isBlank(param.getSignature())) {
            return JSONObject.toJSONString(new JiaofeiYoucaiBaseResult("01", "验签失败"));
        }
        String sign = getSign(param.getJson(), KEY);
        if (!sign.equalsIgnoreCase(param.getSignature())) {
            return JSONObject.toJSONString(new JiaofeiYoucaiBaseResult("01", "验签失败"));
        }
        JiaofeiYoucaiStudentParam studentParam = JSONObject.parseObject(param.getJson(), JiaofeiYoucaiStudentParam.class);
        //if (studentParam.getStudentCode().startsWith("YM")) {
        JiaofeiYoucaiStudentInfoResult studentInfo = new JiaofeiYoucaiStudentInfoResult("00", "成功");
        studentInfo.setSexual("男");
        studentInfo.setStudentCode("YM201");
        studentInfo.setIdCard("41022520000510571X");
        studentInfo.setStudentName("孙康");
        return JSONObject.toJSONString(studentInfo);
        //}
        //return JSONObject.toJSONString(new JiaofeiYoucaiBaseResult("01", "学号不正确"));
    }

    /**
     * 获取学生待缴信息
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/GetFeeItem", method = RequestMethod.POST)
    public String getFeeItem(JiaofeiYoucaiBaseParam param) {
        if (StringUtils.isBlank(param.getJson()) || StringUtils.isBlank(param.getMerchantno()) || StringUtils.isBlank(param.getSignature())) {
            return JSONObject.toJSONString(new JiaofeiYoucaiBaseResult("01", "验签失败"));
        }
        String sign = getSign(param.getJson(), KEY);
        if (!sign.equalsIgnoreCase(param.getSignature())) {
            return JSONObject.toJSONString(new JiaofeiYoucaiBaseResult("01", "验签失败"));
        }
        JiaofeiYoucaiStudentParam studentParam = JSONObject.parseObject(param.getJson(), JiaofeiYoucaiStudentParam.class);
        //if (studentParam.getStudentCode().startsWith("YM")) {
        //JiaofeiYoucaiGetFeeItemResult result = new JiaofeiYoucaiGetFeeItemResult("00", "成功");

        return JSONObject.toJSONString(result);
        //}
        //
        //return JSONObject.toJSONString(new JiaofeiYoucaiBaseResult("01", "学号不正确"));
    }

    /**
     * 下订单
     *
     * @return
     */
    @RequestMapping(value = "/AddOrder", method = RequestMethod.POST)
    public String addOrder(JiaofeiYoucaiBaseParam param) {
        if (StringUtils.isBlank(param.getJson()) || StringUtils.isBlank(param.getMerchantno()) || StringUtils.isBlank(param.getSignature())) {
            return JSONObject.toJSONString(new JiaofeiYoucaiBaseResult("01", "验签失败"));
        }
        String sign = getSign(param.getJson(), KEY);
        if (!sign.equalsIgnoreCase(param.getSignature())) {
            return JSONObject.toJSONString(new JiaofeiYoucaiBaseResult("01", "验签失败"));
        }
        String orderNo = StringUtil.newGuid20();
        JiaofeiYoucaiAddOrderParam orderParam = JSONObject.parseObject(param.getJson(), JiaofeiYoucaiAddOrderParam.class);
        for (JiaofeiYoucaiGetFeeItemResult.FeeItems feeitem : result.getFeeItems()) {
            for (JiaofeiYoucaiGetFeeItemResult.Items items : feeitem.getItems()) {
                if (items.getFeeId().equalsIgnoreCase(orderParam.getPayItems().get(Numbers.INT_0).getFeeId())) {
                    items.setOrderNo(orderNo);
                }
            }
        }
        return JSONObject.toJSONString(new JiaofeiYoucaiBaseResult("00", orderNo));
    }

    /**
     * 更新订单
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/UpdateOrder", method = RequestMethod.POST)
    public String updateOrder(JiaofeiYoucaiBaseParam param) {
        if (StringUtils.isBlank(param.getJson()) || StringUtils.isBlank(param.getMerchantno()) || StringUtils.isBlank(param.getSignature())) {
            return JSONObject.toJSONString(new JiaofeiYoucaiBaseResult("01", "验签失败"));
        }
        String sign = getSign(param.getJson(), KEY);
        if (!sign.equalsIgnoreCase(param.getSignature())) {
            return JSONObject.toJSONString(new JiaofeiYoucaiBaseResult("01", "验签失败"));
        }
        JiaofeiYoucaiUpdateOrderParam updateOrderParam = JSONObject.parseObject(param.getJson(), JiaofeiYoucaiUpdateOrderParam.class);
        for (JiaofeiYoucaiGetFeeItemResult.FeeItems feeitem : result.getFeeItems()) {
            for (JiaofeiYoucaiGetFeeItemResult.Items items : feeitem.getItems()) {
                if (items.getOrderNo().equalsIgnoreCase(updateOrderParam.getOrderNo())) {
                    feeitem.getItems().remove(items);
                }
            }
        }
        return JSONObject.toJSONString(new JiaofeiYoucaiBaseResult("00", "成功"));
    }

    /**
     * 加密
     *
     * @param json
     * @param key
     * @return
     */
    private String getSign(String json, String key) {
        return Md5Util.md5(json + key).toLowerCase();
    }
}
