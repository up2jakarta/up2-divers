package io.github.up2jakarta.cii.format.minified.ram;

import io.github.up2jakarta.cii.format.minified.udt.AmountType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import javax.annotation.processing.Generated;
import java.math.BigDecimal;
import java.util.List;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "TradeSettlementHeaderMonetarySummationType", propOrder = {
        "lineTotalAmount",
        "chargeTotalAmount",
        "allowanceTotalAmount",
        "taxBasisTotalAmount",
        "taxTotalAmount",
        "roundingAmount",
        "grandTotalAmount",
        "totalPrepaidAmount",
        "duePayableAmount"
})
public class TradeSettlementHeaderMonetarySummationType {

    // BT-106
    private BigDecimal lineTotalAmount;

    // BT-108
    private BigDecimal chargeTotalAmount;

    // BT-107
    private BigDecimal allowanceTotalAmount;

    // BT-109
    private BigDecimal taxBasisTotalAmount;

    // BT-110, BT-111
    private List<AmountType> taxTotalAmount;

    // BT-114
    private BigDecimal roundingAmount;

    // BT-112
    private BigDecimal grandTotalAmount;

    // BT-113
    private BigDecimal totalPrepaidAmount;

    // BT-115
    private BigDecimal duePayableAmount;

    @XmlElement(name = "LineTotalAmount")
    public BigDecimal getLineTotalAmount() {
        return this.lineTotalAmount;
    }

    public void setLineTotalAmount(BigDecimal lineTotalAmount) {
        this.lineTotalAmount = lineTotalAmount;
    }

    @XmlElement(name = "ChargeTotalAmount")
    public BigDecimal getChargeTotalAmount() {
        return this.chargeTotalAmount;
    }

    public void setChargeTotalAmount(BigDecimal chargeTotalAmount) {
        this.chargeTotalAmount = chargeTotalAmount;
    }

    @XmlElement(name = "AllowanceTotalAmount")
    public BigDecimal getAllowanceTotalAmount() {
        return this.allowanceTotalAmount;
    }

    public void setAllowanceTotalAmount(BigDecimal allowanceTotalAmount) {
        this.allowanceTotalAmount = allowanceTotalAmount;
    }

    @XmlElement(name = "TaxBasisTotalAmount")
    public BigDecimal getTaxBasisTotalAmount() {
        return this.taxBasisTotalAmount;
    }

    public void setTaxBasisTotalAmount(BigDecimal taxBasisTotalAmount) {
        this.taxBasisTotalAmount = taxBasisTotalAmount;
    }

    @XmlElement(name = "TaxTotalAmount")
    public List<AmountType> getTaxTotalAmount() {
        return this.taxTotalAmount;
    }

    public void setTaxTotalAmount(List<AmountType> taxTotalAmount) {
        this.taxTotalAmount = taxTotalAmount;
    }

    @XmlElement(name = "RoundingAmount")
    public BigDecimal getRoundingAmount() {
        return this.roundingAmount;
    }

    public void setRoundingAmount(BigDecimal roundingAmount) {
        this.roundingAmount = roundingAmount;
    }

    @XmlElement(name = "GrandTotalAmount")
    public BigDecimal getGrandTotalAmount() {
        return this.grandTotalAmount;
    }

    public void setGrandTotalAmount(BigDecimal grandTotalAmount) {
        this.grandTotalAmount = grandTotalAmount;
    }

    @XmlElement(name = "TotalPrepaidAmount")
    public BigDecimal getTotalPrepaidAmount() {
        return this.totalPrepaidAmount;
    }

    public void setTotalPrepaidAmount(BigDecimal totalPrepaidAmount) {
        this.totalPrepaidAmount = totalPrepaidAmount;
    }

    @XmlElement(name = "DuePayableAmount")
    public BigDecimal getDuePayableAmount() {
        return this.duePayableAmount;
    }

    public void setDuePayableAmount(BigDecimal duePayableAmount) {
        this.duePayableAmount = duePayableAmount;
    }

}
