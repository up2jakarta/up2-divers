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
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Optional;
import java.util.stream.Stream;

import static io.github.up2jakarta.csv.core.EventHandler.ERROR_CODE_LIST;
import static io.github.up2jakarta.xml.clv.CodeListConverter.parse;

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
                throw new BeanException(type, String.valueOf(i), "must be no null");
            }
            for (int j = i + 1; j < values.length; j++) {
                if (value == values[j]) {
                    throw new BeanException(type, value.getCode(), "must be unique");
                }
            }
        }
    }

    @Override
    public PropertyConverter<? extends CodeList<?>> forParsing(Up2CodeList config, Field property, Class<?> type) throws BeanException {
        //noinspection unchecked
        final Class<CodeList<?>> clType = (Class<CodeList<?>>) type;
        if (!clType.isEnum()) {
            throw new BeanException(property, "type must be enum");
        }
        final Type[] types = Beans.getTypeArguments(clType, CodeList.class);
        if (types.length == 0 || clType != types[0]) {
            throw new BeanException(property, "type must implements CodeList<" + clType.getSimpleName() + ">");
        }
        checkUnique(clType, clType.getEnumConstants());
        final Optional<Error> error = getError(property);
        final SeverityType level = error.map(Error::severity).orElse(SeverityType.ERROR);
        final String code = error.map(Error::value).orElse(ERROR_CODE_LIST);
        return v -> parse(v, clType, Stream.of(clType.getEnumConstants()), level, code);
    }

    @Override
    public PropertyFormatter<? extends CodeList<?>> forFormatting(Up2CodeList config, Field property) {
        return CodeList::getCode;
    }

}
