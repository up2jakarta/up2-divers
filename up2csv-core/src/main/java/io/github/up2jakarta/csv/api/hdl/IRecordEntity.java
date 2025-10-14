package io.github.up2jakarta.csv.api.hdl;

import io.github.up2jakarta.csv.api.IFullType;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.hdl.IRecordEntity.IKey;
import io.github.up2jakarta.csv.data.Identifiable;
import io.github.up2jakarta.csv.data.Referencable;

/**
 * Contact interface for an input record, useful for error persistence.
 *
 * @param <T> the segment type definition
 */
public interface IRecordEntity<T extends IFullType<?, T>, S extends ISourceEntity<?>, K extends IKey<S>> extends IRecord<T>, Identifiable<K>, Referencable {

    /**
     * Contact interface for input error identifier.
     *
     * @param <F> the input source type
     */
    interface IKey<F extends ISourceEntity<?>> {

        /**
         * @return the computed order by source
         */
        Long getRecordNumber();

        /**
         * @param order the record order in the related input source
         */
        void setRecordNumber(Long order);

        /**
         * @return the related input source
         */
        F getSource();

        /**
         * @param source the input source
         */
        void setSource(F source);

    }

}
