package io.github.up2jakarta.cii.format.minified.ram;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import javax.annotation.processing.Generated;
import java.util.List;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SupplyChainTradeTransactionType", propOrder = {
        "includedSupplyChainTradeLineItem",
        "applicableHeaderTradeAgreement",
        "applicableHeaderTradeDelivery",
        "applicableHeaderTradeSettlement"
})
public class SupplyChainTradeTransactionType {

    // BG-25
    @XmlElement(name = "IncludedSupplyChainTradeLineItem")
    private List<SupplyChainTradeLineItemType> includedSupplyChainTradeLineItem;

    @XmlElement(name = "ApplicableHeaderTradeAgreement", required = true)
    private HeaderTradeAgreementType applicableHeaderTradeAgreement;

    @XmlElement(name = "ApplicableHeaderTradeDelivery", required = true)
    private HeaderTradeDeliveryType applicableHeaderTradeDelivery;

    // BG-19
    @XmlElement(name = "ApplicableHeaderTradeSettlement", required = true)
    private HeaderTradeSettlementType applicableHeaderTradeSettlement;

    public List<SupplyChainTradeLineItemType> getIncludedSupplyChainTradeLineItem() {
        return this.includedSupplyChainTradeLineItem;
    }

    public void setIncludedSupplyChainTradeLineItem(List<SupplyChainTradeLineItemType> includedSupplyChainTradeLineItem) {
        this.includedSupplyChainTradeLineItem = includedSupplyChainTradeLineItem;
    }

    public HeaderTradeAgreementType getApplicableHeaderTradeAgreement() {
        return this.applicableHeaderTradeAgreement;
    }

    public void setApplicableHeaderTradeAgreement(HeaderTradeAgreementType applicableHeaderTradeAgreement) {
        this.applicableHeaderTradeAgreement = applicableHeaderTradeAgreement;
    }

    public HeaderTradeDeliveryType getApplicableHeaderTradeDelivery() {
        return this.applicableHeaderTradeDelivery;
    }

    public void setApplicableHeaderTradeDelivery(HeaderTradeDeliveryType applicableHeaderTradeDelivery) {
        this.applicableHeaderTradeDelivery = applicableHeaderTradeDelivery;
    }

    public HeaderTradeSettlementType getApplicableHeaderTradeSettlement() {
        return this.applicableHeaderTradeSettlement;
    }

    public void setApplicableHeaderTradeSettlement(HeaderTradeSettlementType applicableHeaderTradeSettlement) {
        this.applicableHeaderTradeSettlement = applicableHeaderTradeSettlement;
    }

}
