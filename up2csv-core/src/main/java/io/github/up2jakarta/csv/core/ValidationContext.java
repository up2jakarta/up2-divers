package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.annotation.Validated;
import io.github.up2jakarta.csv.exception.BeanException;
import jakarta.validation.Valid;

import java.lang.reflect.Field;

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

    private static void checkGroups(Validated validated, Class<?> source, String name) throws BeanException {
        final Valid valid = source.getAnnotation(Valid.class);
        if (valid != null) {
            throw new BeanException(source, name, "must not be annotated by @Valid");
        }
        for (final Class<?> group : validated.groups()) {
            if (!group.isInterface()) {
                throw new BeanException(source, name, "@Validated[value = " + group.getName() + ".class must be an interface]");
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
    public static ValidationContext of(Class<?> type) throws BeanException {
        final Validated validated = type.getAnnotation(Validated.class);
        if (validated != null) {
            if (validated.groups().length == 0) {
                return DEFAULT;
            }
            checkGroups(validated, type, "class");
            return new ValidationContext(true, validated.groups());
        }
        final Valid valid = type.getAnnotation(Valid.class);
        if (valid != null) {
            return DEFAULT;
        }
        return DISABLED;
    }

    public static ValidationContext of(Field field) throws BeanException {
        final Validated validated = field.getAnnotation(Validated.class);
        if (validated != null) {
            if (validated.groups().length == 0) {
                throw new BeanException(field, "@Validated must be replaced by @Valid");
            }
            checkGroups(validated, field.getDeclaringClass(), field.getName());
            return new ValidationContext(true, validated.groups());
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
