package io.github.up2jakarta.csv.impl;

import io.github.up2jakarta.csv.api.hdl.IEntityCreator;
import io.github.up2jakarta.xml.api.SeverityType;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SimpleCreator implements IEntityCreator<InputRowEntity, GroupType, InputErrorEntity> {

    @Override
    public final InputErrorEntity create(InputRowEntity row, int offset, GroupType type, SeverityType level, String code, String message, Optional<String> trace) {
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