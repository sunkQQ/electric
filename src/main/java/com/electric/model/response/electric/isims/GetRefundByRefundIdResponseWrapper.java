package com.electric.model.response.electric.isims;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 根据退费ID查询响应包装类
 */
@Getter
@Setter
@ToString
@XmlRootElement(name = "GetRefundByRefundIdResult", namespace = "www.cdgf.com")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetRefundByRefundIdResponseWrapper {

    @XmlElement(name = "ErrorMessage", namespace = "http://schemas.datacontract.org/2004/07/ISimsAppWcfService.Model")
    private String errorMessage;

    @XmlElement(name = "IsSuccess", namespace = "http://schemas.datacontract.org/2004/07/ISimsAppWcfService.Model")
    private String isSuccess;

    @XmlElement(name = "Mdid", namespace = "http://schemas.datacontract.org/2004/07/ISimsAppWcfService.Model")
    private String mdid;

    @XmlElement(name = "Message", namespace = "http://schemas.datacontract.org/2004/07/ISimsAppWcfService.Model")
    private String message;

    @XmlElement(name = "RefundAmount", namespace = "http://schemas.datacontract.org/2004/07/ISimsAppWcfService.Model")
    private String refundAmount;

    @XmlElement(name = "RefundId", namespace = "http://schemas.datacontract.org/2004/07/ISimsAppWcfService.Model")
    private String refundId;

    @XmlElement(name = "RefundTime", namespace = "http://schemas.datacontract.org/2004/07/ISimsAppWcfService.Model")
    private String refundTime;

    @XmlElement(name = "RoomId", namespace = "http://schemas.datacontract.org/2004/07/ISimsAppWcfService.Model")
    private String roomId;

    @XmlElement(name = "StatusCode", namespace = "http://schemas.datacontract.org/2004/07/ISimsAppWcfService.Model")
    private String statusCode;

    @XmlElement(name = "Success", namespace = "http://schemas.datacontract.org/2004/07/ISimsAppWcfService.Model")
    private String success;

    @XmlElement(name = "issend", namespace = "http://schemas.datacontract.org/2004/07/ISimsAppWcfService.Model")
    private String issend;

}
