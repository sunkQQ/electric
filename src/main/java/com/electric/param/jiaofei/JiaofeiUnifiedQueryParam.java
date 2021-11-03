package com.electric.param.jiaofei;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 缴费查询接口参数
 *
 * @Author sunk
 * @Date 2021/11/3
 */
@Getter
@Setter
@ToString
public class JiaofeiUnifiedQueryParam {

    /**
     * 用户编号
     */
    private String userNo;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 签名
     */
    private String sign;
}
