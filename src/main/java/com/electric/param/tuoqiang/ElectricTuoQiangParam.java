package com.electric.param.tuoqiang;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * 
 * @Author Administrator
 * @Date 2021年4月17日
 *
 */
@Getter
@Setter
@ToString
public class ElectricTuoQiangParam {

	/** 回复内容 */
	private String response_content;

	/** int 服务器时间戳 （必填） */
	private String timestamp;

	/** 签名 (必填) */
	private String sign;

	/** 房间ID */
	private String roomId;
}
