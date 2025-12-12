package io.github.up2jakarta.test.impl.full;

import io.github.up2jakarta.csv.core.hdl.BusinessCollector;
import io.github.up2jakarta.csv.core.hdl.EventModeBuilder;
import io.github.up2jakarta.test.impl.GroupType;

public class InputHandler extends BusinessCollector<GroupType, InputRecord, InputError> {

    public InputHandler(InputRecord row) {
        super(row, InputError::new, (r) -> 0);
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
