package io.github.up2jakarta.csv.fmt;

import io.github.up2jakarta.csv.core.hdl.TraceCollector;
import io.github.up2jakarta.csv.data.DataType;

/**
 * Simple event collection for {@link io.github.up2jakarta.csv.fmt.SimpleFullImporter}.
 *
 * @param <B> the business data type
 * @param <R> the record type
 */
public class InputCollector<B extends DataType<B>, R extends InputRecord<?>> extends TraceCollector<B, R, InputError<B, R>> {

    public InputCollector(R row) {
        super(row, InputError::new, (r) -> 0);
    }

}
