package com.electric.param.jiaofei.tiancai;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 天大天财参数
 *
 * @author sunk
 * @date 2023/07/21 13:43
 */
@Getter
@Setter
@ToString
public class JiaofeiTianCaiAbcJsonParam {

    @JSONField(name = "ABC_CODE")
    private String abcCode;

    @JSONField(name = "XH")
    private String xh;

    @JSONField(name = "XM")
    private String xm;

    @JSONField(name = "SFZH")
    private String sfzh;

    @JSONField(name = "SIGN")
    private String sign;
}
