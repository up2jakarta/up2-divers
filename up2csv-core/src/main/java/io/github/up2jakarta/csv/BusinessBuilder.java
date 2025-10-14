package io.github.up2jakarta.csv;

import io.github.up2jakarta.csv.api.IError;
import io.github.up2jakarta.csv.api.IFullType;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.api.hdl.*;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.MapperFactory;
import io.github.up2jakarta.csv.data.BusinessObject;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.impl.FastCollector;
import io.github.up2jakarta.csv.impl.FullCollector;
import io.github.up2jakarta.csv.impl.SimpleAggregator;
import io.github.up2jakarta.csv.ops.*;
import io.github.up2jakarta.xml.api.SeverityType;

/**
 * Base Business builder with provided {@link MapperFactory}.
 *
 * @param <B> the business data type
 */
public class BusinessBuilder<B extends DataType<B>> {

    public static final SeverityType DEFAULT_LEVEL = SeverityType.FATAL;

    private final MapperFactory<B> factory;

    public BusinessBuilder(MapperFactory<B> factory) {
        this.factory = factory;
    }

    /**
     * Creates new preconfigured builder for {@link ModeType#FAST}.
     *
     * @param type the type of business-object
     * @param <T>  the business class
     * @return new builder
     */
    public <T extends BusinessObject> FastBuilder<T> fast(Class<T> type) {
        return new FastBuilder<>(type);
    }

    /**
     * Creates new preconfigured builder for {@link ModeType#FULL}.
     *
     * @param type the type of business-object
     * @param <T>  the business class
     * @return new builder
     */
    public <T extends BusinessObject> FullBuilder<T> full(Class<T> type) {
        return new FullBuilder<>(type);
    }

    public final class FastBuilder<T extends BusinessObject> {

        private final Class<T> type;

        private FastBuilder(Class<T> type) {
            this.type = type;
        }

        /**
         * Creates new preconfigured builder for basic processor aka segregation only.
         *
         * @param rootType  the root node related to business-object type
         * @param nodeTypes the list of nodes
         * @param <I>       the segment type
         * @return new preconfigured final builder
         */
        public <I extends IType<B, I>> FinalFastBuilder<I> fast(I rootType, I[] nodeTypes) {
            return new FinalFastBuilder<>(rootType, nodeTypes);
        }

        /**
         * Creates new preconfigured builder for basic processor aka segregation only.
         *
         * @param rootType the root node related to business-object type
         * @param <I>      the segment type
         * @return new preconfigured final builder
         */
        @SuppressWarnings("unchecked")
        public <I extends Enum<I> & IType<B, I>> FinalFastBuilder<I> fast(I rootType) {
            return this.fast(rootType, ((Class<I>) rootType.getClass()).getEnumConstants());
        }

        /**
         * Creates new preconfigured builder for full processor aka segregation and aggregation.
         *
         * @param rootType  the root node related to business-object type
         * @param nodeTypes the list of nodes
         * @param <I>       the segment type
         * @return new preconfigured final builder
         */
        public <I extends IFullType<B, I>> FinalFullBuilder<I> full(I rootType, I[] nodeTypes) {
            return new FinalFullBuilder<>(rootType, nodeTypes);
        }

        /**
         * Creates new preconfigured builder for full processor aka segregation and aggregation.
         *
         * @param rootType the root node related to business-object type
         * @param <I>      the segment type
         * @return new preconfigured final builder
         */
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

            /**
             * Creates new preconfigured instance for {@link FastSeparator}.
             *
             * @return new fast separator
             * @throws BeanException for any missing or wrong java-beans configuration
             */
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

            /**
             * Creates new preconfigured instance for {@link SimpleAggregator} with default fast-failure severity.
             *
             * @return new simple aggregator
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public SimpleAggregator<T, B, I> build() throws BeanException {
                return new SimpleAggregator<>(factory, type, rootType, nodeTypes);
            }

            /**
             * Creates new preconfigured instance for {@link SimpleAggregator} with the given fast-failure severity.
             *
             * @param failLevel the fast-failure severity
             * @return new simple aggregator
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public SimpleAggregator<T, B, I> build(SeverityType failLevel) throws BeanException {
                return new SimpleAggregator<>(factory, type, rootType, nodeTypes, failLevel);
            }

            /**
             * Creates new preconfigured instance for {@link FastAggregator} with the given fast-failure severity.
             *
             * @param creator   the error creator
             * @param failLevel the fast-failure severity
             * @return new fast aggregation
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public <R extends IRecord<I>, E extends IError<B>> FastAggregator<T, B, I, R, E> build(IErrorCreator<R, B, E> creator, SeverityType failLevel) throws BeanException {
                return new FastAggregator<>(factory, type, rootType, nodeTypes) {
                    @Override
                    protected FastCollector<R, B, E> create(R row) {
                        return new FastCollector<>(row, creator, failLevel);
                    }
                };
            }

            /**
             * Creates new preconfigured instance for {@link FastAggregator} with default fast-failure severity.
             *
             * @param creator the error creator
             * @return new fast aggregator
             * @throws BeanException for any missing or wrong java-beans configuration
             */
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

        /**
         * Creates new preconfigured builder for basic processor aka segregation only.
         *
         * @param rootType  the root node related to business-object type
         * @param nodeTypes the list of nodes
         * @param <I>       the segment type
         * @return new preconfigured final builder
         */
        public <I extends IType<B, I>> FinalFastBuilder<I> fast(I rootType, I[] nodeTypes) {
            return new FinalFastBuilder<>(rootType, nodeTypes);
        }

        /**
         * Creates new preconfigured builder for basic processor aka segregation only.
         *
         * @param rootType the root node related to business-object type
         * @param <I>      the segment type
         * @return new preconfigured final builder
         */
        @SuppressWarnings("unchecked")
        public <I extends Enum<I> & IType<B, I>> FinalFastBuilder<I> fast(I rootType) {
            return this.fast(rootType, ((Class<I>) rootType.getClass()).getEnumConstants());
        }

        /**
         * Creates new preconfigured builder for full processor aka segregation and aggregation.
         *
         * @param rootType  the root node related to business-object type
         * @param nodeTypes the list of nodes
         * @param <I>       the segment type
         * @return new preconfigured final builder
         */
        public <I extends IFullType<B, I>> FinalFullBuilder<I> full(I rootType, I[] nodeTypes) {
            return new FinalFullBuilder<>(rootType, nodeTypes);
        }

        /**
         * Creates new preconfigured builder for full processor aka segregation and aggregation.
         *
         * @param rootType the root node related to business-object type
         * @param <I>      the segment type
         * @return new preconfigured final builder
         */
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

            /**
             * Creates new preconfigured instance for {@link FullSeparator}.
             *
             * @return new full separator
             * @throws BeanException for any missing or wrong java-beans configuration
             */
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

            /**
             * Creates new preconfigured instance for {@link FullAggregator} with the given creator and repository.
             *
             * @param creator    the error creator
             * @param repository the error-order initial-value finder
             * @return new full aggregation
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public <R extends IRecordEntity<I, ?, ?>, E extends IErrorEntity<R, ?, B>> FullAggregator<T, B, I, R, E> build(IEntityCreator<R, B, E> creator, IErrorRepository<R> repository) throws BeanException {
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
