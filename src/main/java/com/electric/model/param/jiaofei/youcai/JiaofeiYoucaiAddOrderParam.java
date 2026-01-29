package com.electric.model.param.jiaofei.youcai;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 有财下单请求参数
 *
 * @author sunk
 * @date 2022/08/08
 */
@Getter
@Setter
@ToString
public class JiaofeiYoucaiAddOrderParam {

    /**
     * 学号
     */
    @JSONField(name = "StudentCode")
    private String         studentCode;

    /**
     * 结算日期
     */
    @JSONField(name = "SettleDate")
    private String         settleDate;

    /**
     * 订单金额
     */
    @JSONField(name = "TotalAmount")
    private String         totalAmount;

    /**
     * 订单明细
     */
    @JSONField(name = "PayItems")
    private List<PayItems> payItems;

    @Getter
    @Setter
    @ToString
    public static class PayItems {

        /**
         * 费用Id 必缴项目有值
         * 选缴项目无值
         * 原值返回
         */
        @JSONField(name = "FeeId")
        private String feeId;

        /**
         * 是否必缴项目
         * True
         * False
         * 原值返回
         */
        @JSONField(name = "IsMustPay")
        private String isMustPay;

        /**
         * 收费年度
         * 原值返回
         */
        @JSONField(name = "Year")
        private String year;

        /**
         * 收费项目编号
         * 原值返回
         */
        @JSONField(name = "ProjectCode")
        private String projectCode;

        /**
         * 收费区间编号
         * 原值返回
         */
        @JSONField(name = "FeeRangCode")
        private String feeRangCode;

        /**
         * 支付金额（元）
         */
        @JSONField(name = "Amount")
        private String amount;
    }
}
