package io.github.up2jakarta.cii.format.minified.ram;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import javax.annotation.processing.Generated;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "LineTradeAgreementType", propOrder = {
        "sellerOrderReferencedDocument",
        "buyerOrderReferencedDocument",
        "grossPriceProductTradePrice",
        "netPriceProductTradePrice"
})
public class LineTradeAgreementType {

    // EXT-FR-FE-BG-09
    private ReferencedDocumentType sellerOrderReferencedDocument;

    private ReferencedDocumentType buyerOrderReferencedDocument;

    private TradePriceType grossPriceProductTradePrice;

    private TradePriceType netPriceProductTradePrice;

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

    @XmlElement(name = "GrossPriceProductTradePrice")
    public TradePriceType getGrossPriceProductTradePrice() {
        return this.grossPriceProductTradePrice;
    }

    public void setGrossPriceProductTradePrice(TradePriceType grossPriceProductTradePrice) {
        this.grossPriceProductTradePrice = grossPriceProductTradePrice;
    }

    @XmlElement(name = "NetPriceProductTradePrice")
    public TradePriceType getNetPriceProductTradePrice() {
        return this.netPriceProductTradePrice;
    }

    public void setNetPriceProductTradePrice(TradePriceType netPriceProductTradePrice) {
        this.netPriceProductTradePrice = netPriceProductTradePrice;
    }

}
