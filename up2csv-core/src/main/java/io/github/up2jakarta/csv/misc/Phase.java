package io.github.up2jakarta.csv.misc;

import io.github.up2jakarta.csv.core.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum Phase {

    MAPPING(LoggerFactory.getLogger(Mapper.class));

    final Logger logger;

    Phase(Logger logger) {
        this.logger = logger;
    }

    public Logger getLogger() {
        return logger;
    }

}
