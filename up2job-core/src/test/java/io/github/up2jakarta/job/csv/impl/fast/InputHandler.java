package io.github.up2jakarta.job.csv.impl.fast;

import io.github.up2jakarta.csv.fmt.hdl.FastCollector;
import io.github.up2jakarta.job.csv.impl.GroupType;

public class InputHandler extends FastCollector<InputRecord, GroupType, InputError> {

    public InputHandler(InputRecord row) {
        super(row, InputError::new);
    }

}
