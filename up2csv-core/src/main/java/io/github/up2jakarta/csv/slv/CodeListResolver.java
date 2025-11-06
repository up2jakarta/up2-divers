package io.github.up2jakarta.csv.slv;

import io.github.up2jakarta.csv.api.ext.Conversion;
import io.github.up2jakarta.csv.api.ext.ConversionResolver;
import io.github.up2jakarta.csv.api.ext.PropertyConverter;
import io.github.up2jakarta.csv.api.ext.PropertyFormatter;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Up2CodeList;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.ext.Beans;
import io.github.up2jakarta.xml.api.SeverityType;
import io.github.up2jakarta.xml.clv.CodeList;
import io.github.up2jakarta.xml.clv.CodeListException;
import io.github.up2jakarta.xml.clv.CodeListProvider;
import io.github.up2jakarta.xml.clv.DefaultProvider;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

import static io.github.up2jakarta.csv.api.IEvent.ERROR_CODE_LIST;
import static io.github.up2jakarta.xml.clv.CodeListConverter.find;
import static java.util.Arrays.asList;

/**
 * Up2 {@link Conversion} resolver that supports {@link CodeList} types.
 */
@Named
@Singleton
public final class CodeListResolver extends ConversionResolver<Up2CodeList> {

    public static void checkUnique(Class<?> type, CodeList<?>[] values) throws BeanException {
        for (var i = 0; i < values.length; i++) {
            final CodeList<?> value = values[i];
            if (value == null || value.getCode() == null) {
                throw new BeanException(type, String.valueOf(i), "must be not null");
            }
            for (int j = i + 1; j < values.length; j++) {
                if (value == values[j]) {
                    throw new BeanException(type, value.getCode(), "must be unique");
                }
            }
        }
    }

    private CodeList<?>[] values(Class<CodeList<?>> type, Class<? extends CodeListProvider<?>> clp) throws BeanException {
        final CodeList<?>[] values;
        if (DefaultProvider.class.equals(clp)) {
            values = DefaultProvider.INSTANCE.values(type);
            checkUnique(type, values);
        } else {
            values = this.<CodeListProvider<CodeList<?>>>getBean(clp).values(type);
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
    public PropertyConverter<? extends CodeList<?>> forParsing(Up2CodeList pc, Field pf, Class<?> pt) throws BeanException {
        //noinspection unchecked
        final Class<CodeList<?>> type = (Class<CodeList<?>>) pt;
        final Type[] types = Beans.getTypeArguments(type, CodeList.class);
        if (types.length == 0 || type != types[0]) {
            throw new BeanException(pf, "type must implements CodeList<" + type.getSimpleName() + ">");
        }
        final CodeList<?>[] values = values(type, pc.value());
        final Optional<Error> error = getError(pf);
        final SeverityType level = error.map(Error::severity).orElse(SeverityType.ERROR);
        final String code = error.map(Error::value).orElse(ERROR_CODE_LIST);
        if (values.length == 0) {
            return v -> {
                throw new CodeListException(type, v, level, code);
            };
        }
        final List<CodeList<?>> lov = asList(values);
        return v -> find(v, type, lov, level, code);
    }

    @Override
    public PropertyFormatter<? extends CodeList<?>> forFormatting(Up2CodeList config, Field property, Class<?> type) {
        return CodeList::getCode;
    }

}
