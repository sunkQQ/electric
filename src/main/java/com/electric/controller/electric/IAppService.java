package com.electric.controller.electric;

import java.util.Date;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.electric.response.electric.isims.GetYEInfoResult;
import com.electric.util.DateUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 常工接口
 *
 * @author sunk
 * @date 2024/04/16
 */
@Slf4j
@WebService(name = "IAppService", targetNamespace = "www.cdgf.com")
public class IAppService {

    /** 
     * 查询余额
     * @param signkey signkey
     * @param roomdm 房间编码
     *
     * @return 结果
     */
    @WebMethod(action = "/GetYEInfo")
    @WebResult(name = "GetYEInfoResult")
    public String GetYEInfo(@WebParam(name = "signkey", targetNamespace = "www.cdgf.com") String signkey,
                            @WebParam(name = "roomdm", targetNamespace = "www.cdgf.com") String roomdm) {
        log.info("查询余额， 请求参数--> roomdm:{}, signkey:{}", roomdm, signkey);
        GetYEInfoResult result = new GetYEInfoResult();
        result.setCode("0");
        result.setMsg("success");

        JSONObject data = new JSONObject();
        data.put("roomdm", "123456");

        JSONArray topUpTypeList = new JSONArray();

        JSONObject topUpType1 = new JSONObject();
        topUpType1.put("cztype", "1096");
        topUpType1.put("mdname", "热水");
        topUpTypeList.add(topUpType1);

        JSONObject topUpType2 = new JSONObject();
        topUpType2.put("cztype", "1097");
        topUpType2.put("mdname", "天然气");
        topUpTypeList.add(topUpType2);

        JSONObject topUpType3 = new JSONObject();
        topUpType3.put("cztype", "1098");
        topUpType3.put("mdname", "照明用电");
        topUpTypeList.add(topUpType3);

        JSONObject topUpType4 = new JSONObject();
        topUpType4.put("cztype", "1099");
        topUpType4.put("mdname", "空调用电");
        topUpTypeList.add(topUpType4);

        JSONObject topUpType5 = new JSONObject();
        topUpType5.put("cztype", "1100");
        topUpType5.put("mdname", "其他");
        topUpTypeList.add(topUpType5);

        JSONObject topUpType6 = new JSONObject();
        topUpType6.put("cztype", "1101");
        topUpType6.put("mdname", "燃气");
        topUpTypeList.add(topUpType6);
        data.put("topUpTypeList", topUpTypeList);

        JSONArray sylList = new JSONArray();
        JSONObject syl1 = new JSONObject();
        syl1.put("SYL", "92337.26");
        syl1.put("mdtype", "1096");
        syl1.put("mdname", "热水");
        syl1.put("SYLJE", 60019.21);
        syl1.put("SYBZJE", "0.00");
        syl1.put("SYBZ", "0.00");
        sylList.add(syl1);

        JSONObject syl2 = new JSONObject();
        syl2.put("SYL", "1.0");
        syl2.put("mdtype", "1097");
        syl2.put("mdname", "天然气");
        syl2.put("SYLJE", "2.0");
        syl2.put("SYBZJE", "0.00");
        syl2.put("SYBZ", "0.00");
        sylList.add(syl2);

        JSONObject syl3 = new JSONObject();
        syl3.put("SYL", "3.0");
        syl3.put("mdtype", "1098");
        syl3.put("mdname", "照明用电");
        syl3.put("SYLJE", "5.0");
        syl3.put("SYBZJE", "0.00");
        syl3.put("SYBZ", "0.00");
        sylList.add(syl3);

        JSONObject syl4 = new JSONObject();
        syl4.put("SYL", "3.0");
        syl4.put("mdtype", "1099");
        syl4.put("mdname", "空调用电");
        syl4.put("SYLJE", "5.0");
        syl4.put("SYBZJE", "0.00");
        syl4.put("SYBZ", "0.00");
        sylList.add(syl4);

        JSONObject syl5 = new JSONObject();
        syl5.put("SYL", "3.0");
        syl5.put("mdtype", "1100");
        syl5.put("mdname", "其他");
        syl5.put("SYLJE", "5.0");
        syl5.put("SYBZJE", "0.00");
        syl5.put("SYBZ", "0.00");
        sylList.add(syl5);

        JSONObject syl6 = new JSONObject();
        syl6.put("SYL", "3.0");
        syl6.put("mdtype", "1101");
        syl6.put("mdname", "燃气");
        syl6.put("SYLJE", "5.0");
        syl6.put("SYBZJE", "0.00");
        syl6.put("SYBZ", "0.00");
        sylList.add(syl6);

        data.put("sylList", sylList);
        result.setData(data);
        return JSONObject.toJSONString(result);
    }

