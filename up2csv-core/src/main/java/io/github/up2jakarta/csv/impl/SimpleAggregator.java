package io.github.up2jakarta.csv.impl;

import io.github.up2jakarta.csv.api.IFullType;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.api.hdl.IErrorCreator;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.MapperFactory;
import io.github.up2jakarta.csv.data.BusinessObject;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.ops.FastAggregator;
import io.github.up2jakarta.xml.api.SeverityType;
import io.github.up2jakarta.xml.clv.CodeListException;
import io.github.up2jakarta.xml.clv.PropertyException;

import java.util.ArrayList;
import java.util.List;

import static io.github.up2jakarta.csv.BusinessBuilder.DEFAULT_LEVEL;
import static io.github.up2jakarta.csv.ops.ModeType.FAST;

public class SimpleAggregator<T extends BusinessObject, B extends DataType<B>, I extends IFullType<B, I>> extends FastAggregator<T, B, I, SimpleRecord<B, I>, SimpleError<B, I>> {

    protected final SeverityType level;

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
        this.level = level;
    }

    @Override
    protected FastCollector<SimpleRecord<B, I>, B, SimpleError<B, I>> create(SimpleRecord<B, I> row) {
        //noinspection RedundantCast
        return new FastCollector<>(row, (IErrorCreator<SimpleRecord<B, I>, B, SimpleError<B, I>>) SimpleError::new, level);
    }

    public SimpleRecord<B, I> record(String... row) throws CodeListException {
        final I type = typing.type(row);
        final String[] data = typing.truncate(type, row);
        return new SimpleRecord<>(type, row[FAST.getBeanIdIndex()], data);
    }

    public final SimpleResult<T, ?> parse(List<String[]> rows) throws BeanException, PropertyException {
        final List<SimpleRecord<B, I>> records = new ArrayList<>(rows.size());
        for (final String[] row : rows) {
            if (row == null || row.length <= mode.getTypeIdIndex()) {
                continue;
            }
            records.add(this.record(row));
        }
        return this.parse(records);
    }

}
