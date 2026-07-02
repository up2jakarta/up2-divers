package io.github.up2jakarta.job.ctx;

import org.slf4j.Logger;

import java.time.Duration;
import java.time.LocalDateTime;

import static io.github.up2jakarta.job.ctx.ContextProvider.debug;
import static java.time.LocalTime.ofSecondOfDay;


public abstract class Loggable {

    protected abstract Logger getLogger();

    @SuppressWarnings("unused")
    protected final void logContext(String reference, String key, Object value, boolean output) {
        debug(getLogger(), "Context", reference, key, value, output);
    }

    @SuppressWarnings("unused")
    protected final void logDuration(String context, Object uid, LocalDateTime startTime) {
        if (startTime != null) {
            final Duration d = Duration.between(startTime, LocalDateTime.now());
            this.getLogger().info("{} execution #[{}] duration {}", context, uid, ofSecondOfDay(d.toSeconds()));
        }
    }

}
