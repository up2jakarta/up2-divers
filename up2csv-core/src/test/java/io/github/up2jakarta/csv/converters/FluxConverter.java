package io.github.up2jakarta.csv.converters;

import io.github.up2jakarta.csv.entities.FluxType;
import io.github.up2jakarta.csv.misc.CodeListConverter;
import org.springframework.stereotype.Component;

@Component
public class FluxConverter extends CodeListConverter<FluxType> {

    FluxConverter() {
        super(FluxType.class, "INF-0001");
    }

}
