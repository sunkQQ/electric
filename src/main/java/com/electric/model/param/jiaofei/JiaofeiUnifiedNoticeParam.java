package com.electric.model.param.jiaofei;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 缴费大厅统一接口缴费参数
 *
 * @Author sunk
 * @Date 2021/11/3
 */
@Getter
@Setter
@ToString
public class JiaofeiUnifiedNoticeParam {

    /**
     * 用户编号
     */
    private String userNo;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 流水号
     */
    private String seqNo;

    /**
     * 缴费项目编码
     */
    private String proCode;

    /**
     * 缴费年份
     */
    private String thirdYear;

    /**
     * 缴费期间编码
     */
    private String durationCode;

    /**
     * 缴费金额（元）
     */
    private String payAmount;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 支付订单号
     */
    private String payOrder;

    /**
     * 缴费状态：1、等待支付 2、支付碾国 3、支付失败 4、订单关闭
     */
    private String orderStatus;

    /**
     * 支付时间
     */
    private String payTime;

    /**
     * 签名
     */
    private String sign;
}
