package com.electric.param.special;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 凯路水费-趣智校园退款申请接口请求参数
 *
 * @author sunk
 * @date 2023/05/12
 */
@Getter
@Setter
@ToString
public class KayRoadQuZhiRefundApplyParam {

    @JSONField(name = "mchId")
    private String  mchId;

    /** 签名方式 */
    @JSONField(name = "signType")
    private String  signType = "Md5";

    /** 用户手机号 */
    @JSONField(name = "telPhone")
    private String  telPhone;

    /** 退款类型 0退款,1退款并销户 */
    @JSONField(name = "refundType")
    private Integer refundType;

    /** 账户姓名 非必填 */
    @JSONField(name = "fullName")
    private String  fullName;

    /** 退款账户 非必填 */
    @JSONField(name = "bankAccount")
    private String  bankAccount;

    /** 联系方式 非必填 */
    @JSONField(name = "phone")
    private String  phone;

    /** 请求时间 */
    @JSONField(name = "timestamp")
    private String  timestamp;

}
