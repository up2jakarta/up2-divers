package io.github.up2jakarta.cii.format.minified.ram;

import io.github.up2jakarta.cii.format.minified.udt.IndicatorType;
import io.github.up2jakarta.cii.ppf.ChargeReasonCodeType;
import io.github.up2jakarta.cii.ppf.adapters.ChargeReasonCodeAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import javax.annotation.processing.Generated;
import java.math.BigDecimal;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "TradeAllowanceChargeType", propOrder = {
        "chargeIndicator",
        "calculationPercent",
        "basisAmount",
        "actualAmount",
        "reasonCode",
        "reason",
        "categoryTradeTax"
})
public class TradeAllowanceChargeType {

    private IndicatorType chargeIndicator;

    // BT-94, BT-101, BT-138, BT-143
    private BigDecimal calculationPercent;

    // BT-93, BT-100, BT-137, BT-142
    private BigDecimal basisAmount;

    // BT-92, BT-99, BT-136, BT-141, BT-147
    private BigDecimal actualAmount;

    // BT-98, BT-105, BT-140, BT-145
    private ChargeReasonCodeType<?> reasonCode;

    // BT-97, BT-104, BT-139, BT-144
    private String reason;

    private TradeTaxType categoryTradeTax;

    @XmlElement(name = "ChargeIndicator")
    public IndicatorType getChargeIndicator() {
        return this.chargeIndicator;
    }

    public void setChargeIndicator(IndicatorType chargeIndicator) {
        this.chargeIndicator = chargeIndicator;
    }

    @XmlElement(name = "CalculationPercent")
    public BigDecimal getCalculationPercent() {
        return this.calculationPercent;
    }

    public void setCalculationPercent(BigDecimal calculationPercent) {
        this.calculationPercent = calculationPercent;
    }

    @XmlElement(name = "BasisAmount")
    public BigDecimal getBasisAmount() {
        return this.basisAmount;
    }

    public void setBasisAmount(BigDecimal basisAmount) {
        this.basisAmount = basisAmount;
    }

    @XmlElement(name = "ActualAmount")
    public BigDecimal getActualAmount() {
        return this.actualAmount;
    }

    public void setActualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
    }

    @XmlElement(name = "ReasonCode")
    public ChargeReasonCodeType<?> getReasonCode() {
        return this.reasonCode;
    }

    public void setReasonCode(ChargeReasonCodeType<?> reasonCode) {
        this.reasonCode = ChargeReasonCodeAdapter.from(reasonCode, chargeIndicator);
    }

    @XmlElement(name = "Reason")
    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @XmlElement(name = "CategoryTradeTax")
    public TradeTaxType getCategoryTradeTax() {
        return this.categoryTradeTax;
    }

    public void setCategoryTradeTax(TradeTaxType categoryTradeTax) {
        this.categoryTradeTax = categoryTradeTax;
    }

}
