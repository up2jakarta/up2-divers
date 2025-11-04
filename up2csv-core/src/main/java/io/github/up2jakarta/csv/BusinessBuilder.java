package io.github.up2jakarta.csv;

import io.github.up2jakarta.csv.api.*;
import io.github.up2jakarta.csv.api.hdl.*;
import io.github.up2jakarta.csv.core.*;
import io.github.up2jakarta.csv.core.hdl.FatalCollector;
import io.github.up2jakarta.csv.core.hdl.RecordCollector;
import io.github.up2jakarta.csv.core.hdl.TraceCollector;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Referencable;
import io.github.up2jakarta.csv.fmt.*;
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
        public <I extends IType<B, I>> FinalBuilder<I> build(I rootType, I[] nodeTypes) {
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
        public <I extends Enum<I> & IType<B, I>> FinalBuilder<I> build(I rootType) {
            return this.build(rootType, ((Class<I>) rootType.getClass()).getEnumConstants());
        }

        public final class FinalBuilder<I extends IType<B, I>> {

            private final I rootType;
            private final I[] nodeTypes;

            private FinalBuilder(I rootType, I[] nodeTypes) {
                this.rootType = rootType;
                this.nodeTypes = nodeTypes;
            }

            /**
             * Creates new preconfigured instance for {@link SimpleUnitExporter}.
             *
             * @return new unit-exporter
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public SimpleUnitExporter<T, B, I> format() throws BeanException {
                return new SimpleUnitExporter<>(factory, type, rootType, nodeTypes);
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
            public <R extends IRecord<I>, E extends IEvent<B>> UnitImporter<B, I, T, R, E> build(ICauseCreator<R, B, E> creator, SeverityType failLevel) throws BeanException {
                return new UnitImporter<>(factory, type, rootType, nodeTypes) {
                    @Override
                    protected FatalCollector<R, B, E> create(R row) {
                        return new FatalCollector<>(row, creator, failLevel);
                    }
                };
            }

            /**
             * Creates new preconfigured instance for {@link UnitImporter} with the given fast-failure severity.
             *
             * @param creator the error creator
             * @return new unit-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public <R extends IRecordCollector<B, I, E, R>, E extends IRecordEvent<B, R, E>> UnitImporter<B, I, T, R, E> build(ICauseCreator<R, B, E> creator) throws BeanException {
                return new UnitImporter<>(factory, type, rootType, nodeTypes) {
                    @Override
                    protected RecordCollector<B, E, R> create(R row) {
                        return new RecordCollector<>(row, creator);
                    }
                };
            }

            /**
             * Creates new preconfigured instance for {@link UnitImporter} with the given creator and repository.
             *
             * @param creator    the error creator
             * @param repository the error-order initial-value finder
             * @return new unit-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public <R extends IRecord<I>, E extends ITraceEvent<B, R, ?>> UnitImporter<B, I, T, R, E> build(ITraceCreator<B, R, E> creator, IRepository<R> repository) throws BeanException {
                return new UnitImporter<>(factory, type, rootType, nodeTypes) {
                    @Override
                    protected TraceCollector<B, R, E> create(R row) {
                        return new TraceCollector<>(row, creator, repository);
                    }
                };
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
        public <I extends IType<B, I>> FinalBuilder<I> build(I rootType, I[] nodeTypes) {
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
        public <I extends Enum<I> & IType<B, I>> FinalBuilder<I> build(I rootType) {
            return this.build(rootType, ((Class<I>) rootType.getClass()).getEnumConstants());
        }

        public final class FinalBuilder<I extends IType<B, I>> {

            private final I rootType;
            private final I[] nodeTypes;

            private FinalBuilder(I rootType, I[] nodeTypes) {
                this.rootType = rootType;
                this.nodeTypes = nodeTypes;
            }

            /**
             * Creates new preconfigured instance for {@link SimpleFastExporter}.
             *
             * @return new fast-exporter
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public SimpleFastExporter<T, B, I> format() throws BeanException {
                return new SimpleFastExporter<>(factory, type, rootType, nodeTypes);
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
            public <R extends IFastRecord<I>, E extends IEvent<B>> FastImporter<B, I, T, R, E> build(ICauseCreator<R, B, E> creator, SeverityType failLevel) throws BeanException {
                return new FastImporter<>(factory, type, rootType, nodeTypes) {
                    @Override
                    protected FatalCollector<R, B, E> create(R row) {
                        return new FatalCollector<>(row, creator, failLevel);
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
            public <R extends IFastRecord<I> & IRecordCollector<B, I, E, R>, E extends IRecordEvent<B, R, E>> FastImporter<B, I, T, R, E> build(ICauseCreator<R, B, E> creator) throws BeanException {
                return new FastImporter<>(factory, type, rootType, nodeTypes) {
                    @Override
                    protected RecordCollector<B, E, R> create(R row) {
                        return new RecordCollector<>(row, creator);
                    }
                };
            }

            /**
             * Creates new preconfigured instance for {@link FastImporter} with the given creator and repository.
             *
             * @param creator    the error creator
             * @param repository the error-order initial-value finder
             * @return new fast-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public <R extends IFastRecord<I>, E extends ITraceEvent<B, R, ?>> FastImporter<B, I, T, R, E> build(ITraceCreator<B, R, E> creator, IRepository<R> repository) throws BeanException {
                return new FastImporter<>(factory, type, rootType, nodeTypes) {
                    @Override
                    protected TraceCollector<B, R, E> create(R row) {
                        return new TraceCollector<>(row, creator, repository);
                    }
                };
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
        public <I extends IType<B, I>> FinalBuilder<I> build(I rootType, I[] nodeTypes) {
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
        public <I extends Enum<I> & IType<B, I>> FinalBuilder<I> build(I rootType) {
            return this.build(rootType, ((Class<I>) rootType.getClass()).getEnumConstants());
        }

        public final class FinalBuilder<I extends IType<B, I>> {

            private final I rootType;
            private final I[] nodeTypes;

            private FinalBuilder(I rootType, I[] nodeTypes) {
                this.rootType = rootType;
                this.nodeTypes = nodeTypes;
            }

            /**
             * Creates new preconfigured instance for {@link SimpleFullExporter}.
             *
             * @return new full-exporter
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public SimpleFullExporter<T, B, I> format() throws BeanException {
                return new SimpleFullExporter<>(factory, type, rootType, nodeTypes);
            }

            /**
             * Creates new preconfigured instance for {@link FullImporter} with the given creator and repository.
             *
             * @param creator   the error creator
             * @param failLevel fast-failure severity
             * @return new full-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public <R extends IFullRecord<I>, E extends IEvent<B>> FullImporter<B, I, T, R, E> build(ICauseCreator<R, B, E> creator, SeverityType failLevel) throws BeanException {
                return new FullImporter<>(factory, type, rootType, nodeTypes) {
                    @Override
                    protected FatalCollector<R, B, E> create(R row) {
                        return new FatalCollector<>(row, creator, failLevel);
                    }
                };
            }

            /**
             * Creates new preconfigured instance for {@link FullImporter} with the given creator and repository.
             *
             * @param creator the error creator
             * @return new full-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public <R extends IFullRecord<I> & IRecordCollector<B, I, E, R>, E extends IRecordEvent<B, R, E>> FullImporter<B, I, T, R, E> build(ICauseCreator<R, B, E> creator) throws BeanException {
                return new FullImporter<>(factory, type, rootType, nodeTypes) {
                    @Override
                    protected RecordCollector<B, E, R> create(R row) {
                        return new RecordCollector<>(row, creator);
                    }
                };
            }

            /**
             * Creates new preconfigured instance for {@link FullImporter} with the given creator and repository.
             *
             * @param creator    the error creator
             * @param repository the error-order initial-value finder
             * @return new full-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public <R extends IFullRecord<I>, E extends ITraceEvent<B, R, ?>> FullImporter<B, I, T, R, E> build(ITraceCreator<B, R, E> creator, IRepository<R> repository) throws BeanException {
                return new FullImporter<>(factory, type, rootType, nodeTypes) {
                    @Override
                    protected TraceCollector<B, R, E> create(R row) {
                        return new TraceCollector<>(row, creator, repository);
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
            public <R extends InputRecord<I>> FullImporter<B, I, T, R, InputError<B, R>> build(IRepository<R> repository) throws BeanException {
                return this.build((ITraceCreator<B, R, InputError<B, R>>) InputError::new, repository);
            }

            /**
             * Creates new preconfigured instance for {@link SimpleFullImporter} with the given creator and repository.
             *
             * @return new simple full-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public SimpleFullImporter<T, B, I> build() throws BeanException {
                return new SimpleFullImporter<>(factory, type, rootType, nodeTypes);
            }

        }

    }

}
