package io.github.up2jakarta.cii.format.minified.udt;

import io.github.up2jakarta.cii.edi.CurrencyCodeType;
import jakarta.xml.bind.annotation.*;

import javax.annotation.processing.Generated;
import java.math.BigDecimal;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AmountType", propOrder = {"value"})
public class AmountType {

    // BT-110, BT-111
    @XmlValue
    private BigDecimal value;

    // BT-110-1, BT-111-1
    @XmlAttribute(name = "currencyID")
    private CurrencyCodeType currencyId;

    public BigDecimal getValue() {
        return this.value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public CurrencyCodeType getCurrencyId() {
        return this.currencyId;
    }

    public void setCurrencyId(CurrencyCodeType currencyId) {
        this.currencyId = currencyId;
    }

}
