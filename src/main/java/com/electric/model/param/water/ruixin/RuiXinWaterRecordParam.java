package com.electric.model.param.water.ruixin;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 消费记录查询参数
 *
 * @author sunk
 * @date 2024/08/07
 */
@Getter
@Setter
@ToString
public class RuiXinWaterRecordParam {

    /** 云平台账号Id */
    private String  companyId;

    /** 三方平台用户唯一信息 */
    private String  cardSn;

    /** 操作计数器 单个用户自动递增 */
    private String  opCount;

    /** 计数器查询标识，可选值 = > < >= <= */
    private String  opCountFlag;

    /** 设备号 */
    private String  deviceNum;

    /** 时间类型 可选值 opTime(消费发生时间) collectTime(记录上传至服务器时间) */
    private String  dateType;

    /** 设备所属部门/组织Id */
    private String  deptId;

    /** 页码 */
    private Integer pageNum;

    /** 单页数据 */
    private Integer pageSize;

    /** 查询日期 起始 */
    private String  beginDate;

    /** 查询日期 结束 */
    private String  endDate;

    /** 三方流水号 */
    private String  orderNumber;

    /** 排序字段 */
    private String  orderByColumn;

    /** 排序规则 */
    private String  orderBy;
}
