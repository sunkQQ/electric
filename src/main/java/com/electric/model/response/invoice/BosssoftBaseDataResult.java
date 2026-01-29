package com.electric.model.response.invoice;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 博思开篇通用返回
 * @author sunk
 * @date 2024/04/10
 */
@Getter
@Setter
@ToString
public class BosssoftBaseDataResult {
    /**
     * 返回结果标识
     *     除 S0000 代码值之外的代码，都表示 失败：如 E0001 等
     */
    private String result;

    /**
     * 返回结果内容
     *     BASE64(错误信息)
     */
    private String message;
}
