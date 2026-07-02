package io.github.up2jakarta.test.fmt.sln;

import io.github.up2jakarta.csv.api.ILinker;
import io.github.up2jakarta.test.core.bs.VirtualAttribute;
import io.github.up2jakarta.test.core.bs.VirtualItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VirtualAttributeLinker implements ILinker<VirtualItem, VirtualAttribute> {

    @Override
    public List<VirtualAttribute> from(VirtualItem parent) {
        return parent.getAttributes();
    }

    @Override
    public void link(VirtualItem parent, VirtualAttribute child) {
        parent.getAttributes().add(child);
    }
}
