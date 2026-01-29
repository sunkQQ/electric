package com.electric.model.param.jiaofei.youcai;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 有财更新订单参数
 *
 * @author sunk
 * @date 2022/08/08
 */
@Getter
@Setter
@ToString
public class JiaofeiYoucaiUpdateOrderParam {

    /**
     * 订单号
     */
    @JSONField(name = "OrderNo")
    private String orderNo;

    /**
     * 1：支付成功
     * 0：支付失败
     */
    @JSONField(name = "OrderState")
    private String orderState;

    /**
     * 交易流水号
     */
    @JSONField(name = "SerialNo")
    private String serialNo;

    /**
     * 支付金额（元）
     */
    @JSONField(name = "PayAmount")
    private String payAmount;

    /**
     * 支付时间，格式：yyy-MM-dd HH:mm:ss
     */
    @JSONField(name = "PayDateTime")
    private String payDateTime;
}
