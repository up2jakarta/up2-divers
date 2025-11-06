package io.github.up2jakarta.job.csv.impl.full;

import io.github.up2jakarta.csv.core.hdl.ETraceCollector;
import io.github.up2jakarta.job.csv.impl.GroupType;

public class InputHandler extends ETraceCollector<GroupType, InputRecord, InputError> {

    public InputHandler(InputRecord row) {
        super(row, InputError::new, (r) -> 0);
    }

}
