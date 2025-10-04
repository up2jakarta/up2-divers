package io.github.up2jakarta.csv.test.bean.mapper;

import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Truncated;
import io.github.up2jakarta.csv.cfg.Up2EnableXML;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.csv.test.codelist.CountryCodeType;
import io.github.up2jakarta.csv.test.codelist.CurrencyCodeType;
import io.github.up2jakarta.csv.test.ext.Dummy1;
import io.github.up2jakarta.xml.api.SeverityType;
import jakarta.validation.Valid;
import jakarta.xml.bind.annotation.XmlType;

@Truncated(1)
@SuppressWarnings("unused")
@Valid
@Up2EnableXML
@XmlType
public class Test1Exception implements Segment {

    @Position(0)
    @Error(value = "W001", severity = SeverityType.WARNING)
    private CountryCodeType country;

    @Position(1)
    @Error(value = "E002", severity = SeverityType.ERROR)
    private CurrencyCodeType currency;

    @Position(2)
    @Dummy1
    @Error(value = "F003", severity = SeverityType.FATAL)
    private String dummy;

    public CountryCodeType getCountry() {
        return country;
    }

    public void setCountry(CountryCodeType country) {
        this.country = country;
    }

    public CurrencyCodeType getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyCodeType currency) {
        this.currency = currency;
    }

    public String getDummy() {
        return dummy;
    }

    public void setDummy(String dummy) {
        this.dummy = dummy;
    }

}
