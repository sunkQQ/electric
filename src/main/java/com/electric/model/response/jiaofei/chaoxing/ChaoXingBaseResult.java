package com.electric.model.response.jiaofei.chaoxing;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 超星接口返回数据
 *
 * @author sunk
 * @date 2023/05/08
 */
@Getter
@Setter
@ToString
public class ChaoXingBaseResult {

    /** 成功标志 */
    private Boolean success;

    /** 提示信息 */
    private String  message;

    /** 错误码 */
    private String  errCode;

    /** 错误吗？ */
    private String  errorCode;

    /** 返回数据 */
    private String  data;
}
