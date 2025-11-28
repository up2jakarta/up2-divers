package io.github.up2jakarta.csv;

import io.github.up2jakarta.csv.api.IFastRecord;
import io.github.up2jakarta.csv.api.IFullRecord;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.api.hdl.*;
import io.github.up2jakarta.csv.core.*;
import io.github.up2jakarta.csv.core.hdl.BusinessCollector;
import io.github.up2jakarta.csv.core.hdl.PropertyCollector;
import io.github.up2jakarta.csv.core.hdl.PropertyFailureCollector;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.IMutual;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.csv.fmt.*;
import io.github.up2jakarta.lov.ConstantProvider;
import io.github.up2jakarta.lov.SeverityType;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.lov.core.Identifiable;

import java.util.List;

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
    public <T extends Segment> Unit<T> unit(Class<T> type) {
        return new Unit<>(type);
    }

    /**
     * Creates new preconfigured builder for {@link ModeType#FAST}.
     *
     * @param type the type of business-object
     * @param <T>  the business class
     * @return new builder
     */
    public <T extends Segment> Fast<T> fast(Class<T> type) {
        return new Fast<>(type);
    }

    /**
     * Creates new preconfigured builder for {@link ModeType#FULL}.
     *
     * @param type the type of business-object
     * @param <T>  the business class
     * @return new builder
     */
    public <T extends Segment> Full<T> full(Class<T> type) {
        return new Full<>(type);
    }

    public final class Unit<T extends Segment> {
        private final Class<T> type;

        private Unit(Class<T> type) {
            this.type = type;
        }

        /**
         * Creates new preconfigured builder for import and export processing.
         *
         * @param root  the root node related to business-object type
         * @param nodes the list of nodes
         * @param <I>   the input segment type
         * @return new preconfigured final builder
         */
        public <I extends IType<B, I>> Builder<I> build(I root, List<I> nodes) {
            return new Builder<>(root, nodes);
        }

        /**
         * Creates new preconfigured builder for import and export processing.
         *
         * @param root the root node related to business-object type
         * @param <I>  the input segment type
         * @return new preconfigured final builder
         */
        public <I extends IType<B, I>> Builder<I> build(I root) {
            return this.build(root, ConstantProvider.values(root));
        }

        public final class Builder<I extends IType<B, I>> {
            private final I root;
            private final List<I> nodes;

            private Builder(I root, List<I> nodes) {
                this.root = root;
                this.nodes = nodes;
            }

            /**
             * Creates new preconfigured instance for {@link SimpleUnitExporter}.
             *
             * @return new unit-exporter
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public SimpleUnitExporter<T, B, I> export() throws BeanException {
                return new SimpleUnitExporter<>(factory, type, root, nodes);
            }

            /**
             * Creates new preconfigured instance for {@link SimpleUnitImporter}.
             *
             * @return new simple unit-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public SimpleUnitImporter<T, B, I> build() throws BeanException {
                return new SimpleUnitImporter<>(factory, type, root, nodes);
            }

            /**
             * Creates new preconfigured instance for {@link UnitImporter} with the specified event-creator.
             *
             * @param creator the event creator
             * @return new unit-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public <R extends IRecord<I>, E extends IPropertyEvent<B, R>> UnitImporter<B, I, T, R, E> build(IPropertyCreator<B, R, E> creator) throws BeanException {
                return new UnitImporter<>(factory, type, root, nodes) {
                    @Override
                    protected PropertyCollector.Builder<B, R, E> newBuilder(int size) {
                        return new PropertyCollector.Builder<>(size, creator);
                    }
                };
            }

            /**
             * Creates new preconfigured instance for {@link UnitImporter} with the specified fast-failure level.
             *
             * @param creator the event creator
             * @param level   the fast-failure level
             * @return new unit-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public <R extends IRecord<I>, E extends IPropertyEvent<B, R>> UnitImporter<B, I, T, R, E> build(IPropertyCreator<B, R, E> creator, SeverityType level) throws BeanException {
                return new UnitImporter<>(factory, type, root, nodes) {
                    @Override
                    protected PropertyFailureCollector.Builder<B, R, E> newBuilder(int size) {
                        return new PropertyFailureCollector.Builder<>(size, creator, level);
                    }
                };
            }

            /**
             * Creates new preconfigured instance for {@link UnitImporter} with the specified event-creator.
             *
             * @param creator the event creator
             * @return new unit-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public <R extends IRecord<I> & IMutualRecord<E, R>, E extends IPropertyEvent<B, R> & IMutual<R, E>> UnitImporter<B, I, T, R, E> build(IMutualPropertyCreator<B, R, E> creator) throws BeanException {
                return new UnitImporter<>(factory, type, root, nodes) {
                    @Override
                    protected PropertyCollector.Builder<B, R, E> newBuilder(int size) {
                        return new PropertyCollector.Builder<>(size, creator);
                    }
                };
            }

            /**
             * Creates new preconfigured instance for {@link UnitImporter} with the specified event-creator.
             *
             * @param creator the event creator
             * @return new full-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public <R extends IRecord<I> & IMutualRecord<E, R> & Identifiable<?>, E extends IBusinessEvent<B, R, ?> & IMutual<R, E>> UnitImporter<B, I, T, R, E> build(IMutualBusinessCreator<B, R, E> creator) throws BeanException {
                return new UnitImporter<>(factory, type, root, nodes) {
                    @Override
                    protected BusinessCollector.Builder<B, R, E> newBuilder(int size) {
                        return new BusinessCollector.Builder<>(size, creator, (r) -> 0);
                    }
                };
            }

            /**
             * Creates new preconfigured instance for {@link UnitImporter} with the specified event-creator and repository.
             *
             * @param creator    the event creator
             * @param repository the max-order finder to be used as initial value for {@link IBusinessEvent} key-order
             * @return new unit-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public <R extends IRecord<I> & Identifiable<?>, E extends IBusinessEvent<B, R, ?>> UnitImporter<B, I, T, R, E> build(IBusinessCreator<B, R, E> creator, IBusinessRepository<R> repository) throws BeanException {
                return new UnitImporter<>(factory, type, root, nodes) {
                    @Override
                    protected BusinessCollector.Builder<B, R, E> newBuilder(int size) {
                        return new BusinessCollector.Builder<>(size, creator, repository);
                    }
                };
            }
        }
    }

    public final class Fast<T extends Segment> {
        private final Class<T> type;

        private Fast(Class<T> type) {
            this.type = type;
        }

        /**
         * Creates new preconfigured builder for import and export processing.
         *
         * @param root  the root node related to business-object type
         * @param nodes the list of nodes
         * @param <I>   the input segment type
         * @return new preconfigured final builder
         */
        public <I extends IType<B, I>> Builder<I> build(I root, List<I> nodes) {
            return new Builder<>(root, nodes);
        }

        /**
         * Creates new preconfigured builder for import and export processing.
         *
         * @param root the root node related to business-object type
         * @param <I>  the input segment type
         * @return new preconfigured final builder
         */
        public <I extends IType<B, I>> Builder<I> build(I root) {
            return this.build(root, ConstantProvider.values(root));
        }

        public final class Builder<I extends IType<B, I>> {
            private final I root;
            private final List<I> nodes;

            private Builder(I root, List<I> nodes) {
                this.root = root;
                this.nodes = nodes;
            }

            /**
             * Creates new preconfigured instance for {@link SimpleFastExporter}.
             *
             * @return new fast-exporter
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public SimpleFastExporter<T, B, I> export() throws BeanException {
                return new SimpleFastExporter<>(factory, type, root, nodes);
            }

            /**
             * Creates new preconfigured instance for {@link SimpleFastImporter}.
             *
             * @return new simple fast-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public SimpleFastImporter<T, B, I> build() throws BeanException {
                return new SimpleFastImporter<>(factory, type, root, nodes);
            }

            /**
             * Creates new preconfigured instance for {@link FastImporter} with the specified event-creator.
             *
             * @param creator the event creator
             * @return new fast-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public <R extends IFastRecord<I, ?>, E extends IPropertyEvent<B, R>> FastImporter<B, I, T, R, E> build(IPropertyCreator<B, R, E> creator) throws BeanException {
                return new FastImporter<>(factory, type, root, nodes) {
                    @Override
                    protected PropertyCollector.Builder<B, R, E> newBuilder(int size) {
                        return new PropertyCollector.Builder<>(size, creator);
                    }
                };
            }

            /**
             * Creates new preconfigured instance for {@link FastImporter} with the specified fast-failure level.
             *
             * @param creator the event creator
             * @param level   the fast-failure level
             * @return new fast-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public <R extends IFastRecord<I, ?>, E extends IPropertyEvent<B, R>> FastImporter<B, I, T, R, E> build(IPropertyCreator<B, R, E> creator, SeverityType level) throws BeanException {
                return new FastImporter<>(factory, type, root, nodes) {
                    @Override
                    protected PropertyFailureCollector.Builder<B, R, E> newBuilder(int size) {
                        return new PropertyFailureCollector.Builder<>(size, creator, level);
                    }
                };
            }

            /**
             * Creates new preconfigured instance for {@link FastImporter} with the specified event-creator.
             *
             * @param creator the event creator
             * @return new fast-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public <R extends IFastRecord<I, ?> & IMutualRecord<E, R>, E extends IPropertyEvent<B, R> & IMutual<R, E>> FastImporter<B, I, T, R, E> build(IMutualPropertyCreator<B, R, E> creator) throws BeanException {
                return new FastImporter<>(factory, type, root, nodes) {
                    @Override
                    protected PropertyCollector.Builder<B, R, E> newBuilder(int size) {
                        return new PropertyCollector.Builder<>(size, creator);
                    }
                };
            }

            /**
             * Creates new preconfigured instance for {@link FastImporter} with the specified event-creator.
             *
             * @param creator the event creator
             * @return new full-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public <R extends IFastRecord<I, ?> & IMutualRecord<E, R> & Identifiable<?>, E extends IBusinessEvent<B, R, ?> & IMutual<R, E>> FastImporter<B, I, T, R, E> build(IMutualBusinessCreator<B, R, E> creator) throws BeanException {
                return new FastImporter<>(factory, type, root, nodes) {
                    @Override
                    protected BusinessCollector.Builder<B, R, E> newBuilder(int size) {
                        return new BusinessCollector.Builder<>(size, creator, (r) -> 0);
                    }
                };
            }

            /**
             * Creates new preconfigured instance for {@link FastImporter} with the specified event-creator and repository.
             *
             * @param creator    the event creator
             * @param repository the max-order finder to be used as initial value for {@link IBusinessEvent} key-order
             * @return new fast-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public <R extends IFastRecord<I, ?> & Identifiable<?>, E extends IBusinessEvent<B, R, ?>> FastImporter<B, I, T, R, E> build(IBusinessCreator<B, R, E> creator, IBusinessRepository<R> repository) throws BeanException {
                return new FastImporter<>(factory, type, root, nodes) {
                    @Override
                    protected BusinessCollector.Builder<B, R, E> newBuilder(int size) {
                        return new BusinessCollector.Builder<>(size, creator, repository);
                    }
                };
            }
        }
    }

    public final class Full<T extends Segment> {
        private final Class<T> type;

        private Full(Class<T> type) {
            this.type = type;
        }

        /**
         * Creates new preconfigured builder for import and export processing.
         *
         * @param root  the root node related to business-object type
         * @param nodes the list of nodes
         * @param <I>   the input segment type
         * @return new preconfigured final builder
         */
        public <I extends IType<B, I>> Builder<I> build(I root, List<I> nodes) {
            return new Builder<>(root, nodes);
        }

        /**
         * Creates new preconfigured builder for import and export processing.
         *
         * @param root the root node related to business-object type
         * @param <I>  the input segment type
         * @return new preconfigured final builder
         */
        public <I extends IType<B, I>> Builder<I> build(I root) {
            return this.build(root, ConstantProvider.values(root));
        }

        public final class Builder<I extends IType<B, I>> {
            private final I root;
            private final List<I> nodes;

            private Builder(I root, List<I> nodes) {
                this.root = root;
                this.nodes = nodes;
            }

            /**
             * Creates new preconfigured instance for {@link SimpleFullExporter}.
             *
             * @return new full-exporter
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public SimpleFullExporter<T, B, I> export() throws BeanException {
                return new SimpleFullExporter<>(factory, type, root, nodes);
            }

            /**
             * Creates new preconfigured instance for {@link FullImporter} with the specified event-creator.
             *
             * @param creator the event creator
             * @return new full-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public <R extends IFullRecord<I, ?>, E extends IPropertyEvent<B, R>> FullImporter<B, I, T, R, E> build(IPropertyCreator<B, R, E> creator) throws BeanException {
                return new FullImporter<>(factory, type, root, nodes) {
                    @Override
                    protected PropertyCollector.Builder<B, R, E> newBuilder(int size) {
                        return new PropertyCollector.Builder<>(size, creator);
                    }
                };
            }

            /**
             * Creates new preconfigured instance for {@link FullImporter} with the fast-failure level.
             *
             * @param creator the event creator
             * @param level   fast-failure level
             * @return new full-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public <R extends IFullRecord<I, ?>, E extends IPropertyEvent<B, R>> FullImporter<B, I, T, R, E> build(IPropertyCreator<B, R, E> creator, SeverityType level) throws BeanException {
                return new FullImporter<>(factory, type, root, nodes) {
                    @Override
                    protected PropertyFailureCollector.Builder<B, R, E> newBuilder(int size) {
                        return new PropertyFailureCollector.Builder<>(size, creator, level);
                    }
                };
            }

            /**
             * Creates new preconfigured instance for {@link FullImporter} with the specified event-creator.
             *
             * @param creator the event creator
             * @return new full-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public <R extends IFullRecord<I, ?> & IMutualRecord<E, R>, E extends IPropertyEvent<B, R> & IMutual<R, E>> FullImporter<B, I, T, R, E> build(IMutualPropertyCreator<B, R, E> creator) throws BeanException {
                return new FullImporter<>(factory, type, root, nodes) {
                    @Override
                    protected PropertyCollector.Builder<B, R, E> newBuilder(int size) {
                        return new PropertyCollector.Builder<>(size, creator);
                    }
                };
            }

            /**
             * Creates new preconfigured instance for {@link FullImporter} with the specified event-creator.
             *
             * @param creator the event creator
             * @return new full-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public <R extends IFullRecord<I, ?> & IMutualRecord<E, R> & Identifiable<?>, E extends IBusinessEvent<B, R, ?> & IMutual<R, E>> FullImporter<B, I, T, R, E> build(IMutualBusinessCreator<B, R, E> creator) throws BeanException {
                return new FullImporter<>(factory, type, root, nodes) {
                    @Override
                    protected BusinessCollector.Builder<B, R, E> newBuilder(int size) {
                        return new BusinessCollector.Builder<>(size, creator, (r) -> 0);
                    }
                };
            }

            /**
             * Creates new preconfigured instance for {@link FullImporter} with the specified event-creator and repository.
             *
             * @param creator    the event creator
             * @param repository the max-order finder to be used as initial value for {@link IBusinessEvent} key-order
             * @return new full-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public <R extends IFullRecord<I, ?> & Identifiable<?>, E extends IBusinessEvent<B, R, ?>> FullImporter<B, I, T, R, E> build(IBusinessCreator<B, R, E> creator, IBusinessRepository<R> repository) throws BeanException {
                return new FullImporter<>(factory, type, root, nodes) {
                    @Override
                    protected BusinessCollector.Builder<B, R, E> newBuilder(int size) {
                        return new BusinessCollector.Builder<>(size, creator, repository);
                    }
                };
            }

            /**
             * Creates new preconfigured instance for {@link FullImporter} with the specified repository.
             *
             * @param repository the max-order finder to be used as initial value for {@link IBusinessEvent} key-order
             * @return new full-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public <P extends Comparable<P>, R extends FullRecord<I, P>> FullImporter<B, I, T, R, FullError<B, P, R>> build(IBusinessRepository<R> repository) throws BeanException {
                return this.build((IBusinessCreator<B, R, FullError<B, P, R>>) FullError::new, repository);
            }

            /**
             * Creates new preconfigured instance for {@link SimpleFullImporter}.
             *
             * @return new simple full-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public SimpleFullImporter<T, B, I> build() throws BeanException {
                return new SimpleFullImporter<>(factory, type, root, nodes);
            }
        }
    }

}
