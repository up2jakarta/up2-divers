package io.github.up2jakarta.test.fmt.sln;

import io.github.up2jakarta.csv.api.ILinker;
import io.github.up2jakarta.test.core.bs.CyclicInvoice;
import io.github.up2jakarta.test.core.bs.CyclicItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CyclicInvoiceItemLinker implements ILinker<CyclicInvoice, CyclicItem> {
    @Override
    public List<CyclicItem> from(CyclicInvoice parent) {
        return parent.getItems();
    }

    @Override
    public void link(CyclicInvoice parent, CyclicItem child) {
        parent.getItems().add(child);
    }
}
