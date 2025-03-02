package io.github.up2jakarta.cii.xml.adapters;

import io.github.up2jakarta.cii.xml.Formatters;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.format.DateTimeParseException;

import static io.github.up2jakarta.cii.xml.Formatters.ISO_OFFSET_TIME;

/**
 * {@link XmlAdapter} mapping JSR-310 {@link OffsetTime} to ISO-8601 string
 * <p>
 * String format details: {@link java.time.format.DateTimeFormatter#ISO_OFFSET_TIME}
 *
 * @see jakarta.xml.bind.annotation.adapters.XmlAdapter
 * @see java.time.OffsetTime
 */
public class OffsetTimeAdapter extends XmlAdapter<String, OffsetTime> {

    @Override
    public OffsetTime unmarshal(String value) {
        var temporalAccessor = ISO_OFFSET_TIME.parseBest(value, OffsetTime::from, LocalTime::from);
        if (temporalAccessor instanceof OffsetTime) {
            return ((OffsetTime) temporalAccessor);
        }
        if (temporalAccessor instanceof LocalTime) {
            return ((LocalTime) temporalAccessor).atOffset(Formatters.DEFAULT_OFFSET);
        }
        throw new DateTimeParseException("Text '" + value + "' could not be parsed to OffsetTime", value, 0);
    }

    @Override
    public String marshal(OffsetTime value) {
        return ISO_OFFSET_TIME.format(value);
    }

}