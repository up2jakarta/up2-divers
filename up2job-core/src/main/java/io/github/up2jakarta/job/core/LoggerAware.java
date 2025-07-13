package io.github.up2jakarta.job.core;

import org.slf4j.Logger;

@FunctionalInterface
public interface LoggerAware {

    Logger getLogger();

}
