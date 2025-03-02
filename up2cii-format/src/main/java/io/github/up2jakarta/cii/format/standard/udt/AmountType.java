package io.github.up2jakarta.cii.format.standard.udt;

import io.github.up2jakarta.cii.edi.CurrencyCodeType;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AmountType", propOrder = {"value"})
public class AmountType {

    @XmlValue
    protected BigDecimal value;

    @XmlAttribute(name = "currencyID")
    protected CurrencyCodeType currencyID;

    @XmlAttribute(name = "currencyCodeListVersionID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String currencyCodeListVersionID;

    /**
     * Gets the value of the value property.
     *
     * @return possible object is {@link BigDecimal }
     */
    public BigDecimal getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     *
     * @param value allowed object is {@link BigDecimal }
     */
    public void setValue(BigDecimal value) {
        this.value = value;
    }

    /**
     * Gets the value of the currencyID property.
     *
     * @return possible object is {@link CurrencyCodeType }
     */
    public CurrencyCodeType getCurrencyID() {
        return currencyID;
    }

    /**
     * Sets the value of the currencyID property.
     *
     * @param value allowed object is {@link CurrencyCodeType }
     */
    public void setCurrencyID(CurrencyCodeType value) {
        this.currencyID = value;
    }

    /**
     * Gets the value of the currencyCodeListVersionID property.
     *
     * @return possible object is {@link String }
     */
    public String getCurrencyCodeListVersionID() {
        return currencyCodeListVersionID;
    }

    /**
     * Sets the value of the currencyCodeListVersionID property.
     *
     * @param value allowed object is {@link String }
     */
    public void setCurrencyCodeListVersionID(String value) {
        this.currencyCodeListVersionID = value;
    }

}
