package io.github.up2jakarta.job.csv.impl.full;

import io.github.up2jakarta.csv.fmt.hdl.FullCollector;
import io.github.up2jakarta.job.csv.impl.GroupType;

public class InputHandler extends FullCollector<InputRecord, GroupType, InputError> {

    public InputHandler(InputRecord row) {
        super(row, InputError::new, (r) -> 0);
    }

}
