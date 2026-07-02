package io.github.up2jakarta.test.impl.sln;

import io.github.up2jakarta.csv.api.ILinker;
import io.github.up2jakarta.test.dto.Invoice;
import io.github.up2jakarta.test.dto.Party;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class InvoicePayerLinker implements ILinker<Invoice, Party> {

    @Override
    public List<Party> from(Invoice parent) {
        return of(parent.getPayer().orElse(null));
    }

    @Override
    public void link(Invoice parent, Party child) {
        parent.setPayer(Optional.of(child));
    }
}
