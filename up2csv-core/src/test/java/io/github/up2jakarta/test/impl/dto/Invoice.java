package io.github.up2jakarta.test.impl.dto;

import io.github.up2jakarta.csv.BusinessId;
import io.github.up2jakarta.csv.BusinessLink;
import io.github.up2jakarta.csv.BusinessObject;
import io.github.up2jakarta.csv.api.Linker;
import io.github.up2jakarta.csv.cfg.*;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.lov.core.Wrapper;
import io.github.up2jakarta.test.core.misc.Parsable;
import io.github.up2jakarta.test.impl.BusinessType;
import io.github.up2jakarta.test.impl.sln.InvoiceAmountLinker;
import io.github.up2jakarta.test.impl.sln.InvoiceItemLinker;
import io.github.up2jakarta.test.impl.sln.InvoiceNoteLinker;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static io.github.up2jakarta.lov.SeverityType.FATAL;
import static io.github.up2jakarta.test.impl.TermType.*;

@Valid
@BusinessType(D001)
@BusinessObject("01")
@Error(value = "CSV-C01", level = FATAL)
public class Invoice extends Parsable {

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

    @BusinessLink(value = "02", min = 1, max = 1)
    @Error("CSV-C02")
    @BusinessType(D002)
    private Party seller;

    @BusinessLink(value = "03", min = 1, max = 1)
    @Error("CSV-C03")
    @BusinessType(D003)
    private Party buyer;

    @BusinessLink(value = "07", max = 1)
    @Error("CSV-C07")
    @BusinessType(D007)
    private Optional<Party> payer = Optional.empty();

    @BusinessLink(value = "08", max = 1)
    @Error("CSV-C08")
    @BusinessType(D008)
    private final Wrapper<Party> payee = new Wrapper<>();

    @BusinessLink(value = "05", bean = @Linker(InvoiceNoteLinker.class))
    private Note[] notes = new Note[0];

    @BusinessLink(value = "04", min = 1, bean = @Linker(InvoiceItemLinker.class))
    private final List<Item> items = new LinkedList<>();

    @BusinessLink(value = "06", bean = @Linker(InvoiceAmountLinker.class))
    private final Map<Amount, Amount.Type> amounts = new LinkedHashMap<>();

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

    public Wrapper<Party> getPayee() {
        return payee;
    }

    public List<Item> getItems() {
        return items;
    }

    public Map<Amount, Amount.Type> getAmounts() {
        return amounts;
    }

    public Note[] getNotes() {
        return notes;
    }

    public void setNotes(Note[] notes) {
        this.notes = notes;
    }

}
