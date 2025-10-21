package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.IError;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.data.BusinessObject;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.xml.clv.PropertyException;

import java.util.List;
import java.util.Objects;

import static io.github.up2jakarta.csv.core.EventHandler.ERROR_VALIDATOR;
import static io.github.up2jakarta.xml.api.SeverityType.ERROR;

final class BSEntry<T extends IType<D, T>, R extends IRecord<T>, D extends DataType<D>, E extends IError<D>> {

    private final T type;
    private final Segment bean;
    private final Up2Mapper<Segment, D> mapper;
    private final EventHandler<R, D, E> handler;

    BSEntry(Up2Mapper<Segment, D> mapper, T type, Segment bean, Up2Collector<R, D, E> handler) {
        this.bean = bean;
        this.type = type;
        this.mapper = mapper;
        this.handler = handler;
    }

    <B extends Segment> B bean() {
        //noinspection unchecked
        return (B) bean;
    }

    T type() {
        return type;
    }

    void validate(int offset) {
        if ((bean instanceof BusinessObject bo) && bo.getReference() == null) {
            bo.setReference(handler.row.getBusinessReference());
        }
        mapper.validate(bean, offset, handler);
    }

    boolean link(BSEntry<T, R, D, E> parent, IType<D, T> childType) throws BeanException {
        if (childType != this.type) {
            return false;
        }
        if (!Objects.equals(parent.handler.row.getBusinessReference(), handler.row.getBusinessReference())) {
            return false;
        }
        if (mapper.parentId.exists()) {
            final Object key = mapper.parentId.get(bean);
            final Object pid = parent.mapper.businessId.get(parent.bean);
            return Objects.equals(key, pid);
        }
        return true;
    }

    void collect(List<E> target) {
        target.addAll(handler.toCollection());
    }

    void handle(IType<D, ?> type, int index, String message) {
        if (type == null) {
            final PropertyException error = new PropertyException(ERROR, ERROR_VALIDATOR, message);
            handler.handleEvent(null, index, error, null);
        } else {
            final PropertyException error = new PropertyException(type.getErrorLevel(), type.getErrorCode(), message);
            handler.handleEvent(type.getBusinessType(), index, error, null);
        }
    }

}
