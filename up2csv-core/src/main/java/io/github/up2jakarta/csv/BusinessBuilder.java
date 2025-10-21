package io.github.up2jakarta.csv;

import io.github.up2jakarta.csv.api.IError;
import io.github.up2jakarta.csv.api.IFullType;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.hdl.*;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.ModeType;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Referencable;
import io.github.up2jakarta.csv.fmt.*;
import io.github.up2jakarta.csv.fmt.hdl.FastCollector;
import io.github.up2jakarta.csv.fmt.hdl.FullCollector;
import io.github.up2jakarta.csv.fmt.hdl.PathError;
import io.github.up2jakarta.csv.fmt.hdl.PathRecord;
import io.github.up2jakarta.xml.api.SeverityType;

/**
 * Base Business builder with provided {@link Up2Factory}.
 *
 * @param <B> the business data type
 */
public class BusinessBuilder<B extends DataType<B>> {

    public static final SeverityType DEFAULT_LEVEL = SeverityType.FATAL;

    private final Up2Factory<B> factory;

    public BusinessBuilder(Up2Factory<B> factory) {
        this.factory = factory;
    }

    /**
     * Creates new preconfigured builder for {@link ModeType#UNIT}.
     *
     * @param type the type of business-object
     * @param <T>  the business class
     * @return new builder
     */
    public <T extends Referencable> UnitBuilder<T> unit(Class<T> type) {
        return new UnitBuilder<>(type);
    }

    /**
     * Creates new preconfigured builder for {@link ModeType#FAST}.
     *
     * @param type the type of business-object
     * @param <T>  the business class
     * @return new builder
     */
    public <T extends Referencable> FastBuilder<T> fast(Class<T> type) {
        return new FastBuilder<>(type);
    }

    /**
     * Creates new preconfigured builder for {@link ModeType#FULL}.
     *
     * @param type the type of business-object
     * @param <T>  the business class
     * @return new builder
     */
    public <T extends Referencable> FullBuilder<T> full(Class<T> type) {
        return new FullBuilder<>(type);
    }

    public final class UnitBuilder<T extends Referencable> {

        private final Class<T> type;

        private UnitBuilder(Class<T> type) {
            this.type = type;
        }

        /**
         * Creates new preconfigured builder for full processor aka segregation and aggregation.
         *
         * @param rootType  the root node related to business-object type
         * @param nodeTypes the list of nodes
         * @param <I>       the segment type
         * @return new preconfigured final builder
         */
        public <I extends IFullType<B, I>> FinalBuilder<I> build(I rootType, I[] nodeTypes) {
            return new FinalBuilder<>(rootType, nodeTypes);
        }

        /**
         * Creates new preconfigured builder for full processor aka segregation and aggregation.
         *
         * @param rootType the root node related to business-object type
         * @param <I>      the segment type
         * @return new preconfigured final builder
         */
        @SuppressWarnings("unchecked")
        public <I extends Enum<I> & IFullType<B, I>> FinalBuilder<I> build(I rootType) {
            return this.build(rootType, ((Class<I>) rootType.getClass()).getEnumConstants());
        }

        public final class FinalBuilder<I extends IFullType<B, I>> {

            private final I rootType;
            private final I[] nodeTypes;

            private FinalBuilder(I rootType, I[] nodeTypes) {
                this.rootType = rootType;
                this.nodeTypes = nodeTypes;
            }

