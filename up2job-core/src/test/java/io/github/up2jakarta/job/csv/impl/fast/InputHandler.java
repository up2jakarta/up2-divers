package io.github.up2jakarta.job.csv.impl.fast;

import io.github.up2jakarta.csv.core.hdl.FatalCollector;
import io.github.up2jakarta.job.csv.impl.GroupType;

public class InputHandler extends FatalCollector<InputRecord, GroupType, InputError> {

    public InputHandler(InputRecord row) {
        super(row, InputError::new);
    }

}
