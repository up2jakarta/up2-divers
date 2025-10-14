package io.github.up2jakarta.csv.core.misc.jpa.checker.base;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Decimal;
import io.github.up2jakarta.csv.cfg.Up2EnableJPA;
import io.github.up2jakarta.csv.core.ext.Prefix;
import io.github.up2jakarta.csv.core.misc.ParsedEntity;
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
