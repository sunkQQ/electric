package com.electric.model.response.special.kayroad;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 凯路趣智校园返回
 *
 * @Author sunk
 * @Date 2021/11/9
 */
@Getter
@Setter
@ToString
public class KayRoadQuZhiBaseResponse {

    /**
     * 错误码：
     * 0.成功
     * 1.参数错误
     * 2.签名错误
     * 3.访问限制
     * 4.访问超时(请求参数 timestamp超时)
     * 10.该账户未注册
     */
    private String errorCode;

    /**
     * 错误信息
     */
    private String message;

    /**
     * 数据  (状态码 errorCode 不为 0 时没有数据)
     */
    private String data;
}
