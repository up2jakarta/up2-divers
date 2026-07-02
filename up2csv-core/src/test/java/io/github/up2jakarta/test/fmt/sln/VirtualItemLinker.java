package io.github.up2jakarta.test.fmt.sln;

import io.github.up2jakarta.csv.api.ILinker;
import io.github.up2jakarta.test.core.bs.VirtualItem;
import io.github.up2jakarta.test.core.bs.VirtualReference;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VirtualItemLinker implements ILinker<VirtualReference, VirtualItem> {

    @Override
    public List<VirtualItem> from(VirtualReference parent) {
        return parent.getItems();
    }

    @Override
    public void link(VirtualReference parent, VirtualItem child) {
        parent.getItems().add(child);
    }
}
