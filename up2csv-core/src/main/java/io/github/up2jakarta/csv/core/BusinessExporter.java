package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.ITerm;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.core.BSLink.RId;
import io.github.up2jakarta.csv.core.BSManager.Key;
import io.github.up2jakarta.csv.core.BSNode.Flat;
import io.github.up2jakarta.csv.core.BSProperty.PFragment;
import io.github.up2jakarta.csv.core.BSProperty.PPosition;
import io.github.up2jakarta.csv.core.BusinessExporter.Format;
import io.github.up2jakarta.csv.core.BusinessImporter.Mapper;
import io.github.up2jakarta.csv.data.SegmentWriter;
import io.github.up2jakarta.csv.hdl.BusinessHandler;
import io.github.up2jakarta.csv.hdl.SimpleCollector;
import io.github.up2jakarta.lov.core.AccessException;
import io.github.up2jakarta.lov.core.BeanException;
import jakarta.validation.Validator;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

import static io.github.up2jakarta.csv.core.ModeType.NEAT;
import static io.github.up2jakarta.lov.core.Beans.cast;

/**
 * Up2J Business Processor that's able to format and segregate java-bean to flat-data.
 * <p>
 * This class cannot be directly inherited, uses {@link NeatExporter} for ordered input or else {@link MessExporter}
 *
 * @param <T> the business object type
 * @param <B> the business term type
 * @param <I> the input segment type
 * @see BusinessImporter
 */
