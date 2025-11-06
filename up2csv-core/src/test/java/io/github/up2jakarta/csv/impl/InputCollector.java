package io.github.up2jakarta.csv.impl;

import io.github.up2jakarta.csv.core.hdl.ETraceCollector;

public class InputCollector extends ETraceCollector<GroupType, InputRecord, InputError> {

    public InputCollector(InputRecord row) {
        super(row, InputError::new, (r) -> 0);
    }

}
