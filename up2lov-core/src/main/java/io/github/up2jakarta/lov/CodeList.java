package io.github.up2jakarta.lov;

/**
 * Up2J contract interface of LOV (List of Values) that can be identified by {@link CodeList#getCode()}.
 *
 * @param <T> the self-type implementation
 */
@SuppressWarnings("unused")
public interface CodeList<T extends CodeList<T>> {

    /**
     * Returns string representation of the specified code-list <code>code</code> and <code>name</code>.
     *
     * @param code the code-list code
     * @param name the code-list name
     * @return the string representation
     */
    static String toString(String code, String name) {
        return "#[" + code + "] " + name;
    }

    /**
     * @return the code value.
     */
    String getCode();

    /**
     * @return the name value.
     */
    String getName();

}
