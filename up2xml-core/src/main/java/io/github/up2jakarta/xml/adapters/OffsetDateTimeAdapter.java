package io.github.up2jakarta.xml.adapters;

import io.github.up2jakarta.lov.SeverityType;
import io.github.up2jakarta.lov.core.SafeAdapter;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

/**
 * {@link XmlAdapter} mapping JSR-310 {@link OffsetDateTime} to ISO-8601 formatted sequence.
 *
 * @see jakarta.xml.bind.annotation.adapters.XmlAdapter
 * @see java.time.OffsetDateTime
 */
public class OffsetDateTimeAdapter extends SafeAdapter<OffsetDateTime> {

    private final DateTimeFormatter formatter;

    public OffsetDateTimeAdapter(DateTimeFormatter formatter, String code) {
        super(OffsetDateTime.class, SeverityType.ERROR, code);
        this.formatter = formatter;
    }

    @Override
    protected final OffsetDateTime doParse(String value) {
        final TemporalAccessor ta = formatter.parseBest(value, OffsetDateTime::from, LocalDateTime::from);
        if (ta instanceof OffsetDateTime odt) {
            return odt;
        }
        return ((LocalDateTime) ta).atOffset(Formatters.defaultOffset());
    }

    @Override
    protected final String doFormat(OffsetDateTime value) {
        return formatter.format(value);
    }

}