            /**
             * Creates new preconfigured instance for {@link UnitExporter}.
             *
             * @return new unit-exporter
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public UnitExporter<T, B, I> format() throws BeanException {
                return new UnitExporter<>(factory, type, rootType, nodeTypes);
            }

            /**
             * Creates new preconfigured instance for {@link SimpleUnitImporter} with default fast-failure severity.
             *
             * @return new simple unit-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public SimpleUnitImporter<T, B, I> build() throws BeanException {
                return new SimpleUnitImporter<>(factory, type, rootType, nodeTypes);
            }

            /**
             * Creates new preconfigured instance for {@link SimpleUnitImporter} with the given fast-failure severity.
             *
             * @param failLevel the fast-failure severity
             * @return new simple unit-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public SimpleUnitImporter<T, B, I> build(SeverityType failLevel) throws BeanException {
                return new SimpleUnitImporter<>(factory, type, rootType, nodeTypes, failLevel);
            }

            /**
             * Creates new preconfigured instance for {@link UnitImporter} with the given fast-failure severity.
             *
             * @param creator   the error creator
             * @param failLevel the fast-failure severity
             * @return new unit-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public <R extends IRecord<I>, E extends IError<B>> UnitImporter<T, B, I, R, E> build(IErrorCreator<R, B, E> creator, SeverityType failLevel) throws BeanException {
                return new UnitImporter<>(factory, type, rootType, nodeTypes) {
                    @Override
                    protected FastCollector<R, B, E> create(R row) {
                        return new FastCollector<>(row, creator, failLevel);
                    }
                };
            }

            /**
             * Creates new preconfigured instance for {@link UnitImporter} with default fast-failure severity.
             *
             * @param creator the error creator
             * @return new unit-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public <R extends IRecord<I>, E extends IError<B>> UnitImporter<T, B, I, R, E> build(IErrorCreator<R, B, E> creator) throws BeanException {
                return build(creator, DEFAULT_LEVEL);
            }

        }
    }

    public final class FastBuilder<T extends Referencable> {

        private final Class<T> type;

        private FastBuilder(Class<T> type) {
            this.type = type;
        }

        /**
         * Creates new preconfigured builder for full processor aka segregation and aggregation.
         *
         * @param rootType  the root node related to business-object type
         * @param nodeTypes the list of nodes
         * @param <I>       the segment type
         * @return new preconfigured final builder
         */
        public <I extends IFullType<B, I>> FinalBuilder<I> build(I rootType, I[] nodeTypes) {
            return new FinalBuilder<>(rootType, nodeTypes);
        }

        /**
         * Creates new preconfigured builder for full processor aka segregation and aggregation.
         *
         * @param rootType the root node related to business-object type
         * @param <I>      the segment type
         * @return new preconfigured final builder
         */
        @SuppressWarnings("unchecked")
        public <I extends Enum<I> & IFullType<B, I>> FinalBuilder<I> build(I rootType) {
            return this.build(rootType, ((Class<I>) rootType.getClass()).getEnumConstants());
        }

        public final class FinalBuilder<I extends IFullType<B, I>> {

            private final I rootType;
            private final I[] nodeTypes;

            private FinalBuilder(I rootType, I[] nodeTypes) {
                this.rootType = rootType;
                this.nodeTypes = nodeTypes;
            }

