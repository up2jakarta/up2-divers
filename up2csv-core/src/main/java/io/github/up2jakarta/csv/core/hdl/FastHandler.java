package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.xml.api.SeverityType;
import jakarta.validation.ConstraintViolation;

import java.util.List;

/**
 * Input events handler that implements fail-fast technique.
 * It's fails at the first error having severity equals or greater than {@link FastHandler#level}
 * <p>
 * It's similar to {@link io.github.up2jakarta.csv.core.hdl.FatalCollector} but does not collect events.
 * <p>
 * this collector is the default mode for {@link io.github.up2jakarta.csv.core.Up2Mapper} when working flat-data.
 */
public final class FastHandler<D extends DataType<D>> extends EventHandler<IRecord<?>, D, IEvent<D>> {

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
    public static <R extends IRecord<?>, D extends DataType<D>, V extends IEvent<D>> EventHandler<R, D, V> of(SeverityType level) {
        //noinspection unchecked
        return (EventHandler<R, D, V>) switch (level) {
            case WARNING -> FastHandler.WARNING;
            case FATAL -> FastHandler.FATAL;
            default -> FastHandler.ERROR;
        };
    }

    @Override
    public List<IEvent<D>> toCollection() {
        return List.of();
    }

    @Override
    public void handle(D data, int offset, SeverityType level, String code, String message) {
        if (level.getLevel() >= this.level) {
            throw new FastException(data, offset, level, code, message);
        }
    }

    @Override
    public void handle(D data, int offset, SeverityType level, String code, Throwable cause) {
        if (level.getLevel() >= this.level) {
            throw new FastException(data, offset, level, code, cause);
        }
    }

    @Override
    public void handle(D data, int offset, Exception exception, Error config) {
        final SeverityType type = level(exception, config);
        if (type.getLevel() >= level) {
            final String code = code(exception, config);
            throw new FastException(data, offset, type, code, exception);
        }
    }

    @Override
    public void handle(D data, int offset, ConstraintViolation<?> violation, Error config) {
        final SeverityType type = level(violation, config);
        if (type.getLevel() >= level) {
            final String code = code(violation, config);
            throw new FastException(data, offset, type, code, violation.getMessage());
        }
    }

}
