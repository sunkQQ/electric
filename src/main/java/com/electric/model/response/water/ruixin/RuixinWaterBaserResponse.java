package com.electric.model.response.water.ruixin;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 瑞信水控返回字段
 *
 * @author sunk
 * @date 2024/08/07
 */
@Getter
@Setter
@ToString
public class RuixinWaterBaserResponse {

    /** 结果编码 200成功 */
    private String code;

    /** 返回数据 */
    private String data;

    /** 结果提示 */
    private String message;
}
