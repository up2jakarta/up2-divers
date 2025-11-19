package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.csv.data.SegmentWriter;
import io.github.up2jakarta.csv.fmt.SimpleFastImporter;
import io.github.up2jakarta.lov.core.AccessException;
import io.github.up2jakarta.lov.core.BeanException;

import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;

/**
 * {@link ModeType#FAST} Processor that's able to segregate and export java-bean to flat-data.
 *
 * @param <T> the business object type
 * @param <B> the business data type
 * @param <I> the input segment type
 * @see SimpleFastImporter
 */
public non-sealed class FastExporter<B extends DataType<B>, I extends IType<B, I>, T extends Segment> extends BusinessExporter<B, I, T> {

    public FastExporter(Up2Factory<B> factory, Class<T> type, I root, List<I> nodes) throws BeanException {
        super(factory, ModeType.FAST, type, root, nodes);
    }

    protected FastExporter(FastImporter<B, I, T, ?, ?> source) throws BeanException {
        super(source);
    }

    /**
     * Segregates the given business-object to many records and notifies the callback for each one.
     *
     * @param bean     the business object to segregate
     * @param callback the segment listener
     * @throws AccessException for any problem when getting properties from the specified business-object
     * @throws IOException     for some reason cannot be opened for writing by the callback.
     */
    public final void format(T bean, SegmentWriter callback) throws AccessException, IOException {
        this.format(bean, () -> null, callback);
    }

    @Override
    final void fill(String[] target, Supplier<String> ignore, IType<?, ?> type, String reference) {
        target[0] = type.getCode();
        target[1] = reference;
    }

}
