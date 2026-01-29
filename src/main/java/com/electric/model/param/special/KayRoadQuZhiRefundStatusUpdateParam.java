package com.electric.model.param.special;

import com.alibaba.fastjson2.annotation.JSONField;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 凯路水费退款申请状态变更字段
 *
 * @author sunk
 * @date 2023/05/12
 */
@Getter
@Setter
@ToString
public class KayRoadQuZhiRefundStatusUpdateParam {

    @JSONField(name = "mchId")
    private String mchId;

    /** 签名方式 */
    @JSONField(name = "signType")
    private String signType = "Md5";

    /** 用户手机号 */
    @JSONField(name = "telPhone")
    private String telPhone;

    /**
     * 退款申请编号
     */
    @JSONField(name = "refundApplyNo")
    private String refundApplyNo;

    /**
     * 退款申请状态 （1成功、2拒绝、3取消申请、4失败）
     */
    @JSONField(name = "refundApplyStatus")
    private String refundApplyStatus;

    /**
     * 退款申请状态变更理由描述说明，
     *      例如退款成功、退款失败、拒绝、⽤户取消申请等等
     */
    @JSONField(name = "remark")
    private String remark;

    /** 请求时间 */
    @JSONField(name = "timestamp")
    private String timestamp;
}
