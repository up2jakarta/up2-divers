package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Truncated;
import io.github.up2jakarta.csv.core.BSNode.Bean;
import io.github.up2jakarta.csv.core.hdl.EventHandler;
import io.github.up2jakarta.csv.core.hdl.FailureException;
import io.github.up2jakarta.csv.core.hdl.FastHandler;
import io.github.up2jakarta.csv.core.hdl.PropertyFailureException;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.lov.SeverityType;
import io.github.up2jakarta.lov.core.AccessException;
import io.github.up2jakarta.lov.core.BeanException;

import static io.github.up2jakarta.csv.core.BSManager.*;
import static io.github.up2jakarta.lov.SeverityType.ERROR;
import static io.github.up2jakarta.lov.core.AccessException.notNull;

/**
 * Up2J Processor that maps and validates flat-data to java-beans, based on java annotations configuration.
 *
 * @param <S> the segment type
 * @param <D> The business data type
 */
public final class Up2Mapper<S extends Segment, D extends DataType<D>> extends Pod<S, D, Bean<S, D, ?>> {

    Up2Mapper(Key<D, S> key, Bean<S, D, ?> node) throws BeanException {
        super(key, node);
        check(node);
    }

    /**
     * Maps and validates input data to java bean depending on annotations like {@link Position}
     * with fail-fast principle but ignores the warnings events.
     *
     * @param record the input data
     * @return the parsed segment
     * @throws AccessException  for any problem when assigning properties of the returned segment from the input record
     * @throws FailureException if an event occurs having level greater or equals {@link SeverityType#ERROR}
     * @see FastHandler#of(SeverityType)
     */
    public S map(final String... record) throws AccessException {
        return this.map(FastHandler.of(ERROR), record);
    }

    /**
     * Maps and validates input data to java bean depending on annotations like {@link Position}
     * and handles events occurred during the mapping phase.
     *
     * @param handler the event handler, must not be null
     * @param record  the input data
     * @return the parsed segment
     * @throws AccessException for any problem when assigning properties of the returned segment from the input record
     */
    public S map(EventHandler<D> handler, String... record) throws AccessException {
        return this.map(handler, offset, record);
    }

    /**
     * Maps and validates input data to java bean depending on annotations like {@link Position}
     * and handles events occurred during the mapping phase.
     *
     * @param handler the error collector, must not be null
     * @param offset  the number of columns reserved {@link Truncated#value()}
     * @param record  the input data
     * @return the parsed segment
     * @throws AccessException for any problem when assigning properties of the returned segment from the input record
     */
    public S map(EventHandler<D> handler, int offset, String... record) throws AccessException {
        if (record == null) {
            return null;
        }
        notNull(handler, Up2Mapper.class, "handler");
        final S bean = node.parse(handler, offset, record);
        node.validate(bean, offset, handler);
        return bean;
    }

    /**
     * Maps and validates input data to java bean depending on annotations like {@link Position}
     * with fail-fast principle but ignores the warnings events.
     *
     * @param record the input data
     * @param <R>    the input record type
     * @return the parsed segment and its parsing/validating events
     * @throws AccessException  for any problem when assigning properties of the returned segment from the input record
     * @throws FailureException if an event occurs having level greater or equals {@link SeverityType#ERROR}
     */
    public <R extends IRecord<?>> S map(R record) throws AccessException, PropertyFailureException {
        return this.map(record, offset, FastHandler.of(ERROR));
    }

    /**
     * Maps and validates input data to java bean depending on annotations like {@link Position}
     * and handles events occurred during the mapping phase.
     *
     * @param record  the input data
     * @param handler the event handler, must not be null
     * @param <R>     the input record type
     * @return the parsed segment
     * @throws AccessException for any problem when assigning properties of the returned segment from the input record
     */
    public <R extends IRecord<?>> S map(R record, EventHandler<D> handler) throws AccessException {
        return this.map(record, offset, handler);
    }

    /**
     * Maps and validates input data to java bean depending on annotations like {@link Position}
     * and handles events occurred during the mapping phase.
     *
     * @param record  the input data
     * @param offset  the number of columns reserved {@link Truncated#value()}
     * @param handler the event handler, must not be null
     * @param <R>     the input record type
     * @return the parsed segment
     * @throws AccessException for any problem when assigning properties of the returned segment from the input record
     */
    public <R extends IRecord<?>> S map(R record, int offset, EventHandler<D> handler) throws AccessException {
        if (record == null) {
            return null;
        }
        final S bean = this.map(handler, offset, record.getData());
        node.update(bean, record);
        return bean;
    }

    /**
     * Converts the current mapper to flatter that is able to map bean-segment to flat-data.
     * This method is faster then {@link Up2Factory#format(Class, io.github.up2jakarta.csv.data.DataResolver)}
     * because the bean is already scanned.
     *
     * @return preconfigured CSV Format for the same segment
     * @throws BeanException if any property is not accessible for read
     */
    public Up2Flatter<S, D> toFlatter() throws BeanException {
        return ft(this).build(Up2Flatter::new);
    }

}
