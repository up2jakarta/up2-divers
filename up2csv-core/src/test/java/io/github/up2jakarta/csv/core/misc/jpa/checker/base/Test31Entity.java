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
public class Test31Entity extends ParsedEntity<Short> {

    @Position(0)
    @Up2Decimal(0)
    @Column(name = "TU_KEY", precision = 5)
    private Short key;

    public Short getKey() {
        return key;
    }

    public void setKey(Short key) {
        this.key = key;
    }
}
