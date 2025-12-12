package io.github.up2jakarta.lov;

import java.util.List;

/**
 * Base Exception for {@link CodeListAdapter} that encapsulates error {@link #code} and {@link #level} attributes.
 */
public class CodeListException extends TypeException {

    public static final String FORMAT = "Unknown input [%s] for CodeList[%s]";
    public static final String UNIQUE = "No unique CodeList[%s] for input [%s], %d results were found";

    private CodeListException(String message, SeverityType level, String code) {
        super(level, code, message);
    }

    public CodeListException(String name, String value, SeverityType level, String code) {
        super(level, code, String.format(FORMAT, value, name));
    }

    static <C extends CodeList<?>> C unique(List<C> list, String name, String value, SeverityType level, String code) {
        if (list.isEmpty()) {
            throw new CodeListException(name, value, level, code);
        }
        if (list.size() == 1) {
            return list.getFirst();
        }
        throw new CodeListException(String.format(UNIQUE, name, value, list.size()), level, code);
    }

}
