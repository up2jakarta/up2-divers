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
public class Test33Entity extends ParsedEntity<Byte> {

    @Position(0)
    @Column(name = "TU_KEY", precision = 3, scale = 2)
    private Byte key;

    public Byte getKey() {
        return key;
    }

    public void setKey(Byte key) {
        this.key = key;
    }
}
