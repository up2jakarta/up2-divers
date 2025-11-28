package io.github.up2jakarta.xml.adapters;

import io.github.up2jakarta.lov.SeverityType;
import io.github.up2jakarta.lov.core.SafeAdapter;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

import static io.github.up2jakarta.xml.adapters.Formatters.defaultOffset;

/**
 * {@link XmlAdapter} mapping JSR-310 {@link OffsetTime} to ISO-8601 formatted sequence.
 *
 * @see jakarta.xml.bind.annotation.adapters.XmlAdapter
 * @see java.time.OffsetTime
 */
public class OffsetTimeAdapter extends SafeAdapter<OffsetTime> {

    private final DateTimeFormatter formatter;

    public OffsetTimeAdapter(DateTimeFormatter formatter, String code) {
        super(OffsetTime.class, SeverityType.ERROR, code);
        this.formatter = formatter;
    }

    @Override
    protected final OffsetTime doParse(String value) {
        final TemporalAccessor ta = formatter.parseBest(value, OffsetTime::from, LocalTime::from);
        if (ta instanceof OffsetTime ot) {
            return ot;
        }
        return ((LocalTime) ta).atOffset(defaultOffset());
    }

    @Override
    protected final String doFormat(OffsetTime value) {
        return formatter.format(value);
    }

}