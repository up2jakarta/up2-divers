package io.github.up2jakarta.cii.format.minified.ram;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import javax.annotation.processing.Generated;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "SupplyChainTradeLineItemType", propOrder = {
        "associatedDocumentLineDocument",
        "specifiedTradeProduct",
        "specifiedLineTradeAgreement",
        "specifiedLineTradeDelivery",
        "specifiedLineTradeSettlement"
})
public class SupplyChainTradeLineItemType {

    private DocumentLineDocumentType associatedDocumentLineDocument;

    // BG-31
    private TradeProductType specifiedTradeProduct;

    // BG-29
    private LineTradeAgreementType specifiedLineTradeAgreement;

    private LineTradeDeliveryType specifiedLineTradeDelivery;

    private LineTradeSettlementType specifiedLineTradeSettlement;

    @XmlElement(name = "AssociatedDocumentLineDocument", required = true)
    public DocumentLineDocumentType getAssociatedDocumentLineDocument() {
        return this.associatedDocumentLineDocument;
    }

    public void setAssociatedDocumentLineDocument(DocumentLineDocumentType associatedDocumentLineDocument) {
        this.associatedDocumentLineDocument = associatedDocumentLineDocument;
    }

    @XmlElement(name = "SpecifiedTradeProduct")
    public TradeProductType getSpecifiedTradeProduct() {
        return this.specifiedTradeProduct;
    }

    public void setSpecifiedTradeProduct(TradeProductType specifiedTradeProduct) {
        this.specifiedTradeProduct = specifiedTradeProduct;
    }

    @XmlElement(name = "SpecifiedLineTradeAgreement")
    public LineTradeAgreementType getSpecifiedLineTradeAgreement() {
        return this.specifiedLineTradeAgreement;
    }

    public void setSpecifiedLineTradeAgreement(LineTradeAgreementType specifiedLineTradeAgreement) {
        this.specifiedLineTradeAgreement = specifiedLineTradeAgreement;
    }

    @XmlElement(name = "SpecifiedLineTradeDelivery")
    public LineTradeDeliveryType getSpecifiedLineTradeDelivery() {
        return this.specifiedLineTradeDelivery;
    }

    public void setSpecifiedLineTradeDelivery(LineTradeDeliveryType specifiedLineTradeDelivery) {
        this.specifiedLineTradeDelivery = specifiedLineTradeDelivery;
    }

    @XmlElement(name = "SpecifiedLineTradeSettlement", required = true)
    public LineTradeSettlementType getSpecifiedLineTradeSettlement() {
        return this.specifiedLineTradeSettlement;
    }

    public void setSpecifiedLineTradeSettlement(LineTradeSettlementType specifiedLineTradeSettlement) {
        this.specifiedLineTradeSettlement = specifiedLineTradeSettlement;
    }

}
