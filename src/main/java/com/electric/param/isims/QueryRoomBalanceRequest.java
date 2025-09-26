package com.electric.param.isims;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 查询房间余额请求对象
 *
 * @author sunkang
 * 2025/9/24
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QueryRoomBalanceRequest", propOrder = { "roomdms", "signkey" })
public class QueryRoomBalanceRequest {

    @XmlElement(name = "Roomdms", namespace = "http://schemas.datacontract.org/2004/07/ISims.App.Wcf.Service.Model")
    private Roomdms roomdms;

    @XmlElement(name = "Signkey", namespace = "http://schemas.datacontract.org/2004/07/ISims.App.Wcf.Service.Model")
    private String  signkey;

    public Roomdms getRoomdms() {
        return roomdms;
    }

    public void setRoomdms(Roomdms roomdms) {
        this.roomdms = roomdms;
    }

    public String getSignkey() {
        return signkey;
    }

    public void setSignkey(String signkey) {
        this.signkey = signkey;
    }
}
