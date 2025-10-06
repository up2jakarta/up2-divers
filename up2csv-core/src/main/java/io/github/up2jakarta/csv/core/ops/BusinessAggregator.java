package io.github.up2jakarta.csv.core.ops;

import io.github.up2jakarta.csv.api.IError;
import io.github.up2jakarta.csv.api.IFullType;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.hdl.IRecordEntity;
import io.github.up2jakarta.csv.core.*;
import io.github.up2jakarta.csv.data.BusinessCreator;
import io.github.up2jakarta.csv.data.BusinessObject;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Segment;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static io.github.up2jakarta.csv.data.DataType.isValid;
import static io.github.up2jakarta.csv.data.DataType.message;
import static io.github.up2jakarta.xml.adapters.KeyCoder.fixed;
import static java.util.Collections.unmodifiableList;

public abstract class BusinessAggregator<T extends BusinessObject, B extends DataType<B>, I extends IFullType<B, I>, R extends IRecord<I>, E extends IError<B>> extends BusinessProcessor<B, I, T> {

    BusinessAggregator(MapperFactory<B> factory, BusinessType<T> type, I rootNode, I[] nodes) throws BeanException {
        super(factory, type, rootNode, nodes);
    }

    static Supplier<String> adapt(AtomicLong counter) {
        return () -> fixed(counter.incrementAndGet());
    }

    static Supplier<String> adapt(AtomicInteger counter) {
        return () -> fixed(counter.incrementAndGet());
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
                    parent.handle(type, config.getBeanIdIndex(), msg);
                } else {
                    children.forEach(e -> e.handle(type, config.getBeanIdIndex(), msg));
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

    private List<BusinessEntry<I, R, B, E>> map(R[] rows, Consumer<BusinessEntry<I, R, B, E>> root, Consumer<BusinessEntry<I, R, B, E>> node) throws BeanException {
        final List<BusinessEntry<I, R, B, E>> result = new ArrayList<>(rows.length);
        for (final R row : rows) {
            final I type = row.getType();
            final int offset = this.offset(type);
            final Mapper<Segment, B> mapper = this.getMapper(type);
            final EventCollector<R, B, E> handler = this.create(row);
            final Segment bean = mapper.map(row, offset, handler);
            final BusinessEntry<I, R, B, E> record = new BusinessEntry<>(mapper, type, bean, handler);
            result.add(record);
            if (this.rootNode == type) {
                root.accept(record);
            } else {
                node.accept(record);
            }
        }
        return unmodifiableList(result);
    }

    public final <C> C parse(R[] rows, BusinessCreator<C, T, E> result) throws BeanException {
        if (rows == null || rows.length == 0) {
            return result.apply(null, List.of());
        }
        final List<BusinessEntry<I, R, B, E>> roots = new ArrayList<>(1);
        final List<BusinessEntry<I, R, B, E>> nodes = new ArrayList<>(rows.length - 1);
        final List<BusinessEntry<I, R, B, E>> store = this.map(rows, roots::add, nodes::add);
        final T invoice = switch (roots.size()) {
            case 1:
                final BusinessEntry<I, R, B, E> root = roots.getFirst();
                this.link(root, nodes);
                if (root.getSource() instanceof IRecordEntity<?> row) {
                    root.<BusinessObject>getBean().setReference(row.getReference());
                }
                nodes.forEach(r -> r.handle(r.getType(), config.getBeanIdIndex(), DataType.DETACHED));
                yield root.getBean();
            case 0:
                nodes.forEach(r -> r.handle(r.getType(), config.getBeanIdIndex(), DataType.DETACHED));
                yield null;
            default:
                roots.forEach(r -> r.handle(rootNode, config.getBeanIdIndex(), message(rootNode.getBusinessType())));
                yield null;
        };
        final List<E> errors = new LinkedList<>();
        store.forEach(r -> r.collect(errors));
        return result.apply(invoice, errors);
    }

    protected abstract EventCollector<R, B, E> create(R row);

}
