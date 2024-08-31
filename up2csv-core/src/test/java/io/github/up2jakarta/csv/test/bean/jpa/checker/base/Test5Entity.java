package io.github.up2jakarta.csv.test.bean.jpa.checker.base;

import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.annotation.Up2EnableJPA;
import io.github.up2jakarta.csv.annotation.Up2Number;
import io.github.up2jakarta.csv.test.input.ParsedEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Up2EnableJPA
@Table(name = "TU_TESTS")
public class Test5Entity extends ParsedEntity<Integer> {

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
