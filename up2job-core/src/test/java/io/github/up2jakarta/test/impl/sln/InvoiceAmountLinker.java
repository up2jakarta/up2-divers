package io.github.up2jakarta.test.impl.sln;

import io.github.up2jakarta.csv.api.ILinker;
import io.github.up2jakarta.test.dto.Amount;
import io.github.up2jakarta.test.dto.Invoice;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InvoiceAmountLinker implements ILinker<Invoice, Amount> {

    @Override
    public List<Amount> from(Invoice parent) {
        return parent.getAmounts();
    }

    @Override
    public void link(Invoice parent, Amount child) {
        parent.getAmounts().add(child);
    }
}
