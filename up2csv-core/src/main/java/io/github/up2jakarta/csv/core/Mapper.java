package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.annotation.Severity;
import io.github.up2jakarta.csv.annotation.Truncated;
import io.github.up2jakarta.csv.exception.BeanException;
import io.github.up2jakarta.csv.extension.BeanContext;
import io.github.up2jakarta.csv.misc.Phase;
import io.github.up2jakarta.csv.persistence.InputError;
import io.github.up2jakarta.csv.persistence.InputRow;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.slf4j.Logger;

import java.util.Set;

import static java.util.Arrays.stream;

/**
 * Map and validate input data to a configurable bean that supports only {@link String} type.
 *
 * @param <S> the segment type
 */
public abstract class Mapper<S extends Segment> {

    final Validator validator;
    final Property<?>[] properties;
    final Class<S> type;
    final int offset;
    private final ValidationContext validation;

    Mapper(Class<S> type, BeanContext context, Validator validator) throws BeanException {
        final Truncated truncated = type.getAnnotation(Truncated.class);
        this.offset = (truncated != null) ? truncated.value() : 0;
        this.properties = BeanSupport.getProperties(context, type, 0);
        this.type = type;
        this.validator = validator;
        this.validation = ValidationContext.of(type);
    }

    /**
     * Map and validate input data to java bean depending on annotations like {@link Position}.
     *
     * @param columns the input data
     * @return the parsed segment
     * @throws BeanException for any problem configuring and assigning fields of the input to bean properties
     */
    public abstract S map(final String... columns) throws BeanException;

    /**
     * Map and validate input data to java bean depending on annotations like {@link Position}.
     * and collect errors in the given collector after full-filling the error properties.
     *
     * @param row       the input data
     * @param collector the error collector
     * @param <R>       the row type
     * @param <V>       the error type
     * @return the parsed segment
     * @throws BeanException for any problem configuring and assigning fields of the input to bean properties
     */
    public final <R extends InputRow, V extends InputError<R, ?>> S map(R row, EventHandler<R, ?, V> collector) throws BeanException {
        collector.check(row);
        final S segment = map(row.getColumns());
        if (validation.isEnabled()) {
            this.validate(segment, validation.getGroups(), collector);
        }
        return segment;
    }

    <R extends InputRow, V extends InputError<R, ?>> void validate(Object b, Class<?>[] g, EventHandler<R, ?, V> h) {
        final Set<ConstraintViolation<Object>> violations = validator.validate(b, g);
        for (final ConstraintViolation<?> v : violations) {
            final Property<?> p = findProperty(v);
            if (p != null) {
                h.handleEvent(p.getOffset() + this.offset, v, p.getField().getAnnotation(Severity.class));
            } else {
                final Logger logger = Phase.MAPPING.getLogger();
                logger.warn("Unknown {}.{} has error: {}", v.getRootBeanClass(), v.getPropertyPath(), v.getMessage());
            }
        }
    }

    /**
     * Compute the property of the given {@link ConstraintViolation}.
     *
     * @param violation the constraint violation
     * @return the found one
     */
    private Property<?> findProperty(final ConstraintViolation<?> violation) {
        final String path = violation.getPropertyPath().toString();
        final String[] fieldNames = path.split("\\.");
        Property<?> property = null;
        Property<?>[] properties = this.properties;
        for (String fieldName : fieldNames) {
            property = stream(properties).filter(p -> fieldName.equals(p.getField().getName())).findFirst().orElse(null);
            if (property instanceof FragmentProperty fp) {
                properties = fp.getProperties();
            } else {
                return property;
            }
        }
        return property;
    }

}
