package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Referencable;
import io.github.up2jakarta.csv.data.SegmentWriter;

import java.io.IOException;
import java.util.function.Supplier;

/**
 * {@link ModeType#FULL} Processor that able to segregate and export java-bean to flat-data.
 *
 * @param <T> the business object type
 */
public non-sealed class FullExporter<B extends DataType<B>, I extends IType<B, I>, T extends Referencable> extends BusinessExporter<B, I, T> {

    public FullExporter(Up2Factory<B> factory, Class<T> type, I rootNode, I[] nodes) throws BeanException {
        super(factory, ModeType.FULL, type, rootNode, nodes);
    }

    public FullExporter(FullImporter<B, I, T, ?, ?> source) throws BeanException {
        super(source);
    }

    /**
     * Segregates the given business-object to many records and notifies the callback for each one.
     *
     * @param bean     the business object to segregate
     * @param rowId    the record-reference supplier
     * @param callback the segment listener
     * @throws BeanException for any problem when getting fields from business-object
     * @throws IOException   for some reason cannot be opened for writing by the callback.
     * @see FullExporter#format(Referencable, Supplier, SegmentWriter)
     */
    public final void format(T bean, Supplier<String> rowId, SegmentWriter callback) throws BeanException, IOException {
        super.format(bean, rowId, callback);
    }

    @Override
    final void fill(String[] target, Supplier<String> rowId, IType<?, ?> type, Referencable source) {
        target[0] = rowId.get();
        target[1] = type.getCode();
        target[2] = source.getReference();
    }

}
