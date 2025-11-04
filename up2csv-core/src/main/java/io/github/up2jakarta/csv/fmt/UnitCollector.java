package io.github.up2jakarta.csv.fmt;

import io.github.up2jakarta.csv.core.hdl.FatalCollector;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.xml.api.SeverityType;

/**
 * Simple event collector based on exceptions for {@link io.github.up2jakarta.csv.fmt.SimpleUnitImporter} and
 * {@link io.github.up2jakarta.csv.fmt.SimpleFastImporter}.
 *
 * @param <B> the business data type
 * @param <R> the record type
 */
public class UnitCollector<B extends DataType<B>, R extends UnitRecord<?>> extends FatalCollector<R, B, MiniError<B, R>> {

    public UnitCollector(R row, SeverityType level) {
        super(row, MiniError::new, level);
    }

}
