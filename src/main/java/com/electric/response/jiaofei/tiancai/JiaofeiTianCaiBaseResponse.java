package com.electric.response.jiaofei.tiancai;

import com.alibaba.fastjson2.annotation.JSONField;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 天大天财缴费返回
 *
 * @author sunk
 * @date 2023/07/21
 */
@Getter
@Setter
@ToString
public class JiaofeiTianCaiBaseResponse {

    /** 状态码（00：代表成功，其余代码均代表失败） */
    @JSONField(name = "RTN_MSG")
    private String rtnMsg;

    /** 结果描述 */
    @JSONField(name = "RTN_CODE")
    private String rtnCode;

    /** 功能代码 */
    @JSONField(name = "ABC_CODE")
    private String abcCode;

    /** 总金额(如100.00) */
    @JSONField(name = "ZJE")
    private String zje;

    /** 签章 */
    @JSONField(name = "SIGN")
    private String sign;

    /** 名单详情 */
    @JSONField(name = "DETAIL")
    private String detail;
}
