package io.github.up2jakarta.csv.io.misc;

import io.github.up2jakarta.csv.impl.FullCollector;
import io.github.up2jakarta.csv.io.impl.GroupType;
import io.github.up2jakarta.xml.api.SeverityType;

import java.util.Optional;

public class InputHandler extends FullCollector<InputRowEntity, GroupType, InputErrorEntity> {

    public InputHandler(InputRowEntity row) {
        super(row, InputHandler::create, (r) -> 0);
    }

    public static InputErrorEntity create(InputRowEntity row, int offset, GroupType type, SeverityType level, String code, String message, Optional<String> trace) {
        final InputErrorEntity error = new InputErrorEntity();
        {
            final InputErrorEntity.PKey key = new InputErrorEntity.PKey();
            key.setRecord(row);
            error.setKey(key);
        }
        error.setSeverity(level);
        error.setOffset(offset);
        error.setType(type);
        error.setCode(code);
        error.setMessage(message);
        error.setTrace(trace.orElse(null));
        return error;
    }

}
