package com.electric.model.response.water.ruixin;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 瑞信水控消费记录返回字段
 *
 * @author sunk
 * @date 2024/08/07
 */
@Getter
@Setter
@ToString
public class RuiXinWaterRecordResponse {

    /**
     * 记录ID
     */
    private Long   id;

    /**
     * 云平台账号ID
     */
    private Long   companyId;

    private String userName;

    /**
     * 账号
     */
    private Long   userId;

    /**
     * 三方平台信息(自动注册)
     * 特别处理
     */
    private String userNo;

    private Long   cardNO;

    private Long   cardSeq;

    /**
     * 实体卡物理ID 电子卡虚拟ID 三方平台信息(设备上传)
     */
    private String cardSN;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 设备号
     */
    private Long   deviceNum;

    /**
     * 设备ID
     */
    private Long   deviceId;

    private Long   sumFare;

    /**
     * 消费金额 分
     */
    private Long   opFare;

    /**
     * 消费时间
     */
    private String opTime;

    /**
     * 数据采集时间
     */
    private String collectTime;

    /**
     * 用户账户操作计数器
     */
    private Long   opCount;

    /**
     * 用水量 ml
     */
    private Long   waterTakeCount;

    /**
     * 使用时长 s
     */
    private Long   timeTakeCount;

    /**
     * 流水号
     */
    private String tradeNo;

    /**
     * 三方平台流水号
     */
    private String outTradeNo;

    /**
     * 记录后续处理进度类型 默认入库为 0
     */
    private Long   handleFlag;

    private Long   attendanceStatus;
    private Long   autoGenerateFlag;
    private Long   cardTypeId;
    private Long   communicateType;
    private Long   consumeType;
    private String consumeTypeDesc;
    private Long   consumeTypeDetail;
    private String consumeTypeDetailDesc;
    private Long   consumeTypeStatistics;
    private String consumeTypeStatisticsDesc;
    private Long   cookbookCode;
    private String cookbookName;
    private Long   cookbookNum;
    private Long   correctFlag;
    private Long   deviceType;
    private Long   discountFare;
    private Long   discountRate;
    private Long   electricTakeCount;
    private Long   isAttendance;
    private Long   mealId;
    private String mealName;
    private Long   oddFare;
    private Long   opTimeLong;
    private Long   opTimeMonth;
    private Long   opTimeYear;
    private Long   paramGroupId;
    private String paramGroupName;
    private Long   posDeptId;
    private String posDeptName;
    private String recordCount;
    private Long   recordNO;
    private String studentId;
    private Long   subsidyAuth;
    private Long   subsidyOddFare;
    private Long   subsidyOpCount;
    private Long   subsidyOpFare;
    private String transferName;
    private Long   transferNum;
    private Long   userDeptId;
    private String userDeptName;
    private Long   waterControl;

}
