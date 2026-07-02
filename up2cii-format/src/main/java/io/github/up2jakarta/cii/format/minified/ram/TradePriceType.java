package io.github.up2jakarta.cii.format.minified.ram;

import io.github.up2jakarta.cii.format.minified.udt.QuantityType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import javax.annotation.processing.Generated;
import java.math.BigDecimal;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TradePriceType", propOrder = {
        "chargeAmount",
        "basisQuantity",
        "appliedTradeAllowanceCharge"
})
public class TradePriceType {

    // BT-146, BT-148
    @XmlElement(name = "ChargeAmount", required = true)
    private BigDecimal chargeAmount;

    // BT-149
    @XmlElement(name = "BasisQuantity")
    private QuantityType basisQuantity;

    @XmlElement(name = "AppliedTradeAllowanceCharge")
    private TradeAllowanceChargeType appliedTradeAllowanceCharge;

    public BigDecimal getChargeAmount() {
        return this.chargeAmount;
    }

    public void setChargeAmount(BigDecimal chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public QuantityType getBasisQuantity() {
        return this.basisQuantity;
    }

    public void setBasisQuantity(QuantityType basisQuantity) {
        this.basisQuantity = basisQuantity;
    }

    public TradeAllowanceChargeType getAppliedTradeAllowanceCharge() {
        return this.appliedTradeAllowanceCharge;
    }

    public void setAppliedTradeAllowanceCharge(TradeAllowanceChargeType appliedTradeAllowanceCharge) {
        this.appliedTradeAllowanceCharge = appliedTradeAllowanceCharge;
    }

}
