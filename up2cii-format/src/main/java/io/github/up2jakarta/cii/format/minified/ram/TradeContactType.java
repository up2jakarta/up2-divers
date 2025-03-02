package io.github.up2jakarta.cii.format.minified.ram;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import javax.annotation.processing.Generated;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "TradeContactType", propOrder = {
        "personName",
        "departmentName",
        "telephoneUniversalCommunication",
        "emailURIUniversalCommunication"
})
public class TradeContactType {

    // BT-41, BT-56, EXT-FR-FE-23, EXT-FR-FE-40, EXT-FR-FE-63, EXT-FR-FE-86, EXT-FR-FE-109 and (1) specifications too.
    private String personName;

    // BT-41, BT-56, EXT-FR-FE-23
    private String departmentName;

    private UniversalCommunicationType telephoneUniversalCommunication;

    private UniversalCommunicationType emailURIUniversalCommunication;

    @XmlElement(name = "PersonName")
    public String getPersonName() {
        return this.personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    @XmlElement(name = "DepartmentName")
    public String getDepartmentName() {
        return this.departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @XmlElement(name = "TelephoneUniversalCommunication")
    public UniversalCommunicationType getTelephoneUniversalCommunication() {
        return this.telephoneUniversalCommunication;
    }

    public void setTelephoneUniversalCommunication(UniversalCommunicationType telephoneUniversalCommunication) {
        this.telephoneUniversalCommunication = telephoneUniversalCommunication;
    }

    @XmlElement(name = "EmailURIUniversalCommunication")
    public UniversalCommunicationType getEmailURIUniversalCommunication() {
        return this.emailURIUniversalCommunication;
    }

    public void setEmailURIUniversalCommunication(UniversalCommunicationType emailURIUniversalCommunication) {
        this.emailURIUniversalCommunication = emailURIUniversalCommunication;
    }

}
