package io.github.up2jakarta.csv.test.bean.jpa.checker.base;

import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.annotation.Up2Decimal;
import io.github.up2jakarta.csv.annotation.Up2EnableJPA;
import io.github.up2jakarta.csv.impl.ParsedEntity;
import io.github.up2jakarta.csv.misc.Prefix;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Up2EnableJPA
@Prefix("TU_")
@Table(name = "TB_TESTS")
@SuppressWarnings("unused")
public class Test40Entity extends ParsedEntity<Double> {

    @Position(0)
    @Up2Decimal(4)
    @Column(name = "TU_KEY", scale = 2)
    private Double key;

    public Double getKey() {
        return key;
    }

    public void setKey(Double key) {
        this.key = key;
    }
}
