package io.github.up2jakarta.csv.hdl;

import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.ITerm;
import io.github.up2jakarta.csv.api.hdl.IPropertyCreator;
import io.github.up2jakarta.csv.api.hdl.IPropertyEvent;
import io.github.up2jakarta.lov.TypeException;

import static io.github.up2jakarta.lov.core.AccessException.notNull;

/**
 * Input events collector of {@link BusinessHandler} that collects all events in {@link #events}.
 *
 * @param <R> the input record type
 * @param <D> the business term type
 * @param <E> the event type
 */
public class PropertyCollector<D extends ITerm<D>, R extends IRecord<?>, E extends IPropertyEvent<D, R>> extends EventModeCollector<D, R, E, TypeException> {
    public static final EventModeType<TypeException> MODE = PropertyModeType.INSTANCE;

    private final IPropertyCreator<D, R, E> creator;

    /**
     * Public constructor fo instance creation.
     *
     * @param source  the input record
     * @param creator the error creator
     */
    public PropertyCollector(R source, IPropertyCreator<D, R, E> creator) {
        super(source, creator, MODE);
        this.creator = notNull(creator, PropertyCollector.class, "creator");
    }

    @Override
    protected final E newEvent(D type, Integer offset, TypeException cause) {
        return creator.apply(source, offset, type, cause);
    }

    /**
     * {@link PropertyCollector} builder.
     */
    public static final class Builder<D extends ITerm<D>, R extends IRecord<?>, E extends IPropertyEvent<D, R>> extends EventModeBuilder<D, R, E> {
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
