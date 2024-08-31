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
import jakarta.validation.constraints.NotNull;

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

    @Override
    public Optional<Convert> get(Class<? extends Segment> segmentType, Field property) throws BeanException {
        final Convert jpa = property.getAnnotation(Convert.class);
        if (jpa != null) {
            if (segmentType.getAnnotation(Entity.class) == null) {
                throw new BeanException(segmentType, "must be annotated with @Entity");
            }
            final Class<?> fieldType = property.getType();
            if (!AttributeConverter.class.isAssignableFrom(jpa.converter())) {
                throw new BeanException(property, "@Convert[converter] must extends AttributeConverter");
            }
            final Class<? extends AttributeConverter<?, String>> converterType = jpa.converter();
            final Type[] arguments = getTypeArguments(converterType, AttributeConverter.class);
            if (!fieldType.equals(arguments[0]) || !String.class.equals(arguments[1])) {
                final String fName = fieldType.getSimpleName();
                throw new BeanException(property, "@Convert[converter] must extends AttributeConverter<" + fName + ", String>");
            }
            return Optional.of(jpa);
        }
        return Optional.empty();
    }

    @Override
    public Conversion<?> resolve(@NotNull Field property, @NotNull Convert config) throws BeanException {
        final Class<? extends AttributeConverter<?, String>> converterType = config.converter();
        final Optional<Error> error = CodeListResolver.getError(property);
        final AttributeConverter<?, String> converter = getBean(context, converterType);
        return Conversion.of(converter::convertToEntityAttribute, error.orElse(null));
    }

}
