package com.electric.param.shuangqi;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 双旗公寓根据id获取学生基本信息
 *
 * @author sunk
 * @date 2023/08/04
 */
@Getter
@Setter
@ToString
public class ShuangqiPersonInfoParam {

    /** 签名 */
    private String key;

    /** 学号 */
    private String personsn;
}
