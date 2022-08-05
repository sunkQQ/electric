package com.electric.param.jiaofei.youcai;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 有财接口请求参数
 *
 * @author sunk
 * @date 2022/08/05
 */
@Getter
@Setter
@ToString
public class JiaofeiYoucaiBaseParam {

    private String merchantno;

    private String json;

    private String Signature;
}
