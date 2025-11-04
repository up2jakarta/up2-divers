package io.github.up2jakarta.job.csv.impl.full;

import io.github.up2jakarta.csv.core.hdl.TraceCollector;
import io.github.up2jakarta.job.csv.impl.GroupType;

public class InputHandler extends TraceCollector<GroupType, InputRecord, InputError> {

    public InputHandler(InputRecord row) {
        super(row, InputError::new, (r) -> 0);
    }

}
