package io.github.up2jakarta.csv.core.ops;

import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.MapperFactory;
import io.github.up2jakarta.csv.data.BusinessObject;
import io.github.up2jakarta.csv.data.DataType;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

import static io.github.up2jakarta.csv.core.ops.BusinessAggregator.adapt;

/**
 * {@link ModeType#FULL} Processor that able to segregate java-bean to flat-data.
 */
public final class FullSeparator<T extends BusinessObject, B extends DataType<B>, I extends IType<B, I>> extends BusinessProcessor<B, I, T> {

    public FullSeparator(MapperFactory<B> factory, Class<T> type, I rootNode, I[] nodes) throws BeanException {
        super(factory, ModeType.FULL.build(type), rootNode, nodes);
    }

    public void format(T bean, AtomicInteger counter, Consumer<String[]> callback) throws BeanException {
        this.format(bean, adapt(counter), callback);
    }

    public void format(T bean, AtomicLong counter, Consumer<String[]> callback) throws BeanException {
        this.format(bean, adapt(counter), callback);
    }

}
