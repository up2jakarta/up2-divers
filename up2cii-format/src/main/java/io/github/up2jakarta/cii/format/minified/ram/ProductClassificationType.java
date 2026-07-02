package io.github.up2jakarta.cii.format.minified.ram;

import io.github.up2jakarta.cii.format.minified.udt.ProductClassCodeType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import javax.annotation.processing.Generated;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProductClassificationType", propOrder = {"classCode"})
public class ProductClassificationType {

    // BT-158
    @XmlElement(name = "ClassCode")
    private ProductClassCodeType classCode;

    public ProductClassCodeType getClassCode() {
        return this.classCode;
    }

    public void setClassCode(ProductClassCodeType classCode) {
        this.classCode = classCode;
    }

}
