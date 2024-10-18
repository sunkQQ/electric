package com.electric.response.jiaofei.chaoxing;

import com.alibaba.fastjson2.annotation.JSONField;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 超星认证接口返回字段
 *
 * @author sunk
 * @date 2023/05/04
 */
@Getter
@Setter
@ToString
public class ChaoXingLibraryTokenResult {

    /** 认证token参数 */
    @JSONField(name = "access_token")
    private String  accessToken;

    /** 认证类型 */
    @JSONField(name = "token_type")
    private String  tokenType;

    /** 超时时间（s） */
    @JSONField(name = "expires_in")
    private Integer expiresIn;

    /** 作用域 */
    private String  scope;

    /** 机构码 */
    private String  groupCode;

    /** 机构租户标志 */
    private String  mappingPath;

    /** token id */
    private String  jti;
}
