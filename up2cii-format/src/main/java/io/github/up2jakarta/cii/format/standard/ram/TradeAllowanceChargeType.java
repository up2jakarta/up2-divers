package io.github.up2jakarta.cii.format.standard.ram;

import io.github.up2jakarta.cii.edi.AllowanceChargeIdentificationCodeType;
import io.github.up2jakarta.cii.format.standard.udt.*;
import io.github.up2jakarta.cii.ppf.ChargeReasonCodeType;
import io.github.up2jakarta.cii.ppf.adapters.ChargeReasonCodeAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "TradeAllowanceChargeType", propOrder = {
        "chargeIndicator",
        "id",
        "sequenceNumeric",
        "calculationPercent",
        "basisAmount",
        "basisQuantity",
        "prepaidIndicator",
        "actualAmount",
        "unitBasisAmount",
        "reasonCode",
        "reason",
        "typeCode",
        "categoryTradeTax",
        "actualTradeCurrencyExchange"
})
@SuppressWarnings("unused")
public class TradeAllowanceChargeType {

    @XmlElement(name = "ChargeIndicator")
    private IndicatorType chargeIndicator;

    @XmlElement(name = "ID")
    private IDType id;

    @XmlElement(name = "SequenceNumeric")
    private NumericType sequenceNumeric;

    @XmlElement(name = "CalculationPercent")
    private PercentType calculationPercent;

    @XmlElement(name = "BasisAmount")
    private AmountType basisAmount;

    @XmlElement(name = "BasisQuantity")
    private QuantityType basisQuantity;

    @XmlElement(name = "PrepaidIndicator")
    private IndicatorType prepaidIndicator;

    @XmlElement(name = "ActualAmount")
    private List<AmountType> actualAmount;

    @XmlElement(name = "UnitBasisAmount")
    private AmountType unitBasisAmount;

    private ChargeReasonCodeType reasonCode;

    @XmlElement(name = "Reason")
    private TextType reason;

    @XmlElement(name = "TypeCode")
    private AllowanceChargeIdentificationCodeType typeCode;

    @XmlElement(name = "CategoryTradeTax")
    private List<TradeTaxType> categoryTradeTax;

    @XmlElement(name = "ActualTradeCurrencyExchange")
    private TradeCurrencyExchangeType actualTradeCurrencyExchange;

    /**
     * Gets the value of the chargeIndicator property.
     *
     * @return possible object is {@link IndicatorType }
     */
    public IndicatorType getChargeIndicator() {
        return chargeIndicator;
    }

    /**
     * Sets the value of the chargeIndicator property.
     *
     * @param value allowed object is {@link IndicatorType }
     */
    public void setChargeIndicator(IndicatorType value) {
        this.chargeIndicator = value;
    }

    /**
     * Gets the value of the id property.
     *
     * @return possible object is {@link IDType }
     */
    public IDType getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     * @param value allowed object is {@link IDType }
     */
    public void setId(IDType value) {
        this.id = value;
    }

    /**
     * Gets the value of the sequenceNumeric property.
     *
     * @return possible object is {@link NumericType }
     */
    public NumericType getSequenceNumeric() {
        return sequenceNumeric;
    }

    /**
     * Sets the value of the sequenceNumeric property.
     *
     * @param value allowed object is {@link NumericType }
     */
    public void setSequenceNumeric(NumericType value) {
        this.sequenceNumeric = value;
    }

    /**
     * Gets the value of the calculationPercent property.
     *
     * @return possible object is {@link PercentType }
     */
    public PercentType getCalculationPercent() {
        return calculationPercent;
    }

    /**
     * Sets the value of the calculationPercent property.
     *
     * @param value allowed object is {@link PercentType }
     */
    public void setCalculationPercent(PercentType value) {
        this.calculationPercent = value;
    }

    /**
     * Gets the value of the basisAmount property.
     *
     * @return possible object is {@link AmountType }
     */
    public AmountType getBasisAmount() {
        return basisAmount;
    }

