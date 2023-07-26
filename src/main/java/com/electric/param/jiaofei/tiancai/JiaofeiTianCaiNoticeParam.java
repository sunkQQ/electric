package com.electric.param.jiaofei.tiancai;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 天大天财缴费通知参数
 *
 * @author sunk
 * @date 2023/07/21
 */
@Getter
@Setter
@ToString
public class JiaofeiTianCaiNoticeParam {

    /** 功能代码 */
    @JSONField(name = "ABC_CODE")
    private String abcCode;

    /** 银行订单号 */
    @JSONField(name = "YHDDH")
    private String yhddh;

    /** 学号 */
    @JSONField(name = "XH")
    private String xh;

    /** 交易时间 */
    @JSONField(name = "DATA")
    private String data;

    /** 总金额 */
    @JSONField(name = "ZJE")
    private String zje;

    /** 签章 */
    @JSONField(name = "SIGN")
    private String sign;

    /** 明细 */
    @JSONField(name = "DETAIL")
    private String detail;

    @Getter
    @Setter
    @ToString
    public static class Detail {

        /** 收费期间代码@收费项目代码 */
        @JSONField(name = "JFBH")
        private String jfbh;

        /** 交易金额 */
        @JSONField(name = "JE")
        private String je;
    }
}
