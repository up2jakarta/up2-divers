package io.github.up2jakarta.cii.format.minified.ram;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import javax.annotation.processing.Generated;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SupplyChainTradeLineItemType", propOrder = {
        "associatedDocumentLineDocument",
        "specifiedTradeProduct",
        "specifiedLineTradeAgreement",
        "specifiedLineTradeDelivery",
        "specifiedLineTradeSettlement"
})
public class SupplyChainTradeLineItemType {

    @XmlElement(name = "AssociatedDocumentLineDocument", required = true)
    private DocumentLineDocumentType associatedDocumentLineDocument;

    // BG-31
    @XmlElement(name = "SpecifiedTradeProduct")
    private TradeProductType specifiedTradeProduct;

    // BG-29
    @XmlElement(name = "SpecifiedLineTradeAgreement")
    private LineTradeAgreementType specifiedLineTradeAgreement;

    @XmlElement(name = "SpecifiedLineTradeDelivery")
    private LineTradeDeliveryType specifiedLineTradeDelivery;

    @XmlElement(name = "SpecifiedLineTradeSettlement", required = true)
    private LineTradeSettlementType specifiedLineTradeSettlement;

    public DocumentLineDocumentType getAssociatedDocumentLineDocument() {
        return this.associatedDocumentLineDocument;
    }

    public void setAssociatedDocumentLineDocument(DocumentLineDocumentType associatedDocumentLineDocument) {
        this.associatedDocumentLineDocument = associatedDocumentLineDocument;
    }

    public TradeProductType getSpecifiedTradeProduct() {
        return this.specifiedTradeProduct;
    }

    public void setSpecifiedTradeProduct(TradeProductType specifiedTradeProduct) {
        this.specifiedTradeProduct = specifiedTradeProduct;
    }

    public LineTradeAgreementType getSpecifiedLineTradeAgreement() {
        return this.specifiedLineTradeAgreement;
    }

    public void setSpecifiedLineTradeAgreement(LineTradeAgreementType specifiedLineTradeAgreement) {
        this.specifiedLineTradeAgreement = specifiedLineTradeAgreement;
    }

    public LineTradeDeliveryType getSpecifiedLineTradeDelivery() {
        return this.specifiedLineTradeDelivery;
    }

    public void setSpecifiedLineTradeDelivery(LineTradeDeliveryType specifiedLineTradeDelivery) {
        this.specifiedLineTradeDelivery = specifiedLineTradeDelivery;
    }

    public LineTradeSettlementType getSpecifiedLineTradeSettlement() {
        return this.specifiedLineTradeSettlement;
    }

    public void setSpecifiedLineTradeSettlement(LineTradeSettlementType specifiedLineTradeSettlement) {
        this.specifiedLineTradeSettlement = specifiedLineTradeSettlement;
    }

}
