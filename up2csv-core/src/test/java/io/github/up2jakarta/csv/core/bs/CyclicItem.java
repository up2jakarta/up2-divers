package io.github.up2jakarta.csv.core.bs;

import io.github.up2jakarta.csv.impl.dto.Item;

public final class CyclicItem extends Item {

    private CyclicInvoice invoice;

    public CyclicInvoice getInvoice() {
        return invoice;
    }

    public void setInvoice(CyclicInvoice invoice) {
        this.invoice = invoice;
    }

}
