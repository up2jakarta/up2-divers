package io.github.up2jakarta.test.impl;

import io.github.up2jakarta.csv.core.hdl.BusinessCollector;
import io.github.up2jakarta.csv.core.hdl.EventModeBuilder;

public class InputCollector extends BusinessCollector<GroupType, InputRecord, InputError> {

    public InputCollector(InputRecord row) {
        super(row, InputError::new, (r) -> 0);
    }

    public static final class Builder extends EventModeBuilder<GroupType, InputRecord, InputError> {
        public Builder(int size) {
            super(size);
        }

        @Override
        protected InputCollector newHandler(InputRecord record) {
            return new InputCollector(record);
        }
    }

}
