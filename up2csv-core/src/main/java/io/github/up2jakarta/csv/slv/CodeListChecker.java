package io.github.up2jakarta.csv.slv;

import io.github.up2jakarta.csv.api.Container;
import io.github.up2jakarta.csv.api.ext.Argument;
import io.github.up2jakarta.csv.api.ext.TypeResolver;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Up2CodeList;
import io.github.up2jakarta.lov.*;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.lov.core.Overrides;
import io.github.up2jakarta.lov.core.TypeContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.lang.reflect.Field;
import java.util.*;

import static io.github.up2jakarta.csv.api.IEvent.EC_CODE_LIST;
import static io.github.up2jakarta.csv.ext.Beans.*;
import static io.github.up2jakarta.lov.SeverityType.ERROR;
import static java.lang.String.join;
import static java.util.function.Predicate.not;

@Named
@Singleton
public final class CodeListChecker implements TypeResolver<CodeList<?>, Up2CodeList> {

    private final Container context;

    @Inject
    public CodeListChecker(Container context) {
        this.context = context;
    }

    private static Map<String, String> arguments(Field pf, Argument[] args, Parameter[] ps) throws BeanException {
        final Map<String, String> result = new HashMap<>(ps.length);
        for (final Argument arg : args) {
            result.put(arg.key(), arg.value());
        }
        final List<String> names = new ArrayList<>(ps.length);
        for (final Parameter parameter : ps) {
            final String name = parameter.value();
            final String value = result.computeIfAbsent(name, k -> parameter.defaultValue());
            if (parameter.required() && value.isEmpty()) {
                throw new BeanException(pf, "@Up2CodeList[value] requires #argument[" + name + "] ");
            }
            names.add(name);
        }
        final List<String> keys = result.keySet().stream().filter(not(names::contains)).toList();
        if (!keys.isEmpty()) {
            throw new BeanException(pf, "@Up2CodeList[value] does not recognize arguments: " + join(", " + keys));
        }
        return Map.copyOf(result);
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
        return Container.from(context, clp, config.name());
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
        final Support sp = Overrides.get(resolver.getClass(), CodeListResolver.class, Support.class);
        final String name = (pc.value().isEmpty()) ? getTypeName(pt) : pc.value();
        if (sp != null) {
            if (Arrays.stream(sp.excludes()).anyMatch(ns -> ns.isAssignableFrom(pt))) {
                throw new BeanException(pf, "@Up2CodeList[value] does not support " + pt);
            }
            return resolver.resolve(pt, new TypeContext(pf, name, level, code, arguments(pf, pc.args(), sp.value())));
        }
        return resolver.resolve(pt, new TypeContext(pf, name, level, code, Map.of()));
    }

}
