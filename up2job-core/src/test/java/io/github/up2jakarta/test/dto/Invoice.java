package io.github.up2jakarta.test.dto;

import io.github.up2jakarta.csv.BusinessId;
import io.github.up2jakarta.csv.BusinessLink;
import io.github.up2jakarta.csv.BusinessObject;
import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.Linker;
import io.github.up2jakarta.csv.cfg.*;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.test.impl.BusinessType;
import io.github.up2jakarta.test.impl.sln.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static io.github.up2jakarta.lov.SeverityType.FATAL;
import static io.github.up2jakarta.test.impl.TermType.*;

@Valid
@BusinessType(D001)
@BusinessObject("01")
@SuppressWarnings("unused")
@Error(value = "CSV-C01", level = FATAL)
public class Invoice implements Segment {

    @Position(0)
    @Up2Token
    @NotEmpty
    @BusinessId
    @BusinessType(I001)
    private String reference;

    @Position(1)
    @Up2Temporal
    @NotNull
    @BusinessType(I002)
    private LocalDate issueDate;

    @Position(2)
    @Up2Decimal(2)
    @NotNull
    @BusinessType(I003)
    private BigDecimal grossAmount;

    @Position(3)
    @Up2Decimal(2)
    @NotNull
    @BusinessType(I004)
    private BigDecimal netAmount;

    @Position(4)
    @Up2Decimal(2)
    @NotNull
    @BusinessType(I005)
    private BigDecimal taxAmount;

    @BusinessLink(value = "02", min = 1, max = 1, bean = @Linker(InvoiceSellerLinker.class))
    @Error("CSV-C02")
    @BusinessType(D002)
    private Party seller;

    @BusinessLink(value = "03", min = 1, max = 1, bean = @Linker(InvoiceBuyerLinker.class))
    @Error("CSV-C03")
    @BusinessType(D003)
    private Party buyer;

    @BusinessLink(value = "07", max = 1, bean = @Linker(InvoicePayerLinker.class))
    @Error("CSV-C07")
    @BusinessType(D007)
    private Optional<Party> payer = Optional.empty();

    @BusinessLink(value = "08", max = 1, bean = @Linker(InvoicePayeeLinker.class))
    @Error("CSV-C08")
    @BusinessType(D008)
    private Optional<Party> payee = Optional.empty();

    @BusinessLink(value = "04", min = 1, bean = @Linker(InvoiceItemLinker.class))
    private final List<Item> items = new LinkedList<>();

    @BusinessLink(value = "05", bean = @Linker(InvoiceNoteLinker.class))
    private final List<Note> notes = new LinkedList<>();

    @BusinessLink(value = "06", bean = @Linker(InvoiceAmountLinker.class))
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

    public List<Note> getNotes() {
        return notes;
    }

    public List<Amount> getAmounts() {
        return amounts;
    }

}
