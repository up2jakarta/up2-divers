package io.github.up2jakarta.csv.core.ops;

import io.github.up2jakarta.csv.api.IFullType;
import io.github.up2jakarta.csv.api.hdl.IErrorEntity;
import io.github.up2jakarta.csv.api.hdl.IRecordEntity;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.MapperFactory;
import io.github.up2jakarta.csv.data.BusinessObject;
import io.github.up2jakarta.csv.data.DataType;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

public abstract class FullAggregator<T extends BusinessObject, B extends DataType<B>, I extends IFullType<B, I>, R extends IRecordEntity<I>, E extends IErrorEntity<R, ?, B>> extends BusinessAggregator<T, B, I, R, E> {

    public FullAggregator(MapperFactory<B> factory, Class<T> type, I rootNode, I[] nodes) throws BeanException {
        super(factory, ModeType.FULL.build(type), rootNode, nodes);
    }

    public void format(T bean, AtomicInteger counter, Consumer<String[]> callback) throws BeanException {
        this.format(bean, adapt(counter), callback);
    }

    public void format(T bean, AtomicLong counter, Consumer<String[]> callback) throws BeanException {
        this.format(bean, adapt(counter), callback);
    }

}
