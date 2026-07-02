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
    @XmlElement(name = "BuyerReference")
    private String buyerReference;

    // BG-4
    @XmlElement(name = "SellerTradeParty")
    private TradePartyType sellerTradeParty;

    // BG-7
    @XmlElement(name = "BuyerTradeParty")
    private TradePartyType buyerTradeParty;

    // EXT-FR-FE-BG-03
    @XmlElement(name = "SalesAgentTradeParty")
    private TradePartyType salesAgentTradeParty;

    // BG-11
    @XmlElement(name = "SellerTaxRepresentativeTradeParty")
    private TradePartyType sellerTaxRepresentativeTradeParty;

    @XmlElement(name = "SellerOrderReferencedDocument")
    private ReferencedDocumentType sellerOrderReferencedDocument;

    @XmlElement(name = "BuyerOrderReferencedDocument")
    private ReferencedDocumentType buyerOrderReferencedDocument;

    @XmlElement(name = "ContractReferencedDocument")
    private ReferencedDocumentType contractReferencedDocument;

    // BG-24
    @XmlElement(name = "AdditionalReferencedDocument")
    private List<ReferencedDocumentType> additionalReferencedDocument;

    // EXT-FR-FE-BG-01
    @XmlElement(name = "BuyerAgentTradeParty")
    private TradePartyType buyerAgentTradeParty;

    @XmlElement(name = "SpecifiedProcuringProject")
    private ProcuringProjectType specifiedProcuringProject;

    public String getBuyerReference() {
        return this.buyerReference;
    }

    public void setBuyerReference(String buyerReference) {
        this.buyerReference = buyerReference;
    }

    public TradePartyType getSellerTradeParty() {
        return this.sellerTradeParty;
    }

    public void setSellerTradeParty(TradePartyType sellerTradeParty) {
        this.sellerTradeParty = sellerTradeParty;
    }

    public TradePartyType getBuyerTradeParty() {
        return this.buyerTradeParty;
    }

    public void setBuyerTradeParty(TradePartyType buyerTradeParty) {
        this.buyerTradeParty = buyerTradeParty;
    }

    public TradePartyType getSalesAgentTradeParty() {
        return this.salesAgentTradeParty;
    }

    public void setSalesAgentTradeParty(TradePartyType salesAgentTradeParty) {
        this.salesAgentTradeParty = salesAgentTradeParty;
    }

    public TradePartyType getSellerTaxRepresentativeTradeParty() {
        return this.sellerTaxRepresentativeTradeParty;
    }

    public void setSellerTaxRepresentativeTradeParty(TradePartyType sellerTaxRepresentativeTradeParty) {
        this.sellerTaxRepresentativeTradeParty = sellerTaxRepresentativeTradeParty;
    }

    public ReferencedDocumentType getSellerOrderReferencedDocument() {
        return this.sellerOrderReferencedDocument;
    }

    public void setSellerOrderReferencedDocument(ReferencedDocumentType sellerOrderReferencedDocument) {
        this.sellerOrderReferencedDocument = sellerOrderReferencedDocument;
    }

    public ReferencedDocumentType getBuyerOrderReferencedDocument() {
        return this.buyerOrderReferencedDocument;
    }

    public void setBuyerOrderReferencedDocument(ReferencedDocumentType buyerOrderReferencedDocument) {
        this.buyerOrderReferencedDocument = buyerOrderReferencedDocument;
    }

    public ReferencedDocumentType getContractReferencedDocument() {
        return this.contractReferencedDocument;
    }

    public void setContractReferencedDocument(ReferencedDocumentType contractReferencedDocument) {
        this.contractReferencedDocument = contractReferencedDocument;
    }

    public List<ReferencedDocumentType> getAdditionalReferencedDocument() {
        return this.additionalReferencedDocument;
    }

    public void setAdditionalReferencedDocument(List<ReferencedDocumentType> additionalReferencedDocument) {
        this.additionalReferencedDocument = additionalReferencedDocument;
    }

    public TradePartyType getBuyerAgentTradeParty() {
        return this.buyerAgentTradeParty;
    }

    public void setBuyerAgentTradeParty(TradePartyType buyerAgentTradeParty) {
        this.buyerAgentTradeParty = buyerAgentTradeParty;
    }

    public ProcuringProjectType getSpecifiedProcuringProject() {
        return this.specifiedProcuringProject;
    }

    public void setSpecifiedProcuringProject(ProcuringProjectType specifiedProcuringProject) {
        this.specifiedProcuringProject = specifiedProcuringProject;
    }

}
