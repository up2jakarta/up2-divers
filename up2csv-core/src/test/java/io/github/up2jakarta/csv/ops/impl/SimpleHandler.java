package io.github.up2jakarta.csv.ops.impl;

import io.github.up2jakarta.csv.impl.FullCollector;

public class SimpleHandler extends FullCollector<InputRowEntity, GroupType, InputErrorEntity> {

    public SimpleHandler(InputRowEntity row, SimpleCreator creator) {
        super(row, creator, (r) -> 0);
    }

}
