package io.github.up2jakarta.csv.core.ext;

import io.github.up2jakarta.csv.api.ext.TypeContext;
import io.github.up2jakarta.csv.api.ext.TypeListener;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.lov.core.BeanException;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.persistence.Entity;

import java.lang.reflect.AnnotatedElement;

import static io.github.up2jakarta.lov.core.Overrides.get;

/**
 * JPA {@link jakarta.persistence.Column} checker implementation.
 */
@Named
@Singleton
public final class JpaColumnChecker implements TypeListener {

    static void checkName(AnnotatedElement source, String name, String prefix, String desc) throws BeanException {
        if (name.isBlank()) {
            throw BeanException.of(source, desc + " must not be empty");
        }
        if (!name.equals(name.toUpperCase())) {
            throw BeanException.of(source, desc + " must be uppercase");
        }
        if (!Character.isAlphabetic(name.charAt(0))) {
            throw BeanException.of(source, desc + " must starts with alphabetic");
        }
        if (!name.matches("^[A-Z0-9_]+$")) {
            throw BeanException.of(source, desc + " must contains only alphanumeric or underscore");
        }
        if (!name.startsWith(prefix)) {
            throw BeanException.of(source, desc + " must starts with \"" + prefix + "\"");
        }
    }

    static String getPrefix(AnnotatedElement source) throws BeanException {
        final Prefix config = source.getAnnotation(Prefix.class);
        if (config != null && !config.value().isBlank()) {
            final String prefix = config.value();
            if (!prefix.equals(prefix.toUpperCase())) {
                throw BeanException.of(source, "@Prefix[value] must be uppercase");
            }
            if (!prefix.endsWith("_")) {
                throw BeanException.of(source, "@Prefix[value] must ends with underscore (_)");
            }
            checkName(source, prefix, "", "@Prefix[value]");
            return prefix;
        }
        return "";
    }

    @Override
    public boolean isActivated(Class<? extends Segment> type) {
        return get(type, Object.class, Entity.class) != null;
    }

    @Override
    public TypeContext beforeSegment(Class<? extends Segment> type) throws BeanException {
        final String prefix = getPrefix(type);
        return new JpaColumnContext(type, prefix);
    }

}
