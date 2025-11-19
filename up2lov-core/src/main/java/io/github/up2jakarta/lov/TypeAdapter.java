package io.github.up2jakarta.lov;

/**
 * Type adapter that formats and parses data {@link String} to the target type {@link T}.
 *
 * @param <T> the property type
 */
public interface TypeAdapter<T> extends PropertyFormatter<T>, PropertyConverter<T> {

    /**
     * @return the supported type.
     */
    Class<T> getSupportedType();

}
