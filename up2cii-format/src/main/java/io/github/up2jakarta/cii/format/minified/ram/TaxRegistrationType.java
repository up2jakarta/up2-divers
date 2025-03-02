package io.github.up2jakarta.cii.format.minified.ram;

import io.github.up2jakarta.cii.format.minified.udt.FiscalIDType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import javax.annotation.processing.Generated;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "TaxRegistrationType", propOrder = {"id"})
public class TaxRegistrationType {

    // BT-31, BT-32, BT-48, EXT-FR-FE-10, EXT-FR-FE-27, EXT-FR-FE-50, EXT-FR-FE-73, EXT-FR-FE-96 and (2) specifications too.
    private FiscalIDType id;

    @XmlElement(name = "ID")
    public FiscalIDType getId() {
        return this.id;
    }

    public void setId(FiscalIDType id) {
        this.id = id;
    }

}
