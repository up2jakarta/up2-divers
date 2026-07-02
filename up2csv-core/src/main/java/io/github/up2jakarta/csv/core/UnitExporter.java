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
 * Up2J {@link ModeType#UNIT} Processor that able to segregate and export java-bean to flat-data.
 *
 * @param <T> the business object type
 * @param <B> the business term type
 * @param <I> the input segment type
 * @see io.github.up2jakarta.csv.fmt.SimpleUnitImporter
 */
public non-sealed class UnitExporter<B extends ITerm<B>, I extends Enum<I> & IType<I>, T extends Segment> extends BusinessExporter<B, I, T> {

    public UnitExporter(Up2Factory<B> factory, Class<T> st, Class<I> it) throws BeanException {
        super(ModeType.UNIT, factory, st, it);
    }

    public UnitExporter(UnitImporter<B, I, T, ?, ?> source) throws BeanException {
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
    final void fill(String[] target, Supplier<String> ignore, I type, String reference) {
        target[0] = type.getCode();
    }

}
