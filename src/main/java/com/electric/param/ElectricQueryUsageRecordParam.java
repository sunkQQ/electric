package com.electric.param;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 查询剩余量参数
 * 
 * @Author Administrator
 * @Date 2020-9-10
 *
 */
@Getter
@Setter
@ToString
public class ElectricQueryUsageRecordParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 校区编码 */
    @NotEmpty(message = "校区编码不能为空")
    private String            areaCode;

    /** 楼栋编码 */
    @NotEmpty(message = "楼栋编码不能为空")
    private String            buildingCode;

    /** 楼层编码 */
    @NotEmpty(message = "楼层编码不能为空")
    private String            floorCode;

    /** 房间编码 */
    @NotEmpty(message = "房间编码不能为空")
    private String            roomCode;

    private Integer           pageNo;

    private Integer           pageSize;

    @NotEmpty(message = "签名不能为空")
    private String            sign;
}
