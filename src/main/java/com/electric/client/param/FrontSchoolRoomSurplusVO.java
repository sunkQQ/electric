package com.electric.client.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author sunk
 * @date 2023/09/27
 */
@Getter
@Setter
@ToString
public class FrontSchoolRoomSurplusVO implements Serializable {

    /**
     * serialVersionUID:序列化ID
     */
    private static final long serialVersionUID = 1L;

    /** 剩余电量 */
    private Double            surplus;

    /** 剩余金额 */
    private Double            amount;

    /** 是否显示剩余电量： 0、显示 1、不显示 */
    private Integer           isShowSurplus;

    /** 是否显示剩余金额：0、显示 1、不显示 */
    private Integer           isShowMoney;

    /** 电费价格（元/度） */
    private BigDecimal        price;

    /** 温馨提示 */
    private String            remind;

    private String            amountPayable;

    private String            usageQuantity;

    private String            thisSettlingTime;

    private String            lastSettlingTime;

    private Integer           system;

    private String            displayRoomName;

    private String            subsidy;

    private String            ids;

    private String            recordTime;

    private String            fieldName;

    private String            fieldValue;

    private Integer           footerLink;

    private Integer           isAllowChange;

    private String            billId;

    private Integer           canBuy           = 0;

    private String            noBuyMsg;

    private BigDecimal        minAmount;
}
