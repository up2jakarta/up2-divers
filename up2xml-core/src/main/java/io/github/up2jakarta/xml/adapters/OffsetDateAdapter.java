package io.github.up2jakarta.xml.adapters;

import io.github.up2jakarta.lov.SeverityType;
import io.github.up2jakarta.lov.core.SafeAdapter;
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
public class OffsetDateAdapter extends SafeAdapter<LocalDate> {

    private final DateTimeFormatter formatter;

    public OffsetDateAdapter(DateTimeFormatter formatter, String code) {
        super(LocalDate.class, SeverityType.ERROR, code);
        this.formatter = formatter;
    }

    @Override
    protected final LocalDate doParse(String value) {
        var ta = ISO_OFFSET_DATE.parseBest(value, OffsetDateTime::from, LocalDate::from);
        if (ta instanceof OffsetDateTime odt) {
            return odt.toLocalDate();
        }
        return (LocalDate) ta;
    }

    @Override
    protected final String doFormat(LocalDate value) {
        return formatter.format(value);
    }

}
