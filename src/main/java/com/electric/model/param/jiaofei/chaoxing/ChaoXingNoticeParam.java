package com.electric.model.param.jiaofei.chaoxing;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 超星缴费通知参数
 *
 * @author sunk
 * @date 2023/05/09
 */
@Getter
@Setter
@ToString
public class ChaoXingNoticeParam {

    /**
     * 证件号类型   1 读者证号  2 读者账号
     */
    private String userIdType;

    /**
     * 证件号码
     */
    private String certNo;

    /**
     * 付款金额
     */
    private String dueDebt;

    /**
     * 支付方式    1 现金  2预付款 3一卡通 4支票 5支付宝 6微信 7银联
     */
    private String payType;

    /**
     * 经手人
     */
    private String createBy;

    /**
     * 工作台
     */
    private String deskId;
}
