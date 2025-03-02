package io.github.up2jakarta.cii.xml.adapters;

import io.github.up2jakarta.cii.xml.OffsetDate;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static io.github.up2jakarta.cii.xml.Formatters.DEFAULT_OFFSET;
import static io.github.up2jakarta.cii.xml.Formatters.ISO_OFFSET_DATE;

/**
 * {@link XmlAdapter} mapping JSR-310 {@link OffsetDate} to ISO-8601 string
 * <p>
 * String format details: {@link DateTimeFormatter#ISO_OFFSET_DATE_TIME}
 *
 * @see jakarta.xml.bind.annotation.adapters.XmlAdapter
 * @see OffsetDate
 */
public class OffsetDateAdapter extends XmlAdapter<String, OffsetDate> {

    @Override
    public OffsetDate unmarshal(String value) {
        var ta = ISO_OFFSET_DATE.parseBest(value, OffsetDate::from, LocalDate::from);
        if (ta instanceof OffsetDate) {
            return ((OffsetDate) ta);
        }
        if (ta instanceof LocalDate) {
            return OffsetDate.of((LocalDate) ta, DEFAULT_OFFSET);
        }
        throw new DateTimeParseException("Text '" + value + "' could not be parsed to OffsetDate.", value, 0);
    }

    @Override
    public String marshal(OffsetDate value) {
        return ISO_OFFSET_DATE.format(value);
    }

}
