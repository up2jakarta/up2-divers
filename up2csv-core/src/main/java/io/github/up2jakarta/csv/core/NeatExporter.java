package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.ITerm;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.data.SegmentWriter;
import io.github.up2jakarta.lov.core.AccessException;
import io.github.up2jakarta.lov.core.BeanException;

import java.util.Collection;

/**
 * Up2J {@link BusinessImporter} implementation for compact mode without export of
 * {@link io.github.up2jakarta.csv.BusinessId} for each underlying segment when
 * {@link io.github.up2jakarta.csv.BusinessLink#automatic()} is enabled.
 *
 * @param <T> the business object type
 * @param <B> the business term type
 * @param <I> the input segment type
 * @see io.github.up2jakarta.csv.data.SimpleNeatImporter
 */
public non-sealed class NeatExporter<B extends ITerm<B>, I extends Enum<I> & IType<I>, T extends Segment> extends BusinessExporter<B, I, T> {

    /**
     * Constructor for {@link ModeType#NEAT} exporter.
     *
     * @param factory the segment factory
     * @param st      the business object type
     * @param it      the business input type
     * @throws BeanException for any missing or wrong bean configuration
     */
    public NeatExporter(Up2Factory<B> factory, Class<T> st, Class<I> it) throws BeanException {
        super(ModeType.NEAT, factory, st, it);
    }

    protected NeatExporter(NeatImporter<B, I, T, ?, ?> source) throws BeanException {
        super(source);
    }

    @Override
    public final <X extends Exception> void format(T bean, SegmentWriter<X> callback) throws X, AccessException {
        if (bean != null) {
            this.format(bean, tree, callback);
        }
    }

    @Override
    final String spec(int index) {
        throw new IllegalArgumentException();
    }

    private <X extends Exception> void format(Segment bean, BSLink<B, I, Format<Segment, B>> link, SegmentWriter<X> sw) throws X {
        final Format<Segment, B> format = link.computer;
        final int index = link.offset;
        final String[] data = new String[index + format.length];
        format.node.format(data, index, bean);
        data[mode.getIndex()] = link.key.getCode();
        sw.accept(data);
        for (final BSLink<B, I, Format<Segment, B>> node : link.links) {
            final Collection<Segment> values = node.from(bean);
            if (values == null) continue;
            for (var value : values) {
                this.format(value, node, sw);
            }
        }
    }

}