public abstract sealed class BusinessExporter<B extends ITerm<B>, I extends Enum<I> & IType<I>, T extends Segment>
        extends BSOperator<B, I, Format<Segment, B>, Mapper<Segment, B>>
        permits NeatExporter, MessExporter {

    BusinessExporter(IMode mode, Up2Factory<B> factory, Class<T> st, Class<I> it) throws BeanException {
        super(mode, factory, cast(st), it);
        BeanChecker.check(mode, tree.computer);
    }

    BusinessExporter(BusinessImporter<B, I, T, ?, ?> importer) throws BeanException {
        super(importer);
        BeanChecker.check(mode, tree.computer);
    }

    /**
     * Validates the given bean and recursively its embeddable segments and gathering events in the specified handler.
     *
     * @param bean    the business-object that is being validated
     * @param handler the event handler
     */
    public final void validate(T bean, BusinessHandler<B> handler) throws AccessException {
        if (bean != null) {
            this.validate(bean, tree, handler);
        }
    }

    /**
     * Validates the given bean and recursively its embeddable segments and returns the collected events.
     *
     * @param bean the business-object that is being validated
     * @return the list of collected events
     */
    public final List<? extends IEvent<B>> validate(T bean) throws AccessException {
        final SimpleCollector<B> collector = new SimpleCollector<>();
        this.validate(bean, collector);
        return collector.toList();
    }

    /**
     * Computes and returns the list of specifications for each segment in the current business-object.
     *
     * @param offset       the title of offset column
     * @param code         the title of code column
     * @param name         the title of name column
     * @param defaultValue the title of default-value column
     * @return the list of specifications
     */
    public final List<Spec<I, B>> specs(String offset, String code, String name, String defaultValue) {
        final ITerm<?> bid = (tree.computer.businessId instanceof PId<?> w) ? w.property.dataType : null;
        final List<Spec<I, B>> result = new LinkedList<>();
        final int index = mode.getIndex();
        tree.visit(1, (d, n) -> {
            final String[][] spec = n.computer.specs(n.index + n.offset + 1);
            spec[0][0] = offset;
            spec[1][0] = code;
            spec[2][0] = name;
            spec[3][0] = defaultValue;
            var i = mode.getIndex() + 1;
            spec[0][i] = String.valueOf(i);
            spec[2][i] = "#Segment";
            if (mode != NEAT && n.offset == mode.getLength()) {
                i = mode.getOffset() + 1;
                spec[0][i] = String.valueOf(i);
                if (bid != null) {
                    spec[1][i] = bid.getCode();
                    spec[2][i] = bid.getName();
                }
            }
            for (i = 1; i <= index; i++) {
                spec[0][i] = String.valueOf(i);
                spec[2][i] = this.spec(i - 1);
            }
            i = n.offset + 1;
            for (final BSLink.VId<B> vid : n.virtualIds) {
                spec[0][i] = String.valueOf(i);
                spec[1][i] = vid.getCode();
                spec[2][i++] = vid.getName();
            }
            result.add(new Spec<>(n, d, spec));
        });
        return result;
    }

    /**
     * Segregates the given business-object to many records and notifies the callback for each one.
     *
     * @param bean     the business object to segregate
     * @param callback the segment writer
     * @param <X>      the type of exception can be thrown by writer
     * @throws X               if the segment writer throws {@link X}
     * @throws AccessException for any problem when getting properties from the specified business-object
     */
    public abstract <X extends Exception> void format(T bean, SegmentWriter<X> callback) throws X, AccessException;

    abstract String spec(int index);

    @Override
    final Format<Segment, B> build(Up2Factory<B> f, Class<Segment> type, Mapper<Segment, B> src) throws BeanException {
        if (src != null) {
            return src.reverse(factory.validator);
        }
        return f.ft(type);
    }

    private void validate(Segment s, BSLink<B, I, Format<Segment, B>> l, BusinessHandler<B> h) throws AccessException {
        final Format<Segment, B> format = l.computer;
        if (format.validate) {
            format.node.validate(factory.validator, h, s, l.offset + l.index);
        }
        for (final BSLink<B, I, Format<Segment, B>> node : l.links) {
            final Collection<Segment> children = node.from(s);
            if (children == null) continue;
            final int size = children.size();
            final String message = this.validate(node, size);
            if (message != null) {
                h.handle(node.event, node.term, message);
            }
            if (size != 0) {
                for (final Segment child : children) {
                    if (child == null) {
                        h.handle(node.event, node.term, "must not be null");
                    } else {
                        this.validate(child, node, h);
                    }
                }
            }
        }
    }

    /**
     * Segment specification model, useful for business-object docs.
     *
     * @param <I> the input segment type
     * @param <D> the business term type
     */
    public static final class Spec<I extends Enum<I> & IType<I>, D extends ITerm<D>> implements Supplier<String[][]> {
        private final Class<? extends Segment> type;
        private final int min, max, depth;
        private final String[][] data;
        private final D term;
        private final I key;

        private Spec(BSLink<D, I, ?> node, int depth, String[][] data) {
            this.type = node.computer.node.type;
            this.term = node.term;
            this.key = node.key;
            this.min = node.min;
            this.max = node.max;
            this.depth = depth;
            this.data = data;
        }

        /**
         * @return the related segment type
         */
        public Class<? extends Segment> type() {
            return type;
        }

        /**
         * @return the depth in the tree
         */
        public int depth() {
            return depth;
        }

        /**
         * @return the related business segment
         */
        public D term() {
            return term;
        }

        /**
         * @return the related segment code
         */
        public I key() {
            return key;
        }

        /**
         * @return the minimum of cardinality
         */
        public int min() {
            return min;
        }

        /**
         * @return the maximum of cardinality
         */
        public int max() {
            return max;
        }

        /**
         * @return the cross table
         */
        @Override
        public String[][] get() {
            return data;
        }
    }

    /**
     * Internal Business Flatter.
     */
    static final class Format<S extends Segment, D extends ITerm<D>> extends Node<S, D, Flat<S, D>> {
        Format(Validator validator, Key<D, S> key, Flat<S, D> node) throws BeanException {
            super(validator, key, node);
        }

        private void format(Flat<?, D> node, String[] result, int offset, Segment bean, List<RId<D>> rids) {
            if (bean == null) {
                node.defaultValues(result, offset);
            } else {
                for (final BSProperty<?, D> p : node.properties) {
                    if (p.getClass() == PFragment.class) {
                        //noinspection unchecked
                        final PFragment<Segment, D> fp = (PFragment<Segment, D>) p;
                        format((Flat<?, D>) fp.node, result, offset, fp.value(bean), rids);
                    } else {
                        boolean mapped = true;
                        for (final RId<D> rid : rids) {
                            if (rid.reference.property.offset == p.offset) {
                                mapped = false;
                                break;
                            }
                        }
                        if (mapped) {
                            //noinspection unchecked
                            final PPosition<Object, D> pp = (PPosition<Object, D>) p;
                            result[offset + p.offset] = pp.format(pp.value(bean));
                        }
                    }
                }
            }
        }

        void format(String[] result, int offset, Segment bean, List<RId<D>> rids) {
            this.format(this.node, result, offset, bean, rids);
        }

        String[][] specs(int offset) {
            final String[][] result = new String[4][length + offset];
            if (this.length != 0) {
                this.node.visit(p -> {
                    final int i = offset + p.offset;
                    result[0][i] = String.valueOf(i);
                    if (p.dataType != null) {
                        result[1][i] = p.dataType.getCode();
                        result[2][i] = p.dataType.getName();
                    } else {
                        result[1][i] = p.getName();
                    }
                    result[3][i] = p.format(null);
                });
            }
            return result;
        }

        Mapper<S, D> reverse(Validator validator) throws BeanException {
            return BSManager.mp(this).build(validator, Mapper::new);
        }
    }
}

