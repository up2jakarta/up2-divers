package io.github.up2jakarta.csv.impl;

import io.github.up2jakarta.csv.input.InputRepository;

public class SimpleHandler extends io.github.up2jakarta.csv.misc.SimpleHandler<InputRowEntity, DataId, SimpleErrorEntity> {

    public SimpleHandler(InputRowEntity row, SimpleCreator creator, InputRepository<InputRowEntity> repository) {
        super(row, creator, repository);
    }

    public SimpleHandler(InputRowEntity row, SimpleCreator creator) {
        this(row, creator, r -> 0);
    }

}
