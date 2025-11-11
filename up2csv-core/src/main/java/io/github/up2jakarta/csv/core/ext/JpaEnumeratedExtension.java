package io.github.up2jakarta.csv.core.ext;

import io.github.up2jakarta.csv.api.ext.*;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.xml.api.PropertyException;
import io.github.up2jakarta.xml.api.SeverityType;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.function.Function;

import static io.github.up2jakarta.csv.api.IEvent.ERROR_XML_ENUM;
import static io.github.up2jakarta.csv.core.ext.Beans.getTypeName;
import static java.util.Arrays.stream;

/**
 * {@link Entity} extension that supports {@link Enumerated}.
 */
@Named
@Singleton
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

    private <T extends Enum<T>> Conversion<?> ofOrdinal(Class<T> type, Optional<Error> error, SeverityType et, String ec) {
        final T[] constants = type.getEnumConstants();
        final PropertyConverter<T> p = v -> {
            try {
                final int ordinal = Integer.parseInt(v);
                return constants[ordinal];
            } catch (Exception exception) {
                final String msg = String.format(FORMAT, v, getTypeName(type));
                throw new PropertyException(et, ec, msg);
            }
        };
        final PropertyFormatter<T> f = v -> {
            for (var i = 0; i < constants.length; i++) {
                if (v == constants[i]) {
                    return String.valueOf(i);
                }
            }
            final String msg = String.format(FORMAT, v, getTypeName(type));
            throw new PropertyException(et, ec, msg);
        };
        return new Conversion<>(type, p, f, error);
    }

    private <T extends Enum<T>> Conversion<?> ofName(Class<T> type, Optional<Error> error, SeverityType et, String ec) {
        final PropertyConverter<T> p = v -> {
            try {
                return Enum.valueOf(type, v);
            } catch (IllegalArgumentException exception) {
                final String msg = String.format(FORMAT, v, getTypeName(type));
                throw new PropertyException(et, ec, msg);
            }
        };
        return new Conversion<>(type, p, Object::toString, error);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<Enumerated> get(Class<? extends Segment> st, Field p, Class<?> type, Field... ps) throws BeanException {
        final Enumerated jpa = p.getAnnotation(Enumerated.class);
        if (jpa != null) {
            if (!st.isAnnotationPresent(Entity.class)) {
                throw new BeanException(st, "must be annotated with @Entity");
            }
            final Class<? extends Enum<?>> enumType = (Class<Enum<?>>) type;
            if (!enumType.isEnum()) {
                throw new BeanException(p, "must not be annotated with @Enumerated");
            }
            return Optional.of(jpa);
        }
        return Optional.empty();
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <V> Conversion<V> resolve(Field property, Class<V> type, Enumerated config) throws BeanException {
        final Class<? extends Enum> enumType = (Class<Enum>) type;
        check(property, enumType, Enum::name);
        final Optional<Error> error = ConversionResolver.getError(property, type);
        final SeverityType level = error.map(Error::severity).orElse(SeverityType.ERROR);
        final String code = error.map(Error::value).orElse(ERROR_XML_ENUM);
        if (EnumType.STRING == config.value()) {
            return ofName(enumType, error, level, code);
        }
        return ofOrdinal(enumType, error, level, code);
    }

}
