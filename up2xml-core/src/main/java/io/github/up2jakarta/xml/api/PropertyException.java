package io.github.up2jakarta.xml.api;

public class PropertyException extends RuntimeException implements IException, MessageFormatter {

    private static final String FORMAT = "%s[%s] : %s";

    protected final String code;
    protected final SeverityType level;

    protected PropertyException(SeverityType level, String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.level = level;
    }

    public PropertyException(SeverityType level, String code, String message) {
        super(message);
        this.code = code;
        this.level = level;
    }

    public static PropertyException of(SeverityType level, String code, Throwable cause) {
        if (cause instanceof PropertyException pex) {
            if (pex.equals(level, code)) {
                return pex;
            } else if (pex.getCause() == null) {
                return new PropertyException(level, code, cause.getMessage());
            }
        }
        return new PropertyException(level, code, cause.getMessage(), cause);
    }

    public final boolean equals(SeverityType level, String code) {
        return code.equals(this.code) && level.equals(this.level);
    }

    /**
     * @return the error severity type
     */
    @Override
    public final SeverityType getSeverity() {
        return level;
    }

    /**
     * @return the error code
     */
    @Override
    public final String getCode() {
        return code;
    }

    @Override
    public String getFormattedMessage() {
        return String.format(FORMAT, level, code, getMessage());
    }

}