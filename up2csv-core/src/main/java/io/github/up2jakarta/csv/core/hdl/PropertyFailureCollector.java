package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.hdl.IPropertyCreator;
import io.github.up2jakarta.csv.api.hdl.IPropertyEvent;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.lov.SeverityType;
import io.github.up2jakarta.lov.TypeException;

/**
 * Extension of {@link PropertyCollector} that fails at the first event having
 * level equals or greater than {@link PropertyFailureCollector#level}
 * <p>
 * It's similar to {@link io.github.up2jakarta.csv.core.hdl.FastHandler} but it collects the older events to be
 * attached to {@link PropertyFailureException#toList()} or returned after processing done.
 *
 * @param <R> the input record type
 * @param <D> the business data type
 * @param <E> the event type
 */
public class PropertyFailureCollector<D extends DataType<D>, R extends IRecord<?>, E extends IPropertyEvent<D, R>> extends EventModeCollector<D, R, E, TypeException> {

    private final IPropertyCreator<D, R, E> creator;
    private final int level;

    /**
     * Public constructor fo instance creation with fail-fast level.
     *
     * @param source  the input record
     * @param creator the event creator
     * @param level   the fast-failure level
     */
    public PropertyFailureCollector(R source, IPropertyCreator<D, R, E> creator, SeverityType level) {
        super(source, creator, PropertyCollector.MODE);
        this.level = level.getAsInt();
        this.creator = creator;
    }

    @Override
    protected final E newEvent(D type, Integer offset, TypeException cause) {
        if (cause.getLevel().getAsInt() < level) {
            return creator.apply(source, offset, type, cause);
        } else {
            throw new PropertyFailureException(type, offset, cause, events);
        }
    }

    /**
     * {@link PropertyFailureCollector} builder.
     */
    public static final class Builder<D extends DataType<D>, R extends IRecord<?>, E extends IPropertyEvent<D, R>> extends EventModeBuilder<D, R, E> {
        private final IPropertyCreator<D, R, E> creator;
        private final SeverityType level;

        public Builder(int size, IPropertyCreator<D, R, E> creator, SeverityType level) {
            super(size);
            this.level = level;
            this.creator = creator;
        }

        @Override
        protected PropertyFailureCollector<D, R, E> newHandler(R record) {
            return new PropertyFailureCollector<>(record, creator, level);
        }
    }

}
