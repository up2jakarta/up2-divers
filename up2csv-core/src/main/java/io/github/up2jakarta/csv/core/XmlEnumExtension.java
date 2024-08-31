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
import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

    private static Map<String, Object> getConstants(Class<?> enumType, Field field) throws BeanException {
        final Object[] constants = enumType.getEnumConstants();
        final Map<String, Object> mapping = new HashMap<>(constants.length);
        var maxLength = 0;
        for (final Object constant : constants) {
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
    public Optional<XmlEnum> get(Class<? extends Segment> segmentType, Field property) throws BeanException {
        final Class<?> fieldType = property.getType();
        if (!fieldType.isEnum()) {
            return Optional.empty();
        }
        final XmlEnum xml = fieldType.getAnnotation(XmlEnum.class);
        if (xml != null) {
            if (fieldType.getAnnotation(XmlType.class) == null) {
                throw new BeanException(fieldType, "must be annotated with @XmlType");
            }
            return Optional.of(xml);
        }
        return Optional.empty();
    }

    @Override
    public Conversion<?> resolve(Field property, XmlEnum config) throws BeanException {
        final Class<?> enumType = property.getType();
        final Map<String, Object> mapping = getConstants(enumType, property);
        final Optional<Error> error = CodeListResolver.getError(property);
        final SeverityType type = error.map(Error::severity).orElse(SeverityType.ERROR);
        final String code = error.map(Error::value).orElse(Errors.ERROR_XML_ENUM);
        return v -> mapping.entrySet().stream().filter(e -> e.getKey().equals(v))
                .map(Map.Entry::getValue)
                .findAny()
                .orElseThrow(() -> {
                    final String msg = String.format(FORMAT, v, enumType.getSimpleName());
                    return new PropertyException(type, code, msg);
                });
    }

}
