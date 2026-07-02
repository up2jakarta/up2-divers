package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.api.hdl.EventCode;
import io.github.up2jakarta.csv.api.hdl.EventLevel;
import io.github.up2jakarta.csv.data.ITerm;
import io.github.up2jakarta.lov.SeverityType;
import jakarta.validation.ConstraintViolation;

/**
 * Input events handler that implements fail-fast technique.
 * It's fails at the first error having level equals or greater than {@link FastHandler#level}
 * <p>
 * It's similar to {@link PropertyCollector} but does not collect events.
 * <p>
 * this collector is the default mode for {@link io.github.up2jakarta.csv.core.Up2Mapper} when working flat-data.
 */
public final class FastHandler<D extends ITerm<D>> extends EventHandler<D> {

    private static final EventHandler<?> FATAL = new FastHandler<>(SeverityType.FATAL);
    private static final EventHandler<?> ERROR = new FastHandler<>(SeverityType.ERROR);
    private static final EventHandler<?> WARNING = new FastHandler<>(SeverityType.WARNING);

    private final int level;

    private FastHandler(SeverityType level) {
        this.level = level.getAsInt();
    }

    /**
     * Returns a pre-configured handler that throws any error that is greater or equals tho the given level.
     *
     * @param level the fast-failure level
     * @param <D>   the business term-type
     * @return an instance that fails at the first error.
     */
    @SuppressWarnings("unchecked")
    public static <D extends ITerm<D>> EventHandler<D> of(SeverityType level) {
        return (EventHandler<D>) switch (level) {
            case WARNING -> FastHandler.WARNING;
            case FATAL -> FastHandler.FATAL;
            default -> FastHandler.ERROR;
        };
    }

    @Override
    public void handle(EventLevel level, EventCode code, D data, int offset, Throwable cause) {
        final SeverityType event = level.get();
        if (event.getAsInt() >= this.level) {
            throw new FailureException(data, offset, event, code.get(), cause);
        }
    }

    @Override
    public void handle(EventLevel level, EventCode code, D type, Integer offset, ConstraintViolation<?> cause) {
        final SeverityType event = level.get();
        if (event.getAsInt() >= this.level) {
            throw new FailureException(type, offset, event, code.get(), cause.getMessage());
        }
    }

}
