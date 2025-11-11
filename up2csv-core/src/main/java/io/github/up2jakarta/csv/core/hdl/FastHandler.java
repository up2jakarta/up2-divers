package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.xml.api.SeverityType;
import jakarta.validation.ConstraintViolation;

/**
 * Input events handler that implements fail-fast technique.
 * It's fails at the first error having severity equals or greater than {@link FastHandler#level}
 * <p>
 * It's similar to {@link PropertyCollector} but does not collect events.
 * <p>
 * this collector is the default mode for {@link io.github.up2jakarta.csv.core.Up2Mapper} when working flat-data.
 */
public final class FastHandler<D extends DataType<D>> extends EventHandler<D> {

    private static final EventHandler<?> FATAL = new FastHandler<>(SeverityType.FATAL);
    private static final EventHandler<?> ERROR = new FastHandler<>(SeverityType.ERROR);
    private static final EventHandler<?> WARNING = new FastHandler<>(SeverityType.WARNING);

    private final int level;

    private FastHandler(SeverityType severity) {
        this.level = severity.getLevel();
    }

    /**
     * Returns a pre-configured handler that throws any error that is greater or equals tho the given level.
     *
     * @param level the minimal error level
     * @param <D>   the business data-type
     * @return an instance that fails at the first error.
     */
    @SuppressWarnings("unchecked")
    public static <D extends DataType<D>> EventHandler<D> of(SeverityType level) {
        return (EventHandler<D>) switch (level) {
            case WARNING -> FastHandler.WARNING;
            case FATAL -> FastHandler.FATAL;
            default -> FastHandler.ERROR;
        };
    }

    @Override
    public void handle(D data, int offset, SeverityType level, String code, Throwable cause) {
        if (level.getLevel() >= this.level) {
            throw new FailureException(data, offset, level, code, cause);
        }
    }

    @Override
    public void handle(D data, int offset, Exception exception, Error config) {
        final SeverityType type = level(exception, config);
        if (type.getLevel() >= level) {
            final String code = code(exception, config);
            throw new FailureException(data, offset, type, code, exception);
        }
    }

    @Override
    public void handle(D data, Integer offset, ConstraintViolation<?> violation, Error config) {
        final SeverityType type = level(violation, config);
        if (type.getLevel() >= level) {
            final String code = code(violation, config);
            throw new FailureException(data, offset, type, code, violation.getMessage());
        }
    }

}
