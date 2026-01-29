package com.electric.model.param.auth;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 获取基础数据信息接口参数
 *
 * @author sunk
 * @date 2023/04/11
 */
@Getter
@Setter
@ToString
public class AuthOrgUserParam implements Serializable {

    private static final long serialVersionUID = 645088380789976516L;

    /**
     * 用户类型：1、学生  2、教职工
     */
    private Integer           userType;

    /**
     * 最后更新时间
     */
    private String            lastUpdateTime;

    /**
     * 当前页数
     */
    private Integer           pageNo;

    /**
     * 每页条数
     */
    private Integer           pageSize;

    /**
     * 签名
     */
    private String            sign;
}
