package com.electric.model.response.invoice;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 博思发票返回结果
 * @author sunk
 * @date 2024/04/10
 */
@Getter
@Setter
@ToString
public class BosssoftBaseResult {

    /**
     * 返回结果参数
     *     各 API 调用返回的内容不同，
     *     实际内容以各接口 API 为准，
     *     参数值为 JSON 格式做 base64
     *     编码，编码字符集 UTF-8
     */
    private String data;

    /**
     * 请求随机标识
     *     每次请求返回一个唯一编号，全局唯一（建议采用 UUID）
     */
    private String noise;

    /**
     * 签名
     *     MD5 摘要结果转换成大写
     */
    private String sign;
}
