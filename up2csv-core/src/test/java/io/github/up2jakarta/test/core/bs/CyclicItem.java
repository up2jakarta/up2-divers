package io.github.up2jakarta.test.core.bs;

import io.github.up2jakarta.test.impl.dto.Item;

public final class CyclicItem extends Item {

    private CyclicInvoice invoice;

    public CyclicInvoice getInvoice() {
        return invoice;
    }

    public void setInvoice(CyclicInvoice invoice) {
        this.invoice = invoice;
    }

}
