package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Referencable;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.csv.data.SegmentWriter;

import java.io.IOException;
import java.util.Collection;
import java.util.function.Supplier;

/**
 * Base Processor that's able to segregate and export java-bean to flat-data.
 *
 * @param <T> the business object type
 * @param <B> the input data type
 * @param <I> the input type
 * @see io.github.up2jakarta.csv.fmt.FastExporter
 * @see io.github.up2jakarta.csv.fmt.FullExporter
 * @see io.github.up2jakarta.csv.fmt.UnitExporter
 */
public abstract non-sealed class BusinessExporter<B extends DataType<B>, I extends IType<B, I>, T extends Referencable> extends BSOperator<B, I, Up2Format<Segment, B>, Up2Mapper<Segment, B>> {

    private final BPFiller filler;

    protected BusinessExporter(Up2Factory<B> factory, ModeType mode, Class<T> type, I root, I[] nodes) throws BeanException {
        super(factory, type, mode, root, nodes);
        this.filler = BPFiller.of(mode);
    }

    @SuppressWarnings("ClassEscapesDefinedScope")
    protected BusinessExporter(BSOperator<B, I, Up2Mapper<Segment, B>, Up2Format<Segment, B>> importer) throws BeanException {
        super(importer);
        this.filler = BPFiller.of(importer.mode);
    }

    private void format(Segment bean, int offset, I type, MDFiller<I> consumer) throws BeanException, IOException {
        final String[] data = this.get(type).unmap(bean, offset);
        consumer.accept(bean, type, data);
        for (final I node : this.getJoins(type)) {
            final Collection<Segment> values = node.joiner().joins(bean);
            if (values == null) {
                continue;
            }
            for (var value : values) {
                this.format(value, mode.length, node, consumer);
            }
        }
    }

    protected void format(T bean, Supplier<String> rowId, SegmentWriter callback) throws BeanException, IOException {
        if (bean == null) {
            return;
        }
        this.format(bean, this.offset, root, (s, t, d) -> {
            final String reference = bean.getReference();
            this.filler.accept(d, rowId, t, reference);
            callback.accept(d);
        });
    }

    @Override
    final Up2Format<Segment, B> build(Up2Factory<B> factory, I type, Up2Mapper<Segment, B> source) throws BeanException {
        if (source != null) {
            return source.toFormat();
        }
        return factory.format(type.getClassType(), factory.resolver.or(type.getBusinessType()));
    }

    @Override
    final void check(String name, Up2Format<Segment, B> node, Up2Format<Segment, B> parent) {
    }

    @FunctionalInterface
    private interface MDFiller<I extends IType<?, I>> {
        void accept(Segment source, I type, String[] data) throws IOException;
    }

    @FunctionalInterface
    private interface BPFiller {
        static BPFiller of(ModeType mode) {
            return switch (mode) {
                case UNIT -> mode::unit;
                case FAST -> mode::fast;
                default -> mode::full;
            };
        }

        void accept(String[] target, Supplier<String> rowId, IType<?, ?> type, String reference);
    }

}
