package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.hdl.IBusinessCreator;
import io.github.up2jakarta.csv.api.hdl.IBusinessEvent;
import io.github.up2jakarta.csv.api.hdl.IBusinessRepository;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Identifiable;
import io.github.up2jakarta.csv.data.LazyCounter;

import static java.util.Objects.requireNonNull;

/**
 * Input events collector of {@link BusinessHandler} that collects all events in {@link #events}.
 *
 * @param <R> the input record type
 * @param <D> the business data type
 * @param <E> the event type
 */
public class BusinessCollector<D extends DataType<D>, R extends IRecord<?> & Identifiable<?>, E extends IBusinessEvent<D, R, ?>> extends EventModeCollector<D, R, E, BusinessException> {
    public static final EventModeType<BusinessException> MODE = BusinessModeType.INSTANCE;

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
        requireNonNull(repository);
        this.creator = requireNonNull(creator);
        this.counter = new LazyCounter(() -> repository.max(source));
    }

    @Override
    protected final E newEvent(D type, Integer offset, BusinessException cause) {
        final int order = counter.getAsInt() + events.size();
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
