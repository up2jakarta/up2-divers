package io.github.up2jakarta.csv.test;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import io.github.up2jakarta.csv.test.input.InputRowEntity;
import io.github.up2jakarta.csv.test.input.SegmentType;
import jakarta.validation.MessageInterpolator;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.junit.jupiter.api.function.Executable;

import java.util.List;
import java.util.Locale;
import java.util.Set;

public final class Tests {

    public static final String TU_V_001 = "TU-V001";

    private Tests() {
    }

    public static InputRowEntity create(SegmentType type, String... columns) {
        final InputRowEntity entity = new InputRowEntity();
        entity.setColumns(columns);
        entity.setType(type);
        return entity;
    }

    public static MessageInterpolator messageInterpolator() {
        return new ParameterMessageInterpolator(
                Set.of(Locale.ENGLISH, Locale.FRENCH),
                Locale.ENGLISH,
                context -> Locale.ENGLISH,
                false
        );
    }

    public static List<ILoggingEvent> hack(org.slf4j.Logger log, Executable executable) throws Throwable {
        final ListAppender<ILoggingEvent> appender = new ListAppender<>();
        final Logger logger = (Logger) log;
        appender.start();
        logger.addAppender(appender);
        executable.execute();
        appender.stop();
        logger.detachAppender(appender);
        return appender.list;
    }

}
