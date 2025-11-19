package io.github.up2jakarta.lov;

/**
 * Encapsulate parse errors when processing {@link CodeList}.
 *
 * @see CodeList
 */
public class CodeListException extends PropertyException {

    public static final String FORMAT = "Unknown value [%s] for CodeList[%s]";

    public CodeListException(Class<? extends CodeList<?>> type, String value, SeverityType level, String code) {
        super(level, code, String.format(FORMAT, value, type.getSimpleName()));
    }

}
