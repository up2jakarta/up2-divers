package io.github.up2jakarta.csv.impl;

import io.github.up2jakarta.csv.misc.SimpleKeyCreator;
import org.springframework.stereotype.Component;

@Component
public class SimpleCreator extends SimpleKeyCreator<InputRowEntity, BusinessType, SimpleErrorEntity> {

    public SimpleCreator() {
        super(SimpleErrorEntity::new);
    }

}