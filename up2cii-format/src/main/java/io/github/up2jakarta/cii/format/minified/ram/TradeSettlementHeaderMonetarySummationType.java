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
@XmlAccessorType(XmlAccessType.FIELD)
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
    @XmlElement(name = "LineTotalAmount")
    private BigDecimal lineTotalAmount;

    // BT-108
    @XmlElement(name = "ChargeTotalAmount")
    private BigDecimal chargeTotalAmount;

    // BT-107
    @XmlElement(name = "AllowanceTotalAmount")
    private BigDecimal allowanceTotalAmount;

    // BT-109
    @XmlElement(name = "TaxBasisTotalAmount")
    private BigDecimal taxBasisTotalAmount;

    // BT-110, BT-111
    @XmlElement(name = "TaxTotalAmount")
    private List<AmountType> taxTotalAmount;

    // BT-114
    @XmlElement(name = "RoundingAmount")
    private BigDecimal roundingAmount;

    // BT-112
    @XmlElement(name = "GrandTotalAmount")
    private BigDecimal grandTotalAmount;

    // BT-113
    @XmlElement(name = "TotalPrepaidAmount")
    private BigDecimal totalPrepaidAmount;

    // BT-115
    @XmlElement(name = "DuePayableAmount")
    private BigDecimal duePayableAmount;

    public BigDecimal getLineTotalAmount() {
        return this.lineTotalAmount;
    }

    public void setLineTotalAmount(BigDecimal lineTotalAmount) {
        this.lineTotalAmount = lineTotalAmount;
    }

    public BigDecimal getChargeTotalAmount() {
        return this.chargeTotalAmount;
    }

    public void setChargeTotalAmount(BigDecimal chargeTotalAmount) {
        this.chargeTotalAmount = chargeTotalAmount;
    }

    public BigDecimal getAllowanceTotalAmount() {
        return this.allowanceTotalAmount;
    }

    public void setAllowanceTotalAmount(BigDecimal allowanceTotalAmount) {
        this.allowanceTotalAmount = allowanceTotalAmount;
    }

    public BigDecimal getTaxBasisTotalAmount() {
        return this.taxBasisTotalAmount;
    }

    public void setTaxBasisTotalAmount(BigDecimal taxBasisTotalAmount) {
        this.taxBasisTotalAmount = taxBasisTotalAmount;
    }

    public List<AmountType> getTaxTotalAmount() {
        return this.taxTotalAmount;
    }

    public void setTaxTotalAmount(List<AmountType> taxTotalAmount) {
        this.taxTotalAmount = taxTotalAmount;
    }

    public BigDecimal getRoundingAmount() {
        return this.roundingAmount;
    }

    public void setRoundingAmount(BigDecimal roundingAmount) {
        this.roundingAmount = roundingAmount;
    }

    public BigDecimal getGrandTotalAmount() {
        return this.grandTotalAmount;
    }

    public void setGrandTotalAmount(BigDecimal grandTotalAmount) {
        this.grandTotalAmount = grandTotalAmount;
    }

    public BigDecimal getTotalPrepaidAmount() {
        return this.totalPrepaidAmount;
    }

    public void setTotalPrepaidAmount(BigDecimal totalPrepaidAmount) {
        this.totalPrepaidAmount = totalPrepaidAmount;
    }

    public BigDecimal getDuePayableAmount() {
        return this.duePayableAmount;
    }

    public void setDuePayableAmount(BigDecimal duePayableAmount) {
        this.duePayableAmount = duePayableAmount;
    }

}
