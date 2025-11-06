package io.github.up2jakarta.xml.adapters;

import io.github.up2jakarta.xml.api.SeverityType;
import io.github.up2jakarta.xml.api.TypeConverter;
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
public class OffsetDateTimeAdapter extends TypeConverter<OffsetDateTime> {

    private final DateTimeFormatter formatter;

    public OffsetDateTimeAdapter(DateTimeFormatter formatter, String errorCode) {
        super(OffsetDateTime.class, SeverityType.ERROR, errorCode);
        this.formatter = formatter;
    }

    @Override
    public final OffsetDateTime parse(String value) {
        final TemporalAccessor ta = formatter.parseBest(value, OffsetDateTime::from, LocalDateTime::from);
        if (ta instanceof OffsetDateTime) {
            return ((OffsetDateTime) ta);
        }
        return ((LocalDateTime) ta).atOffset(Formatters.defaultOffset());
    }

    @Override
    public final String format(OffsetDateTime value) {
        return formatter.format(value);
    }

}
