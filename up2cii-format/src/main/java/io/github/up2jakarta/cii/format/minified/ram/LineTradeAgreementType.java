package io.github.up2jakarta.cii.format.minified.ram;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import javax.annotation.processing.Generated;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LineTradeAgreementType", propOrder = {
        "sellerOrderReferencedDocument",
        "buyerOrderReferencedDocument",
        "grossPriceProductTradePrice",
        "netPriceProductTradePrice"
})
public class LineTradeAgreementType {

    // EXT-FR-FE-BG-09
    @XmlElement(name = "SellerOrderReferencedDocument")
    private ReferencedDocumentType sellerOrderReferencedDocument;

    @XmlElement(name = "BuyerOrderReferencedDocument")
    private ReferencedDocumentType buyerOrderReferencedDocument;

    @XmlElement(name = "GrossPriceProductTradePrice")
    private TradePriceType grossPriceProductTradePrice;

    @XmlElement(name = "NetPriceProductTradePrice")
    private TradePriceType netPriceProductTradePrice;

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

    public TradePriceType getGrossPriceProductTradePrice() {
        return this.grossPriceProductTradePrice;
    }

    public void setGrossPriceProductTradePrice(TradePriceType grossPriceProductTradePrice) {
        this.grossPriceProductTradePrice = grossPriceProductTradePrice;
    }

    public TradePriceType getNetPriceProductTradePrice() {
        return this.netPriceProductTradePrice;
    }

    public void setNetPriceProductTradePrice(TradePriceType netPriceProductTradePrice) {
        this.netPriceProductTradePrice = netPriceProductTradePrice;
    }

}
