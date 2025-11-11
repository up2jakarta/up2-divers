package io.github.up2jakarta.job.csv.impl.unit;

import io.github.up2jakarta.csv.core.hdl.PropertyCollector;
import io.github.up2jakarta.job.csv.impl.GroupType;

public class InputHandler extends PropertyCollector<InputRecord, GroupType, InputError> {

    public InputHandler(InputRecord row) {
        super(row, InputError::new);
    }

}
