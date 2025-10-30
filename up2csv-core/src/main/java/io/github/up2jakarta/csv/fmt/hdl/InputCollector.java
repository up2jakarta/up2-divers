package io.github.up2jakarta.csv.fmt.hdl;

import io.github.up2jakarta.csv.data.DataType;

public class InputCollector<B extends DataType<B>, R extends InputRecord<?, ?>> extends FullCollector<B, R, InputError<B, R>> {

    public InputCollector(R row) {
        super(row, InputError::new, (r) -> 0);
    }

}
