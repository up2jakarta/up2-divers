package io.github.up2jakarta.csv;

import io.github.up2jakarta.csv.api.IError;
import io.github.up2jakarta.csv.api.IFullType;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.api.hdl.*;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.MapperFactory;
import io.github.up2jakarta.csv.core.ops.FastAggregator;
import io.github.up2jakarta.csv.core.ops.FastSeparator;
import io.github.up2jakarta.csv.core.ops.FullAggregator;
import io.github.up2jakarta.csv.core.ops.FullSeparator;
import io.github.up2jakarta.csv.data.BusinessObject;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.impl.FastCollector;
import io.github.up2jakarta.csv.impl.FullCollector;
import io.github.up2jakarta.csv.impl.SimpleAggregator;
import io.github.up2jakarta.xml.api.SeverityType;

public class BusinessBuilder<B extends DataType<B>> {

    public static final SeverityType DEFAULT_LEVEL = SeverityType.FATAL;

    private final MapperFactory<B> factory;

    public BusinessBuilder(MapperFactory<B> factory) {
        this.factory = factory;
    }

    public <T extends BusinessObject> FastBuilder<T> fast(Class<T> type) {
        return new FastBuilder<>(type);
    }

    public <T extends BusinessObject> FullBuilder<T> full(Class<T> type) {
        return new FullBuilder<>(type);
    }

    public final class FastBuilder<T extends BusinessObject> {

        private final Class<T> type;

        private FastBuilder(Class<T> type) {
            this.type = type;
        }

        public <I extends IType<B, I>> FinalFastBuilder<I> fast(I rootType, I[] nodeTypes) {
            return new FinalFastBuilder<>(rootType, nodeTypes);
        }

        @SuppressWarnings("unchecked")
        public <I extends Enum<I> & IType<B, I>> FinalFastBuilder<I> fast(I rootType) {
            return this.fast(rootType, ((Class<I>) rootType.getClass()).getEnumConstants());
        }

        public <I extends IFullType<B, I>> FinalFullBuilder<I> full(I rootType, I[] nodeTypes) {
            return new FinalFullBuilder<>(rootType, nodeTypes);
        }

        @SuppressWarnings("unchecked")
        public <I extends Enum<I> & IFullType<B, I>> FinalFullBuilder<I> full(I rootType) {
            return this.full(rootType, ((Class<I>) rootType.getClass()).getEnumConstants());
        }

        public final class FinalFastBuilder<I extends IType<B, I>> {

            private final I rootType;
            private final I[] nodeTypes;

            private FinalFastBuilder(I rootType, I[] nodeTypes) {
                this.rootType = rootType;
                this.nodeTypes = nodeTypes;
            }

            public FastSeparator<T, B, I> build() throws BeanException {
                return new FastSeparator<>(factory, type, rootType, nodeTypes);
            }

        }

        public final class FinalFullBuilder<I extends IFullType<B, I>> {

            private final I rootType;
            private final I[] nodeTypes;

            private FinalFullBuilder(I rootType, I[] nodeTypes) {
                this.rootType = rootType;
                this.nodeTypes = nodeTypes;
            }

            public SimpleAggregator<T, B, I> build() throws BeanException {
                return new SimpleAggregator<>(factory, type, rootType, nodeTypes);
            }

            public SimpleAggregator<T, B, I> build(SeverityType failLevel) throws BeanException {
                return new SimpleAggregator<>(factory, type, rootType, nodeTypes, failLevel);
            }

            public <R extends IRecord<I>, E extends IError<B>> FastAggregator<T, B, I, R, E> build(IErrorCreator<R, B, E> creator, SeverityType failLevel) throws BeanException {
                return new FastAggregator<>(factory, type, rootType, nodeTypes) {
                    @Override
                    protected FastCollector<R, B, E> create(R row) {
                        return new FastCollector<>(row, creator, failLevel);
                    }
                };
            }

            public <R extends IRecord<I>, E extends IError<B>> FastAggregator<T, B, I, R, E> build(IErrorCreator<R, B, E> creator) throws BeanException {
                return build(creator, DEFAULT_LEVEL);
            }

        }
    }

    public final class FullBuilder<T extends BusinessObject> {

        private final Class<T> type;

        private FullBuilder(Class<T> type) {
            this.type = type;
        }

        public <I extends IType<B, I>> FinalFastBuilder<I> fast(I rootType, I[] nodeTypes) {
            return new FinalFastBuilder<>(rootType, nodeTypes);
        }

        @SuppressWarnings("unchecked")
        public <I extends Enum<I> & IType<B, I>> FinalFastBuilder<I> fast(I rootType) {
            return this.fast(rootType, ((Class<I>) rootType.getClass()).getEnumConstants());
        }

        public <I extends IFullType<B, I>> FinalFullBuilder<I> full(I rootType, I[] nodeTypes) {
            return new FinalFullBuilder<>(rootType, nodeTypes);
        }

        @SuppressWarnings("unchecked")
        public <I extends Enum<I> & IFullType<B, I>> FinalFullBuilder<I> full(I rootType) {
            return this.full(rootType, ((Class<I>) rootType.getClass()).getEnumConstants());
        }

        public final class FinalFastBuilder<I extends IType<B, I>> {

            private final I rootType;
            private final I[] nodeTypes;

            private FinalFastBuilder(I rootType, I[] nodeTypes) {
                this.rootType = rootType;
                this.nodeTypes = nodeTypes;
            }

            public FullSeparator<T, B, I> build() throws BeanException {
                return new FullSeparator<>(factory, type, rootType, nodeTypes);
            }

        }

        public final class FinalFullBuilder<I extends IFullType<B, I>> {

            private final I rootType;
            private final I[] nodeTypes;

            private FinalFullBuilder(I rootType, I[] nodeTypes) {
                this.rootType = rootType;
                this.nodeTypes = nodeTypes;
            }

            public FullSeparator<T, B, I> build() throws BeanException {
                return new FullSeparator<>(factory, type, rootType, nodeTypes);
            }

            public <R extends IRecordEntity<I>, E extends IErrorEntity<R, ?, B>> FullAggregator<T, B, I, R, E> build(IEntityCreator<R, B, E> creator, IErrorRepository<R> repository) throws BeanException {
                return new FullAggregator<>(factory, type, rootType, nodeTypes) {

                    @Override
                    protected FullCollector<R, B, E> create(R row) {
                        return new FullCollector<>(row, creator, repository);
                    }
                };
            }

        }

    }

}
