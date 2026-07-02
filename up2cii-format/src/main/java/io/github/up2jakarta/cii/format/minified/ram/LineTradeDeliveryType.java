package io.github.up2jakarta.cii.format.minified.ram;

import io.github.up2jakarta.cii.format.minified.udt.QuantityType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import javax.annotation.processing.Generated;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LineTradeDeliveryType", propOrder = {
        "billedQuantity",
        "shipToTradeParty",
        "actualDeliverySupplyChainEvent",
        "despatchAdviceReferencedDocument",
        "receivingAdviceReferencedDocument"
})
public class LineTradeDeliveryType {

    // BT-129
    @XmlElement(name = "BilledQuantity")
    private QuantityType billedQuantity;

    // EXT-FR-FE-BG-10
    @XmlElement(name = "ShipToTradeParty")
    private TradePartyType shipToTradeParty;

    // EXT-FR-FE-BG-11
    @XmlElement(name = "ActualDeliverySupplyChainEvent")
    private SupplyChainEventType actualDeliverySupplyChainEvent;

    // EXT-FR-FE-BG-08
    @XmlElement(name = "DespatchAdviceReferencedDocument")
    private ReferencedDocumentType despatchAdviceReferencedDocument;

    // EXT-FR-FE-BG-07
    @XmlElement(name = "ReceivingAdviceReferencedDocument")
    private ReferencedDocumentType receivingAdviceReferencedDocument;

    public QuantityType getBilledQuantity() {
        return this.billedQuantity;
    }

    public void setBilledQuantity(QuantityType billedQuantity) {
        this.billedQuantity = billedQuantity;
    }

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
