package io.github.up2jakarta.csv.data;

import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.xml.clv.CodeListException;

/**
 * Contract interface for record transformer that's able to transform flat-data to framework record representation.
 *
 * @see io.github.up2jakarta.csv.fmt.SimpleUnitImporter
 * @see io.github.up2jakarta.csv.fmt.SimpleFastImporter
 * @see io.github.up2jakarta.csv.fmt.SimpleFullImporter
 */
public interface RecordTransformer<R extends IRecord<?>> {

    /**
     * Creates and returns new record from the specified source.
     *
     * @param source the record source
     * @return new related record
     * @throws CodeListException if type is unknown or cannot be parsed
     */
    R transform(String... source) throws CodeListException;

}
