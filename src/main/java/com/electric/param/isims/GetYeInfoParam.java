package com.electric.param.isims;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * 常工接收参数
 *
 * @author sunk
 * @date 2024/05/25
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class GetYeInfoParam {

    @XmlElement(name = "cdgf:signkey", namespace = "www.cdgf.com/cdgf")
    private String signKey;

    @XmlElement(name = "roomdm")
    private String roomdm;

    public String getSignKey() {
        return signKey;
    }

    public void setSignKey(String signKey) {
        this.signKey = signKey;
    }

    public String getRoomdm() {
        return roomdm;
    }

    public void setRoomdm(String roomdm) {
        this.roomdm = roomdm;
    }

    @Override
    public String toString() {
        return "GetYeInfoParam{" + "signKey='" + signKey + '\'' + ", roomdm='" + roomdm + '\'' + '}';
    }
}
