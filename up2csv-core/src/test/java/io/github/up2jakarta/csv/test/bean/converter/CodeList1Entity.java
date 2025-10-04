package io.github.up2jakarta.csv.test.bean.converter;

import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2CodeList;
import io.github.up2jakarta.csv.impl.ParsedEntity;
import io.github.up2jakarta.csv.test.codelist.Test1CodeList;
import io.github.up2jakarta.xml.api.SeverityType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import static io.github.up2jakarta.csv.test.codelist.CountryConverter.ISO_3166;

@Entity
@Table(name = "TB_TESTS")
public class CodeList1Entity extends ParsedEntity<Test1CodeList> {

    @Position(0)
    @Column(name = "TU_KEY", length = 8)
    @Error(value = ISO_3166, severity = SeverityType.FATAL)
    @Up2CodeList
    private Test1CodeList key;

    public Test1CodeList getKey() {
        return key;
    }

    public void setKey(Test1CodeList key) {
        this.key = key;
    }
}
