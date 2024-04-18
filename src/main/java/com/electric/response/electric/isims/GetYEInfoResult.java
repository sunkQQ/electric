package com.electric.response.electric.isims;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.alibaba.fastjson.JSONObject;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 返回
 *
 * @author sunk
 * @date 2024/04/16
 */
@Getter
@Setter
@ToString
@XmlRootElement(name = "GetYEInfoResult")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetYEInfoResult {
    private String     code;
    private String     msg;
    private JSONObject data;
}
