package io.github.up2jakarta.cii.format.minified.ram;

import io.github.up2jakarta.cii.edi.CurrencyCodeType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import javax.annotation.processing.Generated;
import java.util.List;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.PROPERTY)
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
    private String creditorReferenceId;

    // BT-83
    private String paymentReference;

    // BT-6
    private CurrencyCodeType taxCurrencyCode;

    // BT-5
    private CurrencyCodeType invoiceCurrencyCode;

    // EXT-FR-FE-BG-05
    private TradePartyType invoicerTradeParty;

    // EXT-FR-FE-BG-04
    private TradePartyType invoiceeTradeParty;

    // BG-10
    private TradePartyType payeeTradeParty;

    // EXT-FR-FE-BG-02
    private TradePartyType payerTradeParty;

    // BG-16
    private List<TradeSettlementPaymentMeansType> specifiedTradeSettlementPaymentMeans;

    // BG-23
    private List<TradeTaxType> applicableTradeTax;

    // BG-14
    private SpecifiedPeriodType billingSpecifiedPeriod;

    // BG-20, BG-21
    private List<TradeAllowanceChargeType> specifiedTradeAllowanceCharge;

    private List<TradePaymentTermsType> specifiedTradePaymentTerms;

    // BG-22
    private TradeSettlementHeaderMonetarySummationType specifiedTradeSettlementHeaderMonetarySummation;

    // BG-3
    private ReferencedDocumentType invoiceReferencedDocument;

    private TradeAccountingAccountType receivableSpecifiedTradeAccountingAccount;

    @XmlElement(name = "CreditorReferenceID")
    public String getCreditorReferenceId() {
        return this.creditorReferenceId;
    }

    public void setCreditorReferenceId(String creditorReferenceId) {
        this.creditorReferenceId = creditorReferenceId;
    }

    @XmlElement(name = "PaymentReference")
    public String getPaymentReference() {
        return this.paymentReference;
    }

    public void setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
    }

    @XmlElement(name = "TaxCurrencyCode")
    public CurrencyCodeType getTaxCurrencyCode() {
        return this.taxCurrencyCode;
    }

    public void setTaxCurrencyCode(CurrencyCodeType taxCurrencyCode) {
        this.taxCurrencyCode = taxCurrencyCode;
    }

    @XmlElement(name = "InvoiceCurrencyCode")
    public CurrencyCodeType getInvoiceCurrencyCode() {
        return this.invoiceCurrencyCode;
    }

    public void setInvoiceCurrencyCode(CurrencyCodeType invoiceCurrencyCode) {
        this.invoiceCurrencyCode = invoiceCurrencyCode;
    }

    @XmlElement(name = "InvoicerTradeParty")
    public TradePartyType getInvoicerTradeParty() {
        return this.invoicerTradeParty;
    }

    public void setInvoicerTradeParty(TradePartyType invoicerTradeParty) {
        this.invoicerTradeParty = invoicerTradeParty;
    }

    @XmlElement(name = "InvoiceeTradeParty")
    public TradePartyType getInvoiceeTradeParty() {
        return this.invoiceeTradeParty;
    }

    public void setInvoiceeTradeParty(TradePartyType invoiceeTradeParty) {
        this.invoiceeTradeParty = invoiceeTradeParty;
    }

    @XmlElement(name = "PayeeTradeParty")
    public TradePartyType getPayeeTradeParty() {
        return this.payeeTradeParty;
    }

    public void setPayeeTradeParty(TradePartyType payeeTradeParty) {
        this.payeeTradeParty = payeeTradeParty;
    }

    @XmlElement(name = "PayerTradeParty")
    public TradePartyType getPayerTradeParty() {
        return this.payerTradeParty;
    }

    public void setPayerTradeParty(TradePartyType payerTradeParty) {
        this.payerTradeParty = payerTradeParty;
    }

    @XmlElement(name = "SpecifiedTradeSettlementPaymentMeans")
    public List<TradeSettlementPaymentMeansType> getSpecifiedTradeSettlementPaymentMeans() {
        return this.specifiedTradeSettlementPaymentMeans;
    }

    public void setSpecifiedTradeSettlementPaymentMeans(List<TradeSettlementPaymentMeansType> specifiedTradeSettlementPaymentMeans) {
        this.specifiedTradeSettlementPaymentMeans = specifiedTradeSettlementPaymentMeans;
    }

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

    @XmlElement(name = "SpecifiedTradePaymentTerms")
    public List<TradePaymentTermsType> getSpecifiedTradePaymentTerms() {
        return this.specifiedTradePaymentTerms;
    }

    public void setSpecifiedTradePaymentTerms(List<TradePaymentTermsType> specifiedTradePaymentTerms) {
        this.specifiedTradePaymentTerms = specifiedTradePaymentTerms;
    }

    @XmlElement(name = "SpecifiedTradeSettlementHeaderMonetarySummation")
    public TradeSettlementHeaderMonetarySummationType getSpecifiedTradeSettlementHeaderMonetarySummation() {
        return this.specifiedTradeSettlementHeaderMonetarySummation;
    }

    public void setSpecifiedTradeSettlementHeaderMonetarySummation(TradeSettlementHeaderMonetarySummationType specifiedTradeSettlementHeaderMonetarySummation) {
        this.specifiedTradeSettlementHeaderMonetarySummation = specifiedTradeSettlementHeaderMonetarySummation;
    }

    @XmlElement(name = "InvoiceReferencedDocument")
    public ReferencedDocumentType getInvoiceReferencedDocument() {
        return this.invoiceReferencedDocument;
    }

    public void setInvoiceReferencedDocument(ReferencedDocumentType invoiceReferencedDocument) {
        this.invoiceReferencedDocument = invoiceReferencedDocument;
    }

    @XmlElement(name = "ReceivableSpecifiedTradeAccountingAccount")
    public TradeAccountingAccountType getReceivableSpecifiedTradeAccountingAccount() {
        return this.receivableSpecifiedTradeAccountingAccount;
    }

    public void setReceivableSpecifiedTradeAccountingAccount(TradeAccountingAccountType receivableSpecifiedTradeAccountingAccount) {
        this.receivableSpecifiedTradeAccountingAccount = receivableSpecifiedTradeAccountingAccount;
    }

}
