package io.github.up2jakarta.cii.format.minified.ram;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import javax.annotation.processing.Generated;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "ProductCharacteristicType", propOrder = {
        "description",
        "value"
})
public class ProductCharacteristicType {

    // BT-160
    private String description;

    // BT-161
    private String value;

    @XmlElement(name = "Description")
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlElement(name = "Value")
    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
