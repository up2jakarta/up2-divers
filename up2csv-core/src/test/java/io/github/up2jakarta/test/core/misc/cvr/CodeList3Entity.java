package io.github.up2jakarta.test.core.misc.cvr;

import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2CodeList;
import io.github.up2jakarta.lov.SeverityType;
import io.github.up2jakarta.test.core.misc.ParsedEntity;
import io.github.up2jakarta.test.core.misc.lov.Test3CodeList;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import static io.github.up2jakarta.test.core.misc.lov.CountryConverter.ISO_3166;

@Entity
@Table(name = "TB_TESTS")
public class CodeList3Entity extends ParsedEntity<Test3CodeList> {

    @Position(0)
    @Column(name = "TU_KEY", length = 8)
    @Error(value = ISO_3166, level = SeverityType.FATAL)
    @Up2CodeList
    private Test3CodeList key;

    public Test3CodeList getKey() {
        return key;
    }

    public void setKey(Test3CodeList key) {
        this.key = key;
    }
}
