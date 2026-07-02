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
    @XmlElement(name = "ApplicableTradeTax")
    private TradeTaxType applicableTradeTax;

    // BG-26
    @XmlElement(name = "BillingSpecifiedPeriod")
    private SpecifiedPeriodType billingSpecifiedPeriod;

    // BG-27, BG-28
    @XmlElement(name = "SpecifiedTradeAllowanceCharge")
    private List<TradeAllowanceChargeType> specifiedTradeAllowanceCharge;

    @XmlElement(name = "SpecifiedTradeSettlementLineMonetarySummation")
    private TradeSettlementLineMonetarySummationType specifiedTradeSettlementLineMonetarySummation;

    // EXT-FR-FE-BG-06
    @XmlElement(name = "InvoiceReferencedDocument")
    private ReferencedDocumentType invoiceReferencedDocument;

    // BT-128
    @XmlElement(name = "AdditionalReferencedDocument")
    private List<ReferencedDocumentType> additionalReferencedDocument;

    @XmlElement(name = "ReceivableSpecifiedTradeAccountingAccount")
    private TradeAccountingAccountType receivableSpecifiedTradeAccountingAccount;

    public TradeTaxType getApplicableTradeTax() {
        return this.applicableTradeTax;
    }

    public void setApplicableTradeTax(TradeTaxType applicableTradeTax) {
        this.applicableTradeTax = applicableTradeTax;
    }

    public SpecifiedPeriodType getBillingSpecifiedPeriod() {
        return this.billingSpecifiedPeriod;
    }

    public void setBillingSpecifiedPeriod(SpecifiedPeriodType billingSpecifiedPeriod) {
        this.billingSpecifiedPeriod = billingSpecifiedPeriod;
    }

    public List<TradeAllowanceChargeType> getSpecifiedTradeAllowanceCharge() {
        return this.specifiedTradeAllowanceCharge;
    }

    public void setSpecifiedTradeAllowanceCharge(List<TradeAllowanceChargeType> specifiedTradeAllowanceCharge) {
        this.specifiedTradeAllowanceCharge = specifiedTradeAllowanceCharge;
    }

    public TradeSettlementLineMonetarySummationType getSpecifiedTradeSettlementLineMonetarySummation() {
        return this.specifiedTradeSettlementLineMonetarySummation;
    }

    public void setSpecifiedTradeSettlementLineMonetarySummation(TradeSettlementLineMonetarySummationType specifiedTradeSettlementLineMonetarySummation) {
        this.specifiedTradeSettlementLineMonetarySummation = specifiedTradeSettlementLineMonetarySummation;
    }

    public ReferencedDocumentType getInvoiceReferencedDocument() {
        return this.invoiceReferencedDocument;
    }

    public void setInvoiceReferencedDocument(ReferencedDocumentType invoiceReferencedDocument) {
        this.invoiceReferencedDocument = invoiceReferencedDocument;
    }

    public List<ReferencedDocumentType> getAdditionalReferencedDocument() {
        return this.additionalReferencedDocument;
    }

    public void setAdditionalReferencedDocument(List<ReferencedDocumentType> additionalReferencedDocument) {
        this.additionalReferencedDocument = additionalReferencedDocument;
    }

    public TradeAccountingAccountType getReceivableSpecifiedTradeAccountingAccount() {
        return this.receivableSpecifiedTradeAccountingAccount;
    }

    public void setReceivableSpecifiedTradeAccountingAccount(TradeAccountingAccountType receivableSpecifiedTradeAccountingAccount) {
        this.receivableSpecifiedTradeAccountingAccount = receivableSpecifiedTradeAccountingAccount;
    }

}
