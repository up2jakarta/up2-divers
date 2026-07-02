package io.github.up2jakarta.cii.format.minified.ram;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import javax.annotation.processing.Generated;
import java.math.BigDecimal;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TradeSettlementLineMonetarySummationType", propOrder = {"lineTotalAmount"})
public class TradeSettlementLineMonetarySummationType {

    // BT-131
    @XmlElement(name = "LineTotalAmount")
    private BigDecimal lineTotalAmount;

    public BigDecimal getLineTotalAmount() {
        return this.lineTotalAmount;
    }

    public void setLineTotalAmount(BigDecimal lineTotalAmount) {
        this.lineTotalAmount = lineTotalAmount;
    }

}
