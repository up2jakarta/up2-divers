package io.github.up2jakarta.csv.impl.dto;

import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.*;
import io.github.up2jakarta.csv.core.misc.Parsable;
import io.github.up2jakarta.csv.data.BusinessId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static io.github.up2jakarta.lov.SeverityType.FATAL;

/**
 * {@link io.github.up2jakarta.csv.impl.SegmentType#S01}
 */
@Valid
@Error(value = "CSV-C01", level = FATAL)
@SuppressWarnings("unused")
public class Invoice extends Parsable {

    @Position(0)
    @Up2Token
    @NotEmpty
    @BusinessId
    private String reference;

    @Position(1)
    @Up2Temporal
    @NotNull
    private LocalDate issueDate;

    @Position(2)
    @Up2Decimal(2)
    @NotNull
    private BigDecimal grossAmount;

    @Position(3)
    @Up2Decimal(2)
    @NotNull
    private BigDecimal netAmount;

    @Position(4)
    @Up2Decimal(2)
    @NotNull
    private BigDecimal taxAmount;

    private Party seller;
    private Party buyer;

    private Optional<Party> payer = Optional.empty();
    private Optional<Party> payee = Optional.empty();

    private Note[] notes = new Note[0];
    private List<Item> items = new LinkedList<>();
    private Map<Amount, Amount.Type> amounts = new LinkedHashMap<>();

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
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

    public Party getSeller() {
        return seller;
    }

    public void setSeller(Party seller) {
        this.seller = seller;
    }

    public Party getBuyer() {
        return buyer;
    }

    public void setBuyer(Party buyer) {
        this.buyer = buyer;
    }

    public Optional<Party> getPayer() {
        return payer;
    }

    public void setPayer(Optional<Party> payer) {
        this.payer = payer;
    }

    public Optional<Party> getPayee() {
        return payee;
    }

    public void setPayee(Optional<Party> payee) {
        this.payee = payee;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Map<Amount, Amount.Type> getAmounts() {
        return amounts;
    }

    public void setAmounts(Map<Amount, Amount.Type> amounts) {
        this.amounts = amounts;
    }

    public Note[] getNotes() {
        return notes;
    }

    public void setNotes(Note[] notes) {
        this.notes = notes;
    }

}
