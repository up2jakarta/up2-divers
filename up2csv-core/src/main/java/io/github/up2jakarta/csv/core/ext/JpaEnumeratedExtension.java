package io.github.up2jakarta.csv.core.ext;

import io.github.up2jakarta.csv.api.ext.TypeExtension;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.lov.*;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.lov.core.TypeWrapper;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.function.Function;

import static io.github.up2jakarta.csv.api.IEvent.EC_JPA_ENUM;
import static io.github.up2jakarta.csv.core.ext.Beans.*;
import static io.github.up2jakarta.lov.SeverityType.ERROR;
import static java.lang.String.format;
import static java.util.Arrays.stream;

/**
 * {@link Entity} extension that supports {@link Enumerated}.
 */
@Named
@Singleton
public final class JpaEnumeratedExtension extends TypeExtension<Entity, Enumerated> {

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

    private <T extends Enum<T>> TypeAdapter<?> ofOrdinal(Class<T> type, SeverityType level, String code) {
        final T[] constants = type.getEnumConstants();
        final PropertyConverter<T> parser = v -> {
            try {
                final int ordinal = Integer.parseInt(v);
                return constants[ordinal];
            } catch (Exception cause) {
                throw new PropertyException(level, code, format(FORMAT, v, getTypeName(type)));
            }
        };
        final PropertyFormatter<T> format = v -> {
            for (var i = 0; i < constants.length; i++) {
                if (v == constants[i]) {
                    return String.valueOf(i);
                }
            }
            throw new PropertyException(level, code, format(FORMAT, v, getTypeName(type)));
        };
        return new TypeWrapper<>(type, parser, format);
    }

    private <T extends Enum<T>> TypeAdapter<?> ofName(Class<T> type, SeverityType level, String code) {
        final PropertyConverter<T> parser = v -> {
            try {
                return Enum.valueOf(type, v);
            } catch (Exception cause) {
                throw new PropertyException(level, code, format(FORMAT, v, getTypeName(type)));
            }
        };
        return new TypeWrapper<>(type, parser, Enum::name);
    }

    @Override
    public Optional<Enumerated> get(Class<? extends Segment> st, Field p, Class<?> type, Field... ps) throws BeanException {
        final Enumerated jpa = p.getAnnotation(Enumerated.class);
        if (jpa != null) {
            if (!st.isAnnotationPresent(Entity.class)) {
                throw new BeanException(st, "must be annotated with @Entity");
            }
            final Class<? extends Enum<?>> enumType = cast(type);
            if (!enumType.isEnum()) {
                throw new BeanException(p, "must not be annotated with @Enumerated");
            }
            return Optional.of(jpa);
        }
        return Optional.empty();
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <V> TypeAdapter<V> resolve(Field property, Class<V> type, Enumerated config) throws BeanException {
        final Class<? extends Enum> enumType = (Class<Enum>) type;
        check(property, enumType, Enum::name);
        final Optional<Error> error = error(property, type);
        final SeverityType level = error.map(Error::level).orElse(ERROR);
        final String code = error.map(Error::value).orElse(EC_JPA_ENUM);
        if (EnumType.STRING == config.value()) {
            return ofName(enumType, level, code);
        }
        return ofOrdinal(enumType, level, code);
    }

}
