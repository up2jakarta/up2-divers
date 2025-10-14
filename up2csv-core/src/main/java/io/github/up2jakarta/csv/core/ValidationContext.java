package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.cfg.ValidOverride;
import io.github.up2jakarta.csv.data.Segment;
import jakarta.validation.Valid;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;

import static io.github.up2jakarta.csv.core.BeanException.of;
import static io.github.up2jakarta.csv.core.ext.Path.getOverride;

/**
 * Context representation for JSR-303 validation.
 */
public class ValidationContext {

    public static final ValidationContext DISABLED = new ValidationContext(false);
    public static final ValidationContext DEFAULT = new ValidationContext(true);

    private final boolean enabled;
    private final Class<?>[] groups;

    private ValidationContext(boolean enabled, Class<?>... groups) {
        this.enabled = enabled;
        this.groups = groups;
    }

    private static void checkGroups(ValidOverride valid, AnnotatedElement source) throws BeanException {
        if (source.isAnnotationPresent(Valid.class)) {
            throw of(source, "must not be annotated by @Valid");
        }
        for (final Class<?> group : valid.groups()) {
            if (!group.isInterface()) {
                throw of(source, "@ValidOverride[value = " + group.getName() + ".class must be an interface]");
            }
        }
    }

    /**
     * Factory method that create context from the given bean type.
     *
     * @param type the bean type
     * @return the validation context
     * @throws BeanException if wrong configuration
     */
    public static ValidationContext from(Class<? extends Segment> type) throws BeanException {
        while (type != Segment.class && Segment.class.isAssignableFrom(type)) {
            final ValidOverride override = getOverride(ValidOverride.class, type, ValidOverride::path);
            if (override != null) {
                if (override.disable()) {
                    return DISABLED;
                }
                if (override.groups().length == 0) {
                    return DEFAULT;
                }
                checkGroups(override, type);
                return new ValidationContext(true, override.groups());
            }
            if (type.isAnnotationPresent(Valid.class)) {
                return DEFAULT;
            }
            //noinspection unchecked
            type = (Class<? extends Segment>) type.getSuperclass();
        }
        return DISABLED;
    }

    public ValidationContext build(Field field, ValidOverride override) throws BeanException {
        if (enabled && override != null && !override.disable()) {
            checkGroups(override, field);
            return new ValidationContext(true, override.groups());
        }
        return DISABLED;
    }

    /**
     * @return {@link true} if the validation is enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * @return JSR-303 validation groups
     */
    public Class<?>[] getGroups() {
        return groups;
    }

}
