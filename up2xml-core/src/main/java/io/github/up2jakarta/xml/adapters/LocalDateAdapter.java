package io.github.up2jakarta.xml.adapters;

import io.github.up2jakarta.lov.SeverityType;
import io.github.up2jakarta.lov.core.SafeAdapter;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * {@link XmlAdapter} mapping JSR-310 {@link LocalDate} to ISO-8601 formatted sequence.
 *
 * @see XmlAdapter
 * @see LocalDate
 */
public class LocalDateAdapter extends SafeAdapter<LocalDate> {

    private final DateTimeFormatter formatter;

    public LocalDateAdapter(DateTimeFormatter formatter, String code) {
        super(LocalDate.class, SeverityType.ERROR, code);
        this.formatter = formatter;
    }

    @Override
    protected final LocalDate doParse(String value) {
        return LocalDate.parse(value, formatter);
    }

    @Override
    protected final String doFormat(LocalDate value) {
        return formatter.format(value);
    }

}
