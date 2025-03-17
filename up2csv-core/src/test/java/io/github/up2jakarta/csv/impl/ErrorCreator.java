package io.github.up2jakarta.csv.impl;

import io.github.up2jakarta.csv.misc.CompositeKeyCreator;
import org.springframework.stereotype.Component;

@Component
public class ErrorCreator extends CompositeKeyCreator<InputRowEntity, InputErrorEntity.PKey, DataId, InputErrorEntity> {

    public ErrorCreator() {
        super(InputErrorEntity::new, InputErrorEntity.PKey::new);
    }

}