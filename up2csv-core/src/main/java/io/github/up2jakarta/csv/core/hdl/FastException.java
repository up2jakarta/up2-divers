package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.xml.api.MessageFormatter;
import io.github.up2jakarta.xml.api.PropertyException;
import io.github.up2jakarta.xml.api.SeverityType;

/**
 * Exception implementation for fail-fast events handling without collecting.
 *
 * @see FastHandler
 */
@SuppressWarnings("rawtypes")
public class FastException extends PropertyException implements IEvent {

    protected static final String FORMAT = "#[%s] throws %s";

    protected final DataType<?> dataType;
    protected final int offset;

    FastException(DataType<?> type, int offset, SeverityType level, String code, String message) {
        super(level, code, message);
        this.offset = offset;
        this.dataType = type;
    }

    FastException(DataType<?> type, int offset, SeverityType level, String code, Throwable cause) {
        super(level, code, cause.toString(), cause);
        this.offset = offset;
        this.dataType = type;
    }

    @Override
    public final DataType<?> getType() {
        return dataType;
    }

    @Override
    public final int getOffset() {
        return offset;
    }

    @Override
    public String getFormattedMessage() {
        if (this.getCause() instanceof MessageFormatter mf) {
            return String.format(FORMAT, offset, mf.getFormattedMessage());
        }
        return String.format(FORMAT, offset, super.getFormattedMessage());
    }

}
