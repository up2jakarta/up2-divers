package io.github.up2jakarta.csv;

import io.github.up2jakarta.csv.core.EventHandler;
import io.github.up2jakarta.csv.core.Mapper;
import io.github.up2jakarta.csv.core.MapperFactory;
import io.github.up2jakarta.csv.data.BusinessCreator;
import io.github.up2jakarta.csv.data.BusinessEntry;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.extension.Parsed;
import io.github.up2jakarta.csv.input.InputError;
import io.github.up2jakarta.csv.input.InputSegment;
import io.github.up2jakarta.csv.input.InputType;
import io.github.up2jakarta.csv.misc.BeanException;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static io.github.up2jakarta.csv.data.DataType.buildMessage;
import static io.github.up2jakarta.csv.data.DataType.isValid;
import static io.github.up2jakarta.xml.api.SeverityType.*;
import static java.util.Objects.requireNonNull;

public abstract class BusinessAggregator<O extends Parsed<T, R>, B extends DataType<B>, T extends Enum<T> & InputType<T>, R extends InputSegment<T>, E extends InputError<R, ?, B>> {

    private final Map<InputType<T>, Mapper<? extends Parsed<T, ?>, B>> mappers;
    private final Map<InputType<T>, Set<InputType<T>>> joins;
    private final int index;
    private final T root;

    public BusinessAggregator(MapperFactory<B> factory, T root, Class<O> rootType, int uidIndex) throws BeanException {
        if (root.getLinker().getType() != rootType) {
            throw new BeanException(rootType, "Invalid business mapping");
        }
        this.index = uidIndex;
        this.root = root;
        //noinspection unchecked
        final T[] values = ((Class<T>) root.getClass()).getEnumConstants();
        // Mappers
        final Map<InputType<T>, Mapper<? extends Parsed<T, ?>, B>> mappers = new HashMap<>(values.length);
        for (final T value : values) {
            final Class<? extends Parsed<T, ?>> type = value.getLinker().getType();
            mappers.put(value, factory.build(type));
            requireNonNull(value.getGroupType());
        }
        this.mappers = Collections.unmodifiableMap(mappers);
        // Joins
        final Map<InputType<T>, Set<InputType<T>>> joins = new HashMap<>();
        this.join(root, values, joins::put);
        this.joins = Collections.unmodifiableMap(joins);
    }

    private void join(T root, T[] values, BiConsumer<InputType<T>, Set<InputType<T>>> handler) {
        final Set<InputType<T>> result = new LinkedHashSet<>();
        for (final T value : values) {
            final Class<? extends Parsed<T, ?>> type = value.getLinker().getType();
            final Class<? extends Parsed<T, ?>> parentType = value.getLinker().getParentType();
            if (parentType != type && root.holds(value)) {
                result.add(value);
                join(value, values, handler);
            }
        }
        handler.accept(root, Collections.unmodifiableSet(result));
    }

    private void link(BusinessEntry<T, R, B, E> parent, List<BusinessEntry<T, R, B, E>> data) {
        this.joins.get(parent.getType()).forEach(type -> {
            final List<BusinessEntry<T, R, B, E>> segments = data.stream().filter(e -> e.filter(parent, type)).toList();
            // Validating Cardinality
            if (!isValid(type.getGroupType(), segments.size())) {
                if (segments.isEmpty()) {
                    parent.handle(ERROR, type, "segment is required", index);
                } else {
                    final String msg = buildMessage(type.getGroupType());
                    parent.handle(ERROR, type, msg, index);
                    segments.forEach(e -> e.handle(ERROR, type, msg, index));
                }
            }
            // Linking
            segments.forEach(item -> {
                data.remove(item);
                if (this.joins.containsKey(type)) {
                    link(item, data);
                }
                type.getLinker().link(parent.getSegment(), item.getSegment());
            });
        });
    }

    private List<BusinessEntry<T, R, B, E>> map(R[] rows, Consumer<BusinessEntry<T, R, B, E>> main) throws BeanException {
        final List<BusinessEntry<T, R, B, E>> entries = new ArrayList<>(rows.length);
        for (final R row : rows) {
            final EventHandler<R, ?, B, E> handler = newHandler(row);
            final Parsed<T, ?> entity = mappers.get(row.getType()).map(row, handler);
            final BusinessEntry<T, R, B, E> record = new BusinessEntry<>(entity, handler);
            entries.add(record);
            if (root == row.getType()) {
                main.accept(record);
            }
        }
        return entries;
    }

    public final <C> C parse(R[] rows, BusinessCreator<C, O, E> result) throws BeanException {
        if (rows == null || rows.length == 0) {
            return result.apply(null, List.of());
        }
        final List<BusinessEntry<T, R, B, E>> roots = new LinkedList<>();
        final List<BusinessEntry<T, R, B, E>> entries = this.map(rows, roots::add);
        final List<BusinessEntry<T, R, B, E>> store = List.copyOf(entries);
        final O invoice;
        if (roots.size() != 1) {
            entries.forEach(r -> r.handle(FATAL, root, buildMessage(root.getGroupType()), index));
            invoice = null;
        } else {
            final BusinessEntry<T, R, B, E> main = roots.getFirst();
            entries.removeAll(roots);
            link(main, entries);
            root.getLinker().link(null, main.getSegment());
            entries.forEach(r -> r.handle(WARNING, r.getType(), "segment is detached", index));
            //noinspection unchecked
            invoice = (O) main.getSegment();
        }
        final List<E> errors = new LinkedList<>();
        store.forEach(r -> r.collect(errors));
        return result.apply(invoice, errors);
    }

    protected abstract EventHandler<R, ?, B, E> newHandler(R row);

}
