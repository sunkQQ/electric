package com.electric.param.jiaofei.tiancai;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 天大天财缴费接口参数
 *
 * @author sunk
 * @date 2023/07/21
 */
@Getter
@Setter
@ToString
public class JiaofeiTianCaiBaseParam {

    @JSONField(name = "AbcJson")
    private String abcJson;
}
