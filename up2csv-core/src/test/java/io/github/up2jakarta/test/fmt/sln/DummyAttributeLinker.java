package io.github.up2jakarta.test.fmt.sln;

import io.github.up2jakarta.csv.api.ILinker;
import io.github.up2jakarta.lov.core.AccessException;
import io.github.up2jakarta.test.core.bs.DummyAttribute;
import io.github.up2jakarta.test.core.bs.DummyReference;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DummyAttributeLinker implements ILinker<DummyReference, DummyAttribute> {

    @Override
    public List<DummyAttribute> from(DummyReference parent) {
        return parent.getAttributes();
    }

    @Override
    public void link(DummyReference parent, DummyAttribute child) {
        if ("*".equals(child.getValue())) {
            throw new AccessException(DummyAttribute.class, "value", "invalid value");
        }
        parent.getAttributes().add(child);
    }
}
