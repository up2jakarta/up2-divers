package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.lov.TypeAdapter;
import io.github.up2jakarta.lov.TypeConverter;
import io.github.up2jakarta.lov.TypeFormatter;

import static io.github.up2jakarta.csv.core.BSBuilder.MEP;
import static io.github.up2jakarta.lov.core.AccessException.notNull;

/**
 * Up2J {@link TypeAdapter} that's wraps the formatter and converter functional interfaces.
 *
 * @param <T> the property type
 */
public final class Up2Adapter<T> implements MEP, TypeAdapter<T> {

    private final TypeConverter<T> converter;
    private final TypeFormatter<T> formatter;
    private final Class<T> type;

    public Up2Adapter(Class<T> type, TypeConverter<T> converter, TypeFormatter<T> formatter) {
        this.formatter = notNull(formatter, Up2Adapter.class, "formatter");
        this.converter = notNull(converter, Up2Adapter.class, "converter");
        this.type = notNull(type, Up2Adapter.class, "type");
    }

    public Up2Adapter(Class<T> type, TypeConverter<T> converter) {
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
