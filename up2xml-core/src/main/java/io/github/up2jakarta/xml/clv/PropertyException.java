package io.github.up2jakarta.xml.clv;

import io.github.up2jakarta.xml.api.IError;
import io.github.up2jakarta.xml.api.MessageFormatter;
import io.github.up2jakarta.xml.api.SeverityType;

public class PropertyException extends RuntimeException implements IError, MessageFormatter {

    private static final String FORMAT = "%s[%s] : %s";

    protected final String errorCode;
    protected final SeverityType severityType;

    protected PropertyException(SeverityType severityType, String errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.severityType = severityType;
    }

    public PropertyException(SeverityType level, String code, String message) {
        super(message);
        this.errorCode = code;
        this.severityType = level;
    }

    public static PropertyException of(SeverityType level, String code, Throwable cause) {
        if (cause instanceof PropertyException pex) {
            if (pex.equals(level, code)) {
                return pex;
            } else if (pex.getCause() == null) {
                return new PropertyException(level, code, cause.getMessage());
            }
        }
        return new PropertyException(level, code, cause);
    }

    public final boolean equals(SeverityType level, String code) {
        return code.equals(this.errorCode) && level.equals(this.severityType);
    }

    /**
     * @return the error severity type
     */
    @Override
    public final SeverityType getSeverity() {
        return severityType;
    }

    /**
     * @return the error code
     */
    @Override
    public final String getCode() {
        return errorCode;
    }

    @Override
    public String getFormattedMessage() {
        return String.format(FORMAT, severityType, errorCode, getMessage());
    }

}