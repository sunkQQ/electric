package com.electric.param;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author sunk
 * @date 2024/04/16
 */
@Getter
@Setter
@ToString
@XmlAccessorType(XmlAccessType.FIELD)
public class TestParam {

    @XmlElement(name = "roomdm", namespace = "electric", required = true, type = String.class)
    private String  roomdm;

    private Integer centerExportPlaintext;

    public TestParam(String roomdm) {
        this.roomdm = roomdm;
    }
}
