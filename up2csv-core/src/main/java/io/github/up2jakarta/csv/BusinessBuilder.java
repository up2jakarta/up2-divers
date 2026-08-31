package io.github.up2jakarta.csv;

import io.github.up2jakarta.csv.api.*;
import io.github.up2jakarta.csv.api.hdl.*;
import io.github.up2jakarta.csv.core.MessImporter;
import io.github.up2jakarta.csv.core.NeatImporter;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.data.*;
import io.github.up2jakarta.csv.hdl.BusinessCollector;
import io.github.up2jakarta.csv.hdl.PropertyCollector;
import io.github.up2jakarta.csv.hdl.PropertyFailureCollector;
import io.github.up2jakarta.lov.SeverityType;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.lov.core.Identifiable;

/**
 * Base Business builder with provided {@link Up2Factory}.
 *
 * @param <B> the business term type
 */
public class BusinessBuilder<B extends ITerm<B>> {
    public static final SeverityType MAX_LEVEL = SeverityType.FATAL;

    private final Up2Factory<B> factory;

    public BusinessBuilder(Up2Factory<B> factory) {
        this.factory = factory;
    }

    /**
     * Creates new preconfigured builder for {@link io.github.up2jakarta.csv.core.ModeType#NEAT}.
     *
     * @param type the type of business-object
     * @param <T>  the business object type
     * @return new builder
     */
    public <T extends Segment> NB<T> neat(Class<T> type) {
        return new NB<>(type);
    }

    /**
     * Creates new preconfigured builder for {@link io.github.up2jakarta.csv.core.ModeType#MESS}.
     *
     * @param type the type of business-object
     * @param <T>  the business object type
     * @return new builder
     */
    public <T extends Segment> MB<T> mess(Class<T> type) {
        return new MB<>(type);
    }

    /**
     * Creates new preconfigured builder for {@link io.github.up2jakarta.csv.core.IMode#FULL}.
     *
     * @param type the type of business-object
     * @param <T>  business object type
     * @return new builder
     */
    public <T extends Segment> FB<T> full(Class<T> type) {
        return new FB<>(type);
    }

    public final class NB<T extends Segment> {
        private final Class<T> st;

        private NB(Class<T> st) {
            this.st = st;
        }

        /**
         * Creates new preconfigured builder for import and export processing.
         *
         * @param type the type of input-segment
         * @param <I>  the input segment type
         * @return new preconfigured final builder
         */
        public <I extends Enum<I> & IType<I>> Builder<I> build(Class<I> type) {
            return new Builder<>(type);
        }

        public final class Builder<I extends Enum<I> & IType<I>> {
            private final Class<I> it;

            private Builder(Class<I> type) {
                this.it = type;
            }

