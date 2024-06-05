package com.electric.param.jiaofei.fudanfuyi;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author sunk
 * @date 2024/06/05
 */
@Getter
@Setter
@ToString
public class FuDanFuYiQueryFeeParam {

    /** 接口的版本（1.0.0.6） */
    private String version;

    /** 接口类型(SH03 通知缴费结果)  SH02即查询缴费项时请求的参数 */
    private String transcode;

    /** 登录的用户名 */
    private String userid;

    /** 查询费用项方式 1：根据学号查询 2：根据身份证号或护照号查询费用项 */
    private String queryfeetype;
}
