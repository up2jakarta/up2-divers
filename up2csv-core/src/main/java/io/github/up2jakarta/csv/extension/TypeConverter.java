package io.github.up2jakarta.csv.extension;

import io.github.up2jakarta.csv.exception.PropertyException;
import jakarta.persistence.AttributeConverter;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Up2 configurable {@link io.github.up2jakarta.csv.annotation.Converter}
 * that converts the input data {@link String} to the target property type {@link T}.
 *
 * @param <T> property type
 */
public abstract class TypeConverter<T> extends XmlAdapter<String, T> implements AttributeConverter<T, String> {

    protected final Class<T> supportedType;
    protected final String errorCode;
    protected final SeverityType errorSeverity;

    protected TypeConverter(Class<T> supportedType, SeverityType errorSeverity, String errorCode) {
        this.supportedType = supportedType;
        this.errorCode = errorCode;
        this.errorSeverity = errorSeverity;
    }

    /**
     * @return the default error severity
     */
    public SeverityType getErrorSeverity() {
        return errorSeverity;
    }

    /**
     * @return the default error code
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * @return the supported type
     */
    public Class<T> getSupportedType() {
        return supportedType;
    }

    /**
     * Parses the CSV value to the data representation defined in bean class.
     *
     * @param value the bean property value to be parsed
     * @return the parsed data
     */
    public abstract T parse(@NotNull String value);

    /**
     * Formats the bean value to CSV representation.
     *
     * @param value the bean property value to be formatted
     * @return the formatted {@link String}
     */
    public abstract String format(@NotNull T value);

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
            throw PropertyException.of(errorSeverity, errorCode, cause);
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
            throw PropertyException.of(errorSeverity, errorCode, cause);
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
