package com.electric.model.response.special.kayroad;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 凯路水费退款申请字段
 *
 * @author sunk
 * @date 2023/05/12
 */
@Getter
@Setter
@ToString
public class KayRoadQuZhiRefundApplyResponse {

    /** 退款手机号 */
    private String telPhone;

    /** 退款金额（厘） */
    private String refundMoney;

    /** 退款申请编号 */
    private String refundApplyNo;
}
