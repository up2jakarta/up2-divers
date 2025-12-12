package io.github.up2jakarta.test.core.misc.cvr;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2EnableJPA;
import io.github.up2jakarta.csv.data.Segment;
import jakarta.persistence.Convert;
import jakarta.persistence.Enumerated;

@Up2EnableJPA
public class Test8Resolver implements Segment {

    @Position(0)
    @Enumerated
    @Convert(converter = NumberConverter.class)
    public Integer test;

}
