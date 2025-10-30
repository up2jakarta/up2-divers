package io.github.up2jakarta.csv.api.hdl;

import io.github.up2jakarta.csv.api.IFullType;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.hdl.IFullRecord.IKey;
import io.github.up2jakarta.csv.data.Identifiable;
import io.github.up2jakarta.csv.data.Referencable;

/**
 * Contact interface for an input record, useful for error persistence.
 *
 * @param <T> the input type definition
 * @param <S> the input source type
 * @param <K> the record key type
 */
public interface IFullRecord<T extends IFullType<?, T>, S extends Comparable<S>, K extends IKey<S>> extends IRecord<T>, Identifiable<K>, Referencable {

    /**
     * Contact interface for input error identifier.
     *
     * @param <F> the input source type
     */
    interface IKey<F extends Comparable<F>> {

        /**
         * @return the computed order by source
         */
        Long getRecordNumber();

        /**
         * @return the related input source
         */
        F getSource();

    }

}
