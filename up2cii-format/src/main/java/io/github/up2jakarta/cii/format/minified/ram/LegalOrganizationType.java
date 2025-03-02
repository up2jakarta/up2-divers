package io.github.up2jakarta.cii.format.minified.ram;

import io.github.up2jakarta.cii.format.minified.udt.PartyIDType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import javax.annotation.processing.Generated;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "LegalOrganizationType", propOrder = {
        "id",
        "tradingBusinessName"
})
public class LegalOrganizationType {

    // BT-30, BT-47, EXT-FR-FE-08, BT-61, EXT-FR-FE-48, EXT-FR-FE-71, EXT-FR-FE-94, EXT-FR-FE-117
    private PartyIDType id;

    // BT-28, BT-45, EXT-FR-FE-05, EXT-FR-FE-45, EXT-FR-FE-68, EXT-FR-FE-91, EXT-FR-FE-114
    private String tradingBusinessName;

    @XmlElement(name = "ID")
    public PartyIDType getId() {
        return this.id;
    }

    public void setId(PartyIDType id) {
        this.id = id;
    }

    @XmlElement(name = "TradingBusinessName")
    public String getTradingBusinessName() {
        return this.tradingBusinessName;
    }

    public void setTradingBusinessName(String tradingBusinessName) {
        this.tradingBusinessName = tradingBusinessName;
    }

}
