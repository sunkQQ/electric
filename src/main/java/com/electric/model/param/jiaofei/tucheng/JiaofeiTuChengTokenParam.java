package com.electric.model.param.jiaofei.tucheng;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 图城缴费获取token参数
 *
 * @author sunk
 * @date 2024/11/13
 */
@Getter
@Setter
@ToString
public class JiaofeiTuChengTokenParam {

    /** appid */
    private String appid;

    /** secret */
    private String secret;
}
