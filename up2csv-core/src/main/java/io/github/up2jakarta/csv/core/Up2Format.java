package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.cfg.Truncated;
import io.github.up2jakarta.csv.core.BSNode.BFNode;
import io.github.up2jakarta.csv.core.BSOperator.Processor;
import io.github.up2jakarta.csv.core.hdl.EventHandler;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.DataTypeResolver;
import io.github.up2jakarta.csv.data.Segment;

import static io.github.up2jakarta.csv.core.BSBuilder.reverse;

/**
 * Map and validate input data to a configurable bean that supports only {@link String} type.
 * The
 *
 * @param <S> the segment type
 * @param <D> The input data type
 */
public final class Up2Format<S extends Segment, D extends DataType<D>> extends Processor<S, D, BFNode<S, D>> {

    Up2Format(BFNode<S, D> node) throws BeanException {
        super(node);
    }

    /**
     * Computes and returns the header record from the data types, depending on {@link Up2Factory} resolver.
     *
     * @return the header record
     * @see io.github.up2jakarta.csv.data.DataTypeResolver
     * @see io.github.up2jakarta.csv.data.Definition
     */
    public String[] header() {
        return this.header(offset);
    }

    /**
     * Computes and returns the header record from the data types, depending on {@link Up2Factory} resolver.
     *
     * @param offset the number of columns reserved {@link Truncated#value()}
     * @return the header record
     * @see io.github.up2jakarta.csv.data.DataTypeResolver
     * @see io.github.up2jakarta.csv.data.Definition
     */
    public String[] header(int offset) {
        final String[] result = new String[length + offset];
        if (this.length != 0) {
            this.node.header(result, offset);
        }
        return result;
    }

    /**
     * Flat-Map the given to segment to CSV record within formatting.
     *
     * @param segment the bean that is being mapped to flat-data
     * @return the formatted array of strings
     * @throws BeanException for any problem configuring and reading fields of the input to bean properties
     */
    public String[] unmap(S segment) throws BeanException {
        return this.unmap(segment, offset);
    }

    /**
     * Flat-Map the given to segment to CSV record within formatting.
     *
     * @param segment the bean that is being mapped to flat-data
     * @param offset  the number of columns reserved {@link Truncated#value()}
     * @return the formatted array of strings
     * @throws BeanException for any problem configuring and reading fields of the input to bean properties
     */
    public String[] unmap(S segment, int offset) throws BeanException {
        if (segment == null) {
            return null;
        }
        final String[] result = new String[offset + length];
        if (length != 0) {
            node.format(result, offset, segment);
        }
        return result;
    }

    /**
     * Validates the given bean with the given JSR-303 validation groups and gathering
     * {@link jakarta.validation.ConstraintViolation} in the given handler with recursive validation of embeddable fragments.
     *
     * @param segment the bean that is being validated
     * @param offset  the number of columns reserved {@link Truncated#value()}
     * @param handler the event handler
     */
    public void validate(S segment, int offset, EventHandler<?, D, ? extends IEvent<D>> handler) throws BeanException {
        node.validate(handler, segment, offset);
    }

    /**
     * Validates the given bean with the given JSR-303 validation groups and gathering
     * {@link jakarta.validation.ConstraintViolation} in the given handler with recursive validation of embeddable fragments.
     *
     * @param segment the bean that is being validated
     * @param handler the event handler
     */
    public void validate(S segment, EventHandler<?, D, ? extends IEvent<D>> handler) throws BeanException {
        this.validate(segment, this.offset, handler);
    }

    /**
     * Converts the current formatter to mapper that is able to map bean-segment to flat-data.
     * This method is faster then {@link Up2Factory#build(Class, DataTypeResolver)} when the bean is already scanned.
     *
     * @return preconfigured CSV Mapper for the same segment
     * @throws BeanException if any property is not accessible for writing
     */
    public Up2Mapper<S, D> toMapper() throws BeanException {
        return new Up2Mapper<>(reverse(node));
    }

}
