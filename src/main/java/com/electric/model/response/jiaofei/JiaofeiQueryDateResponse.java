package com.electric.model.response.jiaofei;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 缴费统一查询接口返回参数
 *
 * @Author sunk
 * @Date 2021/11/3
 */
@Getter
@Setter
@ToString
public class JiaofeiQueryDateResponse {

    /**
     * 用户编号
     */
    private String userNo;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 流水号
     */
    private String seqNo;

    /**
     * 缴费项目编码
     */
    private String proCode;

    /**
     * 缴费项目名称
     */
    private String proName;

    /**
     * 缴费年份
     */
    private String thirdYear;

    /**
     * 缴费期间编码
     */
    private String durationCode;

    /**
     * 缴费期间名称
     */
    private String durationName;

    /**
     * 缴费金额（元）
     */
    private String payAmount;

}
