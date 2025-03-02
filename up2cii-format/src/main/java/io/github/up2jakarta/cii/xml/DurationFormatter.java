package io.github.up2jakarta.cii.xml;

import java.io.Serializable;
import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalQuery;

public class DurationFormatter<T extends Temporal & Serializable> extends AbstractFormatter<T, Duration<T>> {

    private static final String SEPARATOR = "-";

    public DurationFormatter(DateTimeFormatter partFormatter, Class<T> partType, TemporalQuery<T> partQuery) {
        super(partType, partQuery, partFormatter);
    }

    @Override
    protected Duration<T> safeParse(String value) {
        var parts = value.trim().split(SEPARATOR);
        if (parts.length != 2) {
            throw new DateTimeException("Cannot parse the temporal unit [" + value + "] to " + Duration.class);
        }
        var startTime = formatter.parse(parts[0], query);
        var endTime = formatter.parse(parts[1], query);
        return Duration.of(startTime, endTime);
    }

    @Override
    protected String safeFormat(Duration<T> value) {
        return formatter.format(value.getStartTime()) + SEPARATOR + formatter.format(value.getEndTime());
    }

    @Override
    public boolean isComposite() {
        return true;
    }
}
