package com.electric.controller.electric;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

import org.apache.commons.collections4.CollectionUtils;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.electric.model.param.isims.BatchRefundRequest;
import com.electric.model.param.isims.GetRefundByRefundIdRequest;
import com.electric.model.param.isims.QueryRoomBalanceRequest;
import com.electric.model.response.electric.isims.BalanceResponseWrapper;
import com.electric.model.response.electric.isims.BatchRefundResponseWrapper;
import com.electric.model.response.electric.isims.GetRefundByRefundIdResponseWrapper;
import com.electric.model.response.electric.isims.GetYEInfoResult;
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

    private static final List<BatchRefundResponseWrapper.RefundResponse> LIST             = new ArrayList<>();

    private static final List<BalanceResponseWrapper.BalanceResponse>    BALANCE_RESPONSE = new ArrayList<>();
    static {
        BalanceResponseWrapper.BalanceResponse balance = new BalanceResponseWrapper.BalanceResponse();
        balance.setLeftdu("0.2");
        balance.setLeftmoney("0.1");
        balance.setLeftration("0.00");
        balance.setLeftrationdu("0.00");
        balance.setMdname("照明用电");
        balance.setMessage("查询成功");
        balance.setHdclassname("电表");
        balance.setCztype("1");
        balance.setStatus("0");
        BALANCE_RESPONSE.add(balance);

        BalanceResponseWrapper.BalanceResponse balance1 = new BalanceResponseWrapper.BalanceResponse();
        balance1.setLeftdu("0.2");
        balance1.setLeftmoney("0.2");
        balance1.setLeftration("0.00");
        balance1.setLeftrationdu("0.00");
        balance1.setMdname("空调用电");
        balance1.setCztype("2");
        balance1.setMessage("查询成功");
        balance1.setHdclassname("电表");
        balance1.setStatus("0");
        BALANCE_RESPONSE.add(balance1);

        BalanceResponseWrapper.BalanceResponse waterBalance = new BalanceResponseWrapper.BalanceResponse();
        waterBalance.setLeftdu("0.2");
        waterBalance.setLeftmoney("0");
        waterBalance.setLeftration("0.00");
        waterBalance.setLeftrationdu("0.00");
        waterBalance.setMdname("热水");
        waterBalance.setCztype("3");
        waterBalance.setMessage("查询成功");
        waterBalance.setHdclassname("水表");
        waterBalance.setStatus("0");
        BALANCE_RESPONSE.add(waterBalance);

        BalanceResponseWrapper.BalanceResponse waterBalance1 = new BalanceResponseWrapper.BalanceResponse();
        waterBalance1.setLeftdu("0.25");
        waterBalance1.setLeftmoney("-0.66");
        waterBalance1.setLeftration("0.00");
        waterBalance1.setLeftrationdu("0.00");
        waterBalance1.setMdname("冷水");
        waterBalance1.setCztype("5");
        waterBalance1.setMessage("查询成功");
        waterBalance1.setHdclassname("水表");
        waterBalance1.setStatus("0");
        BALANCE_RESPONSE.add(waterBalance1);

        BalanceResponseWrapper.BalanceResponse gasBalance = new BalanceResponseWrapper.BalanceResponse();
        gasBalance.setLeftdu("0.3");
        gasBalance.setLeftmoney("0.6");
        gasBalance.setLeftration("0.00");
        gasBalance.setLeftrationdu("0.00");
        gasBalance.setMdname("燃气");
        gasBalance.setCztype("4");
        gasBalance.setMessage("查询成功");
        gasBalance.setHdclassname("气表");
        gasBalance.setStatus("0");
        BALANCE_RESPONSE.add(gasBalance);
    }

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
        data.put("roomdm", roomdm);

        JSONArray topUpTypeList = new JSONArray();

        BALANCE_RESPONSE.forEach(balance -> {
            JSONObject topUpType = new JSONObject();
            topUpType.put("cztype", roomdm + balance.getCztype());
            topUpType.put("mdname", balance.getMdname());
            topUpTypeList.add(topUpType);
        });

        data.put("topUpTypeList", topUpTypeList);

        JSONArray sylList = new JSONArray();
        BALANCE_RESPONSE.forEach(balance -> {
            JSONObject syl = new JSONObject();
            syl.put("SYL", balance.getLeftdu());
            syl.put("mdtype", roomdm + balance.getCztype());
            syl.put("mdname", balance.getMdname());
            syl.put("SYLJE", balance.getLeftmoney());
            syl.put("SYBZJE", balance.getLeftration());
            syl.put("SYBZ", balance.getLeftrationdu());
            syl.put("hdclassname", balance.getHdclassname());
            sylList.add(syl);
        });

        data.put("sylList", sylList);
        result.setData(data);
        String jsonStr = JSONObject.toJSONString(result);
        log.info("查询余额， 返回结果--> {}", jsonStr);
        return jsonStr;
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

        for (BalanceResponseWrapper.BalanceResponse balanceResponse : BALANCE_RESPONSE) {
            if (cztype.endsWith(balanceResponse.getCztype())) {
                balanceResponse.setLeftmoney(
                    new BigDecimal(balanceResponse.getLeftmoney()).add(new BigDecimal(money)).setScale(2, RoundingMode.HALF_UP).toString());
            }
        }

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
                AtomicInteger i = new AtomicInteger();
                BALANCE_RESPONSE.forEach(res -> {
                    BalanceResponseWrapper.BalanceResponse balance = new BalanceResponseWrapper.BalanceResponse();
                    balance.setLeftdu(res.getLeftdu());
                    balance.setLeftmoney(res.getLeftmoney());
                    balance.setLeftration(res.getLeftration());
                    balance.setLeftrationdu(res.getLeftrationdu());
                    balance.setMdname(res.getMdname());
                    balance.setMessage(res.getMessage());
                    balance.setCztype(roomdm + res.getCztype());
                    balance.setHdclassname(res.getHdclassname());
                    balance.setRoomdm(roomdm);
                    balance.setStatus(res.getStatus());
                    balanceResponse.add(balance);
                });
            }
        }
        System.out.println(JSONObject.toJSONString(balanceResponse));
        BalanceResponseWrapper balance = new BalanceResponseWrapper(balanceResponse);

        log.info("查询房间余额，请求参数--> roomdms:{}, 返回结果:{}", request.getRoomdms() != null ? request.getRoomdms().getString() : null,
            JSONObject.toJSONString(balance));
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

                    String money = "";
                    String hdclassname = "电表";
                    for (BalanceResponseWrapper.BalanceResponse balanceResponse : BALANCE_RESPONSE) {
                        if (refundItem.getCztype().endsWith(balanceResponse.getCztype())) {
                            money = balanceResponse.getLeftmoney();
                            balanceResponse.setLeftmoney("0");
                            hdclassname = balanceResponse.getHdclassname();
                        }
                    }

                    // 模拟退费成功的情况
                    refundResponse.setDianliang("-0.2");
                    refundResponse.setMoney("-" + money);
                    refundResponse.setPrice("0.5380");
                    refundResponse.setCztype(refundItem.getCztype());
                    refundResponse.setLeftmoney(new BigDecimal(money));
                    refundResponse.setHdclassname(hdclassname);
                    refundResponse.setMessage("退费处理中");
                    refundResponse.setStatus("0");
                    LIST.add(refundResponse);

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

            response.setRefundResponse(Collections.singletonList(refundResponse));
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

        List<BatchRefundResponseWrapper.RefundResponse> refundList = LIST.stream()
            .filter(refundResponse -> refundResponse.getRefundId().equals(request.getRefundId())).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(refundList)) {
            BatchRefundResponseWrapper.RefundResponse refundResponse = refundList.get(0);
            // 设置响应数据
            response.setErrorMessage("退费成功");
            response.setIsSuccess("true");
            response.setMdid("10003");
            response.setMessage("查询成功");
            response.setRefundAmount(refundResponse.getLeftmoney().toString());
            response.setRefundId(request.getRefundId());
            response.setRefundTime("2025-06-24T11:03:34.903");
            response.setRoomId(refundResponse.getRoomdm());
            response.setStatusCode("200");
            response.setSuccess("true");
            response.setIssend("1");
            log.info("根据退费ID查询退费结果，请求参数--> refundId:{}, 返回结果:{}", request.getRefundId(), response);
            return response;
        } else {
            // 设置响应数据
            response.setErrorMessage("未找到退费订单");
            response.setIsSuccess("true");
            response.setMdid("10003");
            response.setMessage("查询成功");
            response.setRoomId(response.getRoomId());
            response.setStatusCode("404");
            log.info("根据退费ID查询退费结果，请求参数--> refundId:{}, 返回结果:{}", request.getRefundId(), response);
            return response;
        }

    }

    @WebMethod(operationName = "ValidateBatchRefund")
    @WebResult(name = "ValidateBatchRefundResult")
    public BatchRefundResponseWrapper ValidateBatchRefund(@WebParam(name = "request", targetNamespace = "www.cdgf.com") BatchRefundRequest request) {
        log.info("校验房间是否允许退费接口，请求参数--> refundItems:{}, signkey:{}", JSONObject.toJSONString(request.getRefundItems()), request.getSignkey());

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
                refundResponse.setCztype(refundItem.getCztype());
                // 模拟不同的退费结果
                /*if (refundItem.getRoomdm().endsWith("08") || refundItem.getCztype().endsWith("3")) {
                    // 模拟退费失败的情况
                    refundResponse.setDianliang("");
                    refundResponse.setMoney("");
                    refundResponse.setPrice("");
                    refundResponse.setMessage("退费不成功,前一天还在用电,不能进行退费");
                    refundResponse.setStatus("-4");
                } else {*/
                // 模拟退费成功的情况
                //refundResponse.setDianliang("-0.2");
                //refundResponse.setMoney("-0.1");
                //refundResponse.setPrice();
                refundResponse.setMessage("验证通过，可以退费");
                refundResponse.setStatus("0");
                //LIST.add(refundItem.getRefundId());
                //}

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

            response.setRefundResponse(Collections.singletonList(refundResponse));
        }
        log.info("批量校验房间是否允许退费，返回结果--> {}", JSONObject.toJSONString(response));
        return response;
    }

    @WebMethod(operationName = "ReFund")
    @WebResult(name = "ReFundResult")
    public String ReFund(@WebParam(name = "signkey", targetNamespace = "www.cdgf.com") String signkey,
                         @WebParam(name = "custsn", targetNamespace = "www.cdgf.com") String custsn,
                         @WebParam(name = "roomdm", targetNamespace = "www.cdgf.com") String roomdm) {
        log.info("常工V6退费， 请求参数--> roomdm:{}, signkey:{}, custsn:{}", roomdm, signkey, custsn);

        BigDecimal refundMoney = BigDecimal.ZERO;
        for (BalanceResponseWrapper.BalanceResponse balanceResponse : BALANCE_RESPONSE) {
            if (new BigDecimal(balanceResponse.getLeftmoney()).compareTo(BigDecimal.ZERO) > 0) {
                refundMoney = refundMoney.add(new BigDecimal(balanceResponse.getLeftmoney()));
            }
        }

        JSONObject json = new JSONObject();
        json.put("code", 0);
        json.put("msg", "退费成功！");
        json.put("data", refundMoney);
        return json.toString();
    }

    @WebMethod(operationName = "ReFundRecord")
    @WebResult(name = "ReFundRecordResult")
    public String ReFundRecord(@WebParam(name = "signkey", targetNamespace = "www.cdgf.com") String signkey,
                               @WebParam(name = "custsn", targetNamespace = "www.cdgf.com") String custsn,
                               @WebParam(name = "roomdm", targetNamespace = "www.cdgf.com") String roomdm) {
        log.info("常工V6退费查询， 请求参数--> roomdm:{}, signkey:{}, custsn:{}", roomdm, signkey, custsn);

        BigDecimal refundMoney = BigDecimal.ZERO;
        for (BalanceResponseWrapper.BalanceResponse balanceResponse : BALANCE_RESPONSE) {
            if (new BigDecimal(balanceResponse.getLeftmoney()).compareTo(BigDecimal.ZERO) > 0) {
                refundMoney = refundMoney.add(new BigDecimal(balanceResponse.getLeftmoney()));
            }
        }

        JSONObject json = new JSONObject();
        json.put("code", 0);
        json.put("msg", "退费成功！");
        json.put("data", refundMoney);
        return json.toString();
    }

    public static void main(String[] args) {
        Endpoint.publish("http://192.168.75.236:8080/AppService.svc", new IAppService());

        System.out.println("WebService is published!");
    }

}