    int i = 0;

    /**
     * 获取房间硬件状态
     * @param roomdm 房间编码
     * @param mdtype 计量控制类型
     * @param type 1
     * @param signkey 签名key
     * @return 结果
     */
    @WebMethod(operationName = "GetMeterStu")
    @WebResult(name = "GetMeterStuResult")
    public String GetMeterStu(@WebParam(name = "roomdm", targetNamespace = "www.cdgf.com") String roomdm,
                              @WebParam(name = "mdtype", targetNamespace = "www.cdgf.com") String mdtype,
                              @WebParam(name = "type", targetNamespace = "www.cdgf.com") String type,
                              @WebParam(name = "signkey", targetNamespace = "www.cdgf.com") String signkey) {
        log.info("获取房间硬件状态， 请求参数--> roomdm:{}, signkey:{}, mdtype:{}, type:{}", roomdm, signkey, mdtype, type);
        JSONObject json = new JSONObject();
        if (i % 2 == 0) {
            json.put("status", "正常");
        } else {
            json.put("status", "软件关断");
        }
        i++;
        return json.toString();
    }

    /**
     * 充值
     * @param signkey signkey
     * @param orderid 订单号
     * @param cztype 充值类型
     * @param roomdm 房间编码
     * @param money 充值金额
     * @return 结果
     */
    @WebMethod(operationName = "TopUp")
    @WebResult(name = "TopUpResult")
    public String TopUp(@WebParam(name = "signkey", targetNamespace = "www.cdgf.com") String signkey,
                        @WebParam(name = "orderid", targetNamespace = "www.cdgf.com") String orderid,
                        @WebParam(name = "cztype", targetNamespace = "www.cdgf.com") String cztype,
                        @WebParam(name = "roomdm", targetNamespace = "www.cdgf.com") String roomdm,
                        @WebParam(name = "money", targetNamespace = "www.cdgf.com") String money) {
        log.info("充值， 请求参数--> roomdm:{}, signkey:{}, orderid:{}, cztype:{}, money:{}", roomdm, signkey, orderid, cztype, money);
        JSONObject json = new JSONObject();
        json.put("code", 0);
        json.put("msg", "充值成功！");
        return json.toString();
    }

    /**
     * 房间用量查询
     * @param signkey 签名key
     * @param roomdm 房间信息
     * @param fromDate 起始时间
     * @param toDate 结束时间
     * @param mdtype 计量控制类型
     * @return 结果
     */
    @WebMethod(operationName = "GetDayUsedRecords")
    @WebResult(name = "GetDayUsedRecordsResult")
    public String GetDayUsedRecords(@WebParam(name = "signkey", targetNamespace = "www.cdgf.com") String signkey,
                                    @WebParam(name = "roomdm", targetNamespace = "www.cdgf.com") String roomdm,
                                    @WebParam(name = "fromDate", targetNamespace = "www.cdgf.com") String fromDate,
                                    @WebParam(name = "toDate", targetNamespace = "www.cdgf.com") String toDate,
                                    @WebParam(name = "mdtype", targetNamespace = "www.cdgf.com") String mdtype) {
        log.info("用量查询， 请求参数--> roomdm:{}, signkey:{}, fromDate:{}, toDate:{}, mdtype:{}", roomdm, signkey, fromDate, toDate, mdtype);
        JSONObject json = new JSONObject();
        json.put("code", 0);
        json.put("msg", "充值成功！");

        JSONArray array = new JSONArray();
        for (int i = 0; i < 20; i++) {
            JSONObject user = new JSONObject();
            user.put("roomdm", "1001");
            user.put("datetime", "2024-04-18");
            user.put("used", "2");
            /* if (i % 2 == 0) {
                user.put("unit", "吨");
            }*/
            array.add(user);
        }

        json.put("data", array);
        return json.toString();
    }

