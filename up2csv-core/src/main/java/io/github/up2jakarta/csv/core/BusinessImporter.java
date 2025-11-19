package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.api.hdl.IEventBuilder;
import io.github.up2jakarta.csv.core.BSOperator.Computer.BSFormat;
import io.github.up2jakarta.csv.core.BSOperator.Computer.BSMapper;
import io.github.up2jakarta.csv.core.hdl.BusinessHandler;
import io.github.up2jakarta.csv.data.*;
import io.github.up2jakarta.lov.CodeListException;
import io.github.up2jakarta.lov.core.AccessException;
import io.github.up2jakarta.lov.core.BeanException;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static io.github.up2jakarta.csv.api.IEvent.EC_COMPLIANCE;
import static io.github.up2jakarta.csv.core.AccessMode.WO;
import static io.github.up2jakarta.csv.data.DataType.*;
import static io.github.up2jakarta.lov.SeverityType.ERROR;
import static io.github.up2jakarta.lov.core.AccessException.notNull;
import static java.util.Arrays.copyOfRange;
import static java.util.Set.of;

/**
 * Base Processor that's able to aggregate and import java-bean from flat-data.
 *
 * @param <T> the business object type
 * @param <B> the business data type
 * @param <I> the input segment type
 * @param <R> the input record type
 * @param <E> the event type
 * @see FastImporter
 * @see FullImporter
 * @see UnitImporter
 */
