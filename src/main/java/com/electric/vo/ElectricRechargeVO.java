package com.electric.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 电费充值数据
 * 
 * @Author Administrator
 * @Date 2020-9-16
 *
 */
@Getter
@Setter
@ToString
public class ElectricRechargeVO {

	/** 电费订单号 */
	private String orderCode;

	/** 校区编码 */
	private String areaCode;

	/** 楼栋编码 */
	private String buildingCode;

	/** 楼层编码 */
	private String floorCode;

	/** 房间编码 */
	private String roomCode;

	/** 充值金额 */
	private String rechargeMoney;

	/** 学工号 */
	private String jobNo;

	/** 充值时间 */
	private String rechargeTime;

	/** 电费订单号 */
	private String bussiOrder;
}