    /**
     * 常工获取房间充值记录
     * @param signkey 签名类型
     * @param roomdm 房间编码
     * @param fromDate 超始时间
     * @param toDate 结束时间
     * @param cztype 充值类型
     * @return 结果
     */
    @WebMethod(action = "/GetBuyRecord")
    @WebResult(name = "GetBuyRecordResult")
    public String GetBuyRecord(@WebParam(name = "signkey", targetNamespace = "www.cdgf.com") String signkey,
                               @WebParam(name = "roomdm", targetNamespace = "www.cdgf.com") String roomdm,
                               @WebParam(name = "fromDate", targetNamespace = "www.cdgf.com") String fromDate,
                               @WebParam(name = "toDate", targetNamespace = "www.cdgf.com") String toDate,
                               @WebParam(name = "cztype", targetNamespace = "www.cdgf.com") String cztype) {
        log.info("常工获取房间充值记录， 请求参数--> roomdm:{}, signkey:{}, fromDate:{}, toDate:{}, cztype:{}", roomdm, signkey, fromDate, toDate, cztype);
        JSONObject json = new JSONObject();
        json.put("code", 0);
        json.put("msg", "充值成功！");
        JSONArray array = new JSONArray();

        for (int i = 0; i < 20; i++) {
            JSONObject user = new JSONObject();
            user.put("roomdm", roomdm);
            user.put("datetime", DateUtil.dateToString(DateUtil.getNextDayDate(new Date(), -i), DateUtil.DAY_FORMAT));
            user.put("buyusingtpe", "照明用电");
            user.put("money", 2 + i);
            array.add(user);
        }
        json.put("data", array);
        return json.toString();
    }

    /**
     * 电表控制
     * @param roomdm 房间编码
     * @param mdtype 计量控控制类型
     * @param controltype 控制类型 0、软件关断(关闭)  1、硬件控制(打开) 2、软件打开(打开) 3、过流复位 4、负载复位
     * @param operno 没用空着
     * @param signkey signkey
     * @return 结果
     */
    @WebMethod(operationName = "MeterControl")
    @WebResult(name = "MeterControlResult")
    public String MeterControl(@WebParam(name = "roomdm", targetNamespace = "www.cdgf.com") String roomdm,
                               @WebParam(name = "mdtype", targetNamespace = "www.cdgf.com") String mdtype,
                               @WebParam(name = "controltype", targetNamespace = "www.cdgf.com") String controltype,
                               @WebParam(name = "operno", targetNamespace = "www.cdgf.com") String operno,
                               @WebParam(name = "signkey", targetNamespace = "www.cdgf.com") String signkey) {
        log.info("电表控制， 请求参数--> roomdm:{}, signkey:{}, mdtype:{}, controltype:{}, operno:{}, ", roomdm, signkey, mdtype, controltype, operno);
        JSONObject json = new JSONObject();
        json.put("returncode", "1");
        json.put("opno", "正常");
        return json.toString();
    }

    public static void main(String[] args) {
        Endpoint.publish("http://192.168.88.236:8080/AppService.svc", new IAppService());
        System.out.println("WebService is published!");
    }
}
