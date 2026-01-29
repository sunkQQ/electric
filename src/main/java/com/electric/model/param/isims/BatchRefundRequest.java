package com.electric.model.param.isims;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import lombok.ToString;

/**
 * 批量退费请求对象
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BatchRefundRequest", propOrder = { "refundItems", "signkey" })
public class BatchRefundRequest {

    @XmlElement(name = "RefundItems", namespace = "http://schemas.datacontract.org/2004/07/ISims.App.Wcf.Service.Model")
    private RefundItems refundItems;

    @XmlElement(name = "Signkey", namespace = "http://schemas.datacontract.org/2004/07/ISims.App.Wcf.Service.Model")
    private String      signkey;

    public RefundItems getRefundItems() {
        return refundItems;
    }

    public void setRefundItems(RefundItems refundItems) {
        this.refundItems = refundItems;
    }

    public String getSignkey() {
        return signkey;
    }

    public void setSignkey(String signkey) {
        this.signkey = signkey;
    }

    /**
     * 退费项列表包装类
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "RefundItems", propOrder = { "refundItem" })
    public static class RefundItems {

        @XmlElement(name = "RefundItem", namespace = "http://schemas.datacontract.org/2004/07/ISims.App.Wcf.Service.Model")
        private List<RefundItem> refundItem;

        public List<RefundItem> getRefundItem() {
            if (refundItem == null) {
                refundItem = new ArrayList<>();
            }
            return refundItem;
        }

        public void setRefundItem(List<RefundItem> refundItem) {
            this.refundItem = refundItem;
        }
    }

    /**
     * 单个退费项
     */
    @ToString
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "RefundItem", propOrder = { "refundId", "roomdm", "cztype" })
    public static class RefundItem {

        @XmlElement(name = "RefundId", namespace = "http://schemas.datacontract.org/2004/07/ISims.App.Wcf.Service.Model")
        private String refundId;

        @XmlElement(name = "Roomdm", namespace = "http://schemas.datacontract.org/2004/07/ISims.App.Wcf.Service.Model")
        private String roomdm;

        @XmlElement(name = "cztype", namespace = "http://schemas.datacontract.org/2004/07/ISims.App.Wcf.Service.Model")
        private String cztype;

        public String getRefundId() {
            return refundId;
        }

        public void setRefundId(String refundId) {
            this.refundId = refundId;
        }

        public String getRoomdm() {
            return roomdm;
        }

        public void setRoomdm(String roomdm) {
            this.roomdm = roomdm;
        }

        public String getCztype() {
            return cztype;
        }

        public void setCztype(String cztype) {
            this.cztype = cztype;
        }
    }
}