package io.github.up2jakarta.csv.converters;

import io.github.up2jakarta.csv.extension.SeverityType;
import io.github.up2jakarta.csv.misc.CodeListConverter;
import org.springframework.stereotype.Component;

@Component
public class SeverityConverter extends CodeListConverter<SeverityType> {

    SeverityConverter() {
        super(SeverityType.class, "INF-0003");
    }

}
