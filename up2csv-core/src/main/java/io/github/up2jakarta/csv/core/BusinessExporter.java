package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.core.BSOperator.Computer.BSFormat;
import io.github.up2jakarta.csv.core.BSOperator.Computer.BSMapper;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.csv.data.SegmentWriter;

import java.io.IOException;
import java.util.Collection;
import java.util.function.Supplier;

import static io.github.up2jakarta.csv.core.AccessMode.RO;
import static java.util.Set.of;

/**
 * Base Processor that's able to segregate and export java-bean to flat-data.
 *
 * @param <T> the business object type
 * @param <B> the input data type
 * @param <I> the input type
 * @see FastExporter
 * @see FullExporter
 * @see UnitExporter
 */
public abstract sealed class BusinessExporter<B extends DataType<B>, I extends IType<B, I>, T extends Segment>
        extends BSOperator<B, I, BSFormat<Segment, B>, BSMapper<Segment, B>>
        permits UnitExporter, FastExporter, FullExporter {

    private final boolean hasBusinessId;

    BusinessExporter(Up2Factory<B> factory, ModeType mode, Class<T> type, I root, I[] nodes) throws BeanException {
        super(factory, type, mode, root, nodes);
        this.hasBusinessId = this.check(type);
    }

    BusinessExporter(BusinessImporter<B, I, T, ?, ?> importer) throws BeanException {
        super(importer);
        final BSFormat<Segment, B> rootMapper = mappers.get(root);
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
        final BSFormat<Segment, B> format = mappers.get(type);
        final String[] data = new String[offset + format.length];
        format.node.format(data, offset, bean);
        consumer.accept(bean, type, data);
        for (final I node : joins.getOrDefault(type, of())) {
            final Collection<Segment> values = node.getJoinLinker().from(bean);
            if (values == null) {
                continue;
            }
            for (var value : values) {
                this.format(value, mode.length, node, consumer);
            }
        }
    }

    protected void format(T bean, Supplier<String> rowId, SegmentWriter callback) throws IOException {
        if (bean == null) {
            return;
        }
        final String reference = (hasBusinessId) ? bid.format(bean) : null;
        this.format(bean, this.offset, root, (s, t, d) -> {
            this.fill(d, rowId, t, reference);
            callback.accept(d);
        });
    }

    @Override
    final BSFormat<Segment, B> build(Up2Factory<B> factory, I type, BSMapper<Segment, B> source) throws BeanException {
        if (source != null) {
            return new BSFormat<>(source.node.reverse());
        }
        return factory.format(factory.resolver.or(type.getBusinessType()), type.getClassType());
    }

    abstract void fill(String[] target, Supplier<String> rowId, IType<?, ?> type, String reference);

    @FunctionalInterface
    private interface Filler<I extends IType<?, I>> {
        void accept(Segment source, I type, String[] data) throws IOException;
    }

}
