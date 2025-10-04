package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.IError;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Recordable;
import io.github.up2jakarta.csv.misc.BeanException;
import io.github.up2jakarta.xml.api.SeverityType;
import io.github.up2jakarta.xml.codelist.PropertyException;

import java.util.List;

import static io.github.up2jakarta.csv.misc.Beans.UNDEFINED;

public final class BusinessEntry<T extends IType<D, T>, A extends IRecord<T>, D extends DataType<D>, C extends IError<A, ?, ?>> {

    private final Recordable<T, ?> bean;
    private final EventHandler<A, ?, D, C> handler;
    private final Mapper<Recordable<T, ?>, D> mapper;

    public BusinessEntry(Mapper<Recordable<T, ?>, D> mapper, Recordable<T, ?> bean, EventHandler<A, ?, D, C> handler) {
        this.bean = bean;
        this.handler = handler;
        this.mapper = mapper;
    }

    public Recordable<T, ?> getBean() {
        return bean;
    }

    public T getType() {
        return bean.getRecord().getType();
    }

    public boolean filter(BusinessEntry<T, A, D, C> parent, IType<D, T> type) throws BeanException {
        if (type != this.getType()) {
            return false;
        }
        final Object key = mapper.parentId(bean, parent.bean.getClass());
        if (key == UNDEFINED) {
            return true;
        }
        final Object pid = parent.mapper.businessId(parent.bean);
        if (pid == null || key == null) {
            return false;
        }
        return pid.equals(key);
    }

    public void collect(List<C> target) {
        handler.addTo(target);
    }

    public void handle(SeverityType severity, IType<D, ?> segment, String message, int index) {
        final PropertyException error = new PropertyException(severity, segment.getErrorCode(), message);
        handler.handleEvent(segment.getBusinessType(), index, error, null, false);
    }

}
