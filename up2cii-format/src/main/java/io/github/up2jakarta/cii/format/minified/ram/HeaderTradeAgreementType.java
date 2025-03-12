package io.github.up2jakarta.cii.format.minified.ram;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import javax.annotation.processing.Generated;
import java.util.List;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "HeaderTradeAgreementType", propOrder = {
        "buyerReference",
        "sellerTradeParty",
        "buyerTradeParty",
        "salesAgentTradeParty",
        "sellerTaxRepresentativeTradeParty",
        "sellerOrderReferencedDocument",
        "buyerOrderReferencedDocument",
        "contractReferencedDocument",
        "additionalReferencedDocument",
        "buyerAgentTradeParty",
        "specifiedProcuringProject"
})
public class HeaderTradeAgreementType {

    // BT-10
    private String buyerReference;

    // BG-4
    private TradePartyType sellerTradeParty;

    // BG-7
    private TradePartyType buyerTradeParty;

    // EXT-FR-FE-BG-03
    private TradePartyType salesAgentTradeParty;

    // BG-11
    private TradePartyType sellerTaxRepresentativeTradeParty;

    private ReferencedDocumentType sellerOrderReferencedDocument;

    private ReferencedDocumentType buyerOrderReferencedDocument;

    private ReferencedDocumentType contractReferencedDocument;

    // BG-24
    private List<ReferencedDocumentType> additionalReferencedDocument;

    // EXT-FR-FE-BG-01
    private TradePartyType buyerAgentTradeParty;

    private ProcuringProjectType specifiedProcuringProject;

    @XmlElement(name = "BuyerReference")
    public String getBuyerReference() {
        return this.buyerReference;
    }

    public void setBuyerReference(String buyerReference) {
        this.buyerReference = buyerReference;
    }

    @XmlElement(name = "SellerTradeParty")
    public TradePartyType getSellerTradeParty() {
        return this.sellerTradeParty;
    }

    public void setSellerTradeParty(TradePartyType sellerTradeParty) {
        this.sellerTradeParty = sellerTradeParty;
    }

    @XmlElement(name = "BuyerTradeParty")
    public TradePartyType getBuyerTradeParty() {
        return this.buyerTradeParty;
    }

    public void setBuyerTradeParty(TradePartyType buyerTradeParty) {
        this.buyerTradeParty = buyerTradeParty;
    }

    @XmlElement(name = "SalesAgentTradeParty")
    public TradePartyType getSalesAgentTradeParty() {
        return this.salesAgentTradeParty;
    }

    public void setSalesAgentTradeParty(TradePartyType salesAgentTradeParty) {
        this.salesAgentTradeParty = salesAgentTradeParty;
    }

    @XmlElement(name = "SellerTaxRepresentativeTradeParty")
    public TradePartyType getSellerTaxRepresentativeTradeParty() {
        return this.sellerTaxRepresentativeTradeParty;
    }

    public void setSellerTaxRepresentativeTradeParty(TradePartyType sellerTaxRepresentativeTradeParty) {
        this.sellerTaxRepresentativeTradeParty = sellerTaxRepresentativeTradeParty;
    }

    @XmlElement(name = "SellerOrderReferencedDocument")
    public ReferencedDocumentType getSellerOrderReferencedDocument() {
        return this.sellerOrderReferencedDocument;
    }

    public void setSellerOrderReferencedDocument(ReferencedDocumentType sellerOrderReferencedDocument) {
        this.sellerOrderReferencedDocument = sellerOrderReferencedDocument;
    }

    @XmlElement(name = "BuyerOrderReferencedDocument")
    public ReferencedDocumentType getBuyerOrderReferencedDocument() {
        return this.buyerOrderReferencedDocument;
    }

    public void setBuyerOrderReferencedDocument(ReferencedDocumentType buyerOrderReferencedDocument) {
        this.buyerOrderReferencedDocument = buyerOrderReferencedDocument;
    }

    @XmlElement(name = "ContractReferencedDocument")
    public ReferencedDocumentType getContractReferencedDocument() {
        return this.contractReferencedDocument;
    }

    public void setContractReferencedDocument(ReferencedDocumentType contractReferencedDocument) {
        this.contractReferencedDocument = contractReferencedDocument;
    }

    @XmlElement(name = "AdditionalReferencedDocument")
    public List<ReferencedDocumentType> getAdditionalReferencedDocument() {
        return this.additionalReferencedDocument;
    }

    public void setAdditionalReferencedDocument(List<ReferencedDocumentType> additionalReferencedDocument) {
        this.additionalReferencedDocument = additionalReferencedDocument;
    }

    @XmlElement(name = "BuyerAgentTradeParty")
    public TradePartyType getBuyerAgentTradeParty() {
        return this.buyerAgentTradeParty;
    }

    public void setBuyerAgentTradeParty(TradePartyType buyerAgentTradeParty) {
        this.buyerAgentTradeParty = buyerAgentTradeParty;
    }

    @XmlElement(name = "SpecifiedProcuringProject")
    public ProcuringProjectType getSpecifiedProcuringProject() {
        return this.specifiedProcuringProject;
    }

    public void setSpecifiedProcuringProject(ProcuringProjectType specifiedProcuringProject) {
        this.specifiedProcuringProject = specifiedProcuringProject;
    }

}
