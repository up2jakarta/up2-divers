package io.github.up2jakarta.csv.core.ops;

import io.github.up2jakarta.csv.api.IError;
import io.github.up2jakarta.csv.api.IFullType;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.MapperFactory;
import io.github.up2jakarta.csv.data.BusinessObject;
import io.github.up2jakarta.csv.data.DataType;

import java.util.function.Consumer;

public abstract class FastAggregator<T extends BusinessObject, B extends DataType<B>, I extends IFullType<B, I>, R extends IRecord<I>, E extends IError<B>> extends BusinessAggregator<T, B, I, R, E> {

    public FastAggregator(MapperFactory<B> factory, Class<T> type, I rootNode, I[] nodes) throws BeanException {
        super(factory, ModeType.FAST.build(type), rootNode, nodes);
    }

    public final void format(T bean, Consumer<String[]> callback) throws BeanException {
        this.format(bean, () -> null, callback);
    }

}
