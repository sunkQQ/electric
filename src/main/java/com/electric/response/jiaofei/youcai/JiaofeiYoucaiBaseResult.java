package com.electric.response.jiaofei.youcai;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 有财缴费接口返回
 *
 * @author sunk
 * @date 2022/08/05
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class JiaofeiYoucaiBaseResult {

    /**
     * 请求状态  00：成功 其他：失败
     */
    @JSONField(name = "Respcode")
    private String respcode;

    /**
     * 请求描述
     */
    @JSONField(name = "Respdesc")
    private String respdesc;

}
