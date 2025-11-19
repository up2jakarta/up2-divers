package io.github.up2jakarta.csv.impl.dto;

import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Decimal;
import io.github.up2jakarta.csv.cfg.Up2Number;
import io.github.up2jakarta.csv.core.misc.Parsable;
import io.github.up2jakarta.csv.data.BusinessId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

@Valid
@Error("CSV-C03")
@SuppressWarnings("unused")
public class Item extends Parsable {

    @Position(0)
    @Up2Number
    @NotNull
    @BusinessId
    private Long id;

    @Position(1)
    @NotEmpty
    private String product;

    @Position(2)
    @Up2Decimal(4)
    @NotNull
    private BigDecimal quantity;

    @Position(3)
    @Up2Decimal(2)
    @NotNull
    private BigDecimal grossAmount;

    @Position(4)
    @Up2Decimal(2)
    @NotNull
    private BigDecimal netAmount;

    @Position(5)
    @Up2Decimal(2)
    @NotNull
    private BigDecimal taxAmount;

    private final Map<String, Attribute> attributes = new LinkedHashMap<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(BigDecimal grossAmount) {
        this.grossAmount = grossAmount;
    }

    public BigDecimal getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(BigDecimal netAmount) {
        this.netAmount = netAmount;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Map<String, Attribute> getAttributes() {
        return attributes;
    }

}
