package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.hdl.IBusinessCreator;
import io.github.up2jakarta.csv.api.hdl.IBusinessEvent;
import io.github.up2jakarta.csv.api.hdl.IBusinessRepository;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Identifiable;
import io.github.up2jakarta.csv.data.LazyCounter;

import java.util.LinkedList;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Input events collector that is responsible for create the final Event to be collected during the mapping/parsing,
 * useful for error persistence.
 * <p>
 * This collector is the default mode for {@link io.github.up2jakarta.csv.BusinessBuilder} when working with
 * {@link IBusinessCreator} and {@link IBusinessRepository}, it's compatible for all modes.
 *
 * @param <R> the row type
 * @param <D> the data type
 * @param <E> the error type
 */
public non-sealed class BusinessCollector<D extends DataType<D>, R extends IRecord<?> & Identifiable<?>, E extends IBusinessEvent<D, R, ?>> extends BusinessHandler<R, D, E, BusinessException> {

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
        this(source, new LinkedList<>(), creator, repository);
    }

    public BusinessCollector(R row, List<E> collector, IBusinessCreator<D, R, E> creator, IBusinessRepository<R> repository) {
        super(row, collector, BUSINESS_MODE);
        requireNonNull(repository);
        this.creator = requireNonNull(creator);
        this.counter = new LazyCounter(() -> repository.max(row));
    }

    @Override
    final E create(D type, Integer offset, BusinessException cause) {
        final int order = counter.getAsInt() + events.size();
        final String trace = Up2Factory.trace(cause).orElse(null);
        return creator.create(source, order, type, offset, cause, trace);
    }

}
