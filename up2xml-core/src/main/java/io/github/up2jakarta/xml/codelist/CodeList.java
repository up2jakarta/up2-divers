package io.github.up2jakarta.xml.codelist;

/**
 * Up2 base {@link Enum} implementation of LOV (List of Values) that can be identified by {@link CodeList#getCode()}.
 *
 * @param <T> the type of enum implementation
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

    /**
     * Generate and return an enum constant from the given {@code id}.
     *
     * @param code the code-list code
     * @return Java valid constant name
     */
    static String constant(String code) {
        code = code.toUpperCase();
        if (Character.isDigit(code.charAt(0))) {
            code = "V_" + code;
        }
        return code.replace("-", "_");
    }

}
