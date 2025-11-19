package io.github.up2jakarta.csv.core.ext;

import io.github.up2jakarta.csv.api.ext.TypeExtension;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.lov.TypeAdapter;
import io.github.up2jakarta.lov.core.BeanException;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Optional;
import java.util.function.Supplier;

import static io.github.up2jakarta.csv.core.ext.Beans.getTypeArguments;
import static io.github.up2jakarta.csv.core.ext.Beans.getTypeName;
import static java.util.Arrays.stream;

/**
 * {@link Entity} extension that supports {@link Convert}.
 *
 * @see AttributeConverter
 */
@Named
@Singleton
@SuppressWarnings("unchecked")
public final class JpaConvertExtension extends TypeExtension<Entity, Convert> {

    @Inject
    JpaConvertExtension() {
        super(Entity.class);
    }

    private void check(Field p, Class<?> type, Convert jpa) throws BeanException {
        if (!AttributeConverter.class.isAssignableFrom(jpa.converter())) {
            throw new BeanException(p, "@Convert[converter] must extends AttributeConverter");
        }
        final Class<? extends AttributeConverter<?, String>> converterType = jpa.converter();
        final Type[] arguments = getTypeArguments(converterType, AttributeConverter.class);
        if (!type.equals(arguments[0]) || !String.class.equals(arguments[1])) {
            final CharSequence cn = getTypeName(type);
            throw new BeanException(p, "@Convert[converter] should extends AttributeConverter<" + cn + ", String>");
        }
    }

    private Optional<Convert> from(Class<? extends Segment> st, Field field, Class<?> type) throws BeanException {
        final Convert result = this.from(st, field.getName());
        if (result != null) {
            this.check(field, type, result);
            return Optional.of(result);
        }
        st = (Class<? extends Segment>) st.getSuperclass();
        if (Segment.class.isAssignableFrom(st)) {
            return this.from(st, field, type);
        }
        return Optional.empty();
    }

    private Convert from(Class<?> type, String path) {
        final Convert[] jpa = type.getAnnotationsByType(Convert.class);
        return stream(jpa)
                .filter(c -> path.equals(c.attributeName()))
                .findAny()
                .orElse(null);
    }

    private Convert from(Field property, String path, Supplier<Convert> retry) {
        return stream(property.getAnnotationsByType(Convert.class))
                .filter(c -> path.equals(c.attributeName()))
                .findAny()
                .orElseGet(retry);
    }

    @Override
    public Optional<Convert> get(Class<? extends Segment> st, Field p, Class<?> type, Field... ps) throws BeanException {
        Convert result = this.from(p, "", () -> null);
        var path = p.getName();
        for (var i = ps.length - 1; i >= 0; i--) {
            final Field current = ps[i];
            final String next = current.getName() + '.' + path;
            final Convert override = this.from(current, path, () -> this.from(current.getDeclaringClass(), next));
            if (override != null) {
                result = override;
            }
            path = next;
        }
        if (result == null) {
            return this.from(st, p, type);
        }
        this.check(p, type, result);
        return Optional.of(result);
    }

    @Override
    public <V> TypeAdapter<V> resolve(Field property, Class<V> type, Convert config) throws BeanException {
        final Class<? extends AttributeConverter<V, String>> converterType = config.converter();
        final AttributeConverter<V, String> converter = this.getBean(converterType, "");
        if (converter instanceof TypeAdapter<?> pa) {
            return (TypeAdapter<V>) pa;
        }
        return new JpaWrapper<>(type, converter);
    }

}
