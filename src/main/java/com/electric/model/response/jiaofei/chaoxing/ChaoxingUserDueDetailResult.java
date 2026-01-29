package com.electric.model.response.jiaofei.chaoxing;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 超星欠款接口返回字段
 *
 * @author sunk
 * @date 2023/05/08
 */
@Getter
@Setter
@ToString
public class ChaoxingUserDueDetailResult {

    /** 欠款总金额 */
    private String duePay;

    private String list;

    @Getter
    @Setter
    @ToString
    public static class DueDetailList {
        /** 明细id */
        private String behaviourDetailId;

        /** 行为类型 */
        private String behaviourTypeId;

        /** 行为名称 */
        private String behaviourName;

        /**  */
        private String avoidPunish;

        /** 金额 */
        private String dueDebt;

        /** 题名 */
        private String title;

        /** 作者 */
        private String author;

        /** 索书号 */
        private String callNo;

        /** 借阅时间 */
        private String loanDate;

        /** 应还时间 */
        private String normReturnDate;

        /** 还书时间 */
        private String returnDate;
    }
}
