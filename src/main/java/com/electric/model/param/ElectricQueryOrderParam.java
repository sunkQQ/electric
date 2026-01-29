package com.electric.model.param;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 查询订单号参数
 * 
 * @Author Administrator
 * @Date 2020-9-15
 *
 */
@Getter
@Setter
@ToString
public class ElectricQueryOrderParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 校区编码 */
    @NotEmpty(message = "校区编码不能为空")
    private String            areaCode;

    /** 订单编码 */
    @NotEmpty(message = "订单编号不能为空")
    private String            orderCode;

    /** 类型：1、电费 2、水费 */
    private Integer           bussiType;

    /** 签名 */
    @NotEmpty(message = "签名不能为空")
    private String            sign;
}
