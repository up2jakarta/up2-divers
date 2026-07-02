package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.data.ITerm;
import io.github.up2jakarta.csv.data.SegmentWriter;
import io.github.up2jakarta.lov.core.AccessException;
import io.github.up2jakarta.lov.core.BeanException;

import java.io.IOException;
import java.util.function.Supplier;

/**
 * Up2J {@link ModeType#FULL} Processor that able to segregate and export java-bean to flat-data.
 *
 * @param <T> the business object type
 * @see io.github.up2jakarta.csv.fmt.SimpleFullImporter
 */
public non-sealed class FullExporter<B extends ITerm<B>, I extends Enum<I> & IType<I>, T extends Segment> extends BusinessExporter<B, I, T> {

    public FullExporter(Up2Factory<B> factory, Class<T> st, Class<I> it) throws BeanException {
        super(ModeType.FULL, factory, st, it);
    }

    public FullExporter(FullImporter<B, I, T, ?, ?> source) throws BeanException {
        super(source);
    }

    /**
     * Segregates the given business-object to many records and notifies the callback for each one.
     *
     * @param bean     the business object to segregate
     * @param recordId the record-reference supplier
     * @param callback the segment listener
     * @throws AccessException for any problem when getting properties from the specified business-object
     * @throws IOException     for some reason cannot be opened for writing by the callback.
     * @see FullExporter#format(Segment, Supplier, SegmentWriter)
     */
    @Override
    public final void format(T bean, Supplier<String> recordId, SegmentWriter callback) throws AccessException, IOException {
        super.format(bean, recordId, callback);
    }

    @Override
    final void fill(String[] target, Supplier<String> recordId, I type, String reference) {
        target[0] = recordId.get();
        target[1] = type.getCode();
        target[2] = reference;
    }

}
