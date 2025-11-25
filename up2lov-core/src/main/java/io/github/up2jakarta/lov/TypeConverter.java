package io.github.up2jakarta.lov;

import jakarta.persistence.AttributeConverter;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Up2J configurable converter that converts the input data {@link String} to the target property type {@link T}.
 * <p>
 * It's compatible with JAXB {@link XmlAdapter} and JPA {@link AttributeConverter}.
 *
 * @param <T> property type
 */
public abstract class TypeConverter<T> extends XmlAdapter<String, T> implements AttributeConverter<T, String>, TypeAdapter<T> {

    protected final String code;
    protected final Class<T> type;
    protected final SeverityType level;

    protected TypeConverter(Class<T> supportedType, SeverityType level, String code) {
        this.type = supportedType;
        this.level = level;
        this.code = code;
    }

    /**
     * Find the first corresponding type that matches the specified filter.
     *
     * @param pivot  the code-list output
     * @param filter the code-list predicate
     * @param values the stream values
     * @param <C>    the type of CodeList
     * @return the found Documented constant
     * @throws CodeListException if not found
     */
    public static <C> Optional<C> find(Function<C, String> pivot, Predicate<String> filter, List<C> values) {
        for (final C constant : values) {
            if (filter.test(pivot.apply(constant))) {
                return Optional.of(constant);
            }
        }
        return Optional.empty();
    }

    @Override
    public final Class<T> getSupportedType() {
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
            throw PropertyException.of(level, code, cause);
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
            throw PropertyException.of(level, code, cause);
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
