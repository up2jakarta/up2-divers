package io.github.up2jakarta.cii.format.minified.ram;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import javax.annotation.processing.Generated;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HeaderTradeDeliveryType", propOrder = {
        "shipToTradeParty",
        "actualDeliverySupplyChainEvent",
        "despatchAdviceReferencedDocument",
        "receivingAdviceReferencedDocument"
})
public class HeaderTradeDeliveryType {

    // BG-13
    @XmlElement(name = "ShipToTradeParty")
    private TradePartyType shipToTradeParty;

    @XmlElement(name = "ActualDeliverySupplyChainEvent")
    private SupplyChainEventType actualDeliverySupplyChainEvent;

    @XmlElement(name = "DespatchAdviceReferencedDocument")
    private ReferencedDocumentType despatchAdviceReferencedDocument;

    @XmlElement(name = "ReceivingAdviceReferencedDocument")
    private ReferencedDocumentType receivingAdviceReferencedDocument;

    public TradePartyType getShipToTradeParty() {
        return this.shipToTradeParty;
    }

    public void setShipToTradeParty(TradePartyType shipToTradeParty) {
        this.shipToTradeParty = shipToTradeParty;
    }

    public SupplyChainEventType getActualDeliverySupplyChainEvent() {
        return this.actualDeliverySupplyChainEvent;
    }

    public void setActualDeliverySupplyChainEvent(SupplyChainEventType actualDeliverySupplyChainEvent) {
        this.actualDeliverySupplyChainEvent = actualDeliverySupplyChainEvent;
    }

    public ReferencedDocumentType getDespatchAdviceReferencedDocument() {
        return this.despatchAdviceReferencedDocument;
    }

    public void setDespatchAdviceReferencedDocument(ReferencedDocumentType despatchAdviceReferencedDocument) {
        this.despatchAdviceReferencedDocument = despatchAdviceReferencedDocument;
    }

    public ReferencedDocumentType getReceivingAdviceReferencedDocument() {
        return this.receivingAdviceReferencedDocument;
    }

    public void setReceivingAdviceReferencedDocument(ReferencedDocumentType receivingAdviceReferencedDocument) {
        this.receivingAdviceReferencedDocument = receivingAdviceReferencedDocument;
    }

}
