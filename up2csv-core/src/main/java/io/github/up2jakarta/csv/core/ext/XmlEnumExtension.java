package io.github.up2jakarta.csv.core.ext;

import io.github.up2jakarta.csv.api.ext.TypeExtension;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.lov.SeverityType;
import io.github.up2jakarta.lov.TypeAdapter;
import io.github.up2jakarta.lov.TypeConverter;
import io.github.up2jakarta.lov.TypeException;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.lov.core.TypeSupport;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import static io.github.up2jakarta.csv.api.IEvent.EC_XML_ENUM;
import static io.github.up2jakarta.csv.core.ext.Beans.error;
import static io.github.up2jakarta.csv.core.ext.Beans.getTypeName;
import static io.github.up2jakarta.lov.SeverityType.ERROR;
import static java.lang.String.format;
import static java.util.Collections.unmodifiableMap;

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

    private static Map<Enum<?>, String> inverse(Map<String, Enum<?>> source) {
        final Map<Enum<?>, String> mapping = new TreeMap<>();
        for (final Map.Entry<String, Enum<?>> entry : source.entrySet()) {
            mapping.put(entry.getValue(), entry.getKey());
        }
        return unmodifiableMap(mapping);
    }

    private static <V extends Enum<?>> Map<String, V> getConstants(Class<V> type) throws BeanException {
        final Map<String, V> mapping = new TreeMap<>();
        for (final V constant : type.getEnumConstants()) {
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
        return unmodifiableMap(mapping);
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
        final Map<String, Enum<?>> mapping = getConstants(pt);
        final Optional<Error> error = error(pf, pt);
        final SeverityType level = error.map(Error::level).orElse(ERROR);
        final String code = error.map(Error::value).orElse(EC_XML_ENUM);
        final TypeConverter<Enum<?>> parser = k -> {
            final Enum<?> value = mapping.get(k);
            if (value == null) {
                throw new TypeException(level, code, format(FORMAT, k, getTypeName(pt)));
            }
            return value;
        };
        final Map<Enum<?>, String> inverse = inverse(mapping);
        return new TypeSupport<>(pt, parser, inverse::get);
    }

}
