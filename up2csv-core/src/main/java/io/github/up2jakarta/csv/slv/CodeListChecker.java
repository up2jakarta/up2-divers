package io.github.up2jakarta.csv.slv;

import io.github.up2jakarta.csv.api.ext.TypeResolver;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Up2CodeList;
import io.github.up2jakarta.csv.core.BeanContext;
import io.github.up2jakarta.lov.*;
import io.github.up2jakarta.lov.Support.Parameter;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.lov.core.Overrides;
import io.github.up2jakarta.lov.core.TypeContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.lang.reflect.Field;
import java.util.*;

import static io.github.up2jakarta.csv.api.IEvent.EC_CODE_LIST;
import static io.github.up2jakarta.csv.core.ext.Beans.*;
import static io.github.up2jakarta.lov.SeverityType.ERROR;
import static java.lang.String.join;
import static java.util.Collections.unmodifiableMap;
import static java.util.function.Predicate.not;

@Named
@Singleton
public final class CodeListChecker implements TypeResolver<CodeList<?>, Up2CodeList> {

    private final BeanContext context;

    @Inject
    public CodeListChecker(BeanContext context) {
        this.context = context;
    }

    private static String unquote(StringBuilder token) {
        var value = token.toString().trim();
        token.setLength(0);
        var max = value.length() - 1;
        while (value.charAt(0) == '"' && value.charAt(max) == '"') {
            value = value.substring(1, max);
        }
        return value;
    }

    private static List<String> parse(String input, char delimiter) {
        if (input.isEmpty()) {
            return List.of();
        }
        final List<String> tokens = new LinkedList<>();
        final StringBuilder token = new StringBuilder();
        var quoted = false;
        for (int i = 0, size = input.length(); i < size; i++) {
            final char c = input.charAt(i);
            if (c == '"') {
                quoted = !quoted;
            }
            if (c == delimiter && !quoted) {
                tokens.add(unquote(token));
                token.setLength(0);
                continue;
            }
            token.append(c);
        }
        if (!token.isEmpty()) {
            tokens.add(unquote(token));
        }
        return tokens;
    }

    private static Map<String, String> arguments(Field pf, Up2CodeList cl, Support config) throws BeanException {
        final List<String> tokens = parse(cl.args(), '&').stream().filter(not(String::isEmpty)).toList();
        final Map<String, String> result = new LinkedHashMap<>(config.value().length);
        for (final String token : tokens) {
            final List<String> entry = parse(token, '=');
            if (entry.size() == 2) {
                result.put(entry.getFirst(), entry.getLast());
            } else if (!entry.isEmpty()) {
                final String key = entry.getFirst();
                entry.removeFirst();
                final String value = join("=", entry).replace("\"", "\\\"");
                throw new BeanException(pf, "invalid @Up2CodeList[args]: #argument[" + key + "]=\"" + value + "\"");
            }
        }
        final List<String> names = new ArrayList<>(config.value().length);
        for (final Parameter p : config.value()) {
            final String name = p.value();
            final String value = result.computeIfAbsent(name, k -> p.defaultValue());
            if (p.required() && value.isEmpty()) {
                throw new BeanException(pf, "@Up2CodeList[value] requires #argument[" + name + "] ");
            }
            names.add(name);
        }
        final List<String> keys = result.keySet().stream().filter(not(names::contains)).toList();
        if (!keys.isEmpty()) {
            throw new BeanException(pf, "@Up2CodeList[value] does not recognize arguments: " + join(", " + keys));
        }
        return unmodifiableMap(result);
    }

    private CodeListResolver<? extends CodeList<?>> get(Field pf, Class<?> pt, Up2CodeList config) throws BeanException {
        final Class<? extends CodeListResolver<?>> clp = config.type();
        final Class<?> support = getTypeArgument(clp, CodeListResolver.class, 0, void.class);
        if (!support.isAssignableFrom(pt)) {
            throw new BeanException(pf, "@Up2CodeList[value] does not support " + pt);
        }
        if (ConstantProvider.class.equals(clp)) {
            return ConstantProvider.INSTANCE;
        }
        if (DynamicProvider.class.equals(clp)) {
            return DynamicProvider.INSTANCE;
        }
        return getBean(context, clp, config.name());
    }

    @Override
    @SuppressWarnings("unchecked")
    public TypeAdapter<? extends CodeList<?>> resolve(Field pf, Class<CodeList<?>> pt, Up2CodeList pc) throws BeanException {
        final Class<?> support = getTypeArgument(pt, CodeList.class, 0, void.class);
        if (!(support.isAssignableFrom(pt) || pt.isAssignableFrom(support))) {
            throw new BeanException(pf, "type must implements CodeList<" + getTypeName(pt) + ">");
        }
        final CodeListResolver<CodeList<?>> resolver = (CodeListResolver<CodeList<?>>) this.get(pf, pt, pc);
        final Optional<Error> error = error(pf, pt);
        final SeverityType level = error.map(Error::level).orElse(ERROR);
        final String code = error.map(Error::value).orElse(EC_CODE_LIST);
        final Support config = Overrides.get(resolver.getClass(), CodeListResolver.class, Support.class);
        final String name = (pc.value().isEmpty()) ? getTypeName(pt) : pc.value();
        if (config != null) {
            if (Arrays.stream(config.excludes()).anyMatch(ns -> ns.isAssignableFrom(pt))) {
                throw new BeanException(pf, "@Up2CodeList[value] does not support " + pt);
            }
            return resolver.resolve(pt, new TypeContext(pf, name, level, code, arguments(pf, pc, config)));
        }
        return resolver.resolve(pt, new TypeContext(pf, name, level, code, Map.of()));
    }

}
