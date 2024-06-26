package com.electric.param;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 充值参数
 * 
 * @Author Administrator
 * @Date 2020-9-10
 *
 */
@Getter
@Setter
@ToString
public class ElectricRechargeParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 电费订单号 */
    @NotEmpty(message = "电费订单号不能为空")
    private String            orderCode;

    /** 校区编码 */
    @NotEmpty(message = "校区编码不能为空")
    private String            areaCode;

    /** 楼栋编码 */
    @NotEmpty(message = "楼栋编码不能为空")
    private String            buildingCode;

    /** 楼层编码 */
    @NotBlank(message = "楼层编码不能为空")
    private String            floorCode;

    /** 房间编码 */
    @NotEmpty(message = "房间编码不能为空")
    private String            roomCode;

    /** 充值金额 */
    @NotEmpty(message = "充值金额不能为空")
    private String            rechargeMoney;

    /** 学工号 */
    private String            jobNo;

    /** 类型：1、电费 2、水费 */
    private Integer           bussiType;

    @NotEmpty(message = "签名不能为空")
    private String            sign;
}
