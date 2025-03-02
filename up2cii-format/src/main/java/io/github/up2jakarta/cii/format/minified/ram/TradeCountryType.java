package io.github.up2jakarta.cii.format.minified.ram;

import io.github.up2jakarta.cii.edi.CountryIDType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import javax.annotation.processing.Generated;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "TradeCountryType", propOrder = {"id"})
public class TradeCountryType {

    // BT-159
    private CountryIDType id;

    @XmlElement(name = "ID")
    public CountryIDType getId() {
        return this.id;
    }

    public void setId(CountryIDType id) {
        this.id = id;
    }

}
