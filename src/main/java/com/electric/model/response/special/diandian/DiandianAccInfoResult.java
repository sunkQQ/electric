package com.electric.model.response.special.diandian;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 点点接口返回数据
 *
 * @author sunk
 * @date 2024/11/21
 */
@Getter
@Setter
@ToString
public class DiandianAccInfoResult {

    /** 请求成功标志 */
    private Boolean success;

    /** 应答描述 */
    private String  message;

    /** http-code */
    private String  code;

    /** 需BASE64解码+AES解密为json字符串，然后转ARRAY */
    private String  result;

    /** 时间戳 */
    private String  timestamp;

    /** 应答码 */
    private String  errCode;

    @Getter
    @Setter
    @ToString
    public static class AccInfo {

        /** 用户手机号 */
        private String phone;

        /** 用户卡号 */
        private String account;
    }
}
