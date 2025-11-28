package io.github.up2jakarta.lov;

/**
 * Type adapter that's able to parses flat-data to {@link T} and formats the target values to {@link String}.
 *
 * @param <T> the target type
 */
public interface TypeAdapter<T> extends TypeFormatter<T>, TypeConverter<T> {

    /**
     * @return the supported type.
     */
    Class<T> getType();

}
