package io.github.up2jakarta.test.core.misc.map;

import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Truncated;
import io.github.up2jakarta.csv.cfg.Up2EnableXML;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.lov.SeverityType;
import io.github.up2jakarta.test.core.misc.ext.Dummy1;
import io.github.up2jakarta.test.core.misc.lov.CountryCodeType;
import io.github.up2jakarta.test.core.misc.lov.CurrencyCodeType;
import jakarta.validation.Valid;
import jakarta.xml.bind.annotation.XmlType;

@Truncated(1)
@Valid
@Up2EnableXML
@XmlType
public class Test1Exception implements Segment {

    @Position(0)
    @Error(value = "W001", level = SeverityType.WARNING)
    private CountryCodeType country;

    @Position(1)
    @Error(value = "E002", level = SeverityType.ERROR)
    private CurrencyCodeType currency;

    @Position(2)
    @Dummy1
    @Error(value = "F003", level = SeverityType.FATAL)
    private String dummy;

}
