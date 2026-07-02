package io.github.up2jakarta.test.impl.sln;

import io.github.up2jakarta.csv.api.ILinker;
import io.github.up2jakarta.test.impl.dto.Invoice;
import io.github.up2jakarta.test.impl.dto.Item;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InvoiceItemLinker implements ILinker<Invoice, Item> {

    @Override
    public List<Item> from(Invoice parent) {
        return parent.getItems();
    }

    @Override
    public void link(Invoice parent, Item child) {
        parent.getItems().add(child);
    }
}