            /**
             * Creates new preconfigured instance for {@link SimpleNeatExporter}.
             *
             * @return new neat-exporter
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public SimpleNeatExporter<T, B, I> export() throws BeanException {
                return new SimpleNeatExporter<>(factory, st, it);
            }

            /**
             * Creates new preconfigured instance for {@link SimpleNeatImporter}.
             *
             * @return new simple neat-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public SimpleNeatImporter<T, B, I> build() throws BeanException {
                return new SimpleNeatImporter<>(factory, st, it);
            }

            /**
             * Creates new preconfigured instance for {@link NeatImporter} with the specified event-creator.
             *
             * @param creator the event creator
             * @return new neat-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public <R extends IRecord<I>, E extends IPropertyEvent<B, R>> NeatImporter<B, I, T, R, E> build(IPropertyCreator<B, R, E> creator) throws BeanException {
                return new NeatImporter<>(factory, st, it) {
                    @Override
                    protected PropertyCollector.Builder<B, R, E> newBuilder(int size) {
                        return new PropertyCollector.Builder<>(size, creator);
                    }
                };
            }

            /**
             * Creates new preconfigured instance for {@link NeatImporter} with the specified fast-failure level.
             *
             * @param creator the event creator
             * @param level   the fast-failure level
             * @return new neat-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public <R extends IRecord<I>, E extends IPropertyEvent<B, R>> NeatImporter<B, I, T, R, E> build(IPropertyCreator<B, R, E> creator, SeverityType level) throws BeanException {
                return new NeatImporter<>(factory, st, it) {
                    @Override
                    protected PropertyFailureCollector.Builder<B, R, E> newBuilder(int size) {
                        return new PropertyFailureCollector.Builder<>(size, creator, level);
                    }
                };
            }

            /**
             * Creates new preconfigured instance for {@link NeatImporter} with the specified event-creator.
             *
             * @param creator the event creator
             * @return new neat-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public <R extends IRecord<I> & IMutualRecord<E, R>, E extends IPropertyEvent<B, R> & IMutual<R, E>> NeatImporter<B, I, T, R, E> build(IMutualPropertyCreator<B, R, E> creator) throws BeanException {
                return new NeatImporter<>(factory, st, it) {
                    @Override
                    protected PropertyCollector.Builder<B, R, E> newBuilder(int size) {
                        return new PropertyCollector.Builder<>(size, creator);
                    }
                };
            }

            /**
             * Creates new preconfigured instance for {@link NeatImporter} with the specified event-creator.
             *
             * @param creator the event creator
             * @return new full-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public <R extends IRecord<I> & IMutualRecord<E, R> & Identifiable<?>, E extends IBusinessEvent<B, R, ?> & IMutual<R, E>> NeatImporter<B, I, T, R, E> build(IMutualBusinessCreator<B, R, E> creator) throws BeanException {
                return new NeatImporter<>(factory, st, it) {
                    @Override
                    protected BusinessCollector.Builder<B, R, E> newBuilder(int size) {
                        return new BusinessCollector.Builder<>(size, creator, r -> 0);
                    }
                };
            }

            /**
             * Creates new preconfigured instance for {@link NeatImporter} with the specified event-creator and repository.
             *
             * @param creator    the event creator
             * @param repository the max-order finder to be used as initial value for {@link IBusinessEvent} key-order
             * @return new neat-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public <R extends IRecord<I> & Identifiable<?>, E extends IBusinessEvent<B, R, ?>> NeatImporter<B, I, T, R, E> build(IBusinessCreator<B, R, E> creator, IBusinessRepository<R> repository) throws BeanException {
                return new NeatImporter<>(factory, st, it) {
                    @Override
                    protected BusinessCollector.Builder<B, R, E> newBuilder(int size) {
                        return new BusinessCollector.Builder<>(size, creator, repository);
                    }
                };
            }
        }
    }

    public final class MB<T extends Segment> {
        private final Class<T> st;

        private MB(Class<T> st) {
            this.st = st;
        }

        /**
         * Creates new preconfigured builder for import and export processing.
         *
         * @param type the type of input-segment
         * @param <I>  the input segment type
         * @return new preconfigured final builder
         */
        public <I extends Enum<I> & IType<I>> Builder<I> build(Class<I> type) {
            return new Builder<>(type);
        }

        public final class Builder<I extends Enum<I> & IType<I>> {
            private final Class<I> it;

            private Builder(Class<I> type) {
                this.it = type;
            }

            /**
             * Creates new preconfigured instance for {@link SimpleMessExporter}.
             *
             * @return new mess-exporter
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public SimpleMessExporter<T, B, I> export() throws BeanException {
                return new SimpleMessExporter<>(factory, st, it);
            }

            /**
             * Creates new preconfigured instance for {@link SimpleMessImporter}.
             *
             * @return new simple mess-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public SimpleMessImporter<T, B, I> build() throws BeanException {
                return new SimpleMessImporter<>(factory, st, it);
            }

            /**
             * Creates new preconfigured instance for {@link MessImporter} with the specified event-creator.
             *
             * @param creator the event creator
             * @return new mess-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public <R extends IMessRecord<I>, E extends IPropertyEvent<B, R>> MessImporter<B, I, T, R, E> build(IPropertyCreator<B, R, E> creator) throws BeanException {
                return new MessImporter<>(factory, st, it) {
                    @Override
                    protected PropertyCollector.Builder<B, R, E> newBuilder(int size) {
                        return new PropertyCollector.Builder<>(size, creator);
                    }
                };
            }

            /**
             * Creates new preconfigured instance for {@link MessImporter} with the specified fast-failure level.
             *
             * @param creator the event creator
             * @param level   the fast-failure level
             * @return new mess-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public <R extends IMessRecord<I>, E extends IPropertyEvent<B, R>> MessImporter<B, I, T, R, E> build(IPropertyCreator<B, R, E> creator, SeverityType level) throws BeanException {
                return new MessImporter<>(factory, st, it) {
                    @Override
                    protected PropertyFailureCollector.Builder<B, R, E> newBuilder(int size) {
                        return new PropertyFailureCollector.Builder<>(size, creator, level);
                    }
                };
            }

            /**
             * Creates new preconfigured instance for {@link MessImporter} with the specified event-creator.
             *
             * @param creator the event creator
             * @return new mess-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public <R extends IMessRecord<I> & IMutualRecord<E, R>, E extends IPropertyEvent<B, R> & IMutual<R, E>> MessImporter<B, I, T, R, E> build(IMutualPropertyCreator<B, R, E> creator) throws BeanException {
                return new MessImporter<>(factory, st, it) {
                    @Override
                    protected PropertyCollector.Builder<B, R, E> newBuilder(int size) {
                        return new PropertyCollector.Builder<>(size, creator);
                    }
                };
            }

            /**
             * Creates new preconfigured instance for {@link MessImporter} with the specified event-creator.
             *
             * @param creator the event creator
             * @return new full-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public <R extends IMessRecord<I> & IMutualRecord<E, R> & Identifiable<?>, E extends IBusinessEvent<B, R, ?> & IMutual<R, E>> MessImporter<B, I, T, R, E> build(IMutualBusinessCreator<B, R, E> creator) throws BeanException {
                return new MessImporter<>(factory, st, it) {
                    @Override
                    protected BusinessCollector.Builder<B, R, E> newBuilder(int size) {
                        return new BusinessCollector.Builder<>(size, creator, r -> 0);
                    }
                };
            }

            /**
             * Creates new preconfigured instance for {@link MessImporter} with the specified event-creator and repository.
             *
             * @param creator    the event creator
             * @param repository the max-order finder to be used as initial value for {@link IBusinessEvent} key-order
             * @return new mess-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public <R extends IMessRecord<I> & Identifiable<?>, E extends IBusinessEvent<B, R, ?>> MessImporter<B, I, T, R, E> build(IBusinessCreator<B, R, E> creator, IBusinessRepository<R> repository) throws BeanException {
                return new MessImporter<>(factory, st, it) {
                    @Override
                    protected BusinessCollector.Builder<B, R, E> newBuilder(int size) {
                        return new BusinessCollector.Builder<>(size, creator, repository);
                    }
                };
            }
        }
    }

    public final class FB<T extends Segment> {
        private final Class<T> st;

        private FB(Class<T> st) {
            this.st = st;
        }

        /**
         * Creates new preconfigured builder for import and export processing.
         *
         * @param type the type of input-segment
         * @param <I>  the input segment type
         * @return new preconfigured final builder
         */
        public <I extends Enum<I> & IType<I>> Builder<I> build(Class<I> type) {
            return new Builder<>(type);
        }

