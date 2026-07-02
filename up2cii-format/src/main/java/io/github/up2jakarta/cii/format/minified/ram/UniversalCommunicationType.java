package io.github.up2jakarta.cii.format.minified.ram;

import io.github.up2jakarta.cii.format.minified.udt.UriIDType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import javax.annotation.processing.Generated;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UniversalCommunicationType", propOrder = {
        "uriId",
        "completeNumber"
})
public class UniversalCommunicationType {

    // BT-34, BT-43, BT-49, BT-58, EXT-FR-FE-12, EXT-FR-FE-25, EXT-FR-FE-29, EXT-FR-FE-42 and (8) specifications too.
    @XmlElement(name = "URIID")
    private UriIDType uriId;

    // BT-42, BT-57, EXT-FR-FE-24, EXT-FR-FE-41, EXT-FR-FE-64, EXT-FR-FE-87, EXT-FR-FE-110 and (1) specifications too.
    @XmlElement(name = "CompleteNumber")
    private String completeNumber;

    public UriIDType getUriId() {
        return this.uriId;
    }

    public void setUriId(UriIDType uriId) {
        this.uriId = uriId;
    }

    public String getCompleteNumber() {
        return this.completeNumber;
    }

    public void setCompleteNumber(String completeNumber) {
        this.completeNumber = completeNumber;
    }

}
