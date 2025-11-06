package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.xml.api.IException;
import io.github.up2jakarta.xml.api.SeverityType;

public final class ETWrapper implements IException {

    final String code;
    final String message;
    final Throwable cause;
    final SeverityType level;

    public ETWrapper(SeverityType level, String code, String message, Throwable cause) {
        this.cause = cause;
        this.code = code;
        this.level = level;
        this.message = message;
    }

    @Override
    public SeverityType getSeverity() {
        return level;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getCode() {
        return code;
    }

}
