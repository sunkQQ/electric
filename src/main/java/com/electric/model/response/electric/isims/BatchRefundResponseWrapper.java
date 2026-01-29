package com.electric.model.response.electric.isims;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 批量退费响应包装类
 */
@Setter
@ToString
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

    /**
     * 退费响应详情
     */
    @Getter
    @Setter
    @ToString
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "RefundResponse", namespace = "http://schemas.datacontract.org/2004/07/ISims.App.Wcf.Service.Model", propOrder = { "dianliang",
                                                                                                                                       "mdname",
                                                                                                                                       "message",
                                                                                                                                       "money",
                                                                                                                                       "price",
                                                                                                                                       "refundId",
                                                                                                                                       "roomdm",
                                                                                                                                       "status",
                                                                                                                                       "cztype",
                                                                                                                                       "leftmoney",
                                                                                                                                       "hdclassname" })
    public static class RefundResponse {

        @XmlElement(name = "Dianliang", namespace = "http://schemas.datacontract.org/2004/07/ISims.App.Wcf.Service.Model")
        private String     dianliang;

        @XmlElement(name = "Mdname", namespace = "http://schemas.datacontract.org/2004/07/ISims.App.Wcf.Service.Model")
        private String     mdname;

        @XmlElement(name = "Message", namespace = "http://schemas.datacontract.org/2004/07/ISims.App.Wcf.Service.Model")
        private String     message;

        @XmlElement(name = "Money", namespace = "http://schemas.datacontract.org/2004/07/ISims.App.Wcf.Service.Model")
        private String     money;

        @XmlElement(name = "Price", namespace = "http://schemas.datacontract.org/2004/07/ISims.App.Wcf.Service.Model")
        private String     price;

        @XmlElement(name = "RefundId", namespace = "http://schemas.datacontract.org/2004/07/ISims.App.Wcf.Service.Model")
        private String     refundId;

        @XmlElement(name = "Roomdm", namespace = "http://schemas.datacontract.org/2004/07/ISims.App.Wcf.Service.Model")
        private String     roomdm;

        @XmlElement(name = "Status", namespace = "http://schemas.datacontract.org/2004/07/ISims.App.Wcf.Service.Model")
        private String     status;

        @XmlElement(name = "cztype", namespace = "http://schemas.datacontract.org/2004/07/ISims.App.Wcf.Service.Model")
        private String     cztype;

        /**
         * 剩余金额
         */
        @XmlElement(name = "leftmoney", namespace = "http://schemas.datacontract.org/2004/07/ISims.App.Wcf.Service.Model")
        private BigDecimal leftmoney;

        @XmlElement(name = "hdclassname", namespace = "http://schemas.datacontract.org/2004/07/ISims.App.Wcf.Service.Model")
        private String     hdclassname;

    }
}