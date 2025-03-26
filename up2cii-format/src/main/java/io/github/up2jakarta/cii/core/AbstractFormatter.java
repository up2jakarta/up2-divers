package io.github.up2jakarta.cii.core;

import io.github.up2jakarta.cii.edi.TimePointFormatCodeType;

import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalQuery;

/**
 * Abstract {@link TimePointFormatCodeType} formatter
 *
 * @param <T> the JavaTime formatter
 * @param <V> the final type, could be {@link Temporal} or  {@link java.time.temporal.TemporalAmount}
 */
public abstract class AbstractFormatter<T extends Temporal, V> {

    protected final Class<T> javaType;
    protected final TemporalQuery<T> query;
    protected final DateTimeFormatter formatter;

    protected AbstractFormatter(Class<T> javaType, TemporalQuery<T> query, DateTimeFormatter formatter) {
        this.javaType = javaType;
        this.query = query;
        this.formatter = formatter;
    }

    public final Class<T> getTemporalClass() {
        return javaType;
    }

    protected abstract V safeParse(String value);

    protected abstract String safeFormat(V value);

    public abstract boolean isComposite();

    public V parse(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return this.safeParse(value);
    }

    public String format(V value) {
        if (value == null) {
            return null;
        }
        return this.safeFormat(value);
    }
}
