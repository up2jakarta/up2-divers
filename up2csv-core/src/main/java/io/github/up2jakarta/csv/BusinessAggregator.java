package io.github.up2jakarta.csv;

import io.github.up2jakarta.csv.core.EventHandler;
import io.github.up2jakarta.csv.core.Mapper;
import io.github.up2jakarta.csv.core.MapperFactory;
import io.github.up2jakarta.csv.exception.BeanException;
import io.github.up2jakarta.csv.exception.PropertyException;
import io.github.up2jakarta.csv.extension.DataType;
import io.github.up2jakarta.csv.extension.Linked;
import io.github.up2jakarta.csv.extension.Parsed;
import io.github.up2jakarta.csv.extension.SeverityType;
import io.github.up2jakarta.csv.input.InputError;
import io.github.up2jakarta.csv.input.InputSegment;
import io.github.up2jakarta.csv.input.InputType;
import io.github.up2jakarta.csv.misc.BusinessCreator;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.BiConsumer;

import static io.github.up2jakarta.csv.extension.DataType.buildMessage;
import static io.github.up2jakarta.csv.extension.DataType.isValid;
import static io.github.up2jakarta.csv.extension.SeverityType.*;
import static java.util.Objects.requireNonNull;

@SuppressWarnings("unused")
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

    private void error(EventHandler<R, ?, B, E> handler, SeverityType severity, InputType<?> segment, String message) {
        final PropertyException error = new PropertyException(severity, segment.getErrorCode(), message);
        //noinspection unchecked
        handler.handleEvent((B) segment.getGroupType(), index, error, null, false);
    }

    private boolean filter(Parsed<T, ?> parent, Parsed<T, ?> child) {
        if (child instanceof Linked f) {
            return f.isParent(parent);
        }
        return true;
    }

    private void link(Parsed<T, ?> parent, EventHandler<R, ?, B, E> handler, Map<Parsed<T, ?>, EventHandler<R, ?, B, E>> data) {
        this.joins.get(parent.getRecord().getType()).forEach(type -> {
            final List<Entry<Parsed<T, ?>, EventHandler<R, ?, B, E>>> segments = data.entrySet().stream()
                    .filter(e -> type == e.getKey().getRecord().getType())
                    .filter(e -> filter(parent, e.getKey()))
                    .toList();
            // Validating Cardinality
            if (!isValid(type.getGroupType(), segments.size())) {
                if (segments.isEmpty()) {
                    error(handler, ERROR, type, "segment is required");
                } else {
                    final String msg = buildMessage(type.getGroupType());
                    error(handler, ERROR, type, msg);
                    segments.forEach(e -> error(e.getValue(), ERROR, type, msg));
                }
            }
            // Linking
            segments.forEach(item -> {
                type.getLinker().link(parent, item.getKey());
                data.entrySet().removeIf(item::equals);
                if (this.joins.containsKey(type)) {
                    link(item.getKey(), item.getValue(), data);
                }
            });
        });
    }

    private O link(Entry<Parsed<T, ?>, EventHandler<R, ?, B, E>> main, Map<Parsed<T, ?>, EventHandler<R, ?, B, E>> data) {
        //noinspection unchecked
        final O invoice = (O) main.getKey();
        root.getLinker().link(null, invoice);
        data.entrySet().removeIf(main::equals);
        link(invoice, main.getValue(), data);
        // Check detached segment
        data.forEach((e, h) -> error(h, WARNING, e.getRecord().getType(), "segment is detached"));
        return invoice;
    }

    public final <C> C parse(R[] rows, BusinessCreator<C, O, E> result) throws BeanException {
        if (rows == null || rows.length == 0) {
            return result.apply(null, List.of());
        }
        // Mapping all segments
        final Map<Parsed<T, ?>, EventHandler<R, ?, B, E>> data = new HashMap<>(rows.length);
        for (final R row : rows) {
            final EventHandler<R, ?, B, E> handler = newHandler(row);
            final Parsed<T, ?> entity = mappers.get(row.getType()).map(row, handler);
            data.put(entity, handler);
        }
        final Map<Parsed<T, ?>, EventHandler<R, ?, B, E>> all = Map.copyOf(data);
        // Checking root segment
        final List<Entry<Parsed<T, ?>, EventHandler<R, ?, B, E>>> roots = data.entrySet().stream()
                .filter(e -> root == e.getKey().getRecord().getType())
                .toList();
        final O invoice;
        if (roots.size() != 1) {
            data.forEach((e, h) -> error(h, FATAL, root, buildMessage(root.getGroupType())));
            invoice = null;
        } else {
            invoice = link(roots.get(0), data);
        }
        // Collecting errors
        final List<E> errors = new LinkedList<>();
        all.forEach((e, h) -> errors.addAll(h.toList()));
        return result.apply(invoice, errors);
    }

    protected abstract EventHandler<R, ?, B, E> newHandler(R row);

}
