package io.github.up2jakarta.csv.io.misc;

import io.github.up2jakarta.csv.core.hdl.BusinessCollector;
import io.github.up2jakarta.csv.io.impl.GroupType;

public class InputHandler extends BusinessCollector<GroupType, InputRecord, InputError> {

    public InputHandler(InputRecord row) {
        super(row, InputError::new, (r) -> 0);
    }

}
