package io.github.up2jakarta.test.core.misc.jpa.checker.base;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2EnableJPA;
import io.github.up2jakarta.csv.cfg.Up2Number;
import io.github.up2jakarta.csv.core.ext.Up2Prefix;
import io.github.up2jakarta.test.core.misc.ParsedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Up2EnableJPA
@Up2Prefix("TU_")
@Table(name = "TB_TESTS")
public class Test13Entity extends ParsedEntity<Integer> {

    @Position(0)
    @Up2Number
    @Column(name = "TU_ KEY", precision = 10)
    private Integer key;

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }
}
