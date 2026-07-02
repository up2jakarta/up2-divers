package io.github.up2jakarta.csv.core.ext;

import io.github.up2jakarta.csv.api.ext.SimpleExtension;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.core.Up2Adapter;
import io.github.up2jakarta.lov.SeverityType;
import io.github.up2jakarta.lov.TypeAdapter;
import io.github.up2jakarta.lov.TypeConverter;
import io.github.up2jakarta.lov.TypeException;
import io.github.up2jakarta.lov.core.BeanException;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static io.github.up2jakarta.csv.api.IEvent.EC_JPA_ENUM;
import static io.github.up2jakarta.csv.core.ext.Beans.*;
import static io.github.up2jakarta.lov.SeverityType.ERROR;

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

    private static <E extends Enum<?>> Map<String, E> getConstants(Class<E> type, Field field) throws BeanException {
        final E[] constants = type.getEnumConstants();
        final Map<String, E> mapping = new HashMap<>(constants.length);
        var max = 0;
        for (final E constant : constants) {
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
        return Map.copyOf(mapping);
    }

    private static <E extends Enum<E>> Map<E, String> getMapping(E[] constants) {
        final Map<E, String> mapping = new HashMap<>(constants.length);
        for (int i = 0, size = constants.length; i < size; i++) {
            mapping.put(constants[i], String.valueOf(i));
        }
        return Map.copyOf(mapping);
    }

    private static <E extends Enum<E>> TypeAdapter<E> ofOrdinal(Class<E> type, SeverityType level, String code) {
        final E[] constants = type.getEnumConstants();
        final List<E> values = List.of(constants);
        final TypeConverter<E> parser = o -> {
            try {
                final int ordinal = Integer.parseInt(o);
                return values.get(ordinal);
            } catch (Exception cause) {
                throw new TypeException(level, code, String.format(FORMAT, o, getTypeName(type)));
            }
        };
        final Map<E, String> mapping = getMapping(constants);
        return new Up2Adapter<>(type, parser, mapping::get);
    }

    private static <E extends Enum<E>> TypeAdapter<E> ofName(Class<E> pt, Field pf, SeverityType el, String ec) throws BeanException {
        final Map<String, E> mapping = getConstants(pt, pf);
        final TypeConverter<E> parser = k -> {
            final E value = mapping.get(k);
            if (value == null) {
                throw new TypeException(el, ec, String.format(FORMAT, k, getTypeName(pt)));
            }
            return value;
        };
        return new Up2Adapter<>(pt, parser, Enum::name);
    }

    private static <E extends Enum<E>> TypeAdapter<E> resolve(Enumerated pc, Field pf, Class<?> pt) throws BeanException {
        Class<E> type = cast(pt);
        final Optional<Error> error = error(pf, type);
        final SeverityType level = error.map(Error::level).orElse(ERROR);
        final String code = error.map(Error::value).orElse(EC_JPA_ENUM);
        if (EnumType.STRING == pc.value()) {
            return ofName(type, pf, level, code);
        }
        return ofOrdinal(type, level, code);
    }

    @Override
    public TypeAdapter<? extends Enum<?>> resolve(Field pf, Class<Enum<?>> pt, Enumerated pc) throws BeanException {
        return resolve(pc, pf, pt);
    }

}
