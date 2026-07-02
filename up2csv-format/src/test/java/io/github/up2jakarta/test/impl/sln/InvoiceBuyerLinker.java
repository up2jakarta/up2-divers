package io.github.up2jakarta.test.impl.sln;

import io.github.up2jakarta.csv.api.ILinker;
import io.github.up2jakarta.test.dto.Invoice;
import io.github.up2jakarta.test.dto.Party;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InvoiceBuyerLinker implements ILinker<Invoice, Party> {

    @Override
    public List<Party> from(Invoice parent) {
        return of(parent.getBuyer());
    }

    @Override
    public void link(Invoice parent, Party child) {
        parent.setBuyer(child);
    }
}
