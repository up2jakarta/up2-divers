package io.github.up2jakarta.csv.data;

import io.github.up2jakarta.csv.core.EventHandler;
import io.github.up2jakarta.csv.extension.Linked;
import io.github.up2jakarta.csv.extension.Parsed;
import io.github.up2jakarta.csv.input.InputError;
import io.github.up2jakarta.csv.input.InputSegment;
import io.github.up2jakarta.csv.input.InputType;
import io.github.up2jakarta.xml.api.SeverityType;
import io.github.up2jakarta.xml.codelist.PropertyException;

import java.util.List;

public final class BusinessEntry<T extends InputType<D, T>, A extends InputSegment<T>, D extends DataType<D>, C extends InputError<A, ?, ?>> {

    private final Parsed<T, ?> segment;
    private final EventHandler<A, ?, D, C> handler;

    public BusinessEntry(Parsed<T, ?> segment, EventHandler<A, ?, D, C> handler) {
        this.segment = segment;
        this.handler = handler;
    }

    public Parsed<T, ?> getSegment() {
        return segment;
    }

    public T getType() {
        return segment.getRecord().getType();
    }

    public boolean filter(BusinessEntry<T, A, D, C> parent, InputType<D, T> type) {
        if (type != this.getType()) {
            return false;
        }
        if (segment instanceof Linked f) {
            return f.isParent(parent.segment);
        }
        return true;
    }

    public void collect(List<C> target) {
        handler.addTo(target);
    }

    public void handle(SeverityType severity, InputType<D, ?> segment, String message, int index) {
        final PropertyException error = new PropertyException(severity, segment.getErrorCode(), message);
        handler.handleEvent(segment.getBusinessType(), index, error, null, false);
    }

}
