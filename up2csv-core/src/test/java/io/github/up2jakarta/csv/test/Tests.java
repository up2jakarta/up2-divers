package io.github.up2jakarta.csv.test;

import io.github.up2jakarta.csv.test.persistence.InputRowEntity;
import io.github.up2jakarta.csv.test.persistence.SegmentType;
import jakarta.validation.MessageInterpolator;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import java.util.Locale;
import java.util.Set;

public final class Tests {

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

}
