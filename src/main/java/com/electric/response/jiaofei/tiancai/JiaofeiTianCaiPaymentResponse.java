package com.electric.response.jiaofei.tiancai;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 天大天财待缴信息
 *
 * @author sunk
 * @date 2023/07/21
 */
@Getter
@Setter
@ToString
public class JiaofeiTianCaiPaymentResponse {

    /** 收费期间代码 */
    @JSONField(name = "SFQJDM")
    private String sfqjdm;

    /** 收费期间名称 */
    @JSONField(name = "SFQJMC")
    private String sfqjmc;

    /** 收费项目代码 */
    @JSONField(name = "SFXMDM")
    private String sfxmdm;

    /** 收费项目名称 */
    @JSONField(name = "SFXMMC")
    private String sfxmmc;

    /** 交易金额(类型：double，如：100.00) */
    @JSONField(name = "JYYE")
    private BigDecimal jyye;

    /** 备注 */
    @JSONField(name = "BZ")
    private String bz;
}
