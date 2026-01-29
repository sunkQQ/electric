package com.electric.model.param.net;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 城市热点V5网费参数
 *
 * @Author sunk
 * @Date 2021/12/6
 */
@Getter
@Setter
@ToString
public class NetRequestParam {

    /** 编码 */
    private String code;

    /** 是否包含实时用量 */
    private String realtime_flag;

    /** 账号 */
    private String account;

    private String package_group_id;
}
