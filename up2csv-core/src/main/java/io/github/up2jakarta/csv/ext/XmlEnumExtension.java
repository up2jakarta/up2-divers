package io.github.up2jakarta.csv.ext;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.ext.TypeExtension;
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
import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

import java.lang.reflect.Field;
import java.util.*;

import static io.github.up2jakarta.csv.api.IEvent.EC_XML_ENUM;
import static io.github.up2jakarta.csv.ext.Beans.*;
import static io.github.up2jakarta.lov.SeverityType.ERROR;

/**
 * {@link XmlType} extension that supports {@link XmlEnum}.
 *
 * @see XmlEnumValue
 */
@Named
@Singleton
public final class XmlEnumExtension implements TypeExtension<Enum<?>, XmlEnum> {

    public static final String FORMAT = "Unknown input [%s] for @XmlEnum[%s]";

    @Inject
    public XmlEnumExtension() {
        super();
    }

    private static <E extends Enum<E>> Map<E, String> inverse(Map<String, E> source, Class<E> type) {
        final Map<E, String> mapping = new EnumMap<>(type);
        for (final Map.Entry<String, E> entry : source.entrySet()) {
            mapping.put(entry.getValue(), entry.getKey());
        }
        return Collections.unmodifiableMap(mapping);
    }

    private static <E extends Enum<E>> Map<String, E> getConstants(Class<E> type) throws BeanException {
        final E[] values = type.getEnumConstants();
        final Map<String, E> mapping = new HashMap<>(values.length);
        for (final E constant : values) {
            var name = constant.name();
            try {
                final XmlEnumValue xml = type.getField(name).getAnnotation(XmlEnumValue.class);
                if (xml != null) {
                    name = xml.value();
                }
            } catch (NoSuchFieldException ignore) {
            }
            if (mapping.put(name, constant) != null) {
                throw new BeanException(type, name, "must be unique");
            }
        }
        return Map.copyOf(mapping);
    }

    private static <E extends Enum<E>> TypeAdapter<E> resolve(Field source, Class<?> type) throws BeanException {
        final Class<E> et = cast(type);
        final Map<String, E> mapping = getConstants(et);
        final Optional<Error> error = error(source, et);
        final SeverityType level = error.map(Error::level).orElse(ERROR);
        final String code = error.map(Error::value).orElse(EC_XML_ENUM);
        final TypeConverter<E> parser = k -> {
            final E value = mapping.get(k);
            if (value == null) {
                throw new TypeException(level, code, String.format(FORMAT, k, getTypeName(et)));
            }
            return value;
        };
        final Map<E, String> inverse = inverse(mapping, et);
        return new Up2Adapter<>(et, parser, inverse::get);
    }

    @Override
    public Optional<XmlEnum> resolve(Class<? extends Segment> st, Field p, Class<?> pt, Field... ps) throws BeanException {
        final Optional<XmlEnum> xml = Optional.ofNullable(pt.getAnnotation(XmlEnum.class));
        if (xml.isPresent() && !pt.isEnum()) {
            throw new BeanException(p, "type must not be annotated with @" + getTypeName(XmlEnum.class));
        }
        return xml;
    }

    @Override
    public TypeAdapter<? extends Enum<?>> resolve(Field pf, Class<Enum<?>> pt, XmlEnum pc) throws BeanException {
        return resolve(pf, pt);
    }

}
