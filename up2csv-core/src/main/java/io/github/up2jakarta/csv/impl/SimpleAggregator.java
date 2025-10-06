package io.github.up2jakarta.csv.impl;

import io.github.up2jakarta.csv.api.IFullType;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.api.hdl.IErrorCreator;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.MapperFactory;
import io.github.up2jakarta.csv.core.ops.FastAggregator;
import io.github.up2jakarta.csv.data.BusinessObject;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.xml.api.SeverityType;
import io.github.up2jakarta.xml.clv.CodeListConverter;
import io.github.up2jakarta.xml.clv.CodeListException;
import io.github.up2jakarta.xml.clv.PropertyException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static io.github.up2jakarta.csv.BusinessBuilder.DEFAULT_LEVEL;
import static io.github.up2jakarta.csv.core.Errors.ERROR_CODE_LIST;
import static io.github.up2jakarta.xml.api.SeverityType.ERROR;
import static java.util.Arrays.copyOfRange;

public class SimpleAggregator<T extends BusinessObject, B extends DataType<B>, I extends IFullType<B, I>> extends FastAggregator<T, B, I, SimpleRecord<B, I>, SimpleError<B, I>> {

    protected final SeverityType failLevel;
    private final Function<String, I> typeParser;

    public <E extends Enum<E> & IType<B, I>> SimpleAggregator(MapperFactory<B> mf, Class<T> type, E rootNode) throws BeanException {
        this(mf, type, rootNode, DEFAULT_LEVEL);
    }

    public <E extends Enum<E> & IType<B, I>> SimpleAggregator(MapperFactory<B> mf, Class<T> type, E rootNode, SeverityType level) throws BeanException {
        //noinspection unchecked
        this(mf, type, (I) rootNode, ((Class<I>) rootNode.getClass()).getEnumConstants(), level);
    }

    public SimpleAggregator(MapperFactory<B> mf, Class<T> type, I rootNode, I[] nodes) throws BeanException {
        this(mf, type, rootNode, nodes, DEFAULT_LEVEL);
    }

    public SimpleAggregator(MapperFactory<B> mf, Class<T> type, I rootNode, I[] nodes, SeverityType level) throws BeanException {
        super(mf, type, rootNode, nodes);
        this.failLevel = level;
        //noinspection unchecked
        final Class<I> inputType = (Class<I>) rootNode.getClass();
        this.typeParser = v -> CodeListConverter.parse(v, inputType, Stream.of(nodes), ERROR, ERROR_CODE_LIST);
    }

    @Override
    protected FastCollector<SimpleRecord<B, I>, B, SimpleError<B, I>> create(SimpleRecord<B, I> row) {
        //noinspection RedundantCast
        return new FastCollector<>(row, (IErrorCreator<SimpleRecord<B, I>, B, SimpleError<B, I>>) SimpleError::new, failLevel);
    }

    public SimpleRecord<B, I> record(String... row) throws CodeListException {
        final I type = typeParser.apply(row[config.getTypeIdIndex()]);
        final int offset = this.offset(type);
        final String[] data = copyOfRange(row, offset, row.length, String[].class);
        return new SimpleRecord<>(type, data);
    }

    public final SimpleResult<T, ?> parse(Collection<String[]> rows) throws BeanException, PropertyException {
        final List<SimpleRecord<B, I>> records = new ArrayList<>(rows.size());
        for (final String[] row : rows) {
            if (row == null || row.length <= config.getTypeIdIndex()) {
                continue;
            }
            records.add(this.record(row));
        }
        //noinspection unchecked
        return this.parse((SimpleRecord<B, I>[]) records.toArray(SimpleRecord<?, ?>[]::new));
    }

    public final SimpleResult<T, ?> parse(SimpleRecord<B, I>[] rows) throws BeanException {
        return this.parse(rows, SimpleResult::new);
    }

}
