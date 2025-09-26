package com.electric.response.electric.isims;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 批量退费响应包装类
 */
@XmlRootElement(name = "BatchRefundResult", namespace = "www.cdgf.com")
@XmlAccessorType(XmlAccessType.FIELD)
public class BatchRefundResponseWrapper {

    @XmlElement(name = "RefundResponse", namespace = "http://schemas.datacontract.org/2004/07/ISims.App.Wcf.Service.Model")
    private List<RefundResponse> refundResponse;

    public BatchRefundResponseWrapper() {
    }

    public List<RefundResponse> getRefundResponse() {
        if (refundResponse == null) {
            refundResponse = new ArrayList<>();
        }
        return refundResponse;
    }

    public void setRefundResponse(List<RefundResponse> refundResponse) {
        this.refundResponse = refundResponse;
    }

    /**
     * 退费响应详情
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "RefundResponse", namespace = "http://schemas.datacontract.org/2004/07/ISims.App.Wcf.Service.Model", propOrder = { "dianliang",
                                                                                                                                       "mdname",
                                                                                                                                       "message",
                                                                                                                                       "money",
                                                                                                                                       "price",
                                                                                                                                       "refundId",
                                                                                                                                       "roomdm",
                                                                                                                                       "status" })
    public static class RefundResponse {

        @XmlElement(name = "Dianliang", namespace = "http://schemas.datacontract.org/2004/07/ISims.App.Wcf.Service.Model")
        private String dianliang;

        @XmlElement(name = "Mdname", namespace = "http://schemas.datacontract.org/2004/07/ISims.App.Wcf.Service.Model")
        private String mdname;

        @XmlElement(name = "Message", namespace = "http://schemas.datacontract.org/2004/07/ISims.App.Wcf.Service.Model")
        private String message;

        @XmlElement(name = "Money", namespace = "http://schemas.datacontract.org/2004/07/ISims.App.Wcf.Service.Model")
        private String money;

        @XmlElement(name = "Price", namespace = "http://schemas.datacontract.org/2004/07/ISims.App.Wcf.Service.Model")
        private String price;

        @XmlElement(name = "RefundId", namespace = "http://schemas.datacontract.org/2004/07/ISims.App.Wcf.Service.Model")
        private String refundId;

        @XmlElement(name = "Roomdm", namespace = "http://schemas.datacontract.org/2004/07/ISims.App.Wcf.Service.Model")
        private String roomdm;

        @XmlElement(name = "Status", namespace = "http://schemas.datacontract.org/2004/07/ISims.App.Wcf.Service.Model")
        private String status;

        // getter and setter methods
        public String getDianliang() {
            return dianliang;
        }

        public void setDianliang(String dianliang) {
            this.dianliang = dianliang;
        }

        public String getMdname() {
            return mdname;
        }

        public void setMdname(String mdname) {
            this.mdname = mdname;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}