package io.github.up2jakarta.csv.api.hdl;

import io.github.up2jakarta.csv.data.Identifiable;

/**
 * Contact interface for input source, helpful for well computing {@link IRecordEntity.IKey#getRecordNumber()}
 *
 * @param <K> the business identifier type
 */
public interface ISourceEntity<K> extends Identifiable<K> {

}
