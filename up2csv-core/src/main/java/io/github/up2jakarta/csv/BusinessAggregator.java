package io.github.up2jakarta.csv;

import io.github.up2jakarta.csv.core.EventHandler;
import io.github.up2jakarta.csv.core.MapperFactory;
import io.github.up2jakarta.csv.data.BusinessCreator;
import io.github.up2jakarta.csv.data.BusinessEntry;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.extension.Parsed;
import io.github.up2jakarta.csv.input.InputError;
import io.github.up2jakarta.csv.input.InputSegment;
import io.github.up2jakarta.csv.input.Linkable;
import io.github.up2jakarta.csv.misc.BeanException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import static io.github.up2jakarta.csv.data.DataType.buildMessage;
import static io.github.up2jakarta.csv.data.DataType.isValid;
import static io.github.up2jakarta.xml.api.SeverityType.*;

public abstract class BusinessAggregator<
        T extends Parsed<I, R>,
        B extends DataType<B>,
        I extends Enum<I> & Linkable<B, I>,
        R extends InputSegment<I>,
        E extends InputError<R, ?, B>
        >
        extends BusinessMapper<B, I, T, Parsed<I, ?>> {

    private final int idIndex;

    protected BusinessAggregator(MapperFactory<B> factory, I root, Class<T> rootType, int uidIndex) throws BeanException {
        super(factory, root, rootType);
        this.idIndex = uidIndex;
    }

    private void link(BusinessEntry<I, R, B, E> parent, List<BusinessEntry<I, R, B, E>> data) {
        this.getJoins(parent.getType()).forEach(type -> {
            final List<BusinessEntry<I, R, B, E>> segments = data.stream().filter(e -> e.filter(parent, type)).toList();
            // Validating Cardinality
            if (!isValid(type.getBusinessType(), segments.size())) {
                if (segments.isEmpty()) {
                    parent.handle(ERROR, type, "segment is required", idIndex);
                } else {
                    final String msg = buildMessage(type.getBusinessType());
                    parent.handle(ERROR, type, msg, idIndex);
                    segments.forEach(e -> e.handle(ERROR, type, msg, idIndex));
                }
            }
            // Linking
            segments.forEach(item -> {
                data.remove(item);
                if (this.hasJoins(type)) {
                    link(item, data);
                }
                type.linker().link(parent.getSegment(), item.getSegment());
            });
        });
    }

    private List<BusinessEntry<I, R, B, E>> map(R[] rows, Consumer<BusinessEntry<I, R, B, E>> main) throws BeanException {
        final List<BusinessEntry<I, R, B, E>> entries = new ArrayList<>(rows.length);
        for (final R row : rows) {
            final EventHandler<R, ?, B, E> handler = newHandler(row);
            final Parsed<I, ?> entity = this.getMapper(row.getType()).map(row, offset, handler);
            final BusinessEntry<I, R, B, E> record = new BusinessEntry<>(entity, handler);
            entries.add(record);
            if (root == row.getType()) {
                main.accept(record);
            }
        }
        return entries;
    }

    public final <C> C parse(R[] rows, BusinessCreator<C, T, E> result) throws BeanException {
        if (rows == null || rows.length == 0) {
            return result.apply(null, List.of());
        }
        final List<BusinessEntry<I, R, B, E>> roots = new LinkedList<>();
        final List<BusinessEntry<I, R, B, E>> entries = this.map(rows, roots::add);
        final List<BusinessEntry<I, R, B, E>> store = List.copyOf(entries);
        final T invoice;
        if (roots.size() != 1) {
            entries.forEach(r -> r.handle(FATAL, root, buildMessage(root.getBusinessType()), idIndex));
            invoice = null;
        } else {
            final BusinessEntry<I, R, B, E> main = roots.getFirst();
            entries.removeAll(roots);
            link(main, entries);
            root.linker().link(null, main.getSegment());
            entries.forEach(r -> r.handle(WARNING, r.getType(), "segment is detached", idIndex));
            //noinspection unchecked
            invoice = (T) main.getSegment();
        }
        final List<E> errors = new LinkedList<>();
        store.forEach(r -> r.collect(errors));
        return result.apply(invoice, errors);
    }

    protected abstract EventHandler<R, ?, B, E> newHandler(R row);

}
