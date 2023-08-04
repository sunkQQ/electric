package com.electric.response.shuangqi;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 双旗返回字段
 *
 * @author sunk
 * @date 2023/08/03
 */
@Getter
@Setter
@ToString
public class ShuangqiBaseResponse implements Serializable {

    private static final long serialVersionUID = -4559284630571380656L;

    /** 状态码 0 成功 其他失败 */
    private String            code;

    /** 返回说明 */
    private String            msg;

    /** data数据 */
    private String            data;
}
