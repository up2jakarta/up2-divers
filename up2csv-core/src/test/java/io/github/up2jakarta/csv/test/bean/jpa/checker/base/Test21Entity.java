package io.github.up2jakarta.csv.test.bean.jpa.checker.base;

import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.annotation.Up2EnableJPA;
import io.github.up2jakarta.csv.misc.Prefix;
import io.github.up2jakarta.csv.test.input.ParsedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Up2EnableJPA
@Prefix("TU_")
@Table(name = "TB_TESTS")
@SuppressWarnings("unused")
public class Test21Entity extends ParsedEntity<Integer> {

    @Position(0)
    @Column(name = "TU_KEY", precision = 10, nullable = false)
    private Integer key;

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }
}
