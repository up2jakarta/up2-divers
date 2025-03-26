package io.github.up2jakarta.xml.adapters;

import io.github.up2jakarta.xml.api.SeverityType;
import io.github.up2jakarta.xml.codelist.TypeConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalDate;

import static io.github.up2jakarta.xml.adapters.Formatters.TP_LOCAL_DATE;

/**
 * {@link XmlAdapter} mapping JSR-310 {@link LocalDate} to ISO-8601 string
 * <p>
 * String format details: {@link Formatters#TP_LOCAL_DATE}
 *
 * @see XmlAdapter
 * @see LocalDate
 */
@Named
@Singleton
public class LocalDateAdapter extends TypeConverter<LocalDate> {

    protected LocalDateAdapter() {
        super(LocalDate.class, SeverityType.ERROR, "XML-LD01");
    }

    @Override
    public LocalDate parse(String value) {
        return LocalDate.parse(value, TP_LOCAL_DATE);
    }

    @Override
    public String format(LocalDate value) {
        return TP_LOCAL_DATE.format(value);
    }

}
