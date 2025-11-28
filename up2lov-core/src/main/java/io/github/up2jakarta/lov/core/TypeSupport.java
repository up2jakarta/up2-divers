package io.github.up2jakarta.lov.core;

import io.github.up2jakarta.lov.TypeAdapter;
import io.github.up2jakarta.lov.TypeConverter;
import io.github.up2jakarta.lov.TypeFormatter;

import static io.github.up2jakarta.lov.core.AccessException.notNull;

/**
 * Simple {@link TypeAdapter} that's wraps the formatter and converter functional interfaces.
 *
 * @param <T> the property type
 */
public final class TypeSupport<T> implements TypeAdapter<T> {

    private final TypeConverter<T> converter;
    private final TypeFormatter<T> formatter;
    private final Class<T> type;

    public TypeSupport(Class<T> type, TypeConverter<T> converter, TypeFormatter<T> formatter) {
        this.formatter = notNull(formatter, TypeSupport.class, "formatter");
        this.converter = notNull(converter, TypeSupport.class, "converter");
        this.type = notNull(type, TypeSupport.class, "type");
    }

    public TypeSupport(Class<T> type, TypeConverter<T> converter) {
        this(type, converter, Object::toString);
    }

    @Override
    public Class<T> getType() {
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
