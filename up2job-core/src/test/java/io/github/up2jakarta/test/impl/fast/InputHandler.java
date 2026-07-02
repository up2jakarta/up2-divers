package io.github.up2jakarta.test.impl.fast;

import io.github.up2jakarta.csv.core.hdl.EventModeBuilder;
import io.github.up2jakarta.csv.core.hdl.PropertyCollector;
import io.github.up2jakarta.test.impl.TermType;

public class InputHandler extends PropertyCollector<TermType, InputRecord, InputError> {

    public InputHandler(InputRecord row) {
        super(row, InputError::new);
    }

    public static final class Builder extends EventModeBuilder<TermType, InputRecord, InputError> {
        public Builder(int size) {
            super(size);
        }

        @Override
        protected InputHandler newHandler(InputRecord record) {
            return new InputHandler(record);
        }
    }

}
