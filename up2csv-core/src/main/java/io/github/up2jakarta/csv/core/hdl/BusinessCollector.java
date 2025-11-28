package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.hdl.IBusinessCreator;
import io.github.up2jakarta.csv.api.hdl.IBusinessEvent;
import io.github.up2jakarta.csv.api.hdl.IBusinessRepository;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.LazyCounter;
import io.github.up2jakarta.lov.IException;
import io.github.up2jakarta.lov.core.Identifiable;

import static io.github.up2jakarta.lov.core.AccessException.notNull;

/**
 * Input events collector of {@link BusinessHandler} that collects all events in {@link #events}.
 *
 * @param <R> the input record type
 * @param <D> the business data type
 * @param <E> the event type
 */
public class BusinessCollector<D extends DataType<D>, R extends IRecord<?> & Identifiable<?>, E extends IBusinessEvent<D, R, ?>> extends EventModeCollector<D, R, E, IException> {
    public static final EventModeType<? extends IException> MODE = BusinessModeType.INSTANCE;

    private final IBusinessCreator<D, R, E> creator;
    private final LazyCounter counter;

    /**
     * Public constructor fo instance creation.
     *
     * @param source     the input record
     * @param creator    the error creator
     * @param repository the input repository
     */
    public BusinessCollector(R source, IBusinessCreator<D, R, E> creator, IBusinessRepository<R> repository) {
        super(source, creator, MODE);
        notNull(repository, BusinessCollector.class, "repository");
        this.creator = notNull(creator, BusinessCollector.class, "creator");
        this.counter = new LazyCounter(() -> repository.max(source));
    }

    @Override
    protected final E newEvent(D type, Integer offset, IException cause) {
        final int order = counter.getAsInt();
        final String trace = Up2Factory.trace(cause).orElse(null);
        return creator.apply(source, order, type, offset, cause, trace);
    }

    /**
     * {@link BusinessCollector} builder.
     */
    public static final class Builder<D extends DataType<D>, R extends IRecord<?> & Identifiable<?>, E extends IBusinessEvent<D, R, ?>> extends EventModeBuilder<D, R, E> {
        private final IBusinessCreator<D, R, E> creator;
        private final IBusinessRepository<R> repository;

        public Builder(int size, IBusinessCreator<D, R, E> creator, IBusinessRepository<R> repository) {
            super(size);
            this.creator = creator;
            this.repository = repository;
        }

        @Override
        protected BusinessCollector<D, R, E> newHandler(R record) {
            return new BusinessCollector<>(record, creator, repository);
        }
    }
}
