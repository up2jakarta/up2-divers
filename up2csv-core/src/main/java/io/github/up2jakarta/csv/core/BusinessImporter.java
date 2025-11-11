package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.core.BSOperator.Computer.BSFormat;
import io.github.up2jakarta.csv.core.BSOperator.Computer.BSMapper;
import io.github.up2jakarta.csv.core.hdl.BusinessHandler;
import io.github.up2jakarta.csv.data.*;
import io.github.up2jakarta.xml.clv.CodeListException;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

import static io.github.up2jakarta.csv.api.IEvent.ERROR_VALIDATOR;
import static io.github.up2jakarta.csv.core.AccessMode.WO;
import static io.github.up2jakarta.csv.data.DataType.isValid;
import static io.github.up2jakarta.csv.data.DataType.message;
import static io.github.up2jakarta.xml.api.SeverityType.ERROR;
import static java.util.Arrays.copyOfRange;
import static java.util.Collections.unmodifiableList;
import static java.util.Set.of;

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
public abstract sealed class BusinessImporter<B extends DataType<B>, I extends IType<B, I>, T extends Segment, R extends IRecord<I>, E extends IEvent<B>>
        extends BSOperator<B, I, BSMapper<Segment, B>, BSFormat<Segment, B>>
        permits UnitImporter, FastImporter, FullImporter {

    protected final BusinessTyping typing;
    private final boolean withBusinessId;

    BusinessImporter(Up2Factory<B> factory, ModeType mode, Class<T> type, I rootNode, I[] nodes) throws BeanException {
        super(factory, type, mode, rootNode, nodes);
        this.typing = new BusinessTyping(List.of(nodes));
        this.withBusinessId = this.check(root);
    }

    BusinessImporter(BusinessExporter<B, I, T> exporter) throws BeanException {
        super(exporter);
        this.typing = new BusinessTyping(super.nodes);
        this.withBusinessId = this.check(root);
    }

    private boolean check(I parent) throws BeanException {
        final BSMapper<Segment, B> pm = mappers.get(parent);
        for (final I child : joins.getOrDefault(parent, of())) {
            final BSMapper<Segment, B> cm = mappers.get(child);
            if (cm.hasParentId) {
                if (!pm.hasBusinessId) {
                    final Class<?> type = parent.getClassType();
                    throw new BeanException(type, child.getName(), "must have one property annotated by @BusinessId");
                }
                cm.parentId.check(child.getClassType(), pm.businessId);
            }
            this.check(child);
        }
        return pm.businessId.supports(WO);
    }

    private void link(Entry<?, I, R, B, E> parent, List<Entry<?, I, R, B, E>> nodes) {
        for (final I type : joins.getOrDefault(parent.type, of())) {
            // Finding Children
            final List<Entry<?, I, R, B, E>> children = new LinkedList<>();
            for (final Entry<?, I, R, B, E> node : nodes) {
                if (node.link(parent, type, this::testPivot)) {
                    children.add(node);
                }
            }
            // Validating Cardinality
            if (!isValid(type.getBusinessType(), children.size())) {
                final String msg = message(type.getBusinessType());
                if (children.isEmpty()) {
                    parent.handle(type, msg);
                } else {
                    children.forEach(e -> e.handle(type, msg));
                }
            }
            // Linking Children
            for (final Entry<?, I, R, B, E> child : children) {
                nodes.remove(child);
                type.getJoinLinker().link(parent.bean, child.bean);
                if (joins.containsKey(type)) {
                    this.link(child, nodes);
                }
            }
        }
    }

    private Entry<?, I, R, B, E> entry(I type, R row, boolean isNode) {
        final BusinessHandler<R, B, E, ?> handler = this.create(row);
        final BSMapper<Segment, B> mapper = mappers.get(type);
        if (mapper != null) {
            final Segment bean = mapper.map(row, (isNode) ? mode.length : offset, isNode, handler);
            final boolean hasParentId = mapper.hasParentId;
            final Object parentId = (hasParentId) ? mapper.parentId.get(bean) : null;
            final Object businessId = (mapper.hasBusinessId) ? mapper.businessId.get(bean) : null;
            return new Entry<>(mapper, type, bean, handler, hasParentId, parentId, businessId);
        }
        return new Entry<>(null, type, null, handler, false, null, null);
    }

    @SuppressWarnings("unchecked")
    private List<Entry<?, I, R, B, E>> map(Collection<R> rows, Consumer<Entry<T, I, R, B, E>> root, Consumer<Entry<?, I, R, B, E>> node) {
        final List<Entry<?, I, R, B, E>> result = new ArrayList<>(rows.size());
        for (final R row : rows) {
            final I type = row.getType();
            final boolean isNode = this.root != type;
            final Entry<?, I, R, B, E> entry = this.entry(type, row, isNode);
            result.add(entry);
            if (isNode) {
                node.accept(entry);
            } else {
                root.accept((Entry<T, I, R, B, E>) entry);
            }
        }
        return unmodifiableList(result);
    }

    @Override
    final BSMapper<Segment, B> build(Up2Factory<B> factory, I type, BSFormat<Segment, B> source) throws BeanException {
        if (source != null) {
            return new BSMapper<>(source.node.reverse());
        }
        return factory.build(factory.resolver.or(type.getBusinessType()), type.getClassType());
    }

    /**
     * Parses, validates and aggregates the given records.
     *
     * @param records the collection of segments
     * @param creator the custom result creator
     * @return the custom result created by the given <code>creator</code>
     * @throws AccessException for any problem when setting properties of java-beans from input record
     */
    public final <C> C parse(Collection<R> records, BusinessCreator<C, T, E> creator) throws AccessException {
        if (records == null) {
            return null;
        } else if (records.isEmpty()) {
            return creator.apply(null, List.of());
        }
        final List<Entry<T, I, R, B, E>> roots = new ArrayList<>(1);
        final List<Entry<?, I, R, B, E>> nodes = new ArrayList<>(records.size() - 1);
        final List<Entry<?, I, R, B, E>> store = this.map(records, roots::add, nodes::add);
        final T invoice = switch (roots.size()) {
            case 1:
                final Entry<T, I, R, B, E> root = roots.getFirst();
                root.validate(this.offset, this.withBusinessId, this::nullPivot);
                this.link(root, nodes);
                nodes.forEach(r -> r.handle(r.type, DataType.DETACHED));
                yield root.bean;
            case 0:
                nodes.forEach(r -> r.handle(r.type, DataType.DETACHED));
                yield null;
            default:
                roots.forEach(r -> r.handle(this.root, message(this.root.getBusinessType())));
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
     * @throws AccessException for any problem when setting properties of java-beans from input record
     */
    public final <C> C parse(R[] records, BusinessCreator<C, T, E> creator) throws AccessException {
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
     * @throws AccessException for any problem when setting properties of java-beans from input record
     */
    public final Up2Result<T, E> parse(R[] records) throws AccessException {
        return this.parse(records, Up2Result::new);
    }

    /**
     * Parses, validates and aggregates the given records.
     *
     * @param records the collection of segments
     * @return the business-object with collected errors
     * @throws AccessException for any problem when setting properties of java-beans from input record
     */
    public final Up2Result<T, E> parse(Collection<R> records) throws AccessException {
        return this.parse(records, Up2Result::new);
    }

    /**
     * Creates and returns new error-collector for the given record.
     *
     * @param record the input record
     * @return new instance error-collector, must not be <code>null</code>
     */
    protected abstract BusinessHandler<R, B, E, ?> create(R record);

    abstract Object nullPivot(T bean, R record);

    abstract boolean testPivot(R root, R record);

    /**
     * Internal business entry.
     */
    static final class Entry<S extends Segment, T extends IType<D, T>, R extends IRecord<T>, D extends DataType<D>, E extends IEvent<D>> {
        private final T type;
        private final S bean;
        private final R source;
        private final BSMapper<S, D> mapper;
        private final BusinessHandler<R, D, E, ?> handler;
        private final boolean hasParentId;
        private final Object parentId;
        private Object businessId;

        private Entry(BSMapper<S, D> m, T t, S o, BusinessHandler<R, D, E, ?> h, boolean w, Object p, Object b) {
            this.bean = o;
            this.type = t;
            this.mapper = m;
            this.handler = h;
            this.parentId = p;
            this.businessId = b;
            this.hasParentId = w;
            this.source = handler.getSource();
        }

        private void validate(int offset, boolean writable, BiFunction<S, R, Object> setter) {
            if (writable && this.businessId == null) {
                this.businessId = setter.apply(bean, source);
            }
            mapper.node.validate(bean, offset, handler);
        }

        private boolean link(Entry<?, T, R, D, E> parent, IType<D, T> expected, BiPredicate<R, R> filter) {
            if (expected != this.type || !filter.test(source, parent.source)) {
                return false;
            }
            if (hasParentId) {
                return Objects.equals(parentId, parent.businessId);
            }
            return true;
        }

        private void collect(List<E> target) {
            target.addAll(handler.toList());
        }

        private void handle(IType<D, T> type, String message) {
            if (type == null) {
                handler.handle(null, ERROR, ERROR_VALIDATOR, message);
            } else {
                handler.handle(type.getBusinessType(), type.getErrorLevel(), type.getErrorCode(), message);
            }
        }
    }

    public class BusinessTyping {
        private final Up2ListParser<I> parser;

        @SuppressWarnings("unchecked")
        private BusinessTyping(List<I> nodes) {
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
