package io.github.up2jakarta.test.core.bs;

import io.github.up2jakarta.csv.BusinessId;
import io.github.up2jakarta.csv.BusinessLink;
import io.github.up2jakarta.csv.BusinessObject;
import io.github.up2jakarta.csv.api.Linker;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Token;
import io.github.up2jakarta.test.core.misc.Parsable;
import io.github.up2jakarta.test.fmt.sln.CyclicInvoiceItemLinker;
import io.github.up2jakarta.test.impl.BusinessType;
import jakarta.validation.constraints.NotBlank;

import java.util.LinkedList;
import java.util.List;

import static io.github.up2jakarta.test.impl.TermType.NONE;

@Error("CSV-C61")
@BusinessType(NONE)
@BusinessObject("61")
public final class CyclicInvoice extends Parsable {

    @Position(0)
    @BusinessId
    @Up2Token
    @NotBlank
    private String reference;

    @Error("CSV-C62")
    @BusinessLink(value = "62", bean = @Linker(CyclicInvoiceItemLinker.class))
    @BusinessType(NONE)
    private final List<CyclicItem> items = new LinkedList<>();

    public String getReference() {
        return reference;
    }

    public List<CyclicItem> getItems() {
        return items;
    }

}
