package io.github.up2jakarta.cii.format.minified.ram;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import javax.annotation.processing.Generated;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "ProcuringProjectType", propOrder = {
        "id",
        "name"
})
public class ProcuringProjectType {

    // BT-11
    private String id;

    private String name;

    @XmlElement(name = "ID", required = true)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlElement(name = "Name", required = true)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
