package com.electric.param.send;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 电费消息提醒接口
 *
 * @author sys
 * @since: 2019-09-12
 * @history:
 */
@Getter
@Setter
@ToString
public class CenterElectricRemindParam {

    /** 学校*/
    @NotBlank(message = "推送接口ID不能为空")
    private String  pushId;

    private String  areaCode;

    /** 楼栋*/
    private String  buildingCode;

    /** 楼层*/
    private String  floorCode;

    /** 房间*/
    @NotBlank(message = "房间编码不能为空")
    private String  roomCode;

    @NotNull(message = "类型不能为空")
    private Integer type;

    private String  surplus;

    private String  amount;

    private String  title;

    private String  content;

    private String  ymAppId;

    @NotBlank(message = "签名不能为空")
    private String  sign;

}
