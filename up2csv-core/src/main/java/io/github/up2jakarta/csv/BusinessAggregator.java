package io.github.up2jakarta.csv;

import io.github.up2jakarta.csv.api.IError;
import io.github.up2jakarta.csv.api.IFullType;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.core.*;
import io.github.up2jakarta.csv.data.BusinessCreator;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Recordable;
import io.github.up2jakarta.csv.misc.BeanException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import static io.github.up2jakarta.csv.data.DataType.buildMessage;
import static io.github.up2jakarta.csv.data.DataType.isValid;
import static io.github.up2jakarta.xml.api.SeverityType.*;

public abstract class BusinessAggregator<
        T extends Recordable<I, R>,
        B extends DataType<B>,
        I extends Enum<I> & IFullType<B, I>,
        R extends IRecord<I>,
        E extends IError<R, ?, B>
        >
        extends BusinessProcessor<B, I, T, Recordable<I, ?>> {

    private final int idIndex;

    protected BusinessAggregator(MapperFactory<B> factory, I root, Class<T> rootType, int uidIndex) throws BeanException {
        super(factory, root, rootType);
        this.idIndex = uidIndex;
    }

    private void link(BusinessEntry<I, R, B, E> parent, List<BusinessEntry<I, R, B, E>> data) throws BeanException {
        for (final I type : this.getJoins(parent.getType())) {
            final List<BusinessEntry<I, R, B, E>> children = new LinkedList<>();
            for (final BusinessEntry<I, R, B, E> e : data) {
                if (e.filter(parent, type)) {
                    children.add(e);
                }
            }
            // Validating Cardinality
            if (!isValid(type.getBusinessType(), children.size())) {
                if (children.isEmpty()) {
                    parent.handle(ERROR, type, "segment is required", idIndex);
                } else {
                    final String msg = buildMessage(type.getBusinessType());
                    parent.handle(ERROR, type, msg, idIndex);
                    children.forEach(e -> e.handle(ERROR, type, msg, idIndex));
                }
            }
            // Linking
            for (final BusinessEntry<I, R, B, E> child : children) {
                data.remove(child);
                if (this.hasJoins(type)) {
                    link(child, data);
                }
                type.linker().link(parent.getBean(), child.getBean());
            }
        }
    }

    private List<BusinessEntry<I, R, B, E>> map(R[] rows, Consumer<BusinessEntry<I, R, B, E>> main) throws BeanException {
        final List<BusinessEntry<I, R, B, E>> entries = new ArrayList<>(rows.length);
        for (final R row : rows) {
            final EventHandler<R, ?, B, E> handler = newHandler(row);
            final Mapper<Recordable<I, ?>, B> mapper = this.getMapper(row.getType());
            final Recordable<I, ?> entity = mapper.map(row, offset, handler);
            final BusinessEntry<I, R, B, E> record = new BusinessEntry<>(mapper, entity, handler);
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
            root.linker().link(null, main.getBean());
            entries.forEach(r -> r.handle(WARNING, r.getType(), "segment is detached", idIndex));
            //noinspection unchecked
            invoice = (T) main.getBean();
        }
        final List<E> errors = new LinkedList<>();
        store.forEach(r -> r.collect(errors));
        return result.apply(invoice, errors);
    }

    protected abstract EventHandler<R, ?, B, E> newHandler(R row);

}
