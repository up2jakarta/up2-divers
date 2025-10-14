package io.github.up2jakarta.csv.ops;

import io.github.up2jakarta.csv.api.IError;
import io.github.up2jakarta.csv.api.IFullType;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.core.*;
import io.github.up2jakarta.csv.data.BusinessCreator;
import io.github.up2jakarta.csv.data.BusinessObject;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.csv.impl.SimpleResult;

import java.util.*;
import java.util.function.Consumer;

import static io.github.up2jakarta.csv.data.DataType.isValid;
import static io.github.up2jakarta.csv.data.DataType.message;
import static java.util.Collections.unmodifiableList;

/**
 * Internal base implementation for both aggregation and segregation processing.
 *
 * @param <T> the business object type
 * @param <B> the data type
 * @param <I> the segment type
 * @param <R> the record type
 * @param <E> the error type
 * @see FastAggregator
 * @see FullAggregator
 */
public abstract class BusinessAggregator<T extends BusinessObject, B extends DataType<B>, I extends IFullType<B, I>, R extends IRecord<I>, E extends IError<B>> extends BusinessProcessor<B, I, T> {

    BusinessAggregator(MapperFactory<B> factory, ModeType mode, Class<T> type, I rootNode, I[] nodes) throws BeanException {
        super(factory, mode, type, rootNode, nodes);
    }

    private void link(BusinessEntry<I, R, B, E> parent, List<BusinessEntry<I, R, B, E>> nodes) throws BeanException {
        for (final I type : this.getJoins(parent.getType())) {
            // Finding Children
            final List<BusinessEntry<I, R, B, E>> children = new LinkedList<>();
            for (final BusinessEntry<I, R, B, E> node : nodes) {
                if (node.isParent(parent, type)) {
                    children.add(node);
                }
            }
            // Validating Cardinality
            if (!isValid(type.getBusinessType(), children.size())) {
                final String msg = message(type.getBusinessType());
                if (children.isEmpty()) {
                    parent.handle(type, mode.beanIdIndex, msg);
                } else {
                    children.forEach(e -> e.handle(type, mode.beanIdIndex, msg));
                }
            }
            // Linking Children
            for (final BusinessEntry<I, R, B, E> child : children) {
                nodes.remove(child);
                if (this.hasJoins(type)) {
                    this.link(child, nodes);
                }
                type.linker().link(parent.getBean(), child.getBean());
            }
        }
    }

    private List<BusinessEntry<I, R, B, E>> map(Collection<R> rows, Consumer<BusinessEntry<I, R, B, E>> root, Consumer<BusinessEntry<I, R, B, E>> node) throws BeanException {
        final List<BusinessEntry<I, R, B, E>> result = new ArrayList<>(rows.size());
        for (final R row : rows) {
            final I type = row.getType();
            final int offset = typing.offset(type);
            final Mapper<Segment, B> mapper = this.getMapper(type);
            final EventCollector<R, B, E> handler = this.create(row);
            final Segment bean = mapper.map(row, offset, handler);
            final BusinessEntry<I, R, B, E> record = new BusinessEntry<>(mapper, type, bean, handler);
            result.add(record);
            if (this.root == type) {
                root.accept(record);
            } else {
                node.accept(record);
            }
        }
        return unmodifiableList(result);
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
        if (records == null || records.isEmpty()) {
            return creator.apply(null, List.of());
        }
        final List<BusinessEntry<I, R, B, E>> roots = new ArrayList<>(1);
        final List<BusinessEntry<I, R, B, E>> nodes = new ArrayList<>(records.size() - 1);
        final List<BusinessEntry<I, R, B, E>> store = this.map(records, roots::add, nodes::add);
        final T invoice = switch (roots.size()) {
            case 1:
                final BusinessEntry<I, R, B, E> root = roots.getFirst();
                this.link(root, nodes);
                final BusinessObject bean = root.getBean();
                if (bean.getReference() == null) {
                    bean.setReference(root.getSource().getBusinessReference());
                }
                nodes.forEach(r -> r.handle(r.getType(), mode.beanIdIndex, DataType.DETACHED));
                yield root.getBean();
            case 0:
                nodes.forEach(r -> r.handle(r.getType(), mode.beanIdIndex, DataType.DETACHED));
                yield null;
            default:
                roots.forEach(r -> r.handle(this.root, mode.beanIdIndex, message(this.root.getBusinessType())));
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
        if (records == null || records.length == 0) {
            return creator.apply(null, List.of());
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
    public final SimpleResult<T, E> parse(R[] records) throws BeanException {
        return this.parse(records, SimpleResult::new);
    }

    /**
     * Parses, validates and aggregates the given records.
     *
     * @param records the collection of segments
     * @return the business-object with collected errors
     * @throws BeanException for any problem when setting fields from input record
     */
    public final SimpleResult<T, E> parse(Collection<R> records) throws BeanException {
        return this.parse(records, SimpleResult::new);
    }

    /**
     * Creates and returns new error-collector for the given record.
     *
     * @param record the input record
     * @return new instance error-collector, must not be <code>null</code>
     */
    protected abstract EventCollector<R, B, E> create(R record);

}
