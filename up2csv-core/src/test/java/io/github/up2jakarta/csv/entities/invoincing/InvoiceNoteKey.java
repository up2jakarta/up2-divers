package io.github.up2jakarta.csv.entities.invoincing;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class InvoiceNoteKey implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "NOTE_NVC_ID", referencedColumnName = "NVC_ID", nullable = false,
            foreignKey = @ForeignKey(name = "FK_INVOICE_NOTE_INVOICE")
    )
    private InvoiceEntity invoice;

    @Column(name = "NOTE_ORDER", nullable = false)
    private Integer order;

    public InvoiceEntity getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceEntity invoice) {
        this.invoice = invoice;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof InvoiceNoteKey that)) {
            return false;
        }
        return Objects.equals(order, that.order) && Objects.equals(invoice, that.invoice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, invoice);
    }

}
