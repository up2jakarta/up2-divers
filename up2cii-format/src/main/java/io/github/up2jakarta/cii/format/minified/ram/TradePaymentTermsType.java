package io.github.up2jakarta.cii.format.minified.ram;

import io.github.up2jakarta.cii.format.minified.udt.DateTimeType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import javax.annotation.processing.Generated;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TradePaymentTermsType", propOrder = {
        "description",
        "dueDateDateTime",
        "directDebitMandateId"
})
public class TradePaymentTermsType {

    // BT-20
    @XmlElement(name = "Description")
    private String description;

    @XmlElement(name = "DueDateDateTime")
    private DateTimeType dueDateDateTime;

    // BT-89
    @XmlElement(name = "DirectDebitMandateID")
    private String directDebitMandateId;

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DateTimeType getDueDateDateTime() {
        return this.dueDateDateTime;
    }

    public void setDueDateDateTime(DateTimeType dueDateDateTime) {
        this.dueDateDateTime = dueDateDateTime;
    }

    public String getDirectDebitMandateId() {
        return this.directDebitMandateId;
    }

    public void setDirectDebitMandateId(String directDebitMandateId) {
        this.directDebitMandateId = directDebitMandateId;
    }

}
