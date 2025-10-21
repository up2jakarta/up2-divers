package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.IError;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.fmt.hdl.FastException;
import io.github.up2jakarta.xml.api.SeverityType;
import jakarta.validation.ConstraintViolation;

import java.util.List;

/**
 * Internal fail-fast implementation.
 */
public class FastHandler<D extends DataType<D>> extends EventHandler<IRecord<?>, D, IError<D>> {

    private static final EventHandler<?, ?, ?> FATAL = new FastHandler<>(SeverityType.FATAL);
    private static final EventHandler<?, ?, ?> ERROR = new FastHandler<>(SeverityType.ERROR);
    private static final EventHandler<?, ?, ?> WARNING = new FastHandler<>(SeverityType.WARNING);

    private final int level;

    private FastHandler(SeverityType severity) {
        super(null);
        this.level = severity.getLevel();
    }

    /**
     * Returns a pre-configured handler that throws any error that is greater or equals tho the given level.
     *
     * @param level the minimal error level
     * @param <R>   the input record-segment
     * @param <D>   the business data-type
     * @param <V>   the error type
     * @return an instance that fails at the first error.
     */
    public static <R extends IRecord<?>, D extends DataType<D>, V extends IError<D>> EventHandler<R, D, V> of(SeverityType level) {
        //noinspection unchecked
        return (EventHandler<R, D, V>) switch (level) {
            case WARNING -> FastHandler.WARNING;
            case FATAL -> FastHandler.FATAL;
            default -> FastHandler.ERROR;
        };
    }

    @Override
    public List<IError<D>> toCollection() {
        return List.of();
    }

    @Override
    public void handleEvent(D data, int offset, ConstraintViolation<?> violation, Error config) {
        final SeverityType type = errorSeverity(violation, config);
        if (type.getLevel() >= level) {
            final String code = errorCode(violation, config);
            throw new FastException(data, offset, type, code, violation.getMessage());
        }
    }

    @Override
    public void handleEvent(D data, int offset, Exception exception, Error config) {
        final SeverityType type = errorSeverity(exception, config);
        if (type.getLevel() >= level) {
            final String code = errorCode(exception, config);
            throw new FastException(data, offset, type, code, exception);
        }
    }

}
