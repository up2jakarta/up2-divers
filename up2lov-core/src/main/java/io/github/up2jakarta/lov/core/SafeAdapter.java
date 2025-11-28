package io.github.up2jakarta.lov.core;

import io.github.up2jakarta.lov.SeverityType;
import io.github.up2jakarta.lov.TypeAdapter;
import io.github.up2jakarta.lov.TypeException;
import jakarta.persistence.AttributeConverter;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import static io.github.up2jakarta.lov.core.AccessException.notNull;

/**
 * Up2J configurable adapter that safely parses or formats {@link String} inputs from/to the target type {@link T}.
 * <p>
 * It's compatible with XML {@link XmlAdapter} and JPA {@link AttributeConverter}.
 *
 * @param <T> property type
 */
public abstract class SafeAdapter<T> extends XmlAdapter<String, T> implements AttributeConverter<T, String>, TypeAdapter<T> {

    protected final String code;
    protected final Class<T> type;
    protected final SeverityType level;

    protected SafeAdapter(Class<T> type, SeverityType level, String code) {
        this.type = notNull(type, SafeAdapter.class, "type");
        this.level = level;
        this.code = code;
    }

    @Override
    public final Class<T> getType() {
        return type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final T unmarshal(String value) {
        return this.parse(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String marshal(T value) {
        return this.format(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String convertToDatabaseColumn(T attribute) {
        return this.format(attribute);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final T convertToEntityAttribute(String value) {
        return this.parse(value);
    }

    @Override
    public final T parse(String value) {
        if (value == null) {
            return null;
        }
        try {
            return this.doParse(value);
        } catch (Throwable cause) {
            throw TypeException.of(level, code, cause);
        }
    }

    @Override
    public final String format(T value) {
        if (value == null) {
            return null;
        }
        try {
            return this.doFormat(value);
        } catch (Throwable cause) {
            throw TypeException.of(level, code, cause);
        }
    }

    /**
     * Parses the specified value to the data representation defined in bean class.
     *
     * @param value the property value to be parsed, cannot be <code>null</code>
     * @return the parsed data
     */
    protected abstract T doParse(String value) throws Exception;

    /**
     * Formats the specified property value to flat-data representation.
     *
     * @param value the property value to be formatted, cannot be <code>null</code>
     * @return the formatted {@link String}
     */
    protected abstract String doFormat(T value) throws Exception;

}
