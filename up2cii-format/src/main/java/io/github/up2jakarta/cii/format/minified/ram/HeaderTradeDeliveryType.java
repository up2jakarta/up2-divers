package io.github.up2jakarta.cii.format.minified.ram;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import javax.annotation.processing.Generated;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "HeaderTradeDeliveryType", propOrder = {
        "shipToTradeParty",
        "actualDeliverySupplyChainEvent",
        "despatchAdviceReferencedDocument",
        "receivingAdviceReferencedDocument"
})
public class HeaderTradeDeliveryType {

    // BG-13
    private TradePartyType shipToTradeParty;

    private SupplyChainEventType actualDeliverySupplyChainEvent;

    private ReferencedDocumentType despatchAdviceReferencedDocument;

    private ReferencedDocumentType receivingAdviceReferencedDocument;

    @XmlElement(name = "ShipToTradeParty")
    public TradePartyType getShipToTradeParty() {
        return this.shipToTradeParty;
    }

    public void setShipToTradeParty(TradePartyType shipToTradeParty) {
        this.shipToTradeParty = shipToTradeParty;
    }

    @XmlElement(name = "ActualDeliverySupplyChainEvent")
    public SupplyChainEventType getActualDeliverySupplyChainEvent() {
        return this.actualDeliverySupplyChainEvent;
    }

    public void setActualDeliverySupplyChainEvent(SupplyChainEventType actualDeliverySupplyChainEvent) {
        this.actualDeliverySupplyChainEvent = actualDeliverySupplyChainEvent;
    }

    @XmlElement(name = "DespatchAdviceReferencedDocument")
    public ReferencedDocumentType getDespatchAdviceReferencedDocument() {
        return this.despatchAdviceReferencedDocument;
    }

    public void setDespatchAdviceReferencedDocument(ReferencedDocumentType despatchAdviceReferencedDocument) {
        this.despatchAdviceReferencedDocument = despatchAdviceReferencedDocument;
    }

    @XmlElement(name = "ReceivingAdviceReferencedDocument")
    public ReferencedDocumentType getReceivingAdviceReferencedDocument() {
        return this.receivingAdviceReferencedDocument;
    }

    public void setReceivingAdviceReferencedDocument(ReferencedDocumentType receivingAdviceReferencedDocument) {
        this.receivingAdviceReferencedDocument = receivingAdviceReferencedDocument;
    }

}
