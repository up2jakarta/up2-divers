package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.cfg.Truncated;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Segment;
import jakarta.validation.Validator;

import java.util.List;

/**
 * Map and validate input data to a configurable bean that supports only {@link String} type.
 *
 * @param <S> the segment type
 */
public final class Up2Format<S extends Segment, D extends DataType<D>> extends BeanValidator<S, D> {

    Up2Format(Class<S> type, Node<S, D> node) throws BeanException {
        super(type, node);
    }

    /**
     * Computes and returns the header record from the data types, depending on {@link Up2Factory} resolver.
     *
     * @return the header record
     * @see io.github.up2jakarta.csv.data.DataTypeResolver
     * @see io.github.up2jakarta.csv.cfg.Definition
     */
    public String[] header() {
        final String[] result = new String[length + offset];
        if (this.length != 0) {
            ((Node<?, D>) this.node).header(result, offset);
        }
        return result;
    }

    /**
     * Flat-Map the given to segment to CSV record within formatting.
     *
     * @param bean the java bean
     * @return the formatted array of strings
     * @throws BeanException for any problem configuring and reading fields of the input to bean properties
     */
    public String[] unmap(S bean) throws BeanException {
        return this.unmap(bean, offset);
    }

    /**
     * Flat-Map the given to segment to CSV record within formatting.
     *
     * @param bean   the java bean
     * @param offset the number of columns reserved {@link Truncated#value()}
     * @return the formatted array of strings
     * @throws BeanException for any problem configuring and reading fields of the input to bean properties
     */
    public String[] unmap(S bean, int offset) throws BeanException {
        if (bean == null) {
            return null;
        }
        final String[] result = new String[offset + length];
        if (length != 0) {
            ((Node<S, D>) node).format(result, offset, bean);
        }
        return result;
    }

    public Up2Mapper<S, D> toMapper() throws BeanException {
        final Up2Mapper.Node<S, D> copy = new Up2Mapper.Node<>(type, (Node<S, D>) node);
        return new Up2Mapper<>(type, copy);
    }

    static final class Node<S extends Segment, D extends DataType<D>> extends BeanValidator.Node<S, D> {

        Node(Validator validator, BeanContext context, boolean nil, List<Property<?, D>> ps) {
            super(validator, context, nil, ps);
        }

        Node(Up2Mapper.Node<S, D> source) throws BeanException {
            super(source);
        }

        private void header(String[] header, int offset) {
            for (final Property<?, D> p : this.toList()) {
                if (p instanceof PFProperty<?, ?> fp) {
                    //noinspection unchecked
                    final PFProperty<?, D> fragment = (PFProperty<?, D>) fp;
                    ((Node<?, D>) fragment.node).header(header, offset);
                } else if (p.dataType != null) {
                    header[offset + p.offset] = p.dataType.getName();
                }
            }
        }

        private void format(String[] result, int offset, Segment bean) throws BeanException {
            for (final Property<?, D> p : this.toList()) {
                if (p instanceof PFProperty<?, ?> fp) {
                    //noinspection unchecked
                    final PFProperty<?, D> fragment = (PFProperty<?, D>) fp;
                    final Segment value = fragment.getValue(bean);
                    ((Node<?, D>) fragment.node).format(result, offset, value);
                } else {
                    //noinspection unchecked
                    final PProperty<Object, D> pp = (PProperty<Object, D>) p;
                    var value = pp.getValue(bean);
                    result[offset + p.offset] = pp.format(value);
                }
            }
        }
    }

}
