package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.ITerm;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.core.BSLink.RId;
import io.github.up2jakarta.csv.core.BSLink.VId;
import io.github.up2jakarta.csv.data.SegmentWriter;
import io.github.up2jakarta.lov.core.AccessException;
import io.github.up2jakarta.lov.core.BeanException;

import java.util.Collection;

import static io.github.up2jakarta.lov.core.Beans.cast;

/**
 * Up2J {@link BusinessImporter} implementation for compact mode within export of
 * {@link io.github.up2jakarta.csv.BusinessId} for each underlying segment when
 * {@link io.github.up2jakarta.csv.BusinessLink#automatic()} is enabled.
 *
 * @param <T> the business object type
 * @param <B> the business term type
 * @param <I> the input segment type
 * @see MessImporter
 */
public non-sealed class MessExporter<B extends ITerm<B>, I extends Enum<I> & IType<I>, T extends Segment> extends BusinessExporter<B, I, T> {

    /**
     * Constructor for extended {@link ModeType#MESS} exporter without extra meta-data columns.
     *
     * @param factory the segment factory
     * @param st      the business object type
     * @param it      the business input type
     * @throws BeanException for any missing or wrong bean configuration
     */
    public MessExporter(Up2Factory<B> factory, Class<T> st, Class<I> it) throws BeanException {
        super(ModeType.MESS, factory, cast(st), it);
    }

    /**
     * Constructor for extended {@link ModeType#MESS} exporter within extra meta-data columns.
     *
     * @param factory the segment factory
     * @param st      the business object type
     * @param it      the business input type
     * @param size    the size for extra-data columns
     * @throws BeanException for any missing or wrong bean configuration
     */
    public MessExporter(Up2Factory<B> factory, Class<T> st, Class<I> it, int size) throws BeanException {
        super(XMode.of(size), factory, cast(st), it);
    }

    protected MessExporter(MessImporter<B, I, T, ?, ?> importer) throws BeanException {
        super(importer);
    }

    @Override
    public final <X extends Exception> void format(T bean, SegmentWriter<X> callback) throws X, AccessException {
        if (bean != null) {
            final String reference = tree.computer.businessId.format(bean);
            this.format(new String[length], bean, tree, (l, d) -> {
                d[mode.getOffset()] = reference;
                d[mode.getIndex()] = l.key.getCode();
                callback.accept(d);
            });
        }
    }

    /**
     * Returns the specification of extra-column added by the extended mode at the specified {@code index}.
     *
     * @param index the index of extra-column starting from {@code 0}
     */
    @Override
    protected String spec(int index) {
        return "?";
    }

    private <X extends Exception> void format(String[] ps, Segment s, BSLink<B, I, Format<Segment, B>> ln, Filler<I, X> f) throws X {
        final Format<Segment, B> format = ln.computer;
        final int index = ln.index + ln.offset;
        final String[] data = new String[index + format.length];
        if (ln.index == ln.length) {
            format.node.format(data, index, s);
        } else {
            format.format(data, index, s, ln.parentIds);
            for (final RId<B> rid : ln.parentIds) {
                data[rid.offset] = ps[rid.index];
            }
        }
        if (ln.index != 0) {
            var i = ln.offset;
            for (final VId<B> vid : ln.virtualIds) {
                data[i++] = ps[vid.index];
            }
        }
        f.accept(ln, data);
        final int idx = ln.key.ordinal();
        if (ln.computer.identifiable) {
            ps[idx] = ln.computer.businessId.format(s);
        }
        for (final BSLink<B, I, Format<Segment, B>> node : ln.links) {
            final Collection<Segment> children = node.from(s);
            if (children == null) continue;
            for (final Segment child : children) {
                if (child != null) {
                    this.format(ps, child, node, f);
                }
            }
        }
        ps[idx] = null;
    }

    @FunctionalInterface
    private interface Filler<I extends Enum<I> & IType<I>, X extends Exception> {
        void accept(BSLink<?, I, ?> context, String[] data) throws X;
    }

}
