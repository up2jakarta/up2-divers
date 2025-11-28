package io.github.up2jakarta.csv.core.misc.jpa.checker;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2EnableJPA;

@Up2EnableJPA
public class Test5Offset extends Test4Offset {

    public final @Position(5) String key3;

    public Test5Offset(String key1, String key2, String key3, SFragment fragment) {
        super(key1, key2, fragment);
        this.key3 = key3;
    }

}
