package com.electric.controller.electric;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.electric.param.isims.BatchRefundRequest;
import com.electric.param.isims.GetRefundByRefundIdRequest;
import com.electric.param.isims.QueryRoomBalanceRequest;
import com.electric.response.electric.isims.BalanceResponseWrapper;
import com.electric.response.electric.isims.BatchRefundResponseWrapper;
import com.electric.response.electric.isims.GetRefundByRefundIdResponseWrapper;
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

    private static final List<String> LIST = new ArrayList<>();

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

    /**
     * 查询房间余额 (批量查询)
     * @param request 请求对象，包含房间编码列表和签名密钥
     * @return 结果
     */
    @WebMethod(operationName = "QueryRoomBalance")
    @WebResult(name = "QueryRoomBalanceResult")
    public BalanceResponseWrapper QueryRoomBalance(@WebParam(name = "request", targetNamespace = "www.cdgf.com") QueryRoomBalanceRequest request) {
        log.info("查询房间余额，请求参数--> roomdms:{}, signkey:{}", request.getRoomdms() != null ? request.getRoomdms().getString() : null,
            request.getSignkey());
        // 创建响应对象
        List<BalanceResponseWrapper.BalanceResponse> balanceResponse = new ArrayList<>();

        // 设置响应数据 - 这里使用模拟数据，您可以根据实际业务逻辑调整
        if (request.getRoomdms() != null && request.getRoomdms().getString() != null && !request.getRoomdms().getString().isEmpty()) {
            for (String roomdm : request.getRoomdms().getString()) {
                BalanceResponseWrapper.BalanceResponse balance = new BalanceResponseWrapper.BalanceResponse();
                balance.setLeftdu("0.2");
                balance.setLeftmoney("0.1");
                balance.setLeftration("0.00");
                balance.setLeftrationdu("0.00");
                balance.setMdname("11-北102");
                balance.setMessage("查询成功");
                balance.setRoomdm(roomdm);
                balance.setStatus("0");
                balanceResponse.add(balance);
            }
        }

        BalanceResponseWrapper balance = new BalanceResponseWrapper(balanceResponse);
        log.info("查询房间余额，请求参数--> roomdms:{}, 返回结果:{}", request.getRoomdms() != null ? request.getRoomdms().getString() : null, balance);
        // 使用FastJSON转换为JSON字符串返回
        return balance;
    }

    /**
     * 批量退费接口
     * @param request 请求对象，包含退费项列表和签名密钥
     * @return 结果
     */
    @WebMethod(operationName = "BatchRefund")
    @WebResult(name = "BatchRefundResult")
    public BatchRefundResponseWrapper BatchRefund(@WebParam(name = "request", targetNamespace = "www.cdgf.com") BatchRefundRequest request) {
        log.info("批量退费，请求参数--> refundItems size:{}, signkey:{}",
            request.getRefundItems() != null && request.getRefundItems().getRefundItem() != null ? request.getRefundItems().getRefundItem().size()
                : 0,
            request.getSignkey());

        // 创建响应对象
        BatchRefundResponseWrapper response = new BatchRefundResponseWrapper();

        // 处理退费逻辑
        if (request.getRefundItems() != null && request.getRefundItems().getRefundItem() != null
            && !request.getRefundItems().getRefundItem().isEmpty()) {

            List<BatchRefundResponseWrapper.RefundResponse> refundResponses = new ArrayList<>();

            for (BatchRefundRequest.RefundItem refundItem : request.getRefundItems().getRefundItem()) {
                BatchRefundResponseWrapper.RefundResponse refundResponse = new BatchRefundResponseWrapper.RefundResponse();
                refundResponse.setRefundId(refundItem.getRefundId());
                refundResponse.setRoomdm(refundItem.getRoomdm());
                refundResponse.setMdname(refundItem.getRoomdm().substring(refundItem.getRoomdm().length() - 3) + "房间"); // 模拟房间名称

                // 模拟不同的退费结果
                if (refundItem.getRoomdm().endsWith("08")) {
                    // 模拟退费失败的情况
                    refundResponse.setDianliang("");
                    refundResponse.setMoney("");
                    refundResponse.setPrice("");
                    refundResponse.setMessage("退费不成功,前一天还在用电,不能进行退费");
                    refundResponse.setStatus("-4");
                } else {
                    // 模拟退费成功的情况
                    refundResponse.setDianliang("-0.2");
                    refundResponse.setMoney("-0.1");
                    refundResponse.setPrice("0.5380");
                    refundResponse.setMessage("退费处理中");
                    refundResponse.setStatus("0");
                    LIST.add(refundItem.getRefundId());
                }

                refundResponses.add(refundResponse);
            }

            response.setRefundResponse(refundResponses);

        } else {
            // 无退费数据的情况
            BatchRefundResponseWrapper.RefundResponse refundResponse = new BatchRefundResponseWrapper.RefundResponse();
            refundResponse.setRefundId("");
            refundResponse.setRoomdm("");
            refundResponse.setMdname("");
            refundResponse.setDianliang("");
            refundResponse.setMoney("");
            refundResponse.setPrice("");
            refundResponse.setMessage("无退费数据");
            refundResponse.setStatus("-1");

            response.setRefundResponse(Arrays.asList(refundResponse));
        }
        log.info("批量退费，返回结果--> {}", response);
        return response;
    }

    /**
     * 根据退费ID查询退费结果
     * @param request 请求对象，包含退费ID和签名密钥
     * @return 结果
     */
    @WebMethod(operationName = "GetRefundByRefundId")
    @WebResult(name = "GetRefundByRefundIdResult")
    public GetRefundByRefundIdResponseWrapper GetRefundByRefundId(@WebParam(name = "request", targetNamespace = "www.cdgf.com") GetRefundByRefundIdRequest request) {
        log.info("根据退费ID查询退费结果，请求参数--> refundId:{}, signkey:{}", request.getRefundId(), request.getSignkey());

        // 创建响应对象
        GetRefundByRefundIdResponseWrapper response = new GetRefundByRefundIdResponseWrapper();

        // 设置响应数据
        response.setErrorMessage("退费成功");
        response.setIsSuccess("true");
        response.setMdid("10003");
        response.setMessage("查询成功");
        response.setRefundAmount("-0.1");
        response.setRefundId(request.getRefundId());
        response.setRefundTime("2025-06-24T11:03:34.903");
        response.setRoomId("10060204");
        response.setStatusCode("200");
        response.setSuccess("true");
        response.setIssend("1");
        log.info("根据退费ID查询退费结果，请求参数--> refundId:{}, 返回结果:{}", request.getRefundId(), response);
        return response;
    }

    public static void main(String[] args) {
        Endpoint.publish("http://192.168.88.236:8080/AppService.svc", new IAppService());
        System.out.println("WebService is published!");
    }
}