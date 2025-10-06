package io.github.up2jakarta.csv.test.bean.jpa.checker.base;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2EnableJPA;
import io.github.up2jakarta.csv.cfg.Up2Number;
import io.github.up2jakarta.csv.test.ParsedEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Up2EnableJPA
@Table(name = "1_TESTS")
public class Test6Entity extends ParsedEntity<Integer> {

    @Position(0)
    @Up2Number
    private Integer key;

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }
}
