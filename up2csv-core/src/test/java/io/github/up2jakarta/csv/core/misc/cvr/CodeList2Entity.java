package io.github.up2jakarta.csv.core.misc.cvr;

import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2CodeList;
import io.github.up2jakarta.csv.core.misc.ParsedEntity;
import io.github.up2jakarta.csv.core.misc.clv.Test2CodeList;
import io.github.up2jakarta.xml.api.SeverityType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import static io.github.up2jakarta.csv.core.misc.clv.CountryConverter.ISO_3166;

@Entity
@Table(name = "TB_TESTS")
public class CodeList2Entity extends ParsedEntity<Test2CodeList> {

    @Position(0)
    @Column(name = "TU_KEY", length = 8)
    @Error(value = ISO_3166, severity = SeverityType.FATAL)
    @Up2CodeList
    private Test2CodeList key;

    public Test2CodeList getKey() {
        return key;
    }

    public void setKey(Test2CodeList key) {
        this.key = key;
    }
}
