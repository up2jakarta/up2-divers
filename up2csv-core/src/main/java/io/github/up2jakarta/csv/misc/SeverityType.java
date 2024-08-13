package io.github.up2jakarta.csv.misc;

import jakarta.validation.Payload;
import jakarta.xml.bind.ValidationEvent;

/**
 * The error severity constants that supports JAXB {@link ValidationEvent} and JSR-303 {@link Payload}.
 */
public enum SeverityType implements CodeList<SeverityType> {

    WARNING("W", ValidationEvent.WARNING, Level.Warning.class),
    ERROR("E", ValidationEvent.ERROR, Level.Error.class),
    FATAL("F", ValidationEvent.FATAL_ERROR, Level.Fatal.class);

    private final int level;
    private final String code;
    private final Class<? extends Level> payload;

    SeverityType(String code, int level, Class<? extends Level> payload) {
        this.code = code;
        this.level = level;
        this.payload = payload;
    }

    public static SeverityType of(int level) {
        if (level == FATAL.level) {
            return FATAL;
        } else if (level == WARNING.level) {
            return WARNING;
        } else {
            return ERROR;
        }
    }

    public static SeverityType of(Class<? extends Payload> payload) {
        if (FATAL.payload.isAssignableFrom(payload)) {
            return FATAL;
        } else if (WARNING.payload.isAssignableFrom(payload)) {
            return WARNING;
        } else {
            return ERROR;
        }
    }

    public static int compare(SeverityType f, SeverityType s) {
        return Integer.compare(f.level, s.level);
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

    @Override
    public String getCode() {
        return code;
    }

    /**
     * JSR-303 {@link Payload} interface marker
     */
    public interface Level extends Payload {
        /**
         * JSR-303 base marker interface for {@link SeverityType#WARNING}
         */
        interface Warning extends Level {
        }

        /**
         * JSR-303 base marker interface for {@link SeverityType#ERROR}
         */
        interface Error extends Level {
        }

        /**
         * JSR-303 base marker interface for {@link SeverityType#FATAL}
         */
        interface Fatal extends Level {
        }
    }

}

