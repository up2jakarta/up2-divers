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
@XmlType(name = "LineTradeSettlementType", propOrder = {
        "applicableTradeTax",
        "billingSpecifiedPeriod",
        "specifiedTradeAllowanceCharge",
        "specifiedTradeSettlementLineMonetarySummation",
        "invoiceReferencedDocument",
        "additionalReferencedDocument",
        "receivableSpecifiedTradeAccountingAccount"
})
public class LineTradeSettlementType {

    // BG-30
    private List<TradeTaxType> applicableTradeTax;

    // BG-26
    private SpecifiedPeriodType billingSpecifiedPeriod;

    // BG-27, BG-28
    private List<TradeAllowanceChargeType> specifiedTradeAllowanceCharge;

    private TradeSettlementLineMonetarySummationType specifiedTradeSettlementLineMonetarySummation;

    // EXT-FR-FE-BG-06
    private ReferencedDocumentType invoiceReferencedDocument;

    private List<ReferencedDocumentType> additionalReferencedDocument;

    private TradeAccountingAccountType receivableSpecifiedTradeAccountingAccount;

    @XmlElement(name = "ApplicableTradeTax")
    public List<TradeTaxType> getApplicableTradeTax() {
        return this.applicableTradeTax;
    }

    public void setApplicableTradeTax(List<TradeTaxType> applicableTradeTax) {
        this.applicableTradeTax = applicableTradeTax;
    }

    @XmlElement(name = "BillingSpecifiedPeriod")
    public SpecifiedPeriodType getBillingSpecifiedPeriod() {
        return this.billingSpecifiedPeriod;
    }

    public void setBillingSpecifiedPeriod(SpecifiedPeriodType billingSpecifiedPeriod) {
        this.billingSpecifiedPeriod = billingSpecifiedPeriod;
    }

    @XmlElement(name = "SpecifiedTradeAllowanceCharge")
    public List<TradeAllowanceChargeType> getSpecifiedTradeAllowanceCharge() {
        return this.specifiedTradeAllowanceCharge;
    }

    public void setSpecifiedTradeAllowanceCharge(List<TradeAllowanceChargeType> specifiedTradeAllowanceCharge) {
        this.specifiedTradeAllowanceCharge = specifiedTradeAllowanceCharge;
    }

    @XmlElement(name = "SpecifiedTradeSettlementLineMonetarySummation")
    public TradeSettlementLineMonetarySummationType getSpecifiedTradeSettlementLineMonetarySummation() {
        return this.specifiedTradeSettlementLineMonetarySummation;
    }

    public void setSpecifiedTradeSettlementLineMonetarySummation(TradeSettlementLineMonetarySummationType specifiedTradeSettlementLineMonetarySummation) {
        this.specifiedTradeSettlementLineMonetarySummation = specifiedTradeSettlementLineMonetarySummation;
    }

    @XmlElement(name = "InvoiceReferencedDocument")
    public ReferencedDocumentType getInvoiceReferencedDocument() {
        return this.invoiceReferencedDocument;
    }

    public void setInvoiceReferencedDocument(ReferencedDocumentType invoiceReferencedDocument) {
        this.invoiceReferencedDocument = invoiceReferencedDocument;
    }

    @XmlElement(name = "AdditionalReferencedDocument")
    public List<ReferencedDocumentType> getAdditionalReferencedDocument() {
        return this.additionalReferencedDocument;
    }

    public void setAdditionalReferencedDocument(List<ReferencedDocumentType> additionalReferencedDocument) {
        this.additionalReferencedDocument = additionalReferencedDocument;
    }

    @XmlElement(name = "ReceivableSpecifiedTradeAccountingAccount")
    public TradeAccountingAccountType getReceivableSpecifiedTradeAccountingAccount() {
        return this.receivableSpecifiedTradeAccountingAccount;
    }

    public void setReceivableSpecifiedTradeAccountingAccount(TradeAccountingAccountType receivableSpecifiedTradeAccountingAccount) {
        this.receivableSpecifiedTradeAccountingAccount = receivableSpecifiedTradeAccountingAccount;
    }

}
