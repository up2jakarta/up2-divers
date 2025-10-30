package io.github.up2jakarta.csv.impl;

import io.github.up2jakarta.csv.fmt.hdl.FullCollector;

public class InputCollector extends FullCollector<GroupType, InputRecord, InputError> {

    public InputCollector(InputRecord row) {
        super(row, InputError::new, (r) -> 0);
    }

}
