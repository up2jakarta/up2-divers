package io.github.up2jakarta.xml.adapters;

import io.github.up2jakarta.xml.api.SeverityType;
import io.github.up2jakarta.xml.codelist.TypeConverter;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import static io.github.up2jakarta.xml.adapters.Formatters.ISO_OFFSET_DATE;

/**
 * {@link XmlAdapter} mapping JSR-310 {@link LocalDate} to ISO-8601 formatted sequence.
 *
 * @see jakarta.xml.bind.annotation.adapters.XmlAdapter
 * @see LocalDate
 */
public class OffsetDateAdapter extends TypeConverter<LocalDate> {

    private final DateTimeFormatter formatter;

    public OffsetDateAdapter(DateTimeFormatter formatter, String errorCode) {
        super(LocalDate.class, SeverityType.ERROR, errorCode);
        this.formatter = formatter;
    }

    @Override
    public final LocalDate parse(String value) {
        var ta = ISO_OFFSET_DATE.parseBest(value, OffsetDateTime::from, LocalDate::from);
        if (ta instanceof OffsetDateTime) {
            return ((OffsetDateTime) ta).toLocalDate();
        }
        return (LocalDate) ta;
    }

    @Override
    public final String format(LocalDate value) {
        return formatter.format(value);
    }

}
