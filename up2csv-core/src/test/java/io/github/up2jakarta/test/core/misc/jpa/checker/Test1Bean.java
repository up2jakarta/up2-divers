package io.github.up2jakarta.test.core.misc.jpa.checker;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2EnableJPA;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.test.core.misc.jpa.XML1Enum;
import io.github.up2jakarta.test.core.misc.jpa.XML2Enum;
import io.github.up2jakarta.test.core.misc.lov.TestCodeList;
import io.github.up2jakarta.test.core.misc.lov.TestCodeListConverter;
import jakarta.persistence.*;

@Entity
@Up2EnableJPA
@Table(name = "TU_SEGMENT")
public class Test1Bean implements Segment {

    public static final String JPA_XXX = "JPA-XXX";

    @Position(0)
    @Enumerated(EnumType.STRING)
    @Transient
    private XML1Enum enum1;

    @Position(1)
    @Enumerated(EnumType.STRING)
    @Transient
    private XML2Enum enum2;

    @Position(2)
    @Enumerated(EnumType.ORDINAL)
    @Transient
    private XML1Enum enum3;

    @Position(3)
    @Enumerated(EnumType.ORDINAL)
    @Transient
    private XML2Enum enum4;

    @Position(4)
    @Convert(converter = TestCodeListConverter.class)
    @Transient
    private TestCodeList adapter;

}
