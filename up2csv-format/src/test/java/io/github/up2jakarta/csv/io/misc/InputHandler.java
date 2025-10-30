package io.github.up2jakarta.csv.io.misc;

import io.github.up2jakarta.csv.fmt.hdl.FullCollector;
import io.github.up2jakarta.csv.io.impl.GroupType;

public class InputHandler extends FullCollector<GroupType, InputRecord, InputError> {

    public InputHandler(InputRecord row) {
        super(row, InputError::new, (r) -> 0);
    }

}
