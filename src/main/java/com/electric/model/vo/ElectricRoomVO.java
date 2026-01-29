package com.electric.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 房间数据
 * 
 * @Author Administrator
 * @Date 2020-9-16
 *
 */
@Getter
@Setter
@ToString
public class ElectricRoomVO {

	/** 校区编码 */
	private String areaCode;

	/** 校区名称 */
	private String areaName;

	/** 楼栋编码 */
	private String buildingCode;

	/** 楼栋名称 */
	private String buildingName;

	/** 楼层编码 */
	private String floorCode;

	/** 楼层名称 */
	private String floorName;

	/** 房间编码 */
	private String roomCode;

	/** 房间名称 */
	private String roomName;
}
