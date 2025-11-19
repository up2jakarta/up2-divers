package io.github.up2jakarta.lov.core;

import io.github.up2jakarta.lov.PropertyConverter;
import io.github.up2jakarta.lov.PropertyFormatter;
import io.github.up2jakarta.lov.TypeAdapter;

/**
 * Simple {@link TypeAdapter} that's wraps the formatter and converter functional interfaces.
 *
 * @param <T> the property type
 */
public final class TypeWrapper<T> implements TypeAdapter<T> {

    private final PropertyConverter<T> converter;
    private final PropertyFormatter<T> formatter;
    private final Class<T> type;

    public TypeWrapper(Class<T> type, PropertyConverter<T> converter, PropertyFormatter<T> formatter) {
        this.formatter = formatter;
        this.converter = converter;
        this.type = type;
    }

    public TypeWrapper(Class<T> type, PropertyConverter<T> converter) {
        this(type, converter, Object::toString);
    }

    @Override
    public Class<T> getSupportedType() {
        return type;
    }

    @Override
    public T parse(String value) {
        return converter.parse(value);
    }

    @Override
    public String format(T value) {
        return formatter.format(value);
    }

}
