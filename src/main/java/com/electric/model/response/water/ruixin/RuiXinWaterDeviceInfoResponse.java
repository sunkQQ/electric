package com.electric.model.response.water.ruixin;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 瑞信水费设备详情
 *
 * @author sunk
 * @date 2024/08/07
 */
@Getter
@Setter
@ToString
public class RuiXinWaterDeviceInfoResponse {

    private long   communicateType;
    private long   companyId;
    private long   deptId;
    /**
     * 设备别名 名称
     */
    private String deviceName;
    /**
     * 设备号 唯一
     */
    private long   deviceNum;
    private long   deviceType;
    private long   enable;
    private long   entranceType;
    private long   id;
    /**
     * 当前网络地址
     */
    private String inetSocketAddress;
    /**
     * 设备最新数据包到达服务器的时间戳
     */
    private long   lastPackageArriveAt;
    /**
     * 服务器发送最新一包数据给设备的时间戳
     */
    private long   lastPackageSendOutAt;
    private String lastReceivePackageContent;
    private long   lastRecordNO;
    private String lastSendPackageContent;
    private long   packageFromDeviceCount;
    private long   packageFromServerCount;
    private long   paramGroupId;
    /**
     * 设备二维码 base64
     */
    private String qrImgStr;
    /**
     * 设备二维码 Url
     */
    private String qrUrlStr;
    private long   recordNO;
    private String remark;
    /**
     * 设备序列号 唯一
     */
    private String sn;
    private long   transferId;
    private long   waterControl;
}
