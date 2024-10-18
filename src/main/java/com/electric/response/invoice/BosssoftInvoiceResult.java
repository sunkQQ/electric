package com.electric.response.invoice;

import com.alibaba.fastjson2.annotation.JSONField;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 博思开票返回
 *
 * @author sunk
 * @date 2022/07/07
 */
@Getter
@Setter
@ToString
public class BosssoftInvoiceResult {

    /** 业务单号 */
    private String busNo;

    /** 电子票据代码 */
    private String eBillCode;

    /** 电子票据号码 */
    private String eBillNo;

    /** 电子校验码 */
    private String checkCode;

    /**  电子票据二维码图片数据 */
    @JSONField(name = "eBillQRCodeData")
    private String eBillQrCodeData;

    /** 电子票据H5页面URL */
    private String pictureUrl;
}
