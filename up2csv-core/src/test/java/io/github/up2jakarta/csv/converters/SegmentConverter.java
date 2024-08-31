package io.github.up2jakarta.csv.converters;

import io.github.up2jakarta.csv.misc.CodeListConverter;
import io.github.up2jakarta.csv.test.input.SegmentType;
import org.springframework.stereotype.Component;

@Component
public class SegmentConverter extends CodeListConverter<SegmentType> {

    SegmentConverter() {
        super(SegmentType.class, "INF-0002");
    }

}
