package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.hdl.IPropertyCreator;
import io.github.up2jakarta.csv.api.hdl.IPropertyEvent;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.lov.PropertyException;

import static java.util.Objects.requireNonNull;

/**
 * Input events collector of {@link BusinessHandler} that collects all events in {@link #events}.
 *
 * @param <R> the input record type
 * @param <D> the business data type
 * @param <E> the event type
 */
public class PropertyCollector<D extends DataType<D>, R extends IRecord<?>, E extends IPropertyEvent<D, R>> extends EventModeCollector<D, R, E, PropertyException> {
    public static final EventModeType<PropertyException> MODE = PropertyModeType.INSTANCE;

    private final IPropertyCreator<D, R, E> creator;

    /**
     * Public constructor fo instance creation.
     *
     * @param source  the input record
     * @param creator the error creator
     */
    public PropertyCollector(R source, IPropertyCreator<D, R, E> creator) {
        super(source, creator, MODE);
        this.creator = requireNonNull(creator);
    }

    @Override
    protected final E newEvent(D type, Integer offset, PropertyException cause) {
        return creator.apply(source, offset, type, cause);
    }

    /**
     * {@link PropertyCollector} builder.
     */
    public static final class Builder<D extends DataType<D>, R extends IRecord<?>, E extends IPropertyEvent<D, R>> extends EventModeBuilder<D, R, E> {
        private final IPropertyCreator<D, R, E> creator;

        public Builder(int size, IPropertyCreator<D, R, E> creator) {
            super(size);
            this.creator = creator;
        }

        @Override
        protected PropertyCollector<D, R, E> newHandler(R record) {
            return new PropertyCollector<>(record, creator);
        }
    }

}
