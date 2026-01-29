package com.electric.model.response.electric;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 电费每日用电量查询
 * 
 * @author sunk
 * @Data 2020年6月23日
 */
@Getter
@Setter
@ToString
public class AppRoomBuyRecordVO {

    /** 时间 */
    private String dateTime;

    /** 类型（如：电费充值， 不返回不显示） */
    private String typeName;

    /** 充值量（1度或1元） */
    private String amount;

    public AppRoomBuyRecordVO(String dateTime, String typeName, String amount) {
        this.dateTime = dateTime;
        this.typeName = typeName;
        this.amount = amount;
    }

    public AppRoomBuyRecordVO(String dateTime, String amount) {
        this.dateTime = dateTime;
        this.amount = amount;
    }

    public AppRoomBuyRecordVO() {
    }
}
