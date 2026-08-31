package io.github.up2jakarta.csv.data;

import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.lov.CodeListException;
import io.github.up2jakarta.lov.core.AccessException;

import java.util.List;

/**
 * Contract interface for record transformer that's able to transform flat-data to framework record representation.
 *
 * @see SimpleNeatImporter
 * @see SimpleMessImporter
 * @see SimpleFullImporter
 */
public interface RecordTransformer<R extends IRecord<?>> {

    /**
     * Creates and returns new record from the specified <code>source</code>.
     *
     * @param source the record source
     * @return new related record
     * @throws CodeListException if type is unknown or cannot be parsed
     */
    R transform(String... source) throws CodeListException;

    /**
     * Creates and returns the business-object created from the given <code>records</code> source.
     *
     * @param records the input records source
     * @return the parsed business-object with all collected events
     * @throws AccessException   for any problem when setting properties of java-beans from input record
     * @throws CodeListException if type of one record is unknown
     */
    Up2Result<?, ?> transform(List<String[]> records) throws AccessException;

}
