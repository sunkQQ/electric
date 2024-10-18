package com.electric.response.jiaofei.youcai;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * 获取学生代缴信息返回字段
 *
 * @author sunk
 * @date 2022/08/05
 */
@Getter
@Setter
@ToString
public class JiaofeiYoucaiGetFeeItemResult extends JiaofeiYoucaiBaseResult {

    /**
     * 学号
     */
    @JSONField(name = "StudentCode")
    private String         studentCode;

    /**
     * 姓名
     */
    @JSONField(name = "StudentName")
    private String         studentName;

    @JSONField(name = "FeeItems")
    private List<FeeItems> feeItems;

    @Getter
    @Setter
    @ToString
    public static class FeeItems {

        @JSONField(name = "Name")
        private String      name;

        @JSONField(name = "Year")
        private String      year;

        @JSONField(name = "Message")
        private String      message;

        @JSONField(name = "Items")
        private List<Items> items;
    }

    @Getter
    @Setter
    @ToString
    public static class Items {

        /**
         * 费用Id
         * 开始标记
         * 必缴项目:bs-
         * 选缴项目:xs-
         * 学分学费:xfxf-
         */
        @JSONField(name = "FeeId")
        private String     feeId;

        /**
         * 是否必缴项目
         * True
         * False
         */
        @JSONField(name = "IsMustPay")
        private Boolean    isMustPay;

        /**
         * 收费年度
         */
        @JSONField(name = "Year")
        private Integer    year;

        /**
         * 收费项目编号
         */
        @JSONField(name = "ProjectCode")
        private String     projectCode;

        /**
         * 收费项目名称
         */
        @JSONField(name = "ProjectName")
        private String     projectName;

        /**
         * 收费区间编号
         */
        @JSONField(name = "FeeRangCode")
        private String     feeRangCode;

        /**
         * 收费区间名称
         */
        @JSONField(name = "FeeRangName")
        private String     feeRangName;

        /**
         * 收费金额（元）
         */
        @JSONField(name = "Fee")
        private BigDecimal fee;

        /**
         * 入账银行卡号
         */
        @JSONField(name = "BankAccount")
        private String     bankAccount;

        /**
         * 已缴金额
         */
        @JSONField(name = "Paid")
        private String     paid;

        /**
         * 费用类型
         * 0:必收
         * 1:选收
         * 2:学分学费
         */
        @JSONField(name = "FeeType")
        private String     feeType;

        /**
         * 是否允许部分缴费
         */
        @JSONField(name = "IsAllowPartPay")
        private String     isAllowPartPay;

        @JSONField(name = "OrderNo")
        private String     orderNo;
    }

    public JiaofeiYoucaiGetFeeItemResult(String respcode, String respdesc) {
        super(respcode, respdesc);
    }
}
