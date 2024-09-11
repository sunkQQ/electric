package com.electric.util.enums;

/**
 * 请求超时枚举
 *
 * @author sunk
 * @date 2024/09/11
 */
public enum HttpRequestTimeoutEnum {

    /** 10s */
    ten(10, "10秒"),
    /** 15s */
    fifteen(15, "15秒"),
    /** 30s */
    thirty(30, "30秒"),

    /***/
    ;

    private Integer  code;
    private String   name;

    HttpRequestTimeoutEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static HttpRequestTimeoutEnum getEnum(Integer code) {
        if (code == null) {
            return null;
        }
        for (HttpRequestTimeoutEnum httpRequestTimeOutEnum : HttpRequestTimeoutEnum.values()) {
            if (httpRequestTimeOutEnum.getCode().equals(code)) {
                return httpRequestTimeOutEnum;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
