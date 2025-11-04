package io.github.up2jakarta.csv.impl;

import io.github.up2jakarta.csv.core.hdl.TraceCollector;

public class InputCollector extends TraceCollector<GroupType, InputRecord, InputError> {

    public InputCollector(InputRecord row) {
        super(row, InputError::new, (r) -> 0);
    }

}
