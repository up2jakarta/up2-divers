package io.github.up2jakarta.csv.extension;

import jakarta.validation.Payload;
import jakarta.xml.bind.ValidationEvent;

/**
 * The error severity constants that supports JAXB {@link ValidationEvent} and JSR-303 {@link Payload}.
 */
public enum SeverityType implements CodeList<SeverityType> {

    /**
     * Warning severity.
     */
    WARNING("W", ValidationEvent.WARNING),

    /**
     * Error severity.
     */
    ERROR("E", ValidationEvent.ERROR),

    /**
     * Fatal error severity.
     */
    FATAL("F", ValidationEvent.FATAL_ERROR);

    private final int level;
    private final String code;

    SeverityType(String code, int level) {
        this.code = code;
        this.level = level;
    }

    /**
     * Find to the corresponding constant to the given XML {@link ValidationEvent}.
     *
     * @param level the XML level
     * @return the corresponding constant, else {@link #ERROR}
     */
    public static SeverityType of(int level) {
        if (level == FATAL.level) {
            return FATAL;
        } else if (level == WARNING.level) {
            return WARNING;
        } else {
            return ERROR;
        }
    }

    /**
     * @return the JAXB severity level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @return the constant name
     */
    @Override
    public String getName() {
        return name();
    }

    /**
     * @return the severity code
     */
    @Override
    public String getCode() {
        return code;
    }

}

