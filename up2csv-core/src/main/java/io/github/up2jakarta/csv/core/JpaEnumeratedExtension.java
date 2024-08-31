package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.annotation.Error;
import io.github.up2jakarta.csv.exception.BeanException;
import io.github.up2jakarta.csv.exception.PropertyException;
import io.github.up2jakarta.csv.extension.Conversion;
import io.github.up2jakarta.csv.extension.ConversionExtension;
import io.github.up2jakarta.csv.extension.Segment;
import io.github.up2jakarta.csv.extension.SeverityType;
import io.github.up2jakarta.csv.misc.Errors;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.function.Function;

import static java.util.Arrays.stream;

/**
 * {@link Entity} extension that supports {@link Enumerated}.
 */
@Named
@Singleton
@SuppressWarnings({"unchecked", "rawtypes"})
public final class JpaEnumeratedExtension extends ConversionExtension<Entity, Enumerated> {

    public static final String FORMAT = "Unknown value [%s] for @Enumerated[%s]";

    JpaEnumeratedExtension() {
        super(Entity.class);
    }

    private static <T> void check(Field field, Class<T> enumType, Function<T, String> function) throws BeanException {
        final Column jpa = field.getAnnotation(Column.class);
        if (jpa != null) {
            final int length = jpa.length();
            final T[] values = enumType.getEnumConstants();
            final int max = stream(values).map(function).map(String::length).max(Integer::compareTo).orElse(length);
            if (max > length) {
                throw new BeanException(field, "@Column[length] must be greater or equals to " + max);
            }
        }
    }

    @Override
    public Optional<Enumerated> get(Class<? extends Segment> segmentType, Field property) throws BeanException {
        final Enumerated jpa = property.getAnnotation(Enumerated.class);
        if (jpa != null) {
            if (segmentType.getAnnotation(Entity.class) == null) {
                throw new BeanException(segmentType, "must be annotated with @Entity");
            }
            final Class<? extends Enum> enumType = (Class<Enum>) property.getType();
            if (!enumType.isEnum()) {
                throw new BeanException(property, "must not be annotated with @Enumerated");
            }
            return Optional.of(jpa);
        }
        return Optional.empty();
    }

    @Override
    public Conversion<?> resolve(@NotNull Field property, @NotNull Enumerated config) throws BeanException {
        final Class<? extends Enum> enumType = (Class<Enum>) property.getType();
        check(property, enumType, Enum::name);
        final Optional<Error> error = CodeListResolver.getError(property);
        final SeverityType type = error.map(Error::severity).orElse(SeverityType.ERROR);
        final String code = error.map(Error::value).orElse(Errors.ERROR_XML_ENUM);
        if (EnumType.STRING == config.value()) {
            return v -> {
                try {
                    return Enum.valueOf(enumType, v);
                } catch (IllegalArgumentException exception) {
                    final String msg = String.format(FORMAT, v, enumType.getSimpleName());
                    throw new PropertyException(type, code, msg);
                }
            };
        }
        final Object[] constants = enumType.getEnumConstants();
        return v -> {
            try {
                final int ordinal = Integer.parseInt(v);
                return constants[ordinal];
            } catch (Throwable exception) {
                final String msg = String.format(FORMAT, v, enumType.getSimpleName());
                throw new PropertyException(type, code, msg);
            }
        };
    }

}
