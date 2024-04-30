package com.electric.controller.electric;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.ws.Endpoint;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.electric.response.electric.isims.GetYEInfoResult;

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
     * @param signkey
     * @param roomdm
     * @return
     */
    @WebMethod(operationName = "GetYEInfo")
    @WebResult(name = "GetYEInfoResult", targetNamespace = "www.cdgf.com")
    public String GetYEInfo(@WebParam(name = "signkey") String signkey, @WebParam(name = "roomdm") String roomdm) {
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

        /*        JSONObject topUpType2 = new JSONObject();
        topUpType2.put("cztype", "1097");
        topUpType2.put("mdname", "天然气");
        topUpTypeList.add(topUpType2);*/

        JSONObject topUpType3 = new JSONObject();
        topUpType3.put("cztype", "1098");
        topUpType3.put("mdname", "照明用电");
        topUpTypeList.add(topUpType3);

        data.put("topUpTypeList", topUpTypeList);

        JSONArray sylList = new JSONArray();
        JSONObject syl1 = new JSONObject();
        syl1.put("SYL", "0.0");
        syl1.put("mdtype", "1096");
        syl1.put("mdname", "热水");
        syl1.put("SYLJE", "0.0");
        syl1.put("SYBZJE", "0.00");
        syl1.put("SYBZ", "0.00");
        sylList.add(syl1);

        JSONObject syl2 = new JSONObject();
        syl2.put("SYL", "0.0");
        syl2.put("mdtype", "1097");
        syl2.put("mdname", "天然气");
        syl2.put("SYLJE", "0.0");
        syl2.put("SYBZJE", "0.00");
        syl2.put("SYBZ", "0.00");
        sylList.add(syl2);

        JSONObject syl3 = new JSONObject();
        syl3.put("SYL", "0.0");
        syl3.put("mdtype", "1098");
        syl3.put("mdname", "照明用电");
        syl3.put("SYLJE", "0.0");
        syl3.put("SYBZJE", "0.00");
        syl3.put("SYBZ", "0.00");
        sylList.add(syl3);

        data.put("sylList", sylList);
        result.setData(data);
        return JSONObject.toJSONString(result);
    }

    /**
     * 获取房间硬件状态
     * @param roomdm
     * @param mdtype
     * @param type
     * @param signkey
     * @return
     */
    @WebMethod(operationName = "GetMeterStu")
    public @WebResult(name = "GetMeterStuResult") String GetMeterStu(@WebParam(name = "roomdm") String roomdm,
                                                                     @WebParam(name = "mdtype") String mdtype, @WebParam(name = "type") String type,
                                                                     @WebParam(name = "signkey") String signkey) {
        log.info("获取房间硬件状态， 请求参数--> roomdm:{}, signkey:{}", roomdm, signkey);
        JSONObject json = new JSONObject();
        json.put("status", "未用电");
        return json.toString();
    }

    /**
     * 充值
     * @param signkey
     * @param orderid
     * @param cztype
     * @param roomdm
     * @param money
     * @return
     */
    @WebMethod(operationName = "TopUp")
    public @WebResult(name = "TopUpResult") String TopUp(@WebParam(name = "signkey") String signkey, @WebParam(name = "orderid") String orderid,
                                                         @WebParam(name = "cztype") String cztype, @WebParam(name = "roomdm") String roomdm,
                                                         @WebParam(name = "money") String money) {
        log.info("充值， 请求参数--> roomdm:{}, signkey:{}", roomdm, signkey);
        JSONObject json = new JSONObject();
        json.put("code", 0);
        json.put("msg", "充值成功！");
        return json.toString();
    }

    @WebMethod(operationName = "GetDayUsedRecords")
    public @WebResult(name = "GetDayUsedRecordsResult") String GetDayUsedRecords(@WebParam(name = "signkey") String signkey,
                                                                                 @WebParam(name = "roomdm") String roomdm,
                                                                                 @WebParam(name = "fromDate") String fromDate,
                                                                                 @WebParam(name = "toDate") String toDate,
                                                                                 @WebParam(name = "mdtype") String mdtype) {
        log.info("用量查询， 请求参数--> roomdm:{}, signkey:{}", roomdm, signkey);
        JSONObject json = new JSONObject();
        json.put("code", 0);
        json.put("msg", "充值成功！");

        JSONArray array = new JSONArray();
        JSONObject user = new JSONObject();
        user.put("roomdm", "1001");
        user.put("datetime", "2024-04-18");
        user.put("used", "2");
        array.add(user);

        json.put("data", array);
        return json.toString();
    }

    /**
     * 获取房间硬件状态
     * @return
     */
    @WebMethod(operationName = "test")
    @WebResult(name = "testResult")
    public String test(@XmlElement(name = "roomdm", namespace = "##electric") String roomdm) {
        log.info("获取房间硬件状态， 请求参数--> roomdm:{}", roomdm);
        JSONObject json = new JSONObject();
        json.put("status", "未用电");
        return json.toString();
    }

    public static void main(String[] args) {
        //http://192.168.88.236:8080/AppService.svc?wsdl
        Endpoint.publish("http://192.168.88.236:8080/AppService.svc", new IAppService());
        System.out.println("WebService is published!");
    }

}
