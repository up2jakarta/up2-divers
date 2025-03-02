package io.github.up2jakarta.cii.format.minified.ram;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import javax.annotation.processing.Generated;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "DebtorFinancialAccountType", propOrder = {"ibanId"})
public class DebtorFinancialAccountType {

    // BT-91
    private String ibanId;

    @XmlElement(name = "IBANID")
    public String getIbanId() {
        return this.ibanId;
    }

    public void setIbanId(String ibanId) {
        this.ibanId = ibanId;
    }

}
