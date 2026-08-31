package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.ITerm;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.api.hdl.IEventBuilder;
import io.github.up2jakarta.csv.core.BSManager.Key;
import io.github.up2jakarta.csv.core.BSNode.Bean;
import io.github.up2jakarta.csv.core.BusinessExporter.Format;
import io.github.up2jakarta.csv.core.BusinessImporter.Mapper;
import io.github.up2jakarta.csv.data.BusinessCreator;
import io.github.up2jakarta.csv.data.Up2Result;
import io.github.up2jakarta.csv.hdl.BusinessHandler;
import io.github.up2jakarta.lov.CodeListException;
import io.github.up2jakarta.lov.TypeConverter;
import io.github.up2jakarta.lov.core.AccessException;
import io.github.up2jakarta.lov.core.BeanException;
import jakarta.validation.Validator;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import static io.github.up2jakarta.csv.api.IEvent.EC_COMPLIANCE;
import static io.github.up2jakarta.lov.SeverityType.ERROR;
import static io.github.up2jakarta.lov.SeverityType.WARNING;
import static io.github.up2jakarta.lov.core.Beans.cast;
import static java.util.Arrays.asList;
import static java.util.Arrays.copyOfRange;

/**
 * Up2J Business Processor that's able to parse and aggregate java-bean from flat-data.
 * <p>
 * This class cannot be directly inherited, uses {@link NeatImporter} for ordered input or else {@link MessImporter}
 *
 * @param <T> the business object type
 * @param <B> the business term type
 * @param <I> the input segment type
 * @param <R> the input record type
 * @param <E> the event type
 * @see BusinessExporter
 */
