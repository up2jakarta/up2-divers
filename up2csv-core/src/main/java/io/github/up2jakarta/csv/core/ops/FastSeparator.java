package io.github.up2jakarta.csv.core.ops;

import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.MapperFactory;
import io.github.up2jakarta.csv.data.BusinessObject;
import io.github.up2jakarta.csv.data.DataType;

import java.util.function.Consumer;

/**
 * {@link ModeType#FAST} Processor that able to segregate java-bean to flat-data.
 */
public final class FastSeparator<T extends BusinessObject, B extends DataType<B>, I extends IType<B, I>> extends BusinessProcessor<B, I, T> {

    public FastSeparator(MapperFactory<B> factory, Class<T> type, I rootNode, I[] nodes) throws BeanException {
        super(factory, ModeType.FAST.build(type), rootNode, nodes);
    }

    public void format(T bean, Consumer<String[]> callback) throws BeanException {
        this.format(bean, () -> null, callback);
    }

}
