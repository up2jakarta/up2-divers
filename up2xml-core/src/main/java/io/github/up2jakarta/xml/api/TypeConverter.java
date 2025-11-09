package io.github.up2jakarta.xml.api;

import io.github.up2jakarta.xml.clv.CodeListException;
import jakarta.persistence.AttributeConverter;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Up2 configurable converter that converts the input data {@link String} to the target property type {@link T}.
 * <p>
 * It's compatible with JAXB {@link XmlAdapter} and JPA {@link AttributeConverter}.
 *
 * @param <T> property type
 */
public abstract class TypeConverter<T> extends XmlAdapter<String, T> implements AttributeConverter<T, String> {

    protected final String code;
    protected final Class<T> type;
    protected final SeverityType level;

    protected TypeConverter(Class<T> supportedType, SeverityType errorSeverity, String errorCode) {
        this.type = supportedType;
        this.level = errorSeverity;
        this.code = errorCode;
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

    /**
     * @return the default error severity
     */
    public SeverityType getErrorSeverity() {
        return level;
    }

    /**
     * @return the default error code
     */
    public String getErrorCode() {
        return code;
    }

    /**
     * @return the supported type
     */
    public Class<T> getSupportedType() {
        return type;
    }

    /**
     * Parses the CSV value to the data representation defined in bean class.
     *
     * @param value the bean property value to be parsed
     * @return the parsed data
     */
    public abstract T parse(String value);

    /**
     * Formats the bean value to CSV representation.
     *
     * @param value the bean property value to be formatted
     * @return the formatted {@link String}
     */
    public abstract String format(T value);

    /**
     * {@inheritDoc}
     */
    @Override
    public final T unmarshal(String value) {
        if (value == null) {
            return null;
        }
        try {
            return parse(value);
        } catch (Throwable cause) {
            throw PropertyException.of(level, code, cause);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String marshal(T value) {
        if (value == null) {
            return null;
        }
        try {
            return format(value);
        } catch (Throwable cause) {
            throw PropertyException.of(level, code, cause);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String convertToDatabaseColumn(T attribute) {
        return this.marshal(attribute);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final T convertToEntityAttribute(String value) {
        return this.unmarshal(value);
    }

}
