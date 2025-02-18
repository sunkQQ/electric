package com.electric.response.jiaofei.tucheng;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author sunk
 * @date 2024/11/13
 */
@Getter
@Setter
@ToString
public class JiaofeiTuChengBaseResult {
    // 编码
    private String code;

    // 消息
    private String message;

    public JiaofeiTuChengBaseResult(String code, String message) {
        this.message = message;
        this.code = code;
    }

    public JiaofeiTuChengBaseResult() {
    }
}
