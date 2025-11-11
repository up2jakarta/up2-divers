package io.github.up2jakarta.csv;

import io.github.up2jakarta.csv.api.IFastRecord;
import io.github.up2jakarta.csv.api.IFullRecord;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.api.hdl.*;
import io.github.up2jakarta.csv.core.*;
import io.github.up2jakarta.csv.core.hdl.*;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Identifiable;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.csv.fmt.*;
import io.github.up2jakarta.xml.api.SeverityType;

/**
 * Base Business builder with provided {@link Up2Factory}.
 *
 * @param <B> the business data type
 */
public class BusinessBuilder<B extends DataType<B>> {

    public static final SeverityType MAX_LEVEL = SeverityType.FATAL;

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
    public <T extends Segment> UnitBuilder<T> unit(Class<T> type) {
        return new UnitBuilder<>(type);
    }

    /**
     * Creates new preconfigured builder for {@link ModeType#FAST}.
     *
     * @param type the type of business-object
     * @param <T>  the business class
     * @return new builder
     */
    public <T extends Segment> FastBuilder<T> fast(Class<T> type) {
        return new FastBuilder<>(type);
    }

    /**
     * Creates new preconfigured builder for {@link ModeType#FULL}.
     *
     * @param type the type of business-object
     * @param <T>  the business class
     * @return new builder
     */
    public <T extends Segment> FullBuilder<T> full(Class<T> type) {
        return new FullBuilder<>(type);
    }

