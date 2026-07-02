package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.core.BSLink.RId;
import io.github.up2jakarta.csv.core.BSLink.VId;
import io.github.up2jakarta.csv.core.BSOperator.Format;
import io.github.up2jakarta.csv.core.BSOperator.Mapper;
import io.github.up2jakarta.csv.core.hdl.BusinessHandler;
import io.github.up2jakarta.csv.core.hdl.SimpleCollector;
import io.github.up2jakarta.csv.data.ITerm;
import io.github.up2jakarta.csv.data.SegmentWriter;
import io.github.up2jakarta.lov.core.AccessException;
import io.github.up2jakarta.lov.core.BeanException;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

import static io.github.up2jakarta.csv.core.ModeType.FULL;
import static io.github.up2jakarta.csv.core.ModeType.UNIT;
import static io.github.up2jakarta.lov.core.Beans.cast;

/**
 * Up2J Base Processor that's able to segregate and export java-bean to flat-data.
 *
 * @param <T> the business object type
 * @param <B> the business term type
 * @param <I> the input segment type
 * @see FastExporter
 * @see FullExporter
 * @see UnitExporter
 */
public abstract sealed class BusinessExporter<B extends ITerm<B>, I extends Enum<I> & IType<I>, T extends Segment>
        extends BSOperator<B, I, Format<Segment, B>, Mapper<Segment, B>>
        permits UnitExporter, FastExporter, FullExporter {

    private final boolean gettable;

    BusinessExporter(ModeType mode, Up2Factory<B> factory, Class<T> st, Class<I> it) throws BeanException {
        super(mode, factory, cast(st), it);
        this.gettable = tree.isGettable(mode);
    }

    BusinessExporter(BusinessImporter<B, I, T, ?, ?> importer) throws BeanException {
        super(importer);
        this.gettable = tree.isGettable(mode);
    }

    private void format(String[] pids, Segment bean, BSLink<B, I, Format<Segment, B>> link, Filler<I> consumer) throws IOException {
        final Format<Segment, B> format = link.computer;
        final int index = link.index + link.offset;
        final String[] data = new String[index + format.length];
        if (link.index == link.length) {
            format.node.format(data, index, bean);
        } else {
            format.format(data, index, bean, link.parentIds);
            for (final RId<B> rid : link.parentIds) {
                data[rid.offset] = pids[rid.index];
            }
        }
        if (link.index != 0) {
            var i = link.offset;
            for (final VId<B> vid : link.virtualIds) {
                data[i++] = pids[vid.index];
            }
        }
        consumer.accept(bean, link.key, data);
        final int idx = link.key.ordinal();
        if (link.computer.identifiable) {
            pids[idx] = link.computer.businessId.format(bean);
        }
        for (final BSLink<B, I, Format<Segment, B>> node : link.links) {
            final Collection<Segment> values = node.from(bean);
            if (values == null) continue;
            for (var value : values) {
                this.format(pids, value, node, consumer);
            }
        }
        pids[idx] = null;
    }

    private void validate(Segment bean, BSLink<B, I, Format<Segment, B>> link, BusinessHandler<B> handler) throws AccessException {
        final Format<Segment, B> format = link.computer;
        if (format.validate) {
            format.node.validate(format.validator, handler, bean, link.offset + link.index);
        }
        for (final BSLink<B, I, Format<Segment, B>> node : link.links) {
            final Collection<Segment> values = node.from(bean);
            if (values == null) continue;
            if (node.notValid(values.size())) {
                handler.handle(node.event, link.term, link.message());
            }
            for (var value : values) {
                if (value == null) {
                    handler.handle(node.event, node.term, "must not be null");
                } else {
                    this.validate(value, node, handler);
                }
            }
        }
    }

    protected void format(T bean, Supplier<String> recordId, SegmentWriter callback) throws IOException {
        if (bean == null) return;
        final String reference = (gettable) ? tree.computer.businessId.format(bean) : null;
        this.format(new String[length], bean, tree, (s, t, d) -> {
            this.fill(d, recordId, t, reference);
            callback.accept(d);
        });
    }

    /**
     * Validates the given bean and recursively its embeddable segments and gathering events in the specified handler.
     *
     * @param bean    the business-object that is being validated
     * @param handler the event handler
     */
    public void validate(T bean, BusinessHandler<B> handler) throws AccessException {
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
    public List<? extends IEvent<B>> validate(T bean) throws AccessException {
        final SimpleCollector<B> collector = new SimpleCollector<>();
        this.validate(bean, collector);
        return collector.toList();
    }

    @Override
    final Format<Segment, B> build(Up2Factory<B> factory, Class<Segment> type, Mapper<Segment, B> source) throws BeanException {
        if (source != null) {
            return source.reverse();
        }
        return factory.ft(type);
    }

    abstract void fill(String[] target, Supplier<String> recordId, I type, String reference);

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
        final List<Spec<I, B>> result = new LinkedList<>();
        final ITerm<?> bid = (tree.computer.businessId instanceof PId<?> w) ? w.property.dataType : null;
        tree.visit(1, (d, n) -> {
            final String[][] data = n.computer.specs(n.index + n.offset + 1);
            data[0][0] = offset;
            data[1][0] = code;
            data[2][0] = name;
            data[3][0] = defaultValue;
            var i = mode.getTypeIdIndex() + 1;
            data[0][i] = String.valueOf(i);
            data[2][i] = "#Segment";
            if (mode == UNIT ^ n.offset == mode.length) {
                i = mode.getBeanIdIndex() + 1;
                data[0][i] = String.valueOf(i);
                if (bid != null) {
                    data[1][i] = bid.getCode();
                    data[2][i] = bid.getName();
                }
            }
            if (mode == FULL) {
                data[0][1] = "1";
                data[2][1] = "#Record";
            }
            i = n.offset + 1;
            for (final VId<B> vid : n.virtualIds) {
                data[0][i] = String.valueOf(i);
                data[1][i] = vid.getCode();
                data[2][i++] = vid.getName();
            }
            result.add(new Spec<>(n, d, data));
        });
        return result;
    }

    @FunctionalInterface
    private interface Filler<I extends Enum<I> & IType<I>> {
        void accept(Segment source, I type, String[] data) throws IOException;
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

}
