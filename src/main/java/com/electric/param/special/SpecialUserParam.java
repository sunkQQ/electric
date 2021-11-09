package com.electric.param.special;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 生活缴费查询参数
 *
 * @Author sunk
 * @Date 2021/11/9
 */
@Getter
@Setter
@ToString
public class SpecialUserParam {

    /**
     * 用户手机号
     */
    private String telPhone;

    /**
     * 商户号
     */
    private String mchId;

    /**
     * 请求类型:1、查询 2、注册查询
     */
    private String requestType;

    /**
     * 签名类型
     */
    private String signType;

    /**
     * 请求时间
     */
    private String timestamp;

    /**
     * 签名
     */
    private String sign;
}
