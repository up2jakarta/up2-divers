package io.github.up2jakarta.csv.core.misc.cvr;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2EnableJPA;
import io.github.up2jakarta.csv.cfg.Up2EnableXML;
import io.github.up2jakarta.csv.data.Segment;
import jakarta.persistence.Convert;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Up2EnableJPA
@Up2EnableXML
public class Test7Resolver implements Segment {

    @Position(0)
    @Convert(converter = NumberConverter.class)
    @XmlJavaTypeAdapter(NumberConverter.class)
    public Number test;

}
