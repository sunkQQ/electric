package com.electric.model.response.electric.isims;

import java.util.List;

import javax.xml.bind.annotation.*;

import lombok.Getter;
import lombok.Setter;

/**
 * @author sunkang
 * 2025/9/24
 */
@XmlRootElement(name = "QueryRoomBalanceResult", namespace = "www.cdgf.com")
@XmlAccessorType(XmlAccessType.FIELD)
public class BalanceResponseWrapper {

    @XmlElement(name = "BalanceResponse", namespace = "http://schemas.datacontract.org/2004/07/ISims.App.Wcf.Service.Model")
    private List<BalanceResponse> balanceResponse;

    public BalanceResponseWrapper() {
    }

    public BalanceResponseWrapper(List<BalanceResponse> balanceResponse) {
        this.balanceResponse = balanceResponse;
    }

    public List<BalanceResponse> getBalanceResponse() {
        return balanceResponse;
    }

    public void setBalanceResponse(List<BalanceResponse> balanceResponse) {
        this.balanceResponse = balanceResponse;
    }

    @Getter
    @Setter
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "BalanceResponse", namespace = "http://schemas.datacontract.org/2004/07/ISims.App.Wcf.Service.Model", propOrder = { "leftdu",
                                                                                                                                        "leftmoney",
                                                                                                                                        "leftration",
                                                                                                                                        "leftrationdu",
                                                                                                                                        "mdname",
                                                                                                                                        "message",
                                                                                                                                        "roomdm",
                                                                                                                                        "status",
                                                                                                                                        "cztype",
                                                                                                                                        "hdclassname" })
    public static class BalanceResponse {
        @XmlElement(name = "Leftdu", namespace = "http://schemas.datacontract.org/2004/07/ISims.App.Wcf.Service.Model")
        private String leftdu;

        @XmlElement(name = "Leftmoney", namespace = "http://schemas.datacontract.org/2004/07/ISims.App.Wcf.Service.Model")
        private String leftmoney;

        @XmlElement(name = "Leftration", namespace = "http://schemas.datacontract.org/2004/07/ISims.App.Wcf.Service.Model")
        private String leftration;

        @XmlElement(name = "Leftrationdu", namespace = "http://schemas.datacontract.org/2004/07/ISims.App.Wcf.Service.Model")
        private String leftrationdu;

        @XmlElement(name = "Mdname", namespace = "http://schemas.datacontract.org/2004/07/ISims.App.Wcf.Service.Model")
        private String mdname;

        @XmlElement(name = "Message", namespace = "http://schemas.datacontract.org/2004/07/ISims.App.Wcf.Service.Model")
        private String message;

        @XmlElement(name = "Roomdm", namespace = "http://schemas.datacontract.org/2004/07/ISims.App.Wcf.Service.Model")
        private String roomdm;

        @XmlElement(name = "Status", namespace = "http://schemas.datacontract.org/2004/07/ISims.App.Wcf.Service.Model")
        private String status;

        @XmlElement(name = "cztype", namespace = "http://schemas.datacontract.org/2004/07/ISims.App.Wcf.Service.Model")
        private String cztype;

        @XmlElement(name = "hdclassname", namespace = "http://schemas.datacontract.org/2004/07/ISims.App.Wcf.Service.Model")
        private String hdclassname;

    }
}