package io.github.up2jakarta.test.fmt.sln;

import io.github.up2jakarta.csv.api.ILinker;
import io.github.up2jakarta.test.core.bs.CyclicInvoice;
import io.github.up2jakarta.test.core.bs.CyclicItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CyclicItemInvoiceLinker implements ILinker<CyclicItem, CyclicInvoice> {

    @Override
    public List<CyclicInvoice> from(CyclicItem parent) {
        return of(parent.getInvoice());
    }

    @Override
    public void link(CyclicItem parent, CyclicInvoice child) {
        parent.setInvoice(child);
    }
}
