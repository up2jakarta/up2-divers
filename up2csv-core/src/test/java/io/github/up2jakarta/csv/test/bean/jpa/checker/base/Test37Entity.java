package io.github.up2jakarta.csv.test.bean.jpa.checker.base;

import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.annotation.Up2EnableJPA;
import io.github.up2jakarta.csv.misc.Prefix;
import io.github.up2jakarta.csv.test.input.ParsedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;

@Entity
@Up2EnableJPA
@Prefix("TU_")
@Table(name = "TB_TESTS")
@SuppressWarnings("unused")
public class Test37Entity extends ParsedEntity<Byte> {

    @Position(0)
    @Digits(fraction = 0, integer = 3)
    @Column(name = "TU_KEY")
    private Byte key;

    public Byte getKey() {
        return key;
    }

    public void setKey(Byte key) {
        this.key = key;
    }
}
