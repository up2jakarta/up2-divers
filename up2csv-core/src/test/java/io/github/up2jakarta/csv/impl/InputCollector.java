package io.github.up2jakarta.csv.impl;

import io.github.up2jakarta.csv.core.hdl.BusinessCollector;

public class InputCollector extends BusinessCollector<GroupType, InputRecord, InputError> {

    public InputCollector(InputRecord row) {
        super(row, InputError::new, (r) -> 0);
    }

}
