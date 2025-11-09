package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Truncated;
import io.github.up2jakarta.csv.core.BSNode.BFNode;
import io.github.up2jakarta.csv.core.BSNode.BPNode;
import io.github.up2jakarta.csv.core.BSOperator.Getter;
import io.github.up2jakarta.csv.core.BSOperator.Processor;
import io.github.up2jakarta.csv.core.hdl.EventHandler;
import io.github.up2jakarta.csv.core.hdl.FastHandler;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.DataTypeResolver;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.xml.api.SeverityType;

/**
 * Map and validate input data to a configurable bean that supports only {@link String} type.
 *
 * @param <S> the segment type
 * @param <D> The input data type
 */
public final class Up2Mapper<S extends Segment, D extends DataType<D>> extends Processor<S, D, BPNode<S, D, ?>> {

    final Getter<S> parentId;
    final Getter<S> businessId;

    Up2Mapper(BPNode<S, D, ?> node) throws BeanException {
        super(node);
        parentId = Getter.parentId(node.type, node.properties);
        businessId = Getter.businessId(node.type, node.properties);
    }

    /**
     * Maps and validates input data to java bean depending on annotations like {@link Position}
     * with fail-fast principle.
     *
     * @param record the input data
     * @return the parsed segment
     * @throws BeanException for any problem configuring and assigning fields of the input to bean properties
     * @see #map(IRecord, EventHandler)
     * @see FastHandler#of(SeverityType)
     */
    public S map(final String... record) throws BeanException {
        return this.map(FastHandler.of(SeverityType.ERROR), record);
    }

    /**
     * Maps and validates input data to java bean depending on annotations like {@link Position}.
     * and collect errors in the given collector after full-filling the error properties.
     *
     * @param handler the error collector, must not be null
     * @param record  the input data
     * @return the parsed segment
     * @throws BeanException for any problem configuring and assigning fields of the input to bean properties
     */
    public S map(EventHandler<?, D, ? extends IEvent<D>> handler, String... record) throws BeanException {
        return this.map(handler, offset, record);
    }

    /**
     * Maps and validates input data to java bean depending on annotations like {@link Position}.
     * and collect errors in the given collector after full-filling the error properties.
     *
     * @param handler the error collector, must not be null
     * @param offset  the number of columns reserved {@link Truncated#value()}
     * @param record  the input data
     * @return the parsed segment
     * @throws BeanException for any problem configuring and assigning fields of the input to bean properties
     */
    public S map(EventHandler<?, D, ? extends IEvent<D>> handler, int offset, String... record) throws BeanException {
        return node.map(handler, offset, record);
    }

    /**
     * Maps and validates input data to java bean depending on annotations like {@link Position}.
     * and collect errors in the given collector after full-filling the error properties.
     *
     * @param record  the input data
     * @param handler the error collector, must not be null
     * @param <R>     the row type
     * @return the parsed segment
     * @throws BeanException for any problem configuring and assigning fields of the input to bean properties
     */
    public <R extends IRecord<?>> S map(R record, EventHandler<R, D, ? extends IEvent<D>> handler) throws BeanException {
        return this.map(record, offset, handler);
    }

    /**
     * Maps and validates input data to java bean depending on annotations like {@link Position}.
     * and collect errors in the given collector after full-filling the error properties.
     *
     * @param record  the input data
     * @param offset  the number of columns reserved {@link Truncated#value()}
     * @param handler the error collector, must not be null
     * @param <R>     the row type
     * @return the parsed segment
     * @throws BeanException for any problem configuring and assigning fields of the input to bean properties
     */
    public <R extends IRecord<?>> S map(R record, int offset, EventHandler<R, D, ? extends IEvent<D>> handler) throws BeanException {
        return node.map(record, offset, true, handler);
    }

    /**
     * Converts the current mapper to formatter that is able to map bean-segment to flat-data.
     * This method is faster then {@link Up2Factory#format(Class, DataTypeResolver)} when the bean is already scanned.
     *
     * @return preconfigured CSV Format for the same segment
     * @throws BeanException if any property is not accessible for reading
     */
    public Up2Format<S, D> toFormat() throws BeanException {
        return new Up2Format<>(new BFNode<>(node));
    }

}
