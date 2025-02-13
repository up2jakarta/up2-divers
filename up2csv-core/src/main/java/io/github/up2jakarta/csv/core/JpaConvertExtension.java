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

import static io.github.up2jakarta.csv.core.Beans.getBean;
import static io.github.up2jakarta.csv.core.Beans.getTypeArguments;

/**
 * {@link Entity} extension that supports {@link Convert}.
 *
 * @see AttributeConverter
 */
@Named
@Singleton
@SuppressWarnings("unchecked")
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
        final Class<? extends AttributeConverter<?, String>> converterType = jpa.converter();
        final Type[] arguments = getTypeArguments(converterType, AttributeConverter.class);
        if (!type.equals(arguments[0]) || !String.class.equals(arguments[1])) {
            final String fName = type.getSimpleName();
            throw new BeanException(property, "@Convert[converter] must extends AttributeConverter<" + fName + ", String>");
        }
    }

    private Optional<Convert> from(Class<? extends Segment> segmentType, Field property, Class<?> type) throws BeanException {
        for (final Convert jpa : segmentType.getAnnotationsByType(Convert.class)) {
            if (jpa.attributeName().equals(property.getName())) {
                this.check(property, type, jpa);
                return Optional.of(jpa);
            }
        }
        segmentType = (Class<? extends Segment>) segmentType.getSuperclass();
        if (Segment.class.isAssignableFrom(segmentType)) {
            return from(segmentType, property, type);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Convert> get(Class<? extends Segment> segmentType, Field property, Class<?> type, Field... path) throws BeanException {
        final Convert jpa = property.getAnnotation(Convert.class);
        if (jpa != null) {
            if (jpa.attributeName().isBlank() || jpa.attributeName().equals(property.getName())) {
                this.check(property, type, jpa);
                return Optional.of(jpa);
            }
        }
        var fieldPath = property.getName();
        for (var i = path.length - 1; i >= 0; i--) {
            final Convert override = path[i].getAnnotation(Convert.class);
            if (override != null && override.attributeName().equals(fieldPath)) {
                this.check(property, type, override);
                return Optional.of(override);
            }
            fieldPath = path[i].getName() + '.' + fieldPath;
        }
        return from(segmentType, property, type);
    }

    @Override
    public Conversion<?> resolve(Field property, Class<?> type, Convert config) throws BeanException {
        final Class<? extends AttributeConverter<?, String>> converterType = config.converter();
        final Optional<Error> error = CodeListResolver.getError(property);
        final AttributeConverter<?, String> converter = getBean(context, converterType);
        return Conversion.of(converter::convertToEntityAttribute, error.orElse(null));
    }

}
