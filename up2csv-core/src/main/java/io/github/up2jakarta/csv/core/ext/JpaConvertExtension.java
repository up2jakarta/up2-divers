package io.github.up2jakarta.csv.core.ext;

import io.github.up2jakarta.csv.api.ext.TypeExtension;
import io.github.up2jakarta.csv.core.BeanContext;
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

import static io.github.up2jakarta.csv.core.ext.Beans.*;
import static java.util.Arrays.stream;

/**
 * {@link Entity} extension that supports {@link Convert}.
 *
 * @see AttributeConverter
 */
@Named
@Singleton
public final class JpaConvertExtension implements TypeExtension<Object, Convert> {

    private final BeanContext context;

    @Inject
    JpaConvertExtension(BeanContext context) {
        this.context = context;
    }

    private static void check(Field p, Class<?> type, Convert jpa) throws BeanException {
        if (!AttributeConverter.class.isAssignableFrom(jpa.converter())) {
            throw new BeanException(p, "@Convert[converter] must extends AttributeConverter");
        }
        final Type[] types = getTypeArguments(jpa.converter(), AttributeConverter.class);
        if (!(types[0] instanceof Class<?> c) || !c.isAssignableFrom(type) || !String.class.equals(types[1])) {
            final CharSequence cn = getTypeName(type);
            throw new BeanException(p, "@Convert[converter] should extends AttributeConverter<" + cn + ", String>");
        }
    }

    private static Optional<Convert> from(Class<?> st, Field field, Class<?> type) throws BeanException {
        final Convert result = from(st, field.getName());
        if (result != null) {
            check(field, type, result);
            return Optional.of(result);
        }
        st = st.getSuperclass();
        if (Segment.class.isAssignableFrom(st)) {
            return from(st, field, type);
        }
        return Optional.empty();
    }

    private static Convert from(Class<?> type, String path) {
        final Convert[] jpa = type.getAnnotationsByType(Convert.class);
        return stream(jpa)
                .filter(c -> path.equals(c.attributeName()))
                .findAny()
                .orElse(null);
    }

    private static Convert from(Field property, String path, Supplier<Convert> retry) {
        return stream(property.getAnnotationsByType(Convert.class))
                .filter(c -> path.equals(c.attributeName()))
                .findAny()
                .orElseGet(retry);
    }

    @Override
    public Optional<Convert> resolve(Class<? extends Segment> st, Field p, Class<?> pt, Field... ps) throws BeanException {
        Convert result = from(p, "", () -> null);
        var path = p.getName();
        for (var i = ps.length - 1; i >= 0; i--) {
            final Field current = ps[i];
            final String next = current.getName() + '.' + path;
            final Convert override = from(current, path, () -> from(current.getDeclaringClass(), next));
            if (override != null) {
                result = override;
            }
            path = next;
        }
        if (result == null) {
            return from(st, p, pt);
        }
        check(p, pt, result);
        return Optional.of(result);
    }

    @Override
    public TypeAdapter<?> resolve(Field pf, Class<Object> pt, Convert pc) throws BeanException {
        final AttributeConverter<Object, String> converter = getBean(context, pc.converter(), "");
        if (converter instanceof TypeAdapter<?> pa) {
            return pa;
        }
        return new JpaWrapper<>(pt, converter);
    }

}
