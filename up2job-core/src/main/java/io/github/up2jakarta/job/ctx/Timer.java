package io.github.up2jakarta.job.ctx;

import java.time.Duration;
import java.time.LocalDateTime;

import static java.time.LocalTime.ofSecondOfDay;

public abstract class Timer extends LogProvider {

    protected final LocalDateTime startTime;

    protected Timer() {
        this.startTime = LocalDateTime.now();
    }

    protected final Duration get() {
        return Duration.between(this.startTime, LocalDateTime.now());
    }

    protected void log(String context, Object uid) {
        this.getLogger().info("{} execution #[{}] duration {}", context, uid, ofSecondOfDay(this.get().toSeconds()));
    }

}
