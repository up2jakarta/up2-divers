package io.github.up2jakarta.job.csv.impl.fast;

import io.github.up2jakarta.csv.core.hdl.EventModeBuilder;
import io.github.up2jakarta.csv.core.hdl.PropertyCollector;
import io.github.up2jakarta.job.csv.impl.GroupType;

public class InputHandler extends PropertyCollector<GroupType, InputRecord, InputError> {

    public InputHandler(InputRecord row) {
        super(row, InputError::new);
    }

    public static final class Builder extends EventModeBuilder<GroupType, InputRecord, InputError> {
        public Builder(int size) {
            super(size);
        }

        @Override
        protected InputHandler newHandler(InputRecord record) {
            return new InputHandler(record);
        }
    }

}
