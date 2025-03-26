package io.github.up2jakarta.xml.codelist;

import io.github.up2jakarta.xml.api.SeverityType;

/**
 * Encapsulate parse errors when processing {@link CodeList}.
 *
 * @see CodeList
 */
public class CodeListException extends PropertyException {

    public static final String FORMAT = "Unknown value [%s] for CodeList[%s]";

    public CodeListException(Class<? extends CodeList<?>> clType, String value, SeverityType type, String rule) {
        super(type, rule, String.format(FORMAT, value, clType.getSimpleName()));
    }

}
