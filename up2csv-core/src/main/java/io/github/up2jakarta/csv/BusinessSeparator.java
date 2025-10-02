package io.github.up2jakarta.csv;

import io.github.up2jakarta.csv.core.MapperFactory;
import io.github.up2jakarta.csv.data.BusinessConsumer;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.extension.Segment;
import io.github.up2jakarta.csv.input.InputType;
import io.github.up2jakarta.csv.misc.BeanException;

import java.util.function.Consumer;
import java.util.function.Function;

public abstract class BusinessSeparator<T extends Segment, B extends DataType<B>, I extends Enum<I> & InputType<B, I>> extends BusinessMapper<B, I, T, Segment> {

    private static final int MD_LENGTH = 2;

    private final Function<T, String> idFunction;

    public BusinessSeparator(MapperFactory<B> factory, I root, Class<T> type, Function<T, String> idFunction) throws BeanException {
        super(factory, root, type);
        this.idFunction = idFunction;
        if (offset < MD_LENGTH) {
            throw new BeanException(type, "@Truncated[value] must be greater than or equals to " + MD_LENGTH);
        }
    }

    private void format(Segment bean, I type, BusinessConsumer<I> consumer) throws BeanException {
        if (bean == null) {
            return;
        }
        final String[] data = this.getMapper(type).unmap(bean, offset);
        consumer.accept(bean, type, data);
        for (final I segment : this.getJoins(type)) {
            for (var value : segment.joiner().joins(bean)) {
                this.format(value, segment, consumer);
            }
        }
    }

    protected final void format(T bean, int referenceIndex, int discriminatorIndex, Consumer<String[]> callback) throws BeanException {
        if (bean == null) {
            return;
        }
        final String id = idFunction.apply(bean);
        this.format(bean, (s, t, d) -> {
            d[referenceIndex] = id;
            d[discriminatorIndex] = t.getCode();
            callback.accept(d);
        });
    }

    protected final void format(T bean, BusinessConsumer<I> callback) throws BeanException {
        this.format(bean, root, callback);
    }

}
