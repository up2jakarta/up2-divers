package io.github.up2jakarta.cii.core;

import java.io.Serializable;
import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalQuery;

public class PDurationFormatter<T extends Temporal & Serializable> extends AbstractFormatter<T, PDuration<T>> {

    private static final String SEPARATOR = "-";

    public PDurationFormatter(DateTimeFormatter partFormatter, Class<T> partType, TemporalQuery<T> partQuery) {
        super(partType, partQuery, partFormatter);
    }

    @Override
    protected PDuration<T> safeParse(String value) {
        var parts = value.trim().split(SEPARATOR);
        if (parts.length != 2) {
            throw new DateTimeException("Cannot parse the temporal unit [" + value + "] to " + PDuration.class);
        }
        var startTime = formatter.parse(parts[0], query);
        var endTime = formatter.parse(parts[1], query);
        return PDuration.of(startTime, endTime);
    }

    @Override
    protected String safeFormat(PDuration<T> value) {
        return formatter.format(value.getStartTime()) + SEPARATOR + formatter.format(value.getUntilTime());
    }

    @Override
    public boolean isComposite() {
        return true;
    }
}
