package io.github.up2jakarta.csv.fmt.hdl;

import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.xml.api.MessageFormatter;
import io.github.up2jakarta.xml.api.SeverityType;
import io.github.up2jakarta.xml.clv.PropertyException;

public class FastException extends PropertyException {

    protected static final String FORMAT = "#[%s] throws %s";

    protected final DataType<?> dataType;
    protected final int offset;

    public FastException(DataType<?> type, int offset, SeverityType level, String code, String message) {
        super(level, code, message);
        this.offset = offset;
        this.dataType = type;
    }

    public FastException(DataType<?> type, int offset, SeverityType level, String code, Exception cause) {
        super(level, code, cause);
        this.offset = offset;
        this.dataType = type;
    }

    public final DataType<?> getDataType() {
        return dataType;
    }

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
