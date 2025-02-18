package com.electric.param.jiaofei.tucheng;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 图城缴费获取token参数
 *
 * @author sunk
 * @date 2024/11/13
 */
@Getter
@Setter
@ToString
public class JiaofeiTuChengPayParam {
    /** 欠款读者证号 */
    private String  token;

    /** INTERLIB操作员账号 */
    private String  opuser;
    /** 支付欠款金额 */
    private String  money;

    /** 支付方式 1 现金 2 IC卡 3 预付款支付 5 支付宝 6 微支付 7 银行卡支付 */
    private String  monetype;

    /** 集群读者是否可以支付 1 是，0否 */
    private Boolean havecluster;

    /** 支付流水号 */
    private String  serialno;

    /** 操作时间，YYYY-MM-DD HH24:MI:SS格式 */
    private String  updateTime;
}
