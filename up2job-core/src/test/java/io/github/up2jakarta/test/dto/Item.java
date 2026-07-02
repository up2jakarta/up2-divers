package io.github.up2jakarta.test.dto;

import io.github.up2jakarta.csv.BusinessId;
import io.github.up2jakarta.csv.BusinessLink;
import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.Linker;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Decimal;
import io.github.up2jakarta.csv.cfg.Up2Number;
import io.github.up2jakarta.test.impl.BusinessType;
import io.github.up2jakarta.test.impl.sln.ItemAttributeLinker;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import static io.github.up2jakarta.test.impl.TermType.*;

@Valid
@Error("CSV-C04")
@BusinessType(D004)
@SuppressWarnings("unused")
public class Item implements Segment {

    @Position(0)
    @Up2Number
    @NotNull
    @BusinessId
    @BusinessType(I006)
    private Long id;

    @Position(1)
    @NotEmpty
    @BusinessType(I007)
    private String product;

    @Position(value = 2, defaultValue = "1")
    @Up2Decimal(4)
    @NotNull
    @BusinessType(I008)
    private BigDecimal quantity;

    @Position(3)
    @Up2Decimal(2)
    @NotNull
    @BusinessType(I003)
    private BigDecimal grossAmount;

    @Position(4)
    @Up2Decimal(2)
    @NotNull
    @BusinessType(I004)
    private BigDecimal netAmount;

    @Position(5)
    @Up2Decimal(2)
    @NotNull
    @BusinessType(I005)
    private BigDecimal taxAmount;

    @BusinessLink(value = "09", automatic = true, bean = @Linker(ItemAttributeLinker.class))
    private final Map<String, Attribute> attributes = new LinkedHashMap<>();

    public Long getId() {
        return id;
    }

    public String getProduct() {
        return product;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public BigDecimal getGrossAmount() {
        return grossAmount;
    }

    public BigDecimal getNetAmount() {
        return netAmount;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public Map<String, Attribute> getAttributes() {
        return attributes;
    }

}
