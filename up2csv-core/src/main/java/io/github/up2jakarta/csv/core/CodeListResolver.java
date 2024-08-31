package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.annotation.Error;
import io.github.up2jakarta.csv.annotation.Up2CodeList;
import io.github.up2jakarta.csv.exception.BeanException;
import io.github.up2jakarta.csv.extension.CodeList;
import io.github.up2jakarta.csv.extension.Conversion;
import io.github.up2jakarta.csv.extension.ConversionResolver;
import io.github.up2jakarta.csv.extension.SeverityType;
import io.github.up2jakarta.csv.misc.Errors;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Optional;
import java.util.stream.Stream;

import static io.github.up2jakarta.csv.misc.CodeListConverter.parse;

/**
 * Up2 {@link Conversion} resolver that supports {@link CodeList} types.
 */
@Named
@Singleton
public final class CodeListResolver extends ConversionResolver<Up2CodeList> {

    static Optional<Error> getError(Field property) {
        final Error error = property.getAnnotation(Error.class);
        if (error == null) {
            return Optional.ofNullable(property.getType().getAnnotation(Error.class));
        }
        return Optional.of(error);
    }

    @Override
    public Conversion<? extends CodeList<?>> resolve(Up2CodeList config, Field property) throws BeanException {
        //noinspection unchecked
        final Class<CodeList<?>> clType = (Class<CodeList<?>>) property.getType();
        if (!clType.isEnum()) {
            throw new BeanException(property, "type must be enum");
        }
        final Type[] types = Beans.getTypeArguments(clType, CodeList.class);
        if (types.length == 0 || clType != types[0]) {
            throw new BeanException(property, "type must implements CodeList<" + clType.getSimpleName() + ">");
        }
        final Optional<Error> error = CodeListResolver.getError(property);
        final SeverityType type = error.map(Error::severity).orElse(SeverityType.ERROR);
        final String code = error.map(Error::value).orElse(Errors.ERROR_CODE_LIST);
        return v -> parse(v, clType, Stream.of(clType.getEnumConstants()), type, code);
    }
}
