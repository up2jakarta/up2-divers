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
public class Test33Entity extends ParsedEntity<Byte> {

    @Position(0)
    @Column(name = "TU_KEY", precision = 3, scale = 2)
    private @Up2Number Byte key;

    public Byte getKey() {
        return key;
    }

    public void setKey(Byte key) {
        this.key = key;
    }
}
