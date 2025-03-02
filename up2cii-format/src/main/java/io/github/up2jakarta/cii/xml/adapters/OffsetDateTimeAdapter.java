package io.github.up2jakarta.cii.xml.adapters;

import io.github.up2jakarta.cii.xml.Formatters;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;

import static io.github.up2jakarta.cii.xml.Formatters.ISO_OFFSET_DATE_TIME;

/**
 * {@link XmlAdapter} mapping JSR-310 {@link OffsetDateTime} to ISO-8601 string
 * <p>
 * String format details: {@link java.time.format.DateTimeFormatter#ISO_OFFSET_DATE_TIME}
 *
 * @see jakarta.xml.bind.annotation.adapters.XmlAdapter
 * @see java.time.OffsetDateTime
 */
public class OffsetDateTimeAdapter extends XmlAdapter<String, OffsetDateTime> {

    @Override
    public OffsetDateTime unmarshal(String value) {
        var ta = ISO_OFFSET_DATE_TIME.parseBest(value, OffsetDateTime::from, LocalDateTime::from);
        if (ta instanceof OffsetDateTime) {
            return ((OffsetDateTime) ta);
        }
        if (ta instanceof LocalDateTime) {
            return ((LocalDateTime) ta).atOffset(Formatters.DEFAULT_OFFSET);
        }
        throw new DateTimeParseException("Text '" + value + "' could not be parsed to OffsetDateTime.", value, 0);
    }

    @Override
    public String marshal(OffsetDateTime value) {
        return ISO_OFFSET_DATE_TIME.format(value);
    }

}
