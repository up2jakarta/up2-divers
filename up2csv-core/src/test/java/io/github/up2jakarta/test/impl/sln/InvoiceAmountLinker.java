package io.github.up2jakarta.test.impl.sln;

import io.github.up2jakarta.csv.api.ILinker;
import io.github.up2jakarta.test.impl.dto.Amount;
import io.github.up2jakarta.test.impl.dto.Invoice;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class InvoiceAmountLinker implements ILinker<Invoice, Amount> {

    @Override
    public Set<Amount> from(Invoice parent) {
        return parent.getAmounts().keySet();
    }

    @Override
    public void link(Invoice parent, Amount child) {
        final Amount.Type type = switch (child.getValue().signum()) {
            case 0 -> Amount.Type.NONE;
            case 1 -> Amount.Type.CHARGE;
            default -> Amount.Type.ALLOWANCE;
        };
        parent.getAmounts().put(child, type);
    }
}
