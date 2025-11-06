package io.github.up2jakarta.csv.core.misc.cvr;

import io.github.up2jakarta.csv.api.Warning;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.*;
import io.github.up2jakarta.csv.core.misc.ParsedEntity;
import io.github.up2jakarta.csv.core.misc.clv.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

import static io.github.up2jakarta.csv.core.misc.clv.CountryConverter.ISO_3166;

@Valid
@Truncated(1)
@Entity
@Table(name = "TB_TESTS")
@SuppressWarnings("unused")
public class SupportEntity extends ParsedEntity<Integer> {

    @Position(0)
    @Column(name = "TU_ID")
    @Up2Number
    private Integer key;

    @Position(1)
    @Column(name = "TU_REF", length = 8)
    @Size(max = 8, payload = Warning.class)
    @Up2Token
    private String reference;

    @Position(2)
    @Column(name = "TU_DATE")
    @Up2Temporal
    private LocalDate date;

    @Position(3)
    @Column(name = "TU_AMOUNT", precision = 19, scale = 2)
    @Up2Decimal(2)
    private BigDecimal amount;

    @Position(value = 4, converter = CurrencyConverter.class)
    @Column(name = "TU_CURRENCY")
    @Error(value = CurrencyConverter.ISO_4217)
    private CurrencyCodeType currency;

    @Position(5)
    @Column(name = "TU_QUANTITY", precision = 19, scale = 4)
    @Up2Decimal(4)
    private BigDecimal quantity;

    @Position(value = 6, converter = MeasurementUnitConverter.class)
    @Column(name = "TU_CURRENCY")
    private MeasurementUnitCode unit;

    @Position(7)
    @Column(name = "TU_VALID")
    @Up2Boolean(trueValue = "Y", falseValue = "N")
    private Boolean valid;

    @Position(8)
    @Column(name = "TU_SHIP_PERIOD")
    @Up2TemporalAmount
    private Period shippingPeriod;

    @Position(9)
    @Column(name = "TU_SHIP_COUNTRY")
    @Error(value = ISO_3166)
    @Up2CodeList
    private CountryCodeType shippingCountry;

    @Position(10)
    @Up2Base64
    private byte[] base64;

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public CurrencyCodeType getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyCodeType currency) {
        this.currency = currency;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public MeasurementUnitCode getUnit() {
        return unit;
    }

    public void setUnit(MeasurementUnitCode unit) {
        this.unit = unit;
    }

    public Boolean isValid() {
        return valid;
    }

    public Period getShippingPeriod() {
        return shippingPeriod;
    }

    public void setShippingPeriod(Period shippingPeriod) {
        this.shippingPeriod = shippingPeriod;
    }

    public CountryCodeType getShippingCountry() {
        return shippingCountry;
    }

    public void setShippingCountry(CountryCodeType shippingCountry) {
        this.shippingCountry = shippingCountry;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public byte[] getBase64() {
        return base64;
    }

    public void setBase64(byte[] base64) {
        this.base64 = base64;
    }

}
