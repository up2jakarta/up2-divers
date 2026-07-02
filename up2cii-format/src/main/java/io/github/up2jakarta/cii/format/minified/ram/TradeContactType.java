package io.github.up2jakarta.cii.format.minified.ram;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import javax.annotation.processing.Generated;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TradeContactType", propOrder = {
        "personName",
        "departmentName",
        "telephoneUniversalCommunication",
        "emailURIUniversalCommunication"
})
public class TradeContactType {

    // BT-41, BT-56, EXT-FR-FE-23, EXT-FR-FE-40, EXT-FR-FE-63, EXT-FR-FE-86, EXT-FR-FE-109 and (1) specifications too.
    @XmlElement(name = "PersonName")
    private String personName;

    // BT-41, BT-56, EXT-FR-FE-23
    @XmlElement(name = "DepartmentName")
    private String departmentName;

    @XmlElement(name = "TelephoneUniversalCommunication")
    private UniversalCommunicationType telephoneUniversalCommunication;

    @XmlElement(name = "EmailURIUniversalCommunication")
    private UniversalCommunicationType emailURIUniversalCommunication;

    public String getPersonName() {
        return this.personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getDepartmentName() {
        return this.departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public UniversalCommunicationType getTelephoneUniversalCommunication() {
        return this.telephoneUniversalCommunication;
    }

    public void setTelephoneUniversalCommunication(UniversalCommunicationType telephoneUniversalCommunication) {
        this.telephoneUniversalCommunication = telephoneUniversalCommunication;
    }

    public UniversalCommunicationType getEmailURIUniversalCommunication() {
        return this.emailURIUniversalCommunication;
    }

    public void setEmailURIUniversalCommunication(UniversalCommunicationType emailURIUniversalCommunication) {
        this.emailURIUniversalCommunication = emailURIUniversalCommunication;
    }

}
