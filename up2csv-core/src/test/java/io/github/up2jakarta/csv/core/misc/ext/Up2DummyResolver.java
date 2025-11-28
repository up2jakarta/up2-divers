package io.github.up2jakarta.csv.core.misc.ext;

import io.github.up2jakarta.csv.api.ext.TypeResolver;
import io.github.up2jakarta.csv.core.misc.cvr.NumberConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class Up2DummyResolver implements TypeResolver<Number, Up2Dummy> {
    private final NumberConverter delegate;

    @Autowired
    Up2DummyResolver(NumberConverter delegate) {
        this.delegate = delegate;
    }

    @Override
    public NumberConverter resolve(Field property, Class<Number> type, Up2Dummy config) {
        return delegate;
    }
}
