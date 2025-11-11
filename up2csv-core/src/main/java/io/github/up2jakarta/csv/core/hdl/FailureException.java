package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.xml.api.MessageFormatter;
import io.github.up2jakarta.xml.api.PropertyException;
import io.github.up2jakarta.xml.api.SeverityType;

import java.util.Optional;

/**
 * Exception implementation for fail-fast events handling without collecting.
 *
 * @see FastHandler
 */
@SuppressWarnings("rawtypes")
public class FailureException extends PropertyException implements IEvent {

    protected static final String FORMAT = "#[%s] throws %s";

    protected final DataType<?> dataType;
    protected final Integer offset;

    FailureException(DataType<?> type, Integer offset, SeverityType level, String code, String message) {
        super(level, code, message);
        this.offset = offset;
        this.dataType = type;
    }

    FailureException(DataType<?> type, Integer offset, SeverityType level, String code, Throwable cause) {
        super(level, code, cause.toString(), cause);
        this.offset = offset;
        this.dataType = type;
    }

    @Override
    public final DataType<?> getType() {
        return dataType;
    }

    @Override
    public final Integer getOffset() {
        return offset;
    }

    @Override
    public String getFormattedMessage() {
        final String offset = Optional.of(this.offset).map(String::valueOf).orElse("?");
        if (this.getCause() instanceof MessageFormatter mf) {
            return String.format(FORMAT, offset, mf.getFormattedMessage());
        }
        return String.format(FORMAT, offset, super.getFormattedMessage());
    }

}
