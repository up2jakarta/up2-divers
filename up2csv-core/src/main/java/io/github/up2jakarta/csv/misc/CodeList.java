package io.github.up2jakarta.csv.misc;

/**
 * Up2 base {@link Enum} implementation of LOV (List of Values) that can be identified by {@link CodeList#getCode()}.
 *
 * @param <T> the type of enum implementation
 */
@SuppressWarnings("unused")
public interface CodeList<T extends Enum<T>> {

    /**
     * @return the code value.
     */
    String getCode();

    /**
     * @return the name value.
     */
    String getName();

}
