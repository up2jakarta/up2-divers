package io.github.up2jakarta.test.core.misc.jpa;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2EnableJPA;
import io.github.up2jakarta.lov.SeverityType;
import io.github.up2jakarta.test.core.misc.lov.TestCodeList;
import io.github.up2jakarta.test.core.misc.lov.TestCodeListConverter;
import jakarta.persistence.*;

@Entity
@Up2EnableJPA
@Table(name = "TU_SEGMENT")
public class Test2Bean implements Segment {

    public static final String XML_001 = "JPA-001";
    public static final String XML_002 = "JPA-002";
    public static final String XML_003 = "JPA-003";

    @Position(0)
    @Error(value = XML_001, level = SeverityType.WARNING)
    @Enumerated
    @Transient
    private XML1Enum enum1;

    @Position(1)
    @Error(value = XML_002, level = SeverityType.WARNING)
    @Enumerated
    @Transient
    private XML2Enum enum2;

    @Position(2)
    @Error(value = XML_003, level = SeverityType.WARNING)
    @Convert(converter = TestCodeListConverter.class)
    @Transient
    private TestCodeList adapter;

}
