package io.github.up2jakarta.lov;

import jakarta.xml.bind.ValidationEvent;

import java.util.function.IntSupplier;

/**
 * The error level constants that supports JAXB {@link ValidationEvent} and JSR-303 <code>Payload</code>.
 */
public enum SeverityType implements CodeList<SeverityType>, IntSupplier {

    /**
     * Warning.
     */
    WARNING("W", ValidationEvent.WARNING),

    /**
     * Error.
     */
    ERROR("E", ValidationEvent.ERROR),

    /**
     * Fatal error.
     */
    FATAL("F", ValidationEvent.FATAL_ERROR);

    private final int value;
    private final String code;

    SeverityType(String code, int value) {
        this.code = code;
        this.value = value;
    }

    /**
     * Find to the corresponding constant to the given XML {@link ValidationEvent}.
     *
     * @param level the severity level
     * @return the corresponding constant, else {@link #ERROR}
     */
    public static SeverityType of(int level) {
        if (level == FATAL.value) {
            return FATAL;
        } else if (level == WARNING.value) {
            return WARNING;
        }
        return ERROR;
    }

    /**
     * Find to the corresponding constant to the given XML {@link ValidationEvent}.
     *
     * @param name the name of severity-type
     * @return the corresponding constant, else {@link #ERROR}
     */
    public static SeverityType of(String name) {
        if (name == null) {
            return ERROR;
        }
        name = name.toUpperCase();
        if (FATAL.name().equals(name)) {
            return FATAL;
        } else if (WARNING.name().equals(name)) {
            return WARNING;
        }
        return ERROR;
    }

    /**
     * @return the constant level
     */
    @Override
    public int getAsInt() {
        return value;
    }

    /**
     * @return the constant name
     */
    @Override
    public String getName() {
        return name();
    }

    /**
     * @return the constant code
     */
    @Override
    public String getCode() {
        return code;
    }

}

