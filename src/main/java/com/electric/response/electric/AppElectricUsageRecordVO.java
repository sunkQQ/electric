package com.electric.response.electric;

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
public class AppElectricUsageRecordVO {

    /** 时间 */
    private String dateTime;

    /** 用量 */
    private String dayUsage;

    public AppElectricUsageRecordVO(String dateTime, String dayUsage) {
        this.dateTime = dateTime;
        this.dayUsage = dayUsage;
    }

    public AppElectricUsageRecordVO() {
    }
}
