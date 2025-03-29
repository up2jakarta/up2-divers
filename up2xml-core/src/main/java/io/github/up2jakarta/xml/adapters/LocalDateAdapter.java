package io.github.up2jakarta.xml.adapters;

import io.github.up2jakarta.xml.api.SeverityType;
import io.github.up2jakarta.xml.codelist.TypeConverter;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * {@link XmlAdapter} mapping JSR-310 {@link LocalDate} to ISO-8601 formatted sequence.
 *
 * @see XmlAdapter
 * @see LocalDate
 */
public class LocalDateAdapter extends TypeConverter<LocalDate> {

    private final DateTimeFormatter formatter;

    public LocalDateAdapter(DateTimeFormatter formatter, String errorCode) {
        super(LocalDate.class, SeverityType.ERROR, errorCode);
        this.formatter = formatter;
    }

    @Override
    public final LocalDate parse(String value) {
        return LocalDate.parse(value, formatter);
    }

    @Override
    public final String format(LocalDate value) {
        return formatter.format(value);
    }

}
