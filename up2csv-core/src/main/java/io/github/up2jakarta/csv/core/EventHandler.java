package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.IError;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.data.Collectable;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.fmt.hdl.FastCollector;
import io.github.up2jakarta.csv.fmt.hdl.FullCollector;
import io.github.up2jakarta.xml.api.SeverityType;
import io.github.up2jakarta.xml.clv.CodeListException;
import io.github.up2jakarta.xml.clv.PropertyException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.metadata.ConstraintDescriptor;

import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.util.*;

import static java.util.Optional.ofNullable;

/**
 * Internal handler that handles events during the mapping, validation and parsing phases.
 *
 * @param <R> the input row type
 * @param <D> the business data type
 * @param <E> the error type
 * @see Up2Collector for custom definition.
 * @see FastHandler  for fail-fast handler
 */
public abstract class EventHandler<R extends IRecord<?>, D extends DataType<D>, E extends IError<D>> implements Collectable<E> {

    /**
     * Default error code for JS-303 {@link jakarta.validation.ConstraintViolation}
     */
    public static final String ERROR_VALIDATOR = "UP2-V001";
    /**
     * Default error code for {@link io.github.up2jakarta.csv.cfg.Up2Converter}
     */
    public static final String ERROR_CONVERTER = "UP2-C002";
    /**
     * Default error code for {@link io.github.up2jakarta.csv.cfg.Processor}
     */
    public static final String ERROR_PROCESSOR = "UP2-P001";
    /**
     * Default error code for {@link io.github.up2jakarta.csv.cfg.Up2CodeList}
     */
    public static final String ERROR_CODE_LIST = "UP2-P002";
    /**
     * Default error code for {@link jakarta.xml.bind.annotation.XmlEnum}
     */
    public static final String ERROR_XML_ENUM = "UP2-P003";
    /**
     * Default error code for {@link jakarta.xml.bind.annotation.XmlEnum}
     */
    public static final String ERROR_BOOLEAN = "UP2-P004";
    private static final List<String> CLASS_NAMES = Arrays.asList(
            // Business
            BSEntry.class.getName(),
            BSGetter.class.getName(),
            // Handlers
            EventHandler.class.getName(),
            FastHandler.class.getName(),
            Up2Collector.class.getName(),
            FastCollector.class.getName(),
            FullCollector.class.getName(),
            // Properties
            PProperty.class.getName(),
            PFProperty.class.getName(),
            Property.class.getName(),
            PProperty.class.getName() + "$" + PProperty.POProperty.class.getSimpleName(),
            PProperty.class.getName() + "$" + PProperty.PSProperty.class.getSimpleName(),
            PProperty.class.getName() + "$" + PProperty.WProcessor.class.getSimpleName(),
            Property.class.getName() + "$" + Property.Accessor.class.getSimpleName(),
            Property.class.getName() + "$" + Property.ROAccess.class.getSimpleName(),
            Property.class.getName() + "$" + Property.WOAccess.class.getSimpleName(),
            // Processors
            Up2Mapper.class.getName(),
            Up2Format.class.getName(),
            BeanValidator.class.getName(),
            BeanValidator.class.getName() + "$" + BeanValidator.Node.class.getSimpleName(),
            Up2Mapper.class.getName() + "$" + Up2Mapper.Node.class.getSimpleName(),
            Up2Format.class.getName() + "$" + Up2Format.Node.class.getSimpleName()
    );
    protected final R row;

    EventHandler(R row) {
        this.row = row;
    }

    private static Optional<Error> error(ConstraintViolation<?> violation) {
        final ConstraintDescriptor<?> descriptor = violation.getConstraintDescriptor();
        final Class<? extends Annotation> annotationType = descriptor.getAnnotation().annotationType();
        return descriptor.getPayload().stream()
                .filter(Error.Payload.class::isAssignableFrom)
                .map(c -> c.getAnnotation(Error.class))
                .filter(Objects::nonNull)
                .max(Comparator.comparingInt(e -> e.severity().getLevel()))
                .or(() -> ofNullable(annotationType.getAnnotation(Error.class)));
    }

    static SeverityType errorSeverity(ConstraintViolation<?> violation, Error config) {
        if (config != null) {
            return config.severity();
        }
        return error(violation).map(Error::severity).orElse(SeverityType.ERROR);
    }

    static String errorCode(ConstraintViolation<?> violation, Error config) {
        if (config != null) {
            return config.value();
        }
        return error(violation).map(Error::value).orElse(ERROR_VALIDATOR);
    }

    static SeverityType errorSeverity(Exception exception, Error config) {
        if (config != null) {
            return config.severity();
        }
        if (exception instanceof PropertyException pException) {
            return pException.getSeverity();
        }
        return SeverityType.ERROR;
    }

    static String errorCode(Exception exception, Error config) {
        if (exception instanceof CodeListException clException) {
            return clException.getCode();
        }
        if (config != null) {
            return config.value();
        }
        if (exception instanceof PropertyException pException) {
            return pException.getCode();
        }
        return ERROR_CONVERTER;
    }

    /**
     * Prints this throwable and its backtrace to the specified <code>printer</code>.
     * Notes that the stack elements will be truncated from the given <code>entry</code> point class
     *
     * @param error   the exception to print
     * @param entry   the entry point
     * @param printer the printer stream
     */
    public static void stackTrace(Class<?> entry, Throwable error, PrintWriter printer) {
        final String cn = entry.getName();
        printer.println(error);
        final StackTraceElement[] traces = error.getStackTrace();
        for (final StackTraceElement element : traces) {
            if (cn.equals(element.getClassName())) {
                break;
            } else if (!CLASS_NAMES.contains(element.getClassName())) {
                printer.println("\t" + element);
            }
        }
        final Throwable cause = error.getCause();
        if (cause != null) {
            printer.println(cause);
        }
    }

    /**
     * Handle the JSR-303 constraint violation caused by the input at the given offset.
     *
     * @param type      the data type
     * @param offset    the input index
     * @param violation the JSR-303 constraint violation
     * @param config    the error annotation defined at property level
     */
    public abstract void handleEvent(D type, int offset, ConstraintViolation<?> violation, Error config);

    /**
     * Handle any exception caused by the input at the given offset.
     *
     * @param type      the data type
     * @param offset    the input index
     * @param exception thr thrown exception
     * @param config    the error annotation defined at property level
     */
    public abstract void handleEvent(D type, int offset, Exception exception, Error config);

}
