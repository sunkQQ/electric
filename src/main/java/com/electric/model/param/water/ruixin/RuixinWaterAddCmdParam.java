package com.electric.model.param.water.ruixin;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 瑞信水控取水接口参数
 *
 * @author sunk
 * @date 2024/08/07
 */
@Getter
@Setter
@ToString
public class RuixinWaterAddCmdParam {

    /** 云平台账号Id */
    private String accountId;

    /** 实体卡物理ID 电子卡虚拟ID 第三方平台ID */
    private String cardSN;

    /**
     * NO_CARD_PAY_IMD_MONEY 开始本次使用
     * NO_CARD_PAY_IMD_STOP 停止本次使用
     * NO_CARD_IMD_STOP 强制停止
     * 其他值不合法 入参必填
     */
    private String cmd;

    /** 设备号 */
    private String deviceNum;

    /** 用户余额 分 入参必填 */
    private String oddFare;

    /** 本次操作金额 分 */
    private String opFare;

    /** 流水号  */
    private String orderNumber;

    /** 0 扫码 1实体卡 */
    private String waterTakeType;
}
