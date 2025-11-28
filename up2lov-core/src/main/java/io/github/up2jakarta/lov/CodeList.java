package io.github.up2jakarta.lov;

/**
 * Up2J contract interface of LOV (List of Values) that can be identified by {@link CodeList#getCode()}.
 *
 * @param <T> the self-type implementation
 */
@SuppressWarnings("unused")
public interface CodeList<T extends CodeList<T>> {

    /**
     * @return the code value.
     */
    String getCode();

    /**
     * @return the name value.
     */
    String getName();

}
