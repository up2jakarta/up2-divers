package io.github.up2jakarta.cii.format.minified;

import io.github.up2jakarta.cii.format.minified.ram.ExchangedDocumentContextType;
import io.github.up2jakarta.cii.format.minified.ram.ExchangedDocumentType;
import io.github.up2jakarta.cii.format.minified.ram.SupplyChainTradeTransactionType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import javax.annotation.processing.Generated;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "CrossIndustryInvoiceType", propOrder = {
        "exchangedDocumentContext",
        "exchangedDocument",
        "supplyChainTradeTransaction"
})
public class CrossIndustryInvoiceType {

    // BG-2
    private ExchangedDocumentContextType exchangedDocumentContext;

    private ExchangedDocumentType exchangedDocument;

    private SupplyChainTradeTransactionType supplyChainTradeTransaction;

    @XmlElement(name = "ExchangedDocumentContext", required = true)
    public ExchangedDocumentContextType getExchangedDocumentContext() {
        return this.exchangedDocumentContext;
    }

    public void setExchangedDocumentContext(ExchangedDocumentContextType exchangedDocumentContext) {
        this.exchangedDocumentContext = exchangedDocumentContext;
    }

    @XmlElement(name = "ExchangedDocument", required = true)
    public ExchangedDocumentType getExchangedDocument() {
        return this.exchangedDocument;
    }

    public void setExchangedDocument(ExchangedDocumentType exchangedDocument) {
        this.exchangedDocument = exchangedDocument;
    }

    @XmlElement(name = "SupplyChainTradeTransaction", required = true)
    public SupplyChainTradeTransactionType getSupplyChainTradeTransaction() {
        return this.supplyChainTradeTransaction;
    }

    public void setSupplyChainTradeTransaction(SupplyChainTradeTransactionType supplyChainTradeTransaction) {
        this.supplyChainTradeTransaction = supplyChainTradeTransaction;
    }

}
