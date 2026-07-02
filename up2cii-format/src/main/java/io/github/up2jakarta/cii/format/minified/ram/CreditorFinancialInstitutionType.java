package io.github.up2jakarta.cii.format.minified.ram;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import javax.annotation.processing.Generated;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreditorFinancialInstitutionType", propOrder = {"bicId"})
public class CreditorFinancialInstitutionType {

    // BT-86
    @XmlElement(name = "BICID")
    private String bicId;

    public String getBicId() {
        return this.bicId;
    }

    public void setBicId(String bicId) {
        this.bicId = bicId;
    }

}
