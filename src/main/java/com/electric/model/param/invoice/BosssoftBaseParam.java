package com.electric.model.param.invoice;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 博思开票请求参数
 *
 * @author sunk
 * @date 2022/07/04
 */
@Getter
@Setter
@ToString
public class BosssoftBaseParam {

    /**
     * 区划
     *   调用方填写业务系统内部编码值，平台配置对照
     */
    private String region;

    /**
     * 单位标识
     *     调用方填写业务系统内部编码值，平台方配置对照
     */
    private String deptcode;

    /**
     * 应用账号 *
     *     调用方应用账号，由平台提供
     */
    private String appid;

    /**
     * 请求业务参数 *
     *     各接口请求业务参数的内容不同，实际内容以各接口接口为准；
     *     接口请求业务参数值，参数 JSON的格式，并且做 base64 编码,编码字符集 UTF-8
     */
    private String data;

    /**
     * 请求随机标识 *
     *     每次请求生成一个唯一编号，全局唯一（建议使用UUID）
     */
    private String noise;

    /**
     * 版本号 *
     *     默认 1.0
     */
    private String version = "1.0";

    /**
     * 签名 *
     *     MD5摘要结果转换成大写
     */
    private String sign;
}