        public final class Builder<I extends Enum<I> & IType<I>> {
            private final Class<I> it;

            private Builder(Class<I> type) {
                this.it = type;
            }

            /**
             * Creates new preconfigured instance for {@link SimpleFullExporter}.
             *
             * @return new full-exporter
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public SimpleFullExporter<T, B, I> export() throws BeanException {
                return new SimpleFullExporter<>(factory, st, it);
            }

            /**
             * Creates new preconfigured instance for {@link FullImporter} with the specified event-creator.
             *
             * @param creator the event creator
             * @return new full-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public <R extends IFullRecord<I>, E extends IPropertyEvent<B, R>> FullImporter<B, I, T, R, E> build(IPropertyCreator<B, R, E> creator) throws BeanException {
                return new FullImporter<>(factory, st, it) {
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
            public <R extends IFullRecord<I>, E extends IPropertyEvent<B, R>> FullImporter<B, I, T, R, E> build(IPropertyCreator<B, R, E> creator, SeverityType level) throws BeanException {
                return new FullImporter<>(factory, st, it) {
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
            public <R extends IFullRecord<I> & IMutualRecord<E, R>, E extends IPropertyEvent<B, R> & IMutual<R, E>> FullImporter<B, I, T, R, E> build(IMutualPropertyCreator<B, R, E> creator) throws BeanException {
                return new FullImporter<>(factory, st, it) {
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
            public <R extends IFullRecord<I> & Identifiable<?> & IMutualRecord<E, R>, E extends IBusinessEvent<B, R, ?> & IMutual<R, E>> FullImporter<B, I, T, R, E> build(IMutualBusinessCreator<B, R, E> creator) throws BeanException {
                return new FullImporter<>(factory, st, it) {
                    @Override
                    protected BusinessCollector.Builder<B, R, E> newBuilder(int size) {
                        return new BusinessCollector.Builder<>(size, creator, r -> 0);
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
            public <R extends IFullRecord<I> & Identifiable<?>, E extends IBusinessEvent<B, R, ?>> FullImporter<B, I, T, R, E> build(IBusinessCreator<B, R, E> creator, IBusinessRepository<R> repository) throws BeanException {
                return new FullImporter<>(factory, st, it) {
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
            public <R extends FullRecord<I>> FullImporter<B, I, T, R, FullError<B, R>> build(IBusinessRepository<R> repository) throws BeanException {
                return this.build((IBusinessCreator<B, R, FullError<B, R>>) FullError::new, repository);
            }

            /**
             * Creates new preconfigured instance for {@link SimpleFullImporter}.
             *
             * @return new simple full-importer
             * @throws BeanException for any missing or wrong java-beans configuration
             */
            public SimpleFullImporter<T, B, I> build() throws BeanException {
                return new SimpleFullImporter<>(factory, st, it);
            }
        }
    }

}
