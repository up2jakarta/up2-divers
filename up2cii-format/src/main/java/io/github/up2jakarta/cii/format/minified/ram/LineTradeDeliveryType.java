package io.github.up2jakarta.cii.format.minified.ram;

import io.github.up2jakarta.cii.format.minified.udt.QuantityType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import javax.annotation.processing.Generated;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "LineTradeDeliveryType", propOrder = {
        "billedQuantity",
        "shipToTradeParty",
        "actualDeliverySupplyChainEvent",
        "despatchAdviceReferencedDocument",
        "receivingAdviceReferencedDocument"
})
public class LineTradeDeliveryType {

    // BT-129
    private QuantityType billedQuantity;

    // EXT-FR-FE-BG-10
    private TradePartyType shipToTradeParty;

    // EXT-FR-FE-BG-11
    private SupplyChainEventType actualDeliverySupplyChainEvent;

    // EXT-FR-FE-BG-08
    private ReferencedDocumentType despatchAdviceReferencedDocument;

    // EXT-FR-FE-BG-07
    private ReferencedDocumentType receivingAdviceReferencedDocument;

    @XmlElement(name = "BilledQuantity")
    public QuantityType getBilledQuantity() {
        return this.billedQuantity;
    }

    public void setBilledQuantity(QuantityType billedQuantity) {
        this.billedQuantity = billedQuantity;
    }

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
