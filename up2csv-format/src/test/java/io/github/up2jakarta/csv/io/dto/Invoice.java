package io.github.up2jakarta.csv.io.dto;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Decimal;
import io.github.up2jakarta.csv.cfg.Up2Temporal;
import io.github.up2jakarta.csv.cfg.Up2Token;
import io.github.up2jakarta.csv.data.BusinessId;
import io.github.up2jakarta.csv.data.Segment;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Valid
@SuppressWarnings("unused")
public class Invoice implements Segment {

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

    private Party payer;
    private Party payee;

    private final List<Item> items = new LinkedList<>();
    private final List<Note> notes = new LinkedList<>();
    private final List<Amount> amounts = new LinkedList<>();

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

    public Party getPayer() {
        return payer;
    }

    public void setPayer(Party payer) {
        this.payer = payer;
    }

    public Party getPayee() {
        return payee;
    }

    public void setPayee(Party payee) {
        this.payee = payee;
    }

    public List<Item> getItems() {
        return items;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public List<Amount> getAmounts() {
        return amounts;
    }
}
