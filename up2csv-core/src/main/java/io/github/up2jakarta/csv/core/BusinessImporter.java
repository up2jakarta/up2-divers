package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.core.hdl.EventCollector;
import io.github.up2jakarta.csv.data.*;
import io.github.up2jakarta.xml.clv.CodeListException;

import java.util.*;
import java.util.function.Consumer;

import static io.github.up2jakarta.csv.data.DataType.isValid;
import static io.github.up2jakarta.csv.data.DataType.message;
import static java.util.Arrays.copyOfRange;
import static java.util.Collections.unmodifiableList;

/**
 * Base Processor that's able to aggregate and import java-bean from flat-data.
 *
 * @param <T> the business object type
 * @param <B> the input data type
 * @param <I> the input type
 * @param <R> the input record type
 * @param <E> the input error type
 * @see FastImporter
 * @see FullImporter
 * @see UnitImporter
 */
public abstract sealed class BusinessImporter<B extends DataType<B>, I extends IType<B, I>, T extends Referencable, R extends IRecord<I>, E extends IEvent<B>>
        extends BSOperator<B, I, Up2Mapper<Segment, B>, Up2Format<Segment, B>>
        permits UnitImporter, FastImporter, FullImporter {

    protected final BusinessTyping typing;

    BusinessImporter(Up2Factory<B> factory, ModeType mode, Class<T> type, I rootNode, I[] nodes) throws BeanException {
        super(factory, type, mode, rootNode, nodes);
        this.typing = new BusinessTyping(List.of(nodes));
    }

    BusinessImporter(BusinessExporter<B, I, T> exporter) throws BeanException {
        super(exporter);
        this.typing = new BusinessTyping(super.nodes);
    }

    private void link(Entry<I, R, B, E> parent, List<Entry<I, R, B, E>> nodes) throws BeanException {
        for (final I type : this.getJoins(parent.type)) {
            // Finding Children
            final List<Entry<I, R, B, E>> children = new LinkedList<>();
            for (final Entry<I, R, B, E> node : nodes) {
                if (node.link(parent, type, this::testPivot)) {
                    children.add(node);
                }
            }
            // Validating Cardinality
            if (!isValid(type.getBusinessType(), children.size())) {
                final String msg = message(type.getBusinessType());
                if (children.isEmpty()) {
                    parent.handle(type, 0, msg);
                } else {
                    children.forEach(e -> e.handle(type, mode.typeIdIndex, msg));
                }
            }
            // Linking Children
            for (final Entry<I, R, B, E> child : children) {
                nodes.remove(child);
                type.getJoinLinker().link(parent.bean(), child.bean());
                if (this.hasJoins(type)) {
                    this.link(child, nodes);
                }
            }
        }
    }

    private List<Entry<I, R, B, E>> map(Collection<R> rows, Consumer<Entry<I, R, B, E>> root, Consumer<Entry<I, R, B, E>> node) throws BeanException {
        final List<Entry<I, R, B, E>> result = new ArrayList<>(rows.size());
        for (final R row : rows) {
            final I type = row.getType();
            final boolean isNode = this.root != type;
            final Up2Mapper<Segment, B> mapper = this.get(type);
            final EventCollector<R, B, E, ?> handler = this.create(row);
            final Segment bean;
            if (mapper != null) {
                bean = mapper.node.map(row, (isNode) ? mode.length : offset, isNode, handler);
            } else {
                bean = null;
            }
            final Entry<I, R, B, E> record = new Entry<>(mapper, type, bean, handler);
            result.add(record);
            if (isNode) {
                node.accept(record);
            } else {
                root.accept(record);
            }
        }
        return unmodifiableList(result);
    }

    @Override
    final Up2Mapper<Segment, B> build(Up2Factory<B> factory, I type, Up2Format<Segment, B> source) throws BeanException {
        if (source != null) {
            return source.toMapper();
        }
        return factory.build(type.getClassType(), factory.resolver.or(type.getBusinessType()));
    }

    @Override
    final void check(String name, Up2Mapper<Segment, B> child, Up2Mapper<Segment, B> parent) throws BeanException {
        if (!child.parentId.exists()) {
            return;
        }
        if (!parent.businessId.exists()) {
            final Class<?> type = parent.type;
            throw new BeanException(type, "must have one property annotated by @BusinessId to link with #[" + name + ']');
        }
        child.parentId.check(child.type, parent.businessId);
    }

    /**
     * Parses, validates and aggregates the given records.
     *
     * @param records the collection of segments
     * @param creator the custom result creator
     * @return the custom result created by the given <code>creator</code>
     * @throws BeanException for any problem when setting fields from input record
     */
    public final <C> C parse(Collection<R> records, BusinessCreator<C, T, E> creator) throws BeanException {
        if (records == null) {
            return null;
        } else if (records.isEmpty()) {
            return creator.apply(null, List.of());
        }
        final List<Entry<I, R, B, E>> roots = new ArrayList<>(1);
        final List<Entry<I, R, B, E>> nodes = new ArrayList<>(records.size() - 1);
        final List<Entry<I, R, B, E>> store = this.map(records, roots::add, nodes::add);
        final T invoice = switch (roots.size()) {
            case 1:
                final Entry<I, R, B, E> root = roots.getFirst();
                root.validate(this.offset, this::reference);
                this.link(root, nodes);
                nodes.forEach(r -> r.handle(r.type, 0, DataType.DETACHED));
                yield root.bean();
            case 0:
                nodes.forEach(r -> r.handle(r.type, 0, DataType.DETACHED));
                yield null;
            default:
                roots.forEach(r -> r.handle(this.root, mode.typeIdIndex, message(this.root.getBusinessType())));
                yield null;
        };
        final List<E> errors = new LinkedList<>();
        store.forEach(r -> r.collect(errors));
        return creator.apply(invoice, errors);
    }

    /**
     * Parses, validates and aggregates the given records.
     *
     * @param records the array of segments
     * @param creator the custom result creator
     * @return the custom result created by the given <code>creator</code>
     * @throws BeanException for any problem when setting fields from input record
     */
    public final <C> C parse(R[] records, BusinessCreator<C, T, E> creator) throws BeanException {
        if (records == null) {
            return null;
        }
        return this.parse(Arrays.asList(records), creator);
    }

    /**
     * Parses, validates and aggregates the given records.
     *
     * @param records the array of segments
     * @return the business-object with collected errors
     * @throws BeanException for any problem when setting fields from input record
     */
    public final Up2Result<T, E> parse(R[] records) throws BeanException {
        return this.parse(records, Up2Result::new);
    }

    /**
     * Parses, validates and aggregates the given records.
     *
     * @param records the collection of segments
     * @return the business-object with collected errors
     * @throws BeanException for any problem when setting fields from input record
     */
    public final Up2Result<T, E> parse(Collection<R> records) throws BeanException {
        return this.parse(records, Up2Result::new);
    }

    /**
     * Creates and returns new error-collector for the given record.
     *
     * @param record the input record
     * @return new instance error-collector, must not be <code>null</code>
     */
    protected abstract EventCollector<R, B, E, ?> create(R record);

    abstract void reference(BusinessObject bean, R record);

    abstract boolean testPivot(R root, R record);

    public class BusinessTyping {

        private final Up2ListParser<I> parser;

        private BusinessTyping(List<I> nodes) {
            //noinspection unchecked
            this.parser = new Up2ListParser<>((Class<I>) root.getClass(), nodes);
        }

        /**
         * Parses and returns the segment type aka the discriminator.
         *
         * @param record the input record
         * @return the input segment-type
         * @throws CodeListException if unknown value
         */
        public I type(String... record) throws CodeListException {
            return parser.parse(record[mode.typeIdIndex]);
        }

        /**
         * Truncates and return the data columns aka without meta-data.
         *
         * @param type   the input segment-type
         * @param record the input record
         * @return the truncated data
         */
        public String[] truncate(I type, String... record) {
            final int ro = (type == root) ? offset : mode.length;
            return copyOfRange(record, ro, record.length, String[].class);
        }
    }

}
