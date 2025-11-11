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
import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static io.github.up2jakarta.csv.api.IEvent.ERROR_XML_ENUM;
import static io.github.up2jakarta.csv.core.ext.Beans.getTypeName;

/**
 * {@link XmlType} extension that supports {@link XmlEnum}.
 *
 * @see XmlEnumValue
 */
@Named
@Singleton
public final class XmlEnumExtension extends ConversionExtension<XmlType, XmlEnum> {

    public static final String FORMAT = "Unknown value [%s] for @XmlEnum[%s]";

    XmlEnumExtension() {
        super(XmlType.class);
    }

    private static <V> Map<String, V> getConstants(Class<V> enumType, Field field) throws BeanException {
        final V[] constants = enumType.getEnumConstants();
        final Map<String, V> mapping = new HashMap<>(constants.length);
        var maxLength = 0;
        for (final V constant : constants) {
            var constantName = constant.toString();
            try {
                final XmlEnumValue xml = enumType.getField(constantName).getAnnotation(XmlEnumValue.class);
                if (xml != null) {
                    constantName = xml.value();
                }
                mapping.put(constantName, constant);
                if (constantName.length() > maxLength) {
                    maxLength = constantName.length();
                }
            } catch (NoSuchFieldException ignore) {
                throw new BeanException(enumType, constantName, "constant not found");
            }
        }
        final Column jpa = field.getAnnotation(Column.class);
        if (jpa != null) {
            if (maxLength > jpa.length()) {
                throw new BeanException(field, "@Column[length] must be greater or equals to " + maxLength);
            }
        }
        return mapping;
    }

    @Override
    public Optional<XmlEnum> get(Class<? extends Segment> st, Field p, Class<?> type, Field... ps) throws BeanException {
        if (!type.isEnum()) {
            return Optional.empty();
        }
        final XmlEnum xml = type.getAnnotation(XmlEnum.class);
        if (xml != null) {
            if (type.getAnnotation(XmlType.class) == null) {
                throw new BeanException(type, "must be annotated with @XmlType");
            }
            return Optional.of(xml);
        }
        return Optional.empty();
    }

    @Override
    public <V> Conversion<V> resolve(Field property, Class<V> type, XmlEnum config) throws BeanException {
        final Map<String, V> mapping = getConstants(type, property);
        final Optional<Error> error = ConversionResolver.getError(property, type);
        final SeverityType level = error.map(Error::severity).orElse(SeverityType.ERROR);
        final String code = error.map(Error::value).orElse(ERROR_XML_ENUM);
        final PropertyConverter<V> p = v -> mapping.entrySet().stream().filter(e -> e.getKey().equals(v))
                .map(Map.Entry::getValue)
                .findAny()
                .orElseThrow(() -> {
                    final String msg = String.format(FORMAT, v, getTypeName(type));
                    return new PropertyException(level, code, msg);
                });
        final PropertyFormatter<V> f = v -> mapping.entrySet().stream().filter(e -> e.getValue().equals(v))
                .map(Map.Entry::getKey)
                .findAny()
                .orElseGet(v::toString);
        return new Conversion<>(type, p, f);
    }

}
