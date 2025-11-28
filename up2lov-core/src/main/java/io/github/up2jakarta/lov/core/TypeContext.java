package io.github.up2jakarta.lov.core;

import io.github.up2jakarta.lov.IError;
import io.github.up2jakarta.lov.SeverityType;

import java.lang.reflect.Member;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import static io.github.up2jakarta.lov.core.AccessException.notNull;
import static java.util.Collections.unmodifiableMap;

/**
 * Immutable type-context of {@link io.github.up2jakarta.lov.CodeListResolver} arguments.
 */
public final class TypeContext implements Type {

    final Member source;
    private final Map<String, String> args;
    private final SeverityType level;
    private final String code;
    private final String name;

    public TypeContext(Member source, String name, SeverityType level, String code, Map<String, String> args) {
        this.args = unmodifiableMap(notNull(args, TypeContext.class, "arguments"));
        this.source = notNull(source, TypeContext.class, "source");
        this.level = notNull(level, TypeContext.class, "level");
        this.code = notNull(code, TypeContext.class, "code");
        this.name = notNull(name, TypeContext.class, "name");
    }

    /**
     * @see IError#getLevel()
     */
    public SeverityType getLevel() {
        return level;
    }

    /**
     * @see IError#getCode()
     */
    public String getCode() {
        return code;
    }

    @Override
    public String getTypeName() {
        return name;
    }

    /**
     * @see Map#computeIfAbsent(Object, Function)
     */
    public String get(String key, Class<?> type, Computer<String> defaultValue) throws BeanException {
        final String value = args.get(key);
        if (value == null) {
            return defaultValue.apply(type, this);
        }
        return value;
    }

    /**
     * @see Map#computeIfAbsent(Object, Function)
     */
    public String get(String key, Supplier<String> defaultValue) {
        final String value = args.get(key);
        if (value == null) {
            return defaultValue.get();
        }
        return value;
    }

    /**
     * @see Map#computeIfAbsent(Object, Function)
     */
    public String get(String key, String defaultValue) {
        final String value = args.get(key);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    /**
     * @see Map#get(Object)
     */
    public String get(String key) {
        return args.get(key);
    }

    /**
     * @see Map#containsKey(Object)
     */
    public boolean has(String key) {
        return args.containsKey(key);
    }

    @FunctionalInterface
    public interface Computer<T> {
        T apply(Class<?> type, TypeContext context) throws BeanException;
    }

}