    public final class UnitBuilder<T extends Segment> {

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
             * Creates new preconfigured instance for {@link UnitImporter} with the given fast-failure severity.
             *
             * @param creator   the error creator
             * @param failLevel the fast-failure severity
             * @return new unit-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public <R extends IRecord<I>, E extends IPropertyEvent<R, B>> UnitImporter<B, I, T, R, E> build(IPropertyCreator<R, B, E> creator, SeverityType failLevel) throws BeanException {
                if (failLevel == null) {
                    return new UnitImporter<>(factory, type, rootType, nodeTypes) {
                        @Override
                        protected PropertyCollector<R, B, E> create(R row) {
                            return new PropertyCollector<>(row, creator);
                        }
                    };
                }
                return new UnitImporter<>(factory, type, rootType, nodeTypes) {
                    @Override
                    protected PropertyCollector<R, B, E> create(R row) {
                        return new PropertyFailureCollector<>(row, creator, failLevel);
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
            public <R extends ISelfRecord<B, I, E, R>, E extends IPropertyEvent<R, B> & ISelfEvent<B, R, E>> UnitImporter<B, I, T, R, E> build(IPropertyCreator<R, B, E> creator) throws BeanException {
                return new UnitImporter<>(factory, type, rootType, nodeTypes) {
                    @Override
                    protected SelfPropertyCollector<B, R, E> create(R row) {
                        return new SelfPropertyCollector<>(row, creator);
                    }
                };
            }

            /**
             * Creates new preconfigured instance for {@link UnitImporter} with the given creator and repository.
             *
             * @param creator the error creator
             * @return new full-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public <R extends IRecord<I> & ISelfRecord<B, I, E, R> & Identifiable<?>, E extends IBusinessEvent<B, R, ?> & ISelfEvent<B, R, E>> UnitImporter<B, I, T, R, E> build(IBusinessCreator<B, R, E> creator) throws BeanException {
                return new UnitImporter<>(factory, type, rootType, nodeTypes) {
                    @Override
                    protected SelfBusinessCollector<B, R, E> create(R row) {
                        return new SelfBusinessCollector<>(row, creator, (r) -> 0);
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
            public <R extends IRecord<I> & Identifiable<?>, E extends IBusinessEvent<B, R, ?>> UnitImporter<B, I, T, R, E> build(IBusinessCreator<B, R, E> creator, IBusinessRepository<R> repository) throws BeanException {
                return new UnitImporter<>(factory, type, rootType, nodeTypes) {
                    @Override
                    protected BusinessCollector<B, R, E> create(R row) {
                        return new BusinessCollector<>(row, creator, repository);
                    }
                };
            }

        }
    }

    public final class FastBuilder<T extends Segment> {

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
             * Creates new preconfigured instance for {@link FastImporter} with the given fast-failure severity.
             *
             * @param creator   the error creator
             * @param failLevel the fast-failure severity
             * @return new fast-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public <R extends IFastRecord<I, ?>, E extends IPropertyEvent<R, B>> FastImporter<B, I, T, R, E> build(IPropertyCreator<R, B, E> creator, SeverityType failLevel) throws BeanException {
                if (failLevel == null) {
                    return new FastImporter<>(factory, type, rootType, nodeTypes) {
                        @Override
                        protected PropertyCollector<R, B, E> create(R row) {
                            return new PropertyCollector<>(row, creator);
                        }
                    };
                }
                return new FastImporter<>(factory, type, rootType, nodeTypes) {
                    @Override
                    protected PropertyCollector<R, B, E> create(R row) {
                        return new PropertyFailureCollector<>(row, creator, failLevel);
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
            public <R extends IFastRecord<I, ?> & ISelfRecord<B, I, E, R>, E extends IPropertyEvent<R, B> & ISelfEvent<B, R, E>> FastImporter<B, I, T, R, E> build(IPropertyCreator<R, B, E> creator) throws BeanException {
                return new FastImporter<>(factory, type, rootType, nodeTypes) {
                    @Override
                    protected SelfPropertyCollector<B, R, E> create(R row) {
                        return new SelfPropertyCollector<>(row, creator);
                    }
                };
            }

            /**
             * Creates new preconfigured instance for {@link FastImporter} with the given creator and repository.
             *
             * @param creator the error creator
             * @return new full-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public <R extends IFastRecord<I, ?> & ISelfRecord<B, I, E, R> & Identifiable<?>, E extends IBusinessEvent<B, R, ?> & ISelfEvent<B, R, E>> FastImporter<B, I, T, R, E> build(IBusinessCreator<B, R, E> creator) throws BeanException {
                return new FastImporter<>(factory, type, rootType, nodeTypes) {
                    @Override
                    protected SelfBusinessCollector<B, R, E> create(R row) {
                        return new SelfBusinessCollector<>(row, creator, (r) -> 0);
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
            public <R extends IFastRecord<I, ?> & Identifiable<?>, E extends IBusinessEvent<B, R, ?>> FastImporter<B, I, T, R, E> build(IBusinessCreator<B, R, E> creator, IBusinessRepository<R> repository) throws BeanException {
                return new FastImporter<>(factory, type, rootType, nodeTypes) {
                    @Override
                    protected BusinessCollector<B, R, E> create(R row) {
                        return new BusinessCollector<>(row, creator, repository);
                    }
                };
            }

        }
    }

    public final class FullBuilder<T extends Segment> {

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
            public <R extends IFullRecord<I, ?>, E extends IPropertyEvent<R, B>> FullImporter<B, I, T, R, E> build(IPropertyCreator<R, B, E> creator, SeverityType failLevel) throws BeanException {
                if (failLevel == null) {
                    return new FullImporter<>(factory, type, rootType, nodeTypes) {
                        @Override
                        protected PropertyCollector<R, B, E> create(R row) {
                            return new PropertyCollector<>(row, creator);
                        }
                    };
                }
                return new FullImporter<>(factory, type, rootType, nodeTypes) {
                    @Override
                    protected PropertyCollector<R, B, E> create(R row) {
                        return new PropertyFailureCollector<>(row, creator, failLevel);
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
            public <R extends IFullRecord<I, ?> & ISelfRecord<B, I, E, R>, E extends IPropertyEvent<R, B> & ISelfEvent<B, R, E>> FullImporter<B, I, T, R, E> build(IPropertyCreator<R, B, E> creator) throws BeanException {
                return new FullImporter<>(factory, type, rootType, nodeTypes) {
                    @Override
                    protected SelfPropertyCollector<B, R, E> create(R row) {
                        return new SelfPropertyCollector<>(row, creator);
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
            public <R extends IFullRecord<I, ?> & ISelfRecord<B, I, E, R> & Identifiable<?>, E extends IBusinessEvent<B, R, ?> & ISelfEvent<B, R, E>> FullImporter<B, I, T, R, E> build(IBusinessCreator<B, R, E> creator) throws BeanException {
                return new FullImporter<>(factory, type, rootType, nodeTypes) {
                    @Override
                    protected SelfBusinessCollector<B, R, E> create(R row) {
                        return new SelfBusinessCollector<>(row, creator, (r) -> 0);
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
            public <R extends IFullRecord<I, ?> & Identifiable<?>, E extends IBusinessEvent<B, R, ?>> FullImporter<B, I, T, R, E> build(IBusinessCreator<B, R, E> creator, IBusinessRepository<R> repository) throws BeanException {
                return new FullImporter<>(factory, type, rootType, nodeTypes) {
                    @Override
                    protected BusinessCollector<B, R, E> create(R row) {
                        return new BusinessCollector<>(row, creator, repository);
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
            public <P extends Comparable<P>, R extends FullRecord<I, P>> FullImporter<B, I, T, R, FullError<B, P, R>> build(IBusinessRepository<R> repository) throws BeanException {
                return this.build((IBusinessCreator<B, R, FullError<B, P, R>>) FullError::new, repository);
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
