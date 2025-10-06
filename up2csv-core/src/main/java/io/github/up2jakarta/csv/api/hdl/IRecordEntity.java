package io.github.up2jakarta.csv.api.hdl;

import io.github.up2jakarta.csv.api.IFullType;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.data.Identifiable;

/**
 * Contact interface for an input record, useful for error persistence.
 *
 * @param <T> the segment type definition
 */
public interface IRecordEntity<T extends IFullType<?, T>> extends IRecord<T>, Identifiable<String> {

}
