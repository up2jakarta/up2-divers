package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.core.BSOperator.OPS.Format;
import io.github.up2jakarta.csv.core.BSOperator.OPS.Mapper;
import io.github.up2jakarta.csv.core.hdl.BusinessHandler;
import io.github.up2jakarta.csv.core.hdl.SimpleCollector;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.csv.data.SegmentWriter;
import io.github.up2jakarta.lov.core.AccessException;
import io.github.up2jakarta.lov.core.BeanException;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

import static io.github.up2jakarta.csv.core.BeanAccess.RO;
import static io.github.up2jakarta.csv.data.DataType.isValid;
import static io.github.up2jakarta.csv.data.DataType.message;
import static java.util.Set.of;

/**
 * Base Processor that's able to segregate and export java-bean to flat-data.
 *
 * @param <T> the business object type
 * @param <B> the business data type
 * @param <I> the input segment type
 * @see FastExporter
 * @see FullExporter
 * @see UnitExporter
 */
public abstract sealed class BusinessExporter<B extends DataType<B>, I extends IType<B, I>, T extends Segment>
        extends BSOperator<B, I, Format<Segment, B>, Mapper<Segment, B>>
        permits UnitExporter, FastExporter, FullExporter {

    private final boolean hasBusinessId;

    BusinessExporter(Up2Factory<B> factory, ModeType mode, Class<T> type, I root, List<I> nodes) throws BeanException {
        super(factory, type, mode, root, nodes);
        this.hasBusinessId = this.check(type);
    }

    BusinessExporter(BusinessImporter<B, I, T, ?, ?> importer) throws BeanException {
        super(importer);
        final Format<Segment, B> rootMapper = mappers.get(root);
        this.hasBusinessId = this.check(rootMapper.node.type);
    }

    private boolean check(Class<?> type) throws BeanException {
        final boolean readable = mode != ModeType.UNIT;
        if (readable && !bid.supports(RO)) {
            throw new BeanException(type, "must have one property annotated by @BusinessId or implements Referencable");
        }
        return readable;
    }

    private void format(Segment bean, int offset, I type, Filler<I> consumer) throws IOException {
        final Format<Segment, B> format = mappers.get(type);
        final String[] data = new String[offset + format.length];
        format.node.format(data, offset, bean);
        consumer.accept(bean, type, data);
        for (final I node : joins.getOrDefault(type, of())) {
            final Collection<Segment> values = node.from(bean);
            if (values == null) {
                continue;
            }
            for (var value : values) {
                this.format(value, mode.length, node, consumer);
            }
        }
    }

    private void validate(Segment bean, I type, int offset, BusinessHandler<B> handler) throws AccessException {
        if (bean == null) {
            return;
        }
        final Format<Segment, B> format = mappers.get(type);
        format.node.validate(bean, offset, handler);
        for (final I node : joins.getOrDefault(type, of())) {
            final Collection<Segment> values = node.from(bean);
            if (values == null) {
                continue;
            }
            final B data = node.getDataType();
            if (!isValid(data, values.size())) {
                handler.handle(type, message(data));
            }
            for (var value : values) {
                if (value == null) {
                    handler.handle(type, "must not be null");
                } else {
                    this.validate(value, node, mode.length, handler);
                }
            }
        }
    }

    protected void format(T bean, Supplier<String> recordId, SegmentWriter callback) throws IOException {
        if (bean == null) {
            return;
        }
        final String reference = (hasBusinessId) ? bid.format(bean) : null;
        this.format(bean, offset, root, (s, t, d) -> {
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
        this.validate(bean, root, offset, handler);
    }

    /**
     * Validates the given bean and recursively its embeddable segments and returns the collected events.
     *
     * @param bean the business-object that is being validated
     * @return the list of collected events
     */
    public List<? extends IEvent<B>> validate(T bean) throws AccessException {
        final SimpleCollector<B> collector = new SimpleCollector<>();
        this.validate(bean, root, offset, collector);
        return collector.toList();
    }

    @Override
    final Format<Segment, B> build(Up2Factory<B> factory, I type, Mapper<Segment, B> source) throws BeanException {
        if (source != null) {
            return new Format<>(source.node.reverse());
        }
        return factory.format(factory.resolver.or(type.getDataType()), type.getClassType());
    }

    abstract void fill(String[] target, Supplier<String> recordId, IType<?, ?> type, String reference);

    @FunctionalInterface
    private interface Filler<I extends IType<?, I>> {
        void accept(Segment source, I type, String[] data) throws IOException;
    }

}
