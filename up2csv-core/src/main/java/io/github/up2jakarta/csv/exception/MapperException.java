package io.github.up2jakarta.csv.exception;

import io.github.up2jakarta.csv.extension.SeverityType;

public class MapperException extends PropertyException {

    private static final String FORMAT = "#[%s] throws %s";

    private final int offset;

    public MapperException(int offset, SeverityType severity, String errorCode, PropertyException cause) {
        super(severity, errorCode, cause);
        this.offset = offset;
    }

    public int getOffset() {
        return offset;
    }

    @Override
    public PropertyException getCause() {
        return (PropertyException) super.getCause();
    }

    @Override
    public String getFormattedMessage() {
        return String.format(FORMAT, offset, getCause().getFormattedMessage());
    }

}
