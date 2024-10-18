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
public class JiaofeiTianCaiUserInfoResponse {

    /** 姓名 */
    @JSONField(name = "XM")
    private String xm;

    /** 学号 */
    @JSONField(name = "XH")
    private String xh;

    /** 班级名称 */
    @JSONField(name = "BJMC")
    private String bjmc;

    /** 院系名称 */
    @JSONField(name = "YXMC")
    private String yxmc;

    /** 接口代码(ABC0001) */
    @JSONField(name = "ABC_CODE")
    private String abcCode;

    /** 入学年代 */
    @JSONField(name = "RXND")
    private String rxnd;

    /** 班级代码 */
    @JSONField(name = "BJDM")
    private String bjdm;

    /** 院系代码 */
    @JSONField(name = "YXDM")
    private String yxdm;

    /** 证件号 */
    @JSONField(name = "ZJH")
    private String zjh;

    /** 专业代码 */
    @JSONField(name = "ZYDM")
    private String zydm;

    /** 专业名称 */
    @JSONField(name = "ZYMC")
    private String zymc;

    /** 状态码【00：代表成功，其余全部代表失败】 */
    @JSONField(name = "RTN_CODE")
    private String rtnCode;

    /** 结果描述 */
    @JSONField(name = "RTN_MSG")
    private String rtnMsg;

    /** 签章 */
    @JSONField(name = "SIGN")
    private String sign;
}
