package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.exception.BeanException;
import io.github.up2jakarta.csv.extension.BeanContext;
import jakarta.validation.Validator;

import java.lang.reflect.Constructor;

/**
 * Up2 Configurable Factory for {@link Mapper}
 */
public class MapperFactory {

    private final BeanContext context;
    private final Validator validator;

    public MapperFactory(BeanContext context, Validator validator) {
        this.context = context;
        this.validator = validator;
    }

    /**
     * Build a preconfigured CSV Mapper that is able to map input data to bean properties annotated by {@link Position}.
     * Note that only {@link String} property are supported.
     *
     * @param type the type of segment that is being mapped
     * @param <S>  The class of segment
     * @return the CSV mapper
     * @throws BeanException for any missing or wrong bean configuration
     */
    public <S extends Segment> Mapper<S> build(final Class<S> type) throws BeanException {
        BeanSupport.checkBean(type);
        return new DefaultMapper<>(type, context, validator);
    }

    /**
     * Internal Mapper implementation.
     *
     * @param <R>
     */
    private static final class DefaultMapper<R extends Segment> extends Mapper<R> {

        private final Constructor<R> constructor;

        private DefaultMapper(Class<R> type, BeanContext context, Validator validator) throws BeanException {
            super(type, context, validator);
            this.constructor = Beans.getDefaultConstructor(type);
        }

        private static <T> T map(Constructor<T> constructor, Property<?>[] properties, String... columns) throws BeanException {
            final T bean = Beans.newInstance(constructor);
            if (columns.length == 0) {
                return bean;
            }
            for (final Property<?> p : properties) {
                if (p instanceof FragmentProperty fp) {
                    final Object fragment = map(fp.getConstructor(), fp.getProperties(), columns);
                    fp.setValue(bean, fragment);
                } else {
                    final int index = p.getOffset();
                    if (index < columns.length) {
                        final String value = columns[index];
                        ((StringProperty) p).setValue(bean, value);
                    }
                }
            }
            return bean;
        }

        @Override
        public R map(final String... columns) throws BeanException {
            if (columns == null) {
                return null;
            }
            return map(this.constructor, this.properties, columns);
        }

    }

}
