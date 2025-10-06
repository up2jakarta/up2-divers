package io.github.up2jakarta.xml.adapters;

import io.github.up2jakarta.xml.api.SeverityType;
import io.github.up2jakarta.xml.clv.TypeConverter;
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
public class OffsetTimeAdapter extends TypeConverter<OffsetTime> {

    private final DateTimeFormatter formatter;

    public OffsetTimeAdapter(DateTimeFormatter formatter, String errorCode) {
        super(OffsetTime.class, SeverityType.ERROR, errorCode);
        this.formatter = formatter;
    }

    @Override
    public final OffsetTime parse(String value) {
        final TemporalAccessor ta = formatter.parseBest(value, OffsetTime::from, LocalTime::from);
        if (ta instanceof OffsetTime) {
            return ((OffsetTime) ta);
        }
        return ((LocalTime) ta).atOffset(defaultOffset());
    }

    @Override
    public final String format(OffsetTime value) {
        return formatter.format(value);
    }

}