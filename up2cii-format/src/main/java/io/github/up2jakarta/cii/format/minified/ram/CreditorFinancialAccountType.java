package io.github.up2jakarta.cii.format.minified.ram;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import javax.annotation.processing.Generated;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "CreditorFinancialAccountType", propOrder = {
        "ibanId",
        "accountName"
})
public class CreditorFinancialAccountType {

    // BT-84
    private String ibanId;

    // BT-85
    private String accountName;

    @XmlElement(name = "IBANID")
    public String getIbanId() {
        return this.ibanId;
    }

    public void setIbanId(String ibanId) {
        this.ibanId = ibanId;
    }

    @XmlElement(name = "AccountName")
    public String getAccountName() {
        return this.accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

}
