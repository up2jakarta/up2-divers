package io.github.up2jakarta.csv.core.ext;

import io.github.up2jakarta.csv.api.ext.SimpleExtension;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.lov.SeverityType;
import io.github.up2jakarta.lov.TypeAdapter;
import io.github.up2jakarta.lov.TypeConverter;
import io.github.up2jakarta.lov.TypeException;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.lov.core.TypeSupport;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import static io.github.up2jakarta.csv.api.IEvent.EC_JPA_ENUM;
import static io.github.up2jakarta.csv.core.ext.Beans.error;
import static io.github.up2jakarta.csv.core.ext.Beans.getTypeName;
import static io.github.up2jakarta.lov.SeverityType.ERROR;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableMap;

/**
 * {@link Entity} extension that supports {@link Enumerated}.
 */
@Named
@Singleton
public final class JpaEnumeratedExtension extends SimpleExtension<Enum<?>, Enumerated> {

    public static final String FORMAT = "Unknown input [%s] for @Enumerated[%s]";

    @Inject
    public JpaEnumeratedExtension() {
        super(Enumerated.class);
    }

    private static <T extends Enum<?>> Map<String, T> getConstants(Class<T> type, Field field) throws BeanException {
        final Map<String, T> mapping = new TreeMap<>();
        var max = 0;
        for (final T constant : type.getEnumConstants()) {
            final String name = constant.name();
            mapping.put(name, constant);
            if (name.length() > max) {
                max = name.length();
            }
        }
        final Column jpa = field.getAnnotation(Column.class);
        if (jpa != null && max > jpa.length()) {
            throw new BeanException(field, "@Column[length] must be greater or equals to " + max);
        }
        return unmodifiableMap(mapping);
    }

    private static <T extends Enum<?>> Map<T, String> getMapping(T[] constants) {
        final Map<T, String> mapping = new TreeMap<>();
        for (int i = 0, size = constants.length; i < size; i++) {
            mapping.put(constants[i], String.valueOf(i));
        }
        return unmodifiableMap(mapping);
    }

    private static <T extends Enum<?>> TypeAdapter<T> ofOrdinal(Class<T> type, SeverityType level, String code) {
        final T[] constants = type.getEnumConstants();
        final List<T> values = asList(constants);
        final TypeConverter<T> parser = o -> {
            try {
                final int ordinal = Integer.parseInt(o);
                return values.get(ordinal);
            } catch (Exception cause) {
                throw new TypeException(level, code, format(FORMAT, o, getTypeName(type)));
            }
        };
        final Map<T, String> mapping = getMapping(constants);
        return new TypeSupport<>(type, parser, mapping::get);
    }

    private static <T extends Enum<?>> TypeAdapter<T> ofName(Class<T> pt, Field pf, SeverityType el, String ec) throws BeanException {
        final Map<String, T> mapping = getConstants(pt, pf);
        final TypeConverter<T> parser = k -> {
            final T value = mapping.get(k);
            if (value == null) {
                throw new TypeException(el, ec, format(FORMAT, k, getTypeName(pt)));
            }
            return value;
        };
        return new TypeSupport<>(pt, parser, Enum::name);
    }

    @Override
    public TypeAdapter<? extends Enum<?>> resolve(Field pf, Class<Enum<?>> pt, Enumerated pc) throws BeanException {
        final Optional<Error> error = error(pf, pt);
        final SeverityType level = error.map(Error::level).orElse(ERROR);
        final String code = error.map(Error::value).orElse(EC_JPA_ENUM);
        if (EnumType.STRING == pc.value()) {
            return ofName(pt, pf, level, code);
        }
        return ofOrdinal(pt, level, code);
    }

}
