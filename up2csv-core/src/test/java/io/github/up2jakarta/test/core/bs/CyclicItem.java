package io.github.up2jakarta.test.core.bs;

import io.github.up2jakarta.csv.BusinessId;
import io.github.up2jakarta.csv.BusinessLink;
import io.github.up2jakarta.csv.api.Linker;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.test.core.misc.Parsable;
import io.github.up2jakarta.test.fmt.sln.CyclicItemInvoiceLinker;
import jakarta.validation.constraints.NotNull;

public final class CyclicItem extends Parsable {

    @Position(0)
    @BusinessId
    @NotNull
    private String reference;

    @Error("CSV-C63")
    @BusinessLink(value = "63", bean = @Linker(CyclicItemInvoiceLinker.class))
    private CyclicInvoice invoice;

    public String getReference() {
        return reference;
    }

    public CyclicInvoice getInvoice() {
        return invoice;
    }

    public void setInvoice(CyclicInvoice invoice) {
        this.invoice = invoice;
    }
}
