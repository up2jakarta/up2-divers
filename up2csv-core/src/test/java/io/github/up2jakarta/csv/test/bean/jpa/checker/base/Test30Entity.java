package io.github.up2jakarta.csv.test.bean.jpa.checker.base;

import io.github.up2jakarta.csv.annotation.Position;
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
public class Test30Entity extends ParsedEntity<Short> {

    @Position(0)
    @Column(name = "TU_KEY", precision = 5, scale = 2)
    private Short key;

    public Short getKey() {
        return key;
    }

    public void setKey(Short key) {
        this.key = key;
    }
}
