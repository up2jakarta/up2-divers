package io.github.up2jakarta.csv.core.ext;

import io.github.up2jakarta.lov.PropertyException;
import io.github.up2jakarta.lov.TypeAdapter;
import jakarta.persistence.AttributeConverter;

/**
 * Simple {@link TypeAdapter} that wraps JPA {@link AttributeConverter}.
 *
 * @param <T> the property type
 */
public final class JpaWrapper<T> implements TypeAdapter<T> {

    private final AttributeConverter<T, String> delegate;
    private final Class<T> type;

    public JpaWrapper(Class<T> type, AttributeConverter<T, String> delegate) {
        this.delegate = delegate;
        this.type = type;
    }

    @Override
    public Class<T> getSupportedType() {
        return type;
    }

    @Override
    public T parse(String value) {
        return delegate.convertToEntityAttribute(value);
    }

    @Override
    public String format(T value) throws PropertyException {
        return delegate.convertToDatabaseColumn(value);
    }

}