    /**
     * Sets the value of the basisAmount property.
     *
     * @param value allowed object is {@link AmountType }
     */
    public void setBasisAmount(AmountType value) {
        this.basisAmount = value;
    }

    /**
     * Gets the value of the basisQuantity property.
     *
     * @return possible object is {@link QuantityType }
     */
    public QuantityType getBasisQuantity() {
        return basisQuantity;
    }

    /**
     * Sets the value of the basisQuantity property.
     *
     * @param value allowed object is {@link QuantityType }
     */
    public void setBasisQuantity(QuantityType value) {
        this.basisQuantity = value;
    }

    /**
     * Gets the value of the prepaidIndicator property.
     *
     * @return possible object is {@link IndicatorType }
     */
    public IndicatorType getPrepaidIndicator() {
        return prepaidIndicator;
    }

    /**
     * Sets the value of the prepaidIndicator property.
     *
     * @param value allowed object is {@link IndicatorType }
     */
    public void setPrepaidIndicator(IndicatorType value) {
        this.prepaidIndicator = value;
    }

    /**
     * Gets the value of the actualAmount property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the actualAmount property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getActualAmount().add(newItem);
     * </pre>
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AmountType }
     */
    public List<AmountType> getActualAmount() {
        if (actualAmount == null) {
            actualAmount = new ArrayList<>();
        }
        return this.actualAmount;
    }

    /**
     * Gets the value of the unitBasisAmount property.
     *
     * @return possible object is {@link AmountType }
     */
    public AmountType getUnitBasisAmount() {
        return unitBasisAmount;
    }

    /**
     * Sets the value of the unitBasisAmount property.
     *
     * @param value allowed object is {@link AmountType }
     */
    public void setUnitBasisAmount(AmountType value) {
        this.unitBasisAmount = value;
    }

    /**
     * Gets the value of the reasonCode property.
     *
     * @return possible object is {@link ChargeReasonCodeType }
     */
    @XmlElement(name = "ReasonCode")
    public final ChargeReasonCodeType getReasonCode() {
        return reasonCode;
    }

    /**
     * Sets the value of the reasonCode property.
     *
     * @param value allowed object is {@link ChargeReasonCodeType }
     */
    public void setReasonCode(ChargeReasonCodeType value) {
        this.reasonCode = ChargeReasonCodeAdapter.from(value, chargeIndicator);
    }

    /**
     * Gets the value of the reason property.
     *
     * @return possible object is {@link TextType }
     */
    public TextType getReason() {
        return reason;
    }

    /**
     * Sets the value of the reason property.
     *
     * @param value allowed object is {@link TextType }
     */
    public void setReason(TextType value) {
        this.reason = value;
    }

    /**
     * Gets the value of the typeCode property.
     *
     * @return possible object is {@link AllowanceChargeIdentificationCodeType }
     */
    public AllowanceChargeIdentificationCodeType getTypeCode() {
        return typeCode;
    }

    /**
     * Sets the value of the typeCode property.
     *
     * @param value allowed object is {@link AllowanceChargeIdentificationCodeType }
     */
    public void setTypeCode(AllowanceChargeIdentificationCodeType value) {
        this.typeCode = value;
    }

    /**
     * Gets the value of the categoryTradeTax property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the categoryTradeTax property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCategoryTradeTax().add(newItem);
     * </pre>
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TradeTaxType }
     */
    public List<TradeTaxType> getCategoryTradeTax() {
        if (categoryTradeTax == null) {
            categoryTradeTax = new ArrayList<>();
        }
        return this.categoryTradeTax;
    }

    /**
     * Gets the value of the actualTradeCurrencyExchange property.
     *
     * @return possible object is {@link TradeCurrencyExchangeType }
     */
    public TradeCurrencyExchangeType getActualTradeCurrencyExchange() {
        return actualTradeCurrencyExchange;
    }

    /**
     * Sets the value of the actualTradeCurrencyExchange property.
     *
     * @param value allowed object is {@link TradeCurrencyExchangeType }
     */
    public void setActualTradeCurrencyExchange(TradeCurrencyExchangeType value) {
        this.actualTradeCurrencyExchange = value;
    }

}
