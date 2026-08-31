package io.github.up2jakarta.test.impl.mess;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.hdl.PropertyEvent;
import io.github.up2jakarta.lov.TypeException;
import io.github.up2jakarta.test.impl.TermType;

public class InputError extends PropertyEvent<TermType, InputRecord> implements Segment {

    public InputError(InputRecord row, Integer offset, TermType type, TypeException cause) {
        super(row, offset, type, cause);
    }

}
