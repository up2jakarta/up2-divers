package io.github.up2jakarta.csv.impl;

import io.github.up2jakarta.csv.misc.SimpleKeyCreator;
import org.springframework.stereotype.Component;

@Component
public class SimpleCreator extends SimpleKeyCreator<InputRowEntity, DataId, SimpleErrorEntity> {

    public SimpleCreator() {
        super(SimpleErrorEntity::new);
    }

}