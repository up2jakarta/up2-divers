package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.IError;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.xml.clv.PropertyException;

import java.util.List;

public final class BusinessEntry<T extends IType<D, T>, R extends IRecord<T>, D extends DataType<D>, E extends IError<D>> {

    private final T type;
    private final Segment bean;
    private final EventHandler<R, D, E> handler;
    private final Mapper<Segment, D> mapper;

    public BusinessEntry(Mapper<Segment, D> mapper, T type, Segment bean, EventCollector<R, D, E> handler) {
        this.bean = bean;
        this.type = type;
        this.mapper = mapper;
        this.handler = handler;
    }

    public <B extends Segment> B getBean() {
        //noinspection unchecked
        return (B) bean;
    }

    public T getType() {
        return type;
    }

    public R getSource() {
        return handler.row;
    }

    public boolean isParent(BusinessEntry<T, R, D, E> parent, IType<D, T> type) throws BeanException {
        if (type != this.getType()) {
            return false;
        }
        if (mapper.parentId.exists()) {
            final Object key = mapper.parentId.get(bean);
            if (key == null) {
                return false;
            }
            final Object pid = parent.mapper.businessId.get(parent.bean);
            return key.equals(pid);
        }
        return true;
    }

    public void collect(List<E> target) {
        target.addAll(handler.toCollection());
    }

    public void handle(IType<D, ?> type, int index, String message) {
        final PropertyException error = new PropertyException(type.getErrorLevel(), type.getErrorCode(), message);
        handler.handleEvent(type.getBusinessType(), index, error, null);
    }

}
