package io.github.up2jakarta.csv.slv;

import io.github.up2jakarta.csv.api.ext.Conversion;
import io.github.up2jakarta.csv.api.ext.ConversionResolver;
import io.github.up2jakarta.csv.api.ext.PropertyConverter;
import io.github.up2jakarta.csv.api.ext.PropertyFormatter;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Up2CodeList;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.Beans;
import io.github.up2jakarta.csv.core.Errors;
import io.github.up2jakarta.xml.api.SeverityType;
import io.github.up2jakarta.xml.clv.CodeList;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Optional;
import java.util.stream.Stream;

import static io.github.up2jakarta.xml.clv.CodeListConverter.parse;

/**
 * Up2 {@link Conversion} resolver that supports {@link CodeList} types.
 */
@Named
@Singleton
public final class CodeListResolver extends ConversionResolver<Up2CodeList> {

    @Override
    public PropertyConverter<? extends CodeList<?>> forParsing(Up2CodeList config, Field property) throws BeanException {
        //noinspection unchecked
        final Class<CodeList<?>> clType = (Class<CodeList<?>>) property.getType();
        if (!clType.isEnum()) {
            throw new BeanException(property, "type must be enum");
        }
        final Type[] types = Beans.getTypeArguments(clType, CodeList.class);
        if (types.length == 0 || clType != types[0]) {
            throw new BeanException(property, "type must implements CodeList<" + clType.getSimpleName() + ">");
        }
        final Optional<Error> error = getError(property);
        final SeverityType type = error.map(Error::severity).orElse(SeverityType.ERROR);
        final String code = error.map(Error::value).orElse(Errors.ERROR_CODE_LIST);
        return v -> parse(v, clType, Stream.of(clType.getEnumConstants()), type, code);
    }

    @Override
    public PropertyFormatter<? extends CodeList<?>> forFormatting(Up2CodeList config, Field property) {
        return CodeList::getCode;
    }

}
