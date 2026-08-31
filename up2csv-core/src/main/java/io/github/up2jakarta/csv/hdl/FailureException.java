package io.github.up2jakarta.csv.hdl;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.ITerm;
import io.github.up2jakarta.lov.SeverityType;
import io.github.up2jakarta.lov.TypeException;

import java.util.Optional;

/**
 * Exception implementation for fail-fast events handling without collecting.
 *
 * @see FastHandler
 */
@SuppressWarnings("rawtypes")
public class FailureException extends TypeException implements IEvent {

    protected static final String FORMAT = "#[%s] throws %s";

    protected final transient ITerm<?> dataType;
    protected final Integer offset;

    FailureException(ITerm<?> type, Integer offset, SeverityType level, String code, String message) {
        super(level, code, message);
        this.offset = offset;
        this.dataType = type;
    }

    FailureException(ITerm<?> type, Integer offset, SeverityType level, String code, Throwable cause) {
        super(level, code, cause.toString(), cause);
        this.offset = offset;
        this.dataType = type;
    }

    @Override
    public final ITerm<?> getType() {
        return dataType;
    }

    @Override
    public final Integer getOffset() {
        return offset;
    }

    @Override
    public String getLocalizedMessage() {
        final String offset = Optional.of(this.offset).map(String::valueOf).orElse("?");
        return String.format(FORMAT, offset, super.getLocalizedMessage());
    }

}
