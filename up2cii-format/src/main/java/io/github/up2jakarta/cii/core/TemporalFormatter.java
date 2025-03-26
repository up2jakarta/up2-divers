package io.github.up2jakarta.cii.core;


import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalQuery;

public final class TemporalFormatter<T extends Temporal> extends AbstractFormatter<T, T> {

    public TemporalFormatter(DateTimeFormatter formatter, Class<T> javaType, TemporalQuery<T> query) {
        super(javaType, query, formatter);
    }

    @Override
    protected T safeParse(String value) {
        return formatter.parse(value, query);
    }

    @Override
    protected String safeFormat(T value) {
        return formatter.format(value);
    }

    @Override
    public boolean isComposite() {
        return false;
    }

}
