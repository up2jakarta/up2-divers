package io.github.up2jakarta.csv.exception;

import io.github.up2jakarta.csv.extension.SeverityType;

public class PropertyException extends RuntimeException implements MessageFormatter {

    private static final String FORMAT = "%s[%s] : %s";

    private final String errorCode;
    private final SeverityType severityType;

    protected PropertyException(SeverityType severityType, String errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.severityType = severityType;
    }

    public PropertyException(SeverityType severityType, String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.severityType = severityType;
    }

    public static PropertyException of(SeverityType severityType, String errorCode, Throwable cause) {
        if (cause instanceof PropertyException pException) {
            if (errorCode.equals(pException.getErrorCode()) && severityType.equals(pException.getSeverityType())) {
                return pException;
            }
            if (pException.getCause() == null) {
                return new PropertyException(severityType, errorCode, cause.getMessage());
            }
        }
        return new PropertyException(severityType, errorCode, cause);
    }

    /**
     * @return the error severity type
     */
    public SeverityType getSeverityType() {
        return severityType;
    }

    /**
     * @return the error code
     */
    public final String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getFormattedMessage() {
        return String.format(FORMAT, severityType, errorCode, getMessage());
    }

}