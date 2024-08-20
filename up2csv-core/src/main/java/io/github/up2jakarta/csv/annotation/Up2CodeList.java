package io.github.up2jakarta.csv.annotation;

import io.github.up2jakarta.csv.core.CodeListResolver;
import io.github.up2jakarta.csv.extension.CodeList;
import io.github.up2jakarta.csv.extension.Conversion;

import java.lang.annotation.*;

import static io.github.up2jakarta.csv.misc.Errors.ERROR_CODE_LIST;

/**
 * Up2 {@link Conversion} resolver that supports {@link CodeList} types.
 *
 * @see io.github.up2jakarta.csv.misc.CodeListConverter
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Resolver(CodeListResolver.class)
public @interface Up2CodeList {

    /**
     * Overrides {@link io.github.up2jakarta.csv.extension.TypeConverter#getErrorCode()} and
     * {@link io.github.up2jakarta.csv.extension.TypeConverter#getErrorSeverity()}.
     *
     * @return the error annotation
     */
    Error value() default @Error(ERROR_CODE_LIST);

}