public abstract sealed class BusinessImporter<B extends ITerm<B>, I extends Enum<I> & IType<I>, T extends Segment, R extends IRecord<I>, E extends IEvent<B>>
        extends BSOperator<B, I, Mapper<Segment, B>, Format<Segment, B>> implements TypeConverter<I>
        permits NeatImporter, MessImporter {

    /**
     * Constant holding the event message for detached segment
     */
    public static final String DETACHED = "must not be detached";
    private final Map<I, BSLink<B, I, Mapper<Segment, B>>> mapper;
    private final TypeConverter<I> parser;

    BusinessImporter(IMode mode, Up2Factory<B> factory, Class<T> st, Class<I> it) throws BeanException {
        super(mode, factory, cast(st), it);
        this.mapper = tree.toMapper(it);
        this.parser = tree.toParser(it);
    }

    BusinessImporter(BusinessExporter<B, I, T> exporter) throws BeanException {
        super(exporter);
        this.mapper = tree.toMapper(type);
        this.parser = tree.toParser(type);
    }

    /**
     * Parses, validates and aggregates the given records.
     * <p>
     * <b>
     * Note that the specified {@code records} must contains one and only one main record
     * ({@link IRecord#getType()} equals to {@link io.github.up2jakarta.csv.BusinessLink#value()}),
     * else the aggregated business-object will be {@code null}
     * </b>
     *
     * @param records the collection of segments
     * @param creator the custom result creator
     * @return the custom result created by the given <code>creator</code>
     * @throws AccessException for any problem when setting properties of java-beans from input record
     */
    public final <C> C parse(List<R> records, BusinessCreator<C, T, E> creator) throws AccessException {
        if (records == null) {
            return null;
        } else if (records.isEmpty()) {
            return creator.apply(null, List.of());
        }
        return this.doParse(records, creator);
    }

    /**
     * Parses, validates and aggregates the given records.
     *
     * @param records the array of segments
     * @param creator the custom result creator
     * @return the custom result created by the given <code>creator</code>
     * @throws AccessException for any problem when setting properties of java-beans from input record
     * @see #parse(List, BusinessCreator)
     */
    public final <C> C parse(R[] records, BusinessCreator<C, T, E> creator) throws AccessException {
        if (records == null) {
            return null;
        }
        return this.parse(asList(records), creator);
    }

    /**
     * Parses, validates and aggregates the given records.
     *
     * @param records the array of segments
     * @return the business-object with collected errors
     * @throws AccessException for any problem when setting properties of java-beans from input record
     * @see #parse(List, BusinessCreator)
     */
    public final Up2Result<T, E> parse(R[] records) throws AccessException {
        return this.parse(records, Up2Result::new);
    }

    /**
     * Parses, validates and aggregates the given records.
     *
     * @param records the collection of segments
     * @return the business-object with collected errors
     * @throws AccessException for any problem when setting properties of java-beans from input record
     * @see #parse(List, BusinessCreator)
     */
    public final Up2Result<T, E> parse(List<R> records) throws AccessException {
        return this.parse(records, Up2Result::new);
    }

    @Override
    public final I parse(String value) throws CodeListException {
        return parser.parse(value);
    }

    /**
     * Creates and returns business-exporter for the same configuration without scan beans again.
     *
     * @return new business exporter
     * @throws BeanException if any property is not accessible for reads.
     */
    public abstract BusinessExporter<B, I, T> toExporter() throws BeanException;

    /**
     * Parses the segment type then truncates the data columns aka without meta-data.
     *
     * @param creator the record creator
     * @param record  the input record
     * @return the final record instance
     */
    public final R transform(BiFunction<I, String[], R> creator, String... record) throws CodeListException {
        final String code = record[mode.getIndex()];
        final I key = parser.parse(code);
        final int offset = (key == tree.key) ? tree.offset : mode.getLength();
        final String[] data = copyOfRange(record, offset, record.length, String[].class);
        return creator.apply(key, data);
    }

    /**
     * Creates and returns a new valid event-builder for the specified record's length.
     *
     * @param length the length of records, it's helpful for collection size initializing.
     * @return a new business event-builder, must not be <code>null</code>
     */
    protected abstract IEventBuilder<B, R, E> newBuilder(int length);

    @Override
    final Mapper<Segment, B> build(Up2Factory<B> f, Class<Segment> type, Format<Segment, B> src) throws BeanException {
        if (src != null) {
            return src.reverse(factory.validator);
        }
        return f.mp(type);
    }

    abstract <C> C doParse(Collection<R> records, BusinessCreator<C, T, E> creator);

    final <C> boolean validate(BSLink<B, ?, ?> ln, BusinessHandler<B> hd, List<C> cs, BiConsumer<C, String> ch) {
        final int size = cs.size();
        final String message = this.validate(ln, size);
        if (message != null) {
            final boolean many = ln.max > 1;
            if (many || size == 0) {
                hd.handle(ln.event, ln.term, message);
            } else {
                cs.forEach(c -> ch.accept(c, message));
            }
            return many && size != 0;
        }
        return size != 0;
    }

    final BSLink<B, I, Mapper<Segment, B>> map(R record, BusinessHandler<B> handler) {
        final I type = record.getType();
        if (type == null) {
            handler.handle(ERROR, EC_COMPLIANCE, null, mode.getIndex(), "must not be null");
            return null;
        }
        final BSLink<B, I, Mapper<Segment, B>> link = mapper.get(type);
        if (link == null) {
            handler.handle(WARNING, EC_COMPLIANCE, null, mode.getIndex(), DETACHED);
            return null;
        }
        if (record.getData() == null) {
            handler.handle(link.event, link.term, "must not be null");
            return null;
        }
        return link;
    }

    /**
     * Internal Business Mapper.
     */
    static final class Mapper<S extends Segment, D extends ITerm<D>> extends Node<S, D, Bean<S, D, ?>> {
        Mapper(Validator validator, Key<D, S> key, Bean<S, D, ?> node) throws BeanException {
            super(validator, key, node);
            this.check(node);
        }

        public Format<S, D> reverse(Validator validator) throws BeanException {
            return BSManager.ft(this).build(validator, Format::new);
        }
    }
}
