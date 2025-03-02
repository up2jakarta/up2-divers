package io.github.up2jakarta.cii.format.minified.ram;

import io.github.up2jakarta.cii.edi.TaxCategoryCodeType;
import io.github.up2jakarta.cii.edi.TaxTypeCodeType;
import io.github.up2jakarta.cii.edi.TimeReferenceCodeType;
import io.github.up2jakarta.cii.format.minified.udt.DateType;
import io.github.up2jakarta.cii.ppf.TaxExemptionReasonCodeType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import javax.annotation.processing.Generated;
import java.math.BigDecimal;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "TradeTaxType", propOrder = {
        "calculatedAmount",
        "typeCode",
        "exemptionReason",
        "basisAmount",
        "categoryCode",
        "exemptionReasonCode",
        "taxPointDate",
        "dueDateTypeCode",
        "rateApplicablePercent"
})
public class TradeTaxType {

    // BT-117
    private BigDecimal calculatedAmount;

    // BT-31-0, BT-32-0, BT-96, BT-103, BT-119, BT-152
    private TaxTypeCodeType typeCode;

    // BT-120
    private String exemptionReason;

    // BT-116
    private BigDecimal basisAmount;

    // BT-95, BT-102, BT-118, BT-151
    private TaxCategoryCodeType categoryCode;

    // BT-121
    private TaxExemptionReasonCodeType exemptionReasonCode;

    private DateType taxPointDate;

    // BT-8
    private TimeReferenceCodeType dueDateTypeCode;

    // BT-96, BT-103, BT-119, BT-152
    private BigDecimal rateApplicablePercent;

    @XmlElement(name = "CalculatedAmount")
    public BigDecimal getCalculatedAmount() {
        return this.calculatedAmount;
    }

    public void setCalculatedAmount(BigDecimal calculatedAmount) {
        this.calculatedAmount = calculatedAmount;
    }

    @XmlElement(name = "TypeCode")
    public TaxTypeCodeType getTypeCode() {
        return this.typeCode;
    }

    public void setTypeCode(TaxTypeCodeType typeCode) {
        this.typeCode = typeCode;
    }

    @XmlElement(name = "ExemptionReason")
    public String getExemptionReason() {
        return this.exemptionReason;
    }

    public void setExemptionReason(String exemptionReason) {
        this.exemptionReason = exemptionReason;
    }

    @XmlElement(name = "BasisAmount")
    public BigDecimal getBasisAmount() {
        return this.basisAmount;
    }

    public void setBasisAmount(BigDecimal basisAmount) {
        this.basisAmount = basisAmount;
    }

    @XmlElement(name = "CategoryCode")
    public TaxCategoryCodeType getCategoryCode() {
        return this.categoryCode;
    }

    public void setCategoryCode(TaxCategoryCodeType categoryCode) {
        this.categoryCode = categoryCode;
    }

    @XmlElement(name = "ExemptionReasonCode")
    public TaxExemptionReasonCodeType getExemptionReasonCode() {
        return this.exemptionReasonCode;
    }

    public void setExemptionReasonCode(TaxExemptionReasonCodeType exemptionReasonCode) {
        this.exemptionReasonCode = exemptionReasonCode;
    }

    @XmlElement(name = "TaxPointDate")
    public DateType getTaxPointDate() {
        return this.taxPointDate;
    }

    public void setTaxPointDate(DateType taxPointDate) {
        this.taxPointDate = taxPointDate;
    }

    @XmlElement(name = "DueDateTypeCode")
    public TimeReferenceCodeType getDueDateTypeCode() {
        return this.dueDateTypeCode;
    }

    public void setDueDateTypeCode(TimeReferenceCodeType dueDateTypeCode) {
        this.dueDateTypeCode = dueDateTypeCode;
    }

    @XmlElement(name = "RateApplicablePercent")
    public BigDecimal getRateApplicablePercent() {
        return this.rateApplicablePercent;
    }

    public void setRateApplicablePercent(BigDecimal rateApplicablePercent) {
        this.rateApplicablePercent = rateApplicablePercent;
    }

}
