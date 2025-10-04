package io.github.up2jakarta.csv.test.bean.converter;

import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2CodeList;
import io.github.up2jakarta.csv.impl.ParsedEntity;
import io.github.up2jakarta.csv.test.codelist.Test3CodeList;
import io.github.up2jakarta.xml.api.SeverityType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import static io.github.up2jakarta.csv.test.codelist.CountryConverter.ISO_3166;

@Entity
@Table(name = "TB_TESTS")
public class CodeList3Entity extends ParsedEntity<Test3CodeList> {

    @Position(0)
    @Column(name = "TU_KEY", length = 8)
    @Error(value = ISO_3166, severity = SeverityType.FATAL)
    @Up2CodeList
    private Test3CodeList key;

    public Test3CodeList getKey() {
        return key;
    }

    public void setKey(Test3CodeList key) {
        this.key = key;
    }
}
