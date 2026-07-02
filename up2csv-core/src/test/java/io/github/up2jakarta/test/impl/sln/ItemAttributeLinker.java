package io.github.up2jakarta.test.impl.sln;

import io.github.up2jakarta.csv.api.ILinker;
import io.github.up2jakarta.test.impl.dto.Attribute;
import io.github.up2jakarta.test.impl.dto.Item;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class ItemAttributeLinker implements ILinker<Item, Attribute> {

    @Override
    public Collection<Attribute> from(Item parent) {
        return parent.getAttributes().values();
    }

    @Override
    public void link(Item parent, Attribute child) {
        parent.getAttributes().put(child.getKey(), child);
    }
}
