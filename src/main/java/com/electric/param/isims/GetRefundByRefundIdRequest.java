package com.electric.param.isims;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 根据退费ID查询请求对象
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetRefundByRefundIdRequest", propOrder = { "refundId", "signkey" })
public class GetRefundByRefundIdRequest {

    @XmlElement(name = "RefundId", namespace = "http://schemas.datacontract.org/2004/07/ISimsAppWcfService.Model")
    private String refundId;

    @XmlElement(name = "Signkey", namespace = "http://schemas.datacontract.org/2004/07/ISimsAppWcfService.Model")
    private String signkey;

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    public String getSignkey() {
        return signkey;
    }

    public void setSignkey(String signkey) {
        this.signkey = signkey;
    }

}