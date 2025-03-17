package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.annotation.Error;
import io.github.up2jakarta.csv.exception.BeanException;
import io.github.up2jakarta.csv.extension.BeanContext;
import io.github.up2jakarta.csv.extension.Conversion;
import io.github.up2jakarta.csv.extension.ConversionExtension;
import io.github.up2jakarta.csv.extension.Segment;
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

import static io.github.up2jakarta.csv.misc.Beans.getBean;
import static io.github.up2jakarta.csv.misc.Beans.getTypeArguments;
import static java.util.Arrays.stream;

/**
 * {@link Entity} extension that supports {@link Convert}.
 *
 * @see AttributeConverter
 */
@Named
@Singleton
public final class JpaConvertExtension extends ConversionExtension<Entity, Convert> {

    private final BeanContext context;

    @Inject
    JpaConvertExtension(BeanContext context) {
        super(Entity.class);
        this.context = context;
    }

    private void check(Field property, Class<?> type, Convert jpa) throws BeanException {
        if (!AttributeConverter.class.isAssignableFrom(jpa.converter())) {
            throw new BeanException(property, "@Convert[converter] must extends AttributeConverter");
        }
        //noinspection unchecked
        final Class<? extends AttributeConverter<?, String>> converterType = jpa.converter();
        final Type[] arguments = getTypeArguments(converterType, AttributeConverter.class);
        if (!type.equals(arguments[0]) || !String.class.equals(arguments[1])) {
            final String fName = type.getSimpleName();
            throw new BeanException(property, "@Convert[converter] must extends AttributeConverter<" + fName + ", String>");
        }
    }

    private Optional<Convert> from(Class<? extends Segment> segmentType, Field field, Class<?> fieldType) throws BeanException {
        final Convert result = this.from(segmentType, field.getName());
        if (result != null) {
            this.check(field, fieldType, result);
            return Optional.of(result);
        }
        //noinspection unchecked
        segmentType = (Class<? extends Segment>) segmentType.getSuperclass();
        if (Segment.class.isAssignableFrom(segmentType)) {
            return this.from(segmentType, field, fieldType);
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
    public Optional<Convert> get(Class<? extends Segment> segmentType, Field last, Class<?> type, Field... paths) throws BeanException {
        Convert result = this.from(last, "", () -> null);
        var path = last.getName();
        for (var i = paths.length - 1; i >= 0; i--) {
            final Field current = paths[i];
            final String next = current.getName() + '.' + path;
            final Convert override = this.from(current, path, () -> this.from(current.getDeclaringClass(), next));
            if (override != null) {
                result = override;
            }
            path = next;
        }
        if (result == null) {
            return this.from(segmentType, last, type);
        }
        this.check(last, type, result);
        return Optional.of(result);
    }

    @Override
    public Conversion<?> resolve(Field property, Class<?> type, Convert config) throws BeanException {
        //noinspection unchecked
        final Class<? extends AttributeConverter<?, String>> converterType = config.converter();
        final Optional<Error> error = CodeListResolver.getError(property);
        final AttributeConverter<?, String> converter = getBean(context, converterType);
        return Conversion.of(converter::convertToEntityAttribute, error.orElse(null));
    }

}
