package com.electric.model.param;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 查询房间数据请求参数
 * 
 * @Author Administrator
 * @Date 2020-9-15
 *
 */
@Getter
@Setter
@ToString
public class ElectricQueryRoomListParam {

    /** 类型：1、电费 2、水费 */
    private Integer bussiType;

    /** 签名 */
    @NotEmpty(message = "签名不能为空")
    private String  sign;
}