            /**
             * Creates new preconfigured instance for {@link FastExporter}.
             *
             * @return new fast-exporter
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public FastExporter<T, B, I> format() throws BeanException {
                return new FastExporter<>(factory, type, rootType, nodeTypes);
            }

            /**
             * Creates new preconfigured instance for {@link SimpleFastImporter} with default fast-failure severity.
             *
             * @return new simple fast-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public SimpleFastImporter<T, B, I> build() throws BeanException {
                return new SimpleFastImporter<>(factory, type, rootType, nodeTypes);
            }

            /**
             * Creates new preconfigured instance for {@link SimpleFastImporter} with the given fast-failure severity.
             *
             * @param failLevel the fast-failure severity
             * @return new simple fast-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public SimpleFastImporter<T, B, I> build(SeverityType failLevel) throws BeanException {
                return new SimpleFastImporter<>(factory, type, rootType, nodeTypes, failLevel);
            }

            /**
             * Creates new preconfigured instance for {@link FastImporter} with the given fast-failure severity.
             *
             * @param creator   the error creator
             * @param failLevel the fast-failure severity
             * @return new fast-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public <R extends IRecord<I>, E extends IError<B>> FastImporter<T, B, I, R, E> build(IErrorCreator<R, B, E> creator, SeverityType failLevel) throws BeanException {
                return new FastImporter<>(factory, type, rootType, nodeTypes) {
                    @Override
                    protected FastCollector<R, B, E> create(R row) {
                        return new FastCollector<>(row, creator, failLevel);
                    }
                };
            }

            /**
             * Creates new preconfigured instance for {@link FastImporter} with default fast-failure severity.
             *
             * @param creator the error creator
             * @return new fast-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public <R extends IRecord<I>, E extends IError<B>> FastImporter<T, B, I, R, E> build(IErrorCreator<R, B, E> creator) throws BeanException {
                return build(creator, DEFAULT_LEVEL);
            }

        }
    }

    public final class FullBuilder<T extends Referencable> {

        private final Class<T> type;

        private FullBuilder(Class<T> type) {
            this.type = type;
        }

        /**
         * Creates new preconfigured builder for full processor aka segregation and aggregation.
         *
         * @param rootType  the root node related to business-object type
         * @param nodeTypes the list of nodes
         * @param <I>       the segment type
         * @return new preconfigured final builder
         */
        public <I extends IFullType<B, I>> FinalBuilder<I> build(I rootType, I[] nodeTypes) {
            return new FinalBuilder<>(rootType, nodeTypes);
        }

        /**
         * Creates new preconfigured builder for full processor aka segregation and aggregation.
         *
         * @param rootType the root node related to business-object type
         * @param <I>      the segment type
         * @return new preconfigured final builder
         */
        @SuppressWarnings("unchecked")
        public <I extends Enum<I> & IFullType<B, I>> FinalBuilder<I> build(I rootType) {
            return this.build(rootType, ((Class<I>) rootType.getClass()).getEnumConstants());
        }

        public final class FinalBuilder<I extends IFullType<B, I>> {

            private final I rootType;
            private final I[] nodeTypes;

            private FinalBuilder(I rootType, I[] nodeTypes) {
                this.rootType = rootType;
                this.nodeTypes = nodeTypes;
            }

            /**
             * Creates new preconfigured instance for {@link FullExporter}.
             *
             * @return new full-exporter
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public FullExporter<T, B, I> format() throws BeanException {
                return new FullExporter<>(factory, type, rootType, nodeTypes);
            }

            /**
             * Creates new preconfigured instance for {@link FullImporter} with the given creator and repository.
             *
             * @param creator    the error creator
             * @param repository the error-order initial-value finder
             * @return new full-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public <R extends IRecordEntity<I, ?, ?>, E extends IErrorEntity<R, ?, B>> FullImporter<T, B, I, R, E> build(IEntityCreator<R, B, E> creator, IErrorRepository<R> repository) throws BeanException {
                return new FullImporter<>(factory, type, rootType, nodeTypes) {

                    @Override
                    protected FullCollector<R, B, E> create(R row) {
                        return new FullCollector<>(row, creator, repository);
                    }
                };
            }

            /**
             * Creates new preconfigured instance for {@link FullImporter} with the given creator and repository.
             *
             * @param repository the error-order initial-value finder
             * @return new full-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public <R extends PathRecord<I>> FullImporter<T, B, I, R, PathError<B, R>> build(IErrorRepository<R> repository) throws BeanException {
                return this.build((IEntityCreator<R, B, PathError<B, R>>) PathError::new, repository);
            }

            /**
             * Creates new preconfigured instance for {@link SimpleFullImporter} with the given creator and repository.
             *
             * @return new simple full-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public FullImporter<T, B, I, PathRecord<I>, PathError<B, PathRecord<I>>> build() throws BeanException {
                return new SimpleFullImporter<>(factory, type, rootType, nodeTypes);
            }

        }

    }

}