public abstract sealed class BusinessImporter<B extends DataType<B>, I extends IType<B, I>, T extends Segment, R extends IRecord<I>, E extends IEvent<B>>
        extends BSOperator<B, I, BSMapper<Segment, B>, BSFormat<Segment, B>>
        permits UnitImporter, FastImporter, FullImporter {

    protected final BusinessTyping typing;
    private final boolean withBusinessId;

    BusinessImporter(Up2Factory<B> factory, ModeType mode, Class<T> type, I root, List<I> nodes) throws BeanException {
        super(factory, type, mode, root, nodes);
        this.typing = new BusinessTyping(nodes);
        this.withBusinessId = this.check(root);
    }

    BusinessImporter(BusinessExporter<B, I, T> exporter) throws BeanException {
        super(exporter);
        this.typing = new BusinessTyping(nodes);
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

    private void link(Entry<?> parent, List<Entry<?>> nodes) {
        for (final I type : joins.getOrDefault(parent.type, of())) {
            // Finding Children
            final List<Entry<?>> children = new LinkedList<>();
            for (final Entry<?> node : nodes) {
                if (node.link(parent, type, this::testPivot)) {
                    children.add(node);
                }
            }
            // Validating Cardinality
            final B data = type.getDataType();
            if (!isValid(data, children.size())) {
                if (children.isEmpty()) {
                    parent.handler.handle(type, message(data));
                } else {
                    children.forEach(e -> e.handle(message(data)));
                }
            }
            // Linking Children
            for (final Entry<?> child : children) {
                child.safe(() -> {
                    type.link(parent.bean, child.bean);
                    nodes.remove(child);
                }, () -> "cannot link with " + parent);
                if (joins.containsKey(type)) {
                    this.link(child, nodes);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private Listable<E> map(Collection<R> records, Consumer<Entry<T>> root, Consumer<Entry<?>> node) {
        final IEventBuilder<B, R, E> builder = notNull(this.newBuilder(records.size()), this.getClass(), "newBuilder");
        for (final R record : records) {
            if (record == null) {
                continue;
            }
            final BusinessHandler<B> handler = notNull(builder.of(record), builder.getClass(), "of");
            final I type = record.getType();
            if (type == null) {
                handler.handle(ERROR, EC_COMPLIANCE, null, mode.typeIdIndex, "must not be null");
                continue;
            }
            final BSMapper<Segment, B> mapper = mappers.get(type);
            if (mapper == null) {
                handler.handle(type, DETACHED);
                continue;
            }
            if (this.root != type) {
                node.accept(new Entry<>(mapper, handler, type, record, true));
            } else {
                root.accept((Entry<T>) new Entry<>(mapper, handler, type, record, false));
            }
        }
        return builder;
    }

    @Override
    final BSMapper<Segment, B> build(Up2Factory<B> factory, I type, BSFormat<Segment, B> source) throws BeanException {
        if (source != null) {
            return new BSMapper<>(source.node.reverse());
        }
        return factory.build(factory.resolver.or(type.getDataType()), type.getClassType());
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
        final List<Entry<T>> roots = new ArrayList<>(1);
        final List<Entry<?>> nodes = new ArrayList<>(records.size() - 1);
        final Listable<E> store = this.map(records, roots::add, nodes::add);
        final T invoice = switch (roots.size()) {
            case 1:
                final Entry<T> root = roots.getFirst();
                root.validate(this.offset, this.withBusinessId, this::nullPivot);
                this.link(root, nodes);
                nodes.forEach(n -> n.handle(DETACHED));
                yield root.bean;
            case 0:
                nodes.forEach(n -> n.handle(DETACHED));
                yield null;
            default:
                final String msg = message(this.root.getDataType());
                roots.forEach(r -> r.handle(msg));
                yield null;
        };
        return creator.apply(invoice, store.toList());
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
     * Creates and returns a new valid event-builder for the specified record's length.
     *
     * @param length the length of records, it's helpful for collection size initializing.
     * @return a new business event-builder, must not be <code>null</code>
     */
    protected abstract IEventBuilder<B, R, E> newBuilder(int length);

    abstract Object nullPivot(T bean, R record);

    abstract boolean testPivot(R root, R record);

    /**
     * Internal business entry.
     */
    final class Entry<S extends Segment> {
        private final I type;
        private final S bean;
        private final R source;
        private final BSMapper<S, B> mapper;
        private final BusinessHandler<B> handler;
        private final boolean hasParentId;
        private final Object parentId;
        private Object businessId;

        private Entry(BSMapper<S, B> mapper, BusinessHandler<B> handler, I type, R record, boolean validate) {
            this.type = type;
            this.source = record;
            this.mapper = mapper;
            this.handler = handler;
            this.hasParentId = mapper.hasParentId;
            this.bean = mapper.map(record, (validate) ? mode.length : offset, validate, handler);
            this.businessId = this.id(mapper.businessId, mapper.hasBusinessId, "business");
            this.parentId = this.id(mapper.parentId, hasParentId, "parent");
        }

        void safe(Operation fn, Supplier<String> message) {
            try {
                fn.apply();
            } catch (RuntimeException cause) {
                handler.handle(type, message.get(), cause);
            }
        }

        <V> V safe(Supplier<V> fn, Supplier<String> message) {
            try {
                return fn.get();
            } catch (RuntimeException cause) {
                handler.handle(type, message.get(), cause);
                return null;
            }
        }

        private Object id(BAccessor<Segment, Object> id, boolean sp, String cn) {
            if (sp) {
                return safe(() -> id.get(bean), () -> "cannot retrieve the " + cn + " identifier");
            }
            return null;
        }

        private void validate(int offset, boolean writable, BiFunction<S, R, Object> setter) {
            if (writable && this.businessId == null) {
                this.businessId = safe(() -> setter.apply(bean, source), () -> "cannot update the business identifier");
            }
            mapper.node.validate(bean, offset, handler);
        }

        private boolean link(Entry<?> parent, IType<B, I> expected, BiPredicate<R, R> filter) {
            if (expected != this.type || !filter.test(source, parent.source)) {
                return false;
            }
            if (hasParentId) {
                return Objects.equals(parentId, parent.businessId);
            }
            return true;
        }

        private void handle(String message) {
            handler.handle(type, message);
        }

        @Override
        public String toString() {
            return "Segment#[" + type.getCode() + ']';
        }

        @FunctionalInterface
        interface Operation {
            void apply() throws RuntimeException;
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
