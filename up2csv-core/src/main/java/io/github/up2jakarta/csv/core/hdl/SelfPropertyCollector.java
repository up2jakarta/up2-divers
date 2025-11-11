package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.api.hdl.IPropertyCreator;
import io.github.up2jakarta.csv.api.hdl.IPropertyEvent;
import io.github.up2jakarta.csv.api.hdl.ISelfEvent;
import io.github.up2jakarta.csv.api.hdl.ISelfRecord;
import io.github.up2jakarta.csv.data.DataType;

/**
 * Extension of {@link PropertyCollector}, useful when each row have its related errors.
 *
 * @param <R> the record type
 * @param <D> the data type
 * @param <E> the error type
 */
public class SelfPropertyCollector<D extends DataType<D>, R extends ISelfRecord<D, ?, E, R>, E extends ISelfEvent<D, R, E> & IPropertyEvent<R, D>> extends PropertyCollector<R, D, E> {

    /**
     * Public constructor fo instance creation.
     *
     * @param source  the input record
     * @param creator the error creator
     */
    public SelfPropertyCollector(R source, IPropertyCreator<R, D, E> creator) {
        super(source, source.getErrors(), creator, UNDEFINED_LEVEL);
    }

}
