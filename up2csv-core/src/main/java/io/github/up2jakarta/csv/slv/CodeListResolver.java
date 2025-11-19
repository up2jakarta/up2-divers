package io.github.up2jakarta.csv.slv;

import io.github.up2jakarta.csv.api.ext.TypeResolver;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Up2CodeList;
import io.github.up2jakarta.lov.*;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.lov.core.TypeWrapper;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

import static io.github.up2jakarta.csv.api.IEvent.EC_CODE_LIST;
import static io.github.up2jakarta.csv.core.ext.Beans.*;
import static io.github.up2jakarta.lov.CodeListConverter.find;
import static io.github.up2jakarta.lov.SeverityType.ERROR;

@Named
@Singleton
public final class CodeListResolver extends TypeResolver<Up2CodeList> {

    public static void checkUnique(Class<?> type, List<? extends CodeList<?>> values) throws BeanException {
        for (var i = 0; i < values.size(); i++) {
            final CodeList<?> value = values.get(i);
            if (value == null || value.getCode() == null) {
                throw new BeanException(type, String.valueOf(i), "must be not null");
            }
            for (int j = i + 1; j < values.size(); j++) {
                if (value == values.get(j)) {
                    throw new BeanException(type, value.getCode(), "must be unique");
                }
            }
        }
    }

    private List<CodeList<?>> values(Class<CodeList<?>> type, Up2CodeList config) throws BeanException {
        final Class<? extends CodeListProvider<?>> clp = config.value();
        final List<CodeList<?>> values;
        if (DefaultProvider.class.equals(clp)) {
            values = DefaultProvider.INSTANCE.values(type);
            checkUnique(type, values);
        } else {
            values = this.<CodeListProvider<CodeList<?>>>getBean(clp, config.name()).values(type);
            checkUnique(type, values);
            for (final CodeList<?> value : values) {
                if (!type.isInstance(value)) {
                    throw new BeanException(type, value.getCode(), "must be instance of " + type);
                }
            }
        }
        return values;
    }

    @Override
    public TypeAdapter<? extends CodeList<?>> resolve(Field pf, Class<?> pt, Up2CodeList pc) throws BeanException {
        final Class<CodeList<?>> type = cast(pt);
        final Type[] types = getTypeArguments(type, CodeList.class);
        if (types.length == 0 || type != types[0]) {
            throw new BeanException(pf, "type must implements CodeList<" + getTypeName(type) + ">");
        }
        final Optional<Error> error = error(pf, type);
        final SeverityType level = error.map(Error::level).orElse(ERROR);
        final String code = error.map(Error::value).orElse(EC_CODE_LIST);
        final List<CodeList<?>> values = values(type, pc);
        if (values.isEmpty()) {
            final PropertyConverter<CodeList<?>> parser = (v) -> {
                throw new CodeListException(type, v, level, code);
            };
            return new TypeWrapper<>(type, parser, CodeList::getCode);
        }
        final PropertyConverter<CodeList<?>> parser = (v) -> find(v, type, values, level, code);
        return new TypeWrapper<>(type, parser, CodeList::getCode);
    }

}
