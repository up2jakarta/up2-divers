package io.github.up2jakarta.cii.format.minified.ram;

import io.github.up2jakarta.cii.edi.CurrencyCodeType;
import io.github.up2jakarta.cii.format.minified.udt.IDType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import javax.annotation.processing.Generated;
import java.util.List;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HeaderTradeSettlementType", propOrder = {
        "creditorReferenceId",
        "paymentReference",
        "taxCurrencyCode",
        "invoiceCurrencyCode",
        "invoicerTradeParty",
        "invoiceeTradeParty",
        "payeeTradeParty",
        "payerTradeParty",
        "specifiedTradeSettlementPaymentMeans",
        "applicableTradeTax",
        "billingSpecifiedPeriod",
        "specifiedTradeAllowanceCharge",
        "specifiedTradePaymentTerms",
        "specifiedTradeSettlementHeaderMonetarySummation",
        "invoiceReferencedDocument",
        "receivableSpecifiedTradeAccountingAccount"
})
public class HeaderTradeSettlementType {

    // BT-90
    @XmlElement(name = "CreditorReferenceID")
    private IDType creditorReferenceId;

    // BT-83
    @XmlElement(name = "PaymentReference")
    private String paymentReference;

    // BT-6
    @XmlElement(name = "TaxCurrencyCode")
    private CurrencyCodeType taxCurrencyCode;

    // BT-5
    @XmlElement(name = "InvoiceCurrencyCode")
    private CurrencyCodeType invoiceCurrencyCode;

    // EXT-FR-FE-BG-05
    @XmlElement(name = "InvoicerTradeParty")
    private TradePartyType invoicerTradeParty;

    // EXT-FR-FE-BG-04
    @XmlElement(name = "InvoiceeTradeParty")
    private TradePartyType invoiceeTradeParty;

    // BG-10
    @XmlElement(name = "PayeeTradeParty")
    private TradePartyType payeeTradeParty;

    // EXT-FR-FE-BG-02
    @XmlElement(name = "PayerTradeParty")
    private TradePartyType payerTradeParty;

    // BG-16
    @XmlElement(name = "SpecifiedTradeSettlementPaymentMeans")
    private List<TradeSettlementPaymentMeansType> specifiedTradeSettlementPaymentMeans;

    // BG-23
    @XmlElement(name = "ApplicableTradeTax")
    private List<TradeTaxType> applicableTradeTax;

    // BG-14
    @XmlElement(name = "BillingSpecifiedPeriod")
    private SpecifiedPeriodType billingSpecifiedPeriod;

    // BG-20, BG-21
    @XmlElement(name = "SpecifiedTradeAllowanceCharge")
    private List<TradeAllowanceChargeType> specifiedTradeAllowanceCharge;

    @XmlElement(name = "SpecifiedTradePaymentTerms")
    private TradePaymentTermsType specifiedTradePaymentTerms;

    // BG-22
    @XmlElement(name = "SpecifiedTradeSettlementHeaderMonetarySummation")
    private TradeSettlementHeaderMonetarySummationType specifiedTradeSettlementHeaderMonetarySummation;

    // BG-3
    @XmlElement(name = "InvoiceReferencedDocument")
    private List<ReferencedDocumentType> invoiceReferencedDocument;

    @XmlElement(name = "ReceivableSpecifiedTradeAccountingAccount")
    private TradeAccountingAccountType receivableSpecifiedTradeAccountingAccount;

    public IDType getCreditorReferenceId() {
        return this.creditorReferenceId;
    }

    public void setCreditorReferenceId(IDType creditorReferenceId) {
        this.creditorReferenceId = creditorReferenceId;
    }

    public String getPaymentReference() {
        return this.paymentReference;
    }

    public void setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
    }

    public CurrencyCodeType getTaxCurrencyCode() {
        return this.taxCurrencyCode;
    }

    public void setTaxCurrencyCode(CurrencyCodeType taxCurrencyCode) {
        this.taxCurrencyCode = taxCurrencyCode;
    }

    public CurrencyCodeType getInvoiceCurrencyCode() {
        return this.invoiceCurrencyCode;
    }

    public void setInvoiceCurrencyCode(CurrencyCodeType invoiceCurrencyCode) {
        this.invoiceCurrencyCode = invoiceCurrencyCode;
    }

    public TradePartyType getInvoicerTradeParty() {
        return this.invoicerTradeParty;
    }

    public void setInvoicerTradeParty(TradePartyType invoicerTradeParty) {
        this.invoicerTradeParty = invoicerTradeParty;
    }

    public TradePartyType getInvoiceeTradeParty() {
        return this.invoiceeTradeParty;
    }

    public void setInvoiceeTradeParty(TradePartyType invoiceeTradeParty) {
        this.invoiceeTradeParty = invoiceeTradeParty;
    }

    public TradePartyType getPayeeTradeParty() {
        return this.payeeTradeParty;
    }

    public void setPayeeTradeParty(TradePartyType payeeTradeParty) {
        this.payeeTradeParty = payeeTradeParty;
    }

    public TradePartyType getPayerTradeParty() {
        return this.payerTradeParty;
    }

    public void setPayerTradeParty(TradePartyType payerTradeParty) {
        this.payerTradeParty = payerTradeParty;
    }

    public List<TradeSettlementPaymentMeansType> getSpecifiedTradeSettlementPaymentMeans() {
        return this.specifiedTradeSettlementPaymentMeans;
    }

    public void setSpecifiedTradeSettlementPaymentMeans(List<TradeSettlementPaymentMeansType> specifiedTradeSettlementPaymentMeans) {
        this.specifiedTradeSettlementPaymentMeans = specifiedTradeSettlementPaymentMeans;
    }

    public List<TradeTaxType> getApplicableTradeTax() {
        return this.applicableTradeTax;
    }

    public void setApplicableTradeTax(List<TradeTaxType> applicableTradeTax) {
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

    public TradePaymentTermsType getSpecifiedTradePaymentTerms() {
        return this.specifiedTradePaymentTerms;
    }

    public void setSpecifiedTradePaymentTerms(TradePaymentTermsType specifiedTradePaymentTerms) {
        this.specifiedTradePaymentTerms = specifiedTradePaymentTerms;
    }

    public TradeSettlementHeaderMonetarySummationType getSpecifiedTradeSettlementHeaderMonetarySummation() {
        return this.specifiedTradeSettlementHeaderMonetarySummation;
    }

    public void setSpecifiedTradeSettlementHeaderMonetarySummation(TradeSettlementHeaderMonetarySummationType specifiedTradeSettlementHeaderMonetarySummation) {
        this.specifiedTradeSettlementHeaderMonetarySummation = specifiedTradeSettlementHeaderMonetarySummation;
    }

    public List<ReferencedDocumentType> getInvoiceReferencedDocument() {
        return this.invoiceReferencedDocument;
    }

    public void setInvoiceReferencedDocument(List<ReferencedDocumentType> invoiceReferencedDocument) {
        this.invoiceReferencedDocument = invoiceReferencedDocument;
    }

    public TradeAccountingAccountType getReceivableSpecifiedTradeAccountingAccount() {
        return this.receivableSpecifiedTradeAccountingAccount;
    }

    public void setReceivableSpecifiedTradeAccountingAccount(TradeAccountingAccountType receivableSpecifiedTradeAccountingAccount) {
        this.receivableSpecifiedTradeAccountingAccount = receivableSpecifiedTradeAccountingAccount;
    }

}
