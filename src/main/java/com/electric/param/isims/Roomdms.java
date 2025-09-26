package com.electric.param.isims;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 房间编码列表包装类
 * @author sunkang
 * 2025/9/24
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Roomdms", propOrder = { "string" })
public class Roomdms {

    @XmlElement(name = "string", namespace = "http://schemas.microsoft.com/2003/10/Serialization/Arrays")
    private List<String> string;

    public List<String> getString() {
        return string;
    }

    public void setString(List<String> string) {
        this.string = string;
    }
}
