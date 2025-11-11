package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.hdl.IPropertyCreator;
import io.github.up2jakarta.csv.api.hdl.IPropertyEvent;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.xml.api.SeverityType;

import java.util.LinkedList;

/**
 * Extension of {@link PropertyCollector} that's fails at the first error having
 * severity equals or greater than the specified {@link SeverityType}.
 *
 * @param <R> the row type
 * @param <D> the data type
 * @param <E> the error type
 */
public class PropertyFailureCollector<R extends IRecord<?>, D extends DataType<D>, E extends IPropertyEvent<R, D>> extends PropertyCollector<R, D, E> {

    /**
     * Public constructor fo instance creation with fail-fast severity level.
     *
     * @param source  the input record
     * @param creator the error creator
     * @param type    the severity level for failure
     */
    public PropertyFailureCollector(R source, IPropertyCreator<R, D, E> creator, SeverityType type) {
        super(source, new LinkedList<>(), creator, type.getLevel());
    }

}
