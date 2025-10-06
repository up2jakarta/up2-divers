package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.impl.FastCollector;
import io.github.up2jakarta.csv.impl.FullCollector;
import io.github.up2jakarta.xml.api.SeverityType;
import io.github.up2jakarta.xml.clv.CodeListException;
import io.github.up2jakarta.xml.clv.PropertyException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.metadata.ConstraintDescriptor;

import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Optional.ofNullable;

public final class Errors {

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
    public static final String ERROR_PROCESSOR = "UP2-P003";

    /**
     * Default error code for {@link io.github.up2jakarta.csv.cfg.Up2CodeList}
     */
    public static final String ERROR_CODE_LIST = "UP2-P004";

    /**
     * Default error code for {@link jakarta.xml.bind.annotation.XmlEnum}
     */
    public static final String ERROR_XML_ENUM = "UP2-P005";

    /**
     * Default error code for {@link jakarta.xml.bind.annotation.XmlEnum}
     */
    public static final String ERROR_BOOLEAN = "UP2-P006";

    private static final List<String> CLASS_NAMES = List.of(
            ObjectProperty.class.getName(),
            PositionProperty.class.getName(),
            StringProperty.class.getName(),
            BeanProperty.class.getName(),
            BeanNode.class.getName(),
            EventHandler.class.getName(),
            FastHandler.class.getName(),
            EventCollector.class.getName(),
            FastCollector.class.getName(),
            FullCollector.class.getName(),
            MapperFactory.class.getName() + "$" + MapperFactory.DefaultMapper.class.getSimpleName(),
            Mapper.class.getName(),
            ProcessorWrapper.class.getName(),
            TechnicalChecker.class.getName(),
            CompositeChecker.class.getName()
    );


    private Errors() {
    }

    private static Optional<Error> getError(ConstraintViolation<?> violation) {
        final ConstraintDescriptor<?> descriptor = violation.getConstraintDescriptor();
        final Class<? extends Annotation> annotationType = descriptor.getAnnotation().annotationType();
        return descriptor.getPayload().stream()
                .filter(Error.Payload.class::isAssignableFrom)
                .map(c -> c.getAnnotation(Error.class))
                .filter(Objects::nonNull)
                .max(Comparator.comparingInt(e -> e.severity().getLevel()))
                .or(() -> ofNullable(annotationType.getAnnotation(Error.class)));
    }

    static SeverityType getSeverity(ConstraintViolation<?> violation, Error config) {
        if (config != null) {
            return config.severity();
        }
        return getError(violation).map(Error::severity).orElse(SeverityType.ERROR);
    }

    static String getErrorCode(ConstraintViolation<?> violation, Error config) {
        if (config != null) {
            return config.value();
        }
        return getError(violation).map(Error::value).orElse(ERROR_VALIDATOR);
    }

    static SeverityType getSeverity(Exception exception, Error config) {
        if (config != null) {
            return config.severity();
        }
        if (exception instanceof PropertyException pException) {
            return pException.getSeverity();
        }
        return SeverityType.ERROR;
    }

    static String getErrorCode(Exception exception, Error config) {
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

    public static void stackTrace(Throwable error, Class<?> entry, PrintWriter printer) {
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

}
