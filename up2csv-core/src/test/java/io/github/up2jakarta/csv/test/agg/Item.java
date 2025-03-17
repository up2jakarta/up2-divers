package io.github.up2jakarta.csv.test.agg;

import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.annotation.Up2Decimal;
import io.github.up2jakarta.csv.annotation.Up2Number;
import io.github.up2jakarta.csv.impl.Parsable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@Valid
@SuppressWarnings("unused")
public class Item extends Parsable {

    @Position(0)
    @Up2Number
    @NotNull
    private Long id;

    @Position(1)
    @NotNull
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

    private final List<Attribute> attributes = new LinkedList<>();

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

    public List<Attribute> getAttributes() {
        return attributes;
    }

}